import java.awt.Color
import java.awt.image.BufferedImage
import java.io.BufferedReader
import java.io.File
import java.lang.Exception
import java.time.LocalTime
import java.util.Collections.max
import javax.imageio.ImageIO

var rGBCube : BissectedCube = getRGBCube("./src/main/resources/dataColors.txt")

var notDone = true

var tvThickness = 2

fun main(args: Array<String>) {
    println("\tWORLD MACHINE COLOR FILTER")

    var command : String?

    while(notDone) {
        print("> ")
        command = readlnOrNull()
        if (command != null) {
            val arguments = command.split(" ".toRegex()).toTypedArray()

            if (arguments.isNotEmpty()) {
                treatCommand(arguments)
            }else{
                println("Invalid command")
            }
        }
    }
}

fun treatCommand(args : Array<String>) {
    val commandHead = args[0]

    when (commandHead) {
        "help" -> helpCommand()
        //"description" -> println("TODO")
        "apply" ->  {
                        val inputPath = args[1]
                        val outputPath =  if (args.size >= 3) {
                             args[2]
                        }else{
                            inputPath.substring(0,inputPath.length-4) + "_WorldMachineColorFilter.png"
                        }
                        applyCommand(inputPath, outputPath)
                    }
        "stt" -> {
            try {
                val newThickness = (args[1]).toInt()
                if (newThickness >= 0) {
                    tvThickness = newThickness
                    println("TV thickness updated to $tvThickness")
                }else{
                    println("TV thickness must be positive.")
                }
            }catch(e:Exception) {
                println("An error has occurred : $e")
            }
        }
        "credits" -> creditsCommand()
        "quit" -> quitCommand()
        else -> println("Invalid command")
    }
}

fun applyCommand(pathIn : String, pathOut: String) {
    try {
        applyOnImage(pathIn, pathOut, rGBCube)
        println("Image successfully created")
    }catch (e:Exception) {
        println("An error has occurred : $e")
    }
}

fun helpCommand() {
    println("help\n\tShows this list.\n")
    //println("description\n\tShows a description of the application\n")
    println("apply [input image path] [output image path]\n\tApply the World Machine color filter on an image\n\tIf no output path is given, the file will be created in the same directory as the input.\n")
    println("stt [integer] \n\tSets the thickness of the TV effect, in number of pixels.\n\tFor no effect, input 0.\n")
    println("credits\n\tShows the app's credits.\n")
    println("quit\n\tClose the app.")
}

fun creditsCommand() {
    println("\tWORLD MACHINE COLOR FILTER")
    println("Creator\t\t\tMaxBuster")
    println("Designer\t\tMaxBuster")
    println("Developer\t\tMaxBuster")
    println("OneShot\t\t\tFutureCat")
    println("Source images\tFutureCat")
    println("\nProject repository : https://github.com/MaxBuster380/WorldMachine-Color-Filter")
}

fun quitCommand() {
    notDone = false
}

// -----------------------------------   TV EFFECT  -----------------------------------

fun getTVColor(inColor:Color):Int {
    val red = inColor.red; val green = inColor.green; val blue = inColor.blue

    val darkeningFactor = (5f/255f)

    val hsb = Color.RGBtoHSB(red,green,blue,null)
    val h = hsb[0]; val s = hsb[1]; val b = max(listOf((hsb[2]-darkeningFactor),0f))

    return Color.HSBtoRGB(h,s,b)
}

fun applyTVEffect(source:BufferedImage,thickness:Int) {
    var y = 0
    var targetY = thickness
    while(y < source.height) {

        for(x in 0 until source.width) {
            val tvColorInt = getTVColor(Color(source.getRGB(x,y)))
            source.setRGB(x,y,tvColorInt)
        }

        y += 1
        if (y == targetY) {
            y += thickness
            targetY += thickness * 2
        }
    }
}

// ----------------------------------- APPLY FILTER -----------------------------------
fun applyOnImage(pathIn : String, pathOut: String, rGBCube : BissectedCube) {
    val inputImage: BufferedImage = ImageIO.read(File(pathIn))

    val imageWidth = inputImage.width
    val imageHeight = inputImage.height

    val outputImage = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)

    // Set up UnionFind field
    val unionFindForrest : MutableList<UnionFind<TwoDVector>> = mutableListOf()
    for(x in 0 until imageWidth) {
        for(y in 0 until imageHeight) {
            unionFindForrest += UnionFind(TwoDVector(x,y))
        }
    }

    // Form zones
    for(i in unionFindForrest.indices) {
        val elt1 = unionFindForrest[i]

        if (i+1 < unionFindForrest.size) {
            val elt2 = unionFindForrest[i+1]
            if (colorOf(inputImage, elt1) == colorOf(inputImage, elt2)) {
                elt1.union(elt2)
            }
        }

        if (i+imageWidth < unionFindForrest.size) {
            val elt2 = unionFindForrest[i+imageWidth]
            if (colorOf(inputImage, elt1) == colorOf(inputImage, elt2)) {
                elt1.union(elt2)
            }
        }
    }

    val progressUpdate = 30000
    var progressIterator = 0
    var allProgress = 0
    // Calculate the color of the representatives
    for(elt in unionFindForrest) {
        if (elt.isRepresentative()) {
            val inputColor = colorOf(inputImage,elt)
            val inputVector = intColorToVector(inputColor)

            val outputVector = applyFilter(rGBCube, inputVector)
            val outputColor = vectorToIntColor(outputVector)

            setColorOf(outputImage, elt, outputColor)
        }
        progressIterator += 1
        allProgress += 1
        if (progressIterator >= progressUpdate) {
            progressIterator = 0
            println("Update \t " +
                    "${LocalTime.now().toString().substring(0,10)} \t " +
                    "${(allProgress*100.0/unionFindForrest.size).toString().substring(0,5)}% done.")
        }
    }

    // Assign the color of the representative
    for(elt in unionFindForrest) {
        setColorOf(outputImage, elt, colorOf(outputImage, elt.find()))
    }

    applyTVEffect(outputImage,tvThickness)

    val outputFile = File(pathOut)
    ImageIO.write(outputImage, "png", outputFile)
}
fun colorOf(sourceImage : BufferedImage, element : UnionFind<TwoDVector>):Int {
    val vector = element.getValue()
    val x = vector.getComp(0)
    val y = vector.getComp(1)
    return sourceImage.getRGB(x,y)
}
fun setColorOf(destImage : BufferedImage, element : UnionFind<TwoDVector>, color : Int) {
    val vector = element.getValue()
    val x = vector.getComp(0)
    val y = vector.getComp(1)
    destImage.setRGB(x,y,color)
}
fun vectorToIntColor(vector : ThreeDVector):Int {
    val comps = vector.getAllComp()
    var res = 255
    for(comp in comps) {
        res = (res shl 8) or (comp and 255)
    }
    return res
}
fun intColorToVector(color : Int):ThreeDVector {
    val red = (color and (255 shl 16)) shr 16
    val green = (color and (255 shl 8)) shr 8
    val blue = (color and 255)
    return ThreeDVector(red, green, blue)
}
fun applyFilter(rGBCube : BissectedCube, target:ThreeDVector):ThreeDVector {
    val tetra = rGBCube.getSectionOf(target) ?: throw Exception("(applyFilter) No section found for $target")

    val barycentricCoords = tetra.getBarycentricCoordinates(target)
    val vertices = tetra.getVertices() as List<RelatedVector>

    val outputTetra = Tetrahedron(
        vertices[0].getOutputVector(),
        vertices[1].getOutputVector(),
        vertices[2].getOutputVector(),
        vertices[3].getOutputVector()
    )

    return outputTetra.translate(barycentricCoords)
}
fun getRGBCube(path:String) : BissectedCube {
    val fileColors = loadDataColors(path)
    removeDuplicateColors(fileColors)

    val black = find(fileColors, 0, 0, 0)
    val red =  find(fileColors, 255, 0, 0)
    val green = find(fileColors, 0, 255, 0)
    val blue = find(fileColors, 0, 0, 255)
    val yellow = find(fileColors, 255, 255, 0)
    val magenta = find(fileColors, 255, 0, 255)
    val cyan = find(fileColors, 0, 255, 255)
    val white = find(fileColors, 255, 255, 255)

    if (black == null)      { throw Exception("(Main.getRGBCube) Black not found") }
    if (red == null)        { throw Exception("(Main.getRGBCube) Red not found") }
    if (green == null)      { throw Exception("(Main.getRGBCube) Green not found") }
    if (blue == null)       { throw Exception("(Main.getRGBCube) Blue not found") }
    if (yellow == null)     { throw Exception("(Main.getRGBCube) Yellow not found") }
    if (magenta == null)    { throw Exception("(Main.getRGBCube) Magenta not found") }
    if (cyan == null)       { throw Exception("(Main.getRGBCube) Cyan not found") }
    if (white == null)      { throw Exception("(Main.getRGBCube) White not found") }

    val rGBCube = BissectedCube(black, red, green, blue, yellow, magenta, cyan, white)

    fileColors.remove(black)
    fileColors.remove(red); fileColors.remove(green); fileColors.remove(blue)
    fileColors.remove(yellow); fileColors.remove(magenta); fileColors.remove(cyan)
    fileColors.remove(white)

    for (color in fileColors) {
        rGBCube.bissectOn(color)
    }

    return rGBCube
}
fun removeDuplicateColors(list : MutableList<RelatedVector>) {
    var i = 0
    while (i < list.size) {
        var j = list.size-1
        while(j > i && i < list.size) {
            //println("i=$i, j=$j, size=${list.size}")
            if (list[i].equals(list[j])) {
                list.removeAt(j)
            }

            j -= 1
        }

        i += 1
    }
}
fun loadDataColors(path:String): MutableList<RelatedVector> {
    val resList = mutableListOf<RelatedVector>()

    val bufferedReader: BufferedReader = File(path).bufferedReader()

    for (line in bufferedReader.lines()) {
        resList += createDataColor(line)
    }

    bufferedReader.close()

    return resList
}
fun find(source: List<ThreeDVector>, x: Int, y: Int, z: Int): ThreeDVector? {
    var res: ThreeDVector? = null
    val target = ThreeDVector(x, y, z)

    var i = 0
    while (i < source.size && res == null) {
        if (source[i].equals(target)) {
            res = source[i]
        }

        i += 1
    }

    return res
}
fun createDataColor(text: String): RelatedVector {
    val match = Regex("^(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s*$").find(text)
        ?: throw Exception("(createDataColor) text does not follow the valid format")

    val x1 = (match.groupValues[1]).toInt()
    val y1 = (match.groupValues[2]).toInt()
    val z1 = (match.groupValues[3]).toInt()
    val x2 = (match.groupValues[4]).toInt()
    val y2 = (match.groupValues[5]).toInt()
    val z2 = (match.groupValues[6]).toInt()

    return RelatedVector(x1, y1, z1, x2, y2, z2)
}

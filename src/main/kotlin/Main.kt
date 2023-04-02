import java.awt.image.BufferedImage
import java.io.BufferedReader
import java.io.File
import java.lang.Exception
import java.time.LocalTime
import javax.imageio.ImageIO
import kotlin.math.round

fun main(args: Array<String>) {
    val pathIn = "./src/main/resources/images/artwork.png"
    val pathOut = "./src/main/resources/output.png"
    val rGBCube = getRGBCube("./src/main/resources/dataColors.txt")

    applyOnImage(pathIn, pathOut, rGBCube)
}

fun applyOnImage(pathIn : String, pathOut: String, rGBCube : BissectedCube) {
    val image: BufferedImage = ImageIO.read(File(pathIn))

    val imageWidth = image.width
    val imageHeight = image.height

    var nbPixelsTreated = 0
    val nbPixels = imageWidth*imageHeight
    val displayStep = nbPixels/100
    var nextDisplay = 0

    val outputImage = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)

    for(x in 0 until imageWidth) {
        for(y in 0 until imageHeight) {
            val inputColor = image.getRGB(x,y)
            val inputVector = intColorToVector(inputColor)

            val outputVector = applyFilter(rGBCube, inputVector)
            val outputColor = vectorToIntColor(outputVector)

            outputImage.setRGB(x,y,outputColor)

            nbPixelsTreated += 1
            if (nbPixelsTreated > nextDisplay) {
                nextDisplay += displayStep
                val progress = round(nbPixelsTreated.toDouble()*100.0/nbPixels.toDouble()).toInt()
                println("${LocalTime.now()} : $progress/100")
            }
        }
    }

    val outputFile = File(pathOut)
    ImageIO.write(outputImage, "png", outputFile)
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
        //println("$color, ${rGBCube.getNbSections()}")
    }

    rGBCube.sortSections()

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
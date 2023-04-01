import java.io.BufferedReader
import java.io.File
import java.lang.Exception


val resourcePath = "${System.getProperty("user.dir")}/src/main/resources"
fun main(args: Array<String>) {
    println(loadDataColors())
}

fun loadDataColors():List<RelatedVector> {
    val resList = mutableListOf<RelatedVector>()

    val bufferedReader: BufferedReader = File("$resourcePath/dataColors.txt").bufferedReader()

    for (line in bufferedReader.lines()) {
        resList += createDataColor(line)
    }

    bufferedReader.close()

    return resList
}

fun createDataColor(text : String) : RelatedVector {
    val matchTest = Regex("^(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s*$").find(text)
    if (matchTest == null) {
        throw Exception("(createDataColor) text does not follow the valid format")
    }

    val match = matchTest!!
    val x1 = (match.groupValues[1]).toInt()
    val y1 = (match.groupValues[2]).toInt()
    val z1 = (match.groupValues[3]).toInt()
    val x2 = (match.groupValues[4]).toInt()
    val y2 = (match.groupValues[5]).toInt()
    val z2 = (match.groupValues[6]).toInt()

    return RelatedVector(x1,y1,z1,x2,y2,z2)
}
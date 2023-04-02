import java.lang.Exception
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.sign

/**
 * @author MaxBuster
 */
class Tetrahedron {

    // INSTANCE ATTRIBUTES
    private val vertices : MutableList<ThreeDVector>
    private val volume : Int

    // CONSTRUCTORS
    constructor(v0 : ThreeDVector, v1 : ThreeDVector, v2 : ThreeDVector, v3 : ThreeDVector) {
        vertices = mutableListOf(v0,v1,v2,v3)
        volume = calculateVolume()
    }

    // INSTANCE METHODS

    /**
     * Returns the point generated with the given barycentric coordinates
     */
    fun translate(barycentricCoordinates : List<Double>):ThreeDVector {
        if (barycentricCoordinates.size != 4) {
            throw Exception("(Tetrahedron.translate) barycentricCoordinates's size is ${barycentricCoordinates.size}, where 4 is expected")
        }

        var x = 0.0; var y = 0.0; var z = 0.0

        for (i in vertices.indices) {
            x += vertices[i].getComp(0) * barycentricCoordinates[i]
            y += vertices[i].getComp(1) * barycentricCoordinates[i]
            z += vertices[i].getComp(2) * barycentricCoordinates[i]
        }

        return ThreeDVector(round(x).toInt(), round(y).toInt(), round(z).toInt())
    }

    /**
     * Checks if the given point is inside the tetrahedron
     */
    fun contains(target : ThreeDVector) : Boolean {
        val rawBaryCoords = getRawBarycentricCoordinates(target)

        var volumeSum = 0
        for(coord in rawBaryCoords) {
            volumeSum += coord
        }

        return volume.toInt() == volumeSum
    }

    /**
     * Creates 4 different tetrahedrons that all have the given point as a vertice, the other vertices are vertices of the called tetrahedron.
     *  (clientVector, vertice1, vertice2, vertice3),
     *  (clientVector, vertice0, vertice2, vertice3),
     *  (clientVector, vertice0, vertice1, vertice3),
     *  (clientVector, vertice0, vertice1, vertice2)
     */
    fun cut(clientVector:ThreeDVector):List<Tetrahedron> {
        val tV0 = Tetrahedron(clientVector, vertices[1], vertices[2], vertices[3])
        val tV1 = Tetrahedron(clientVector, vertices[0], vertices[2], vertices[3])
        val tV2 = Tetrahedron(clientVector, vertices[0], vertices[1], vertices[3])
        val tV3 = Tetrahedron(clientVector, vertices[0], vertices[1], vertices[2])

        return listOf(tV0, tV1, tV2, tV3)
    }

    /**
     * Returns the barycentric coordinates of a given point based on the tetrahedron
     */
    fun getBarycentricCoordinates(clientVector : ThreeDVector):List<Double> {
        val cutPieces = cut(clientVector)

        val coordinates : MutableList<Double> = mutableListOf()
        for(i in cutPieces.indices) {
            coordinates += (cutPieces[i].volume.toDouble() / this.volume.toDouble())
        }

        return coordinates
    }

    fun getVertices():List<ThreeDVector> {
        return vertices
    }

    fun getVolume():Int {
        return volume
    }

    // PRIVATE INSTANCE METHODS

    /**
     * Returns the barycentric coordinates of a given point based on the tetrahedron
     */
    private fun getRawBarycentricCoordinates(clientVector : ThreeDVector):List<Int> {
        val cutPieces = cut(clientVector)

        val coordinates : MutableList<Int> = mutableListOf()
        for(i in cutPieces.indices) {
            coordinates += (cutPieces[i].volume.toInt())
        }

        return coordinates
    }

    /**
     * Calculates the volume of the tetrahedron
     */
    private fun calculateVolume() : Int {
        //https://www.had2know.org/academics/tetrahedron-volume-4-vertices.html

        val vectorMatrix = IntMatrix(4,4)

        for(i in vertices.indices) {
            val point = vertices[i]
            vectorMatrix.set(0,i,point.getComp(0))
            vectorMatrix.set(1,i,point.getComp(1))
            vectorMatrix.set(2,i,point.getComp(2))
            vectorMatrix.set(3,i,1)
        }

        // The correct volume formula would be "abs(vectorMatrix.det()) / 6.0"
        // However, a constant 1/6 factor adds inprecision, and removing it just means assuming every tetrahedron is 6x bigger, which in most cases isn't a problem
        return abs(vectorMatrix.det())// / 6.0
    }
}
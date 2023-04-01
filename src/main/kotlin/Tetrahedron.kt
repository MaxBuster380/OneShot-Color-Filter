import kotlin.math.abs
import kotlin.math.sign

/**
 * @author MaxBuster
 */
class Tetrahedron {

    // INSTANCE ATTRIBUTES
    private val vertices : MutableList<ThreeDVector>
    private val volume : Double

    // CONSTRUCTORS
    constructor(v0 : ThreeDVector, v1 : ThreeDVector, v2 : ThreeDVector, v3 : ThreeDVector) {
        vertices = mutableListOf(v0,v1,v2,v3)
        volume = calculateVolume()
    }

    // INSTANCE METHODS

    /**
     * Checks if the given point is inside the tetrahedron
     */
    fun contains(target : ThreeDVector) : Boolean {
        //https://stackoverflow.com/questions/25179693/how-to-check-whether-the-point-is-in-the-tetrahedron-or-not

        val v1 = vertices[0]
        val v2 = vertices[1]
        val v3 = vertices[2]
        val v4 = vertices[3]

        val test1 = sameSide(v1, v2, v3, v4, target)
        val test2 = sameSide(v2, v3, v4, v1, target)
        val test3 = sameSide(v3, v4, v1, v2, target)
        val test4 = sameSide(v4, v1, v2, v3, target)

        return test1 && test2 && test3 && test4
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
            coordinates += (cutPieces[i].volume / this.volume)
        }

        return coordinates
    }

    fun getVertices():List<ThreeDVector> {
        return vertices
    }

    fun getVolume():Double {
        return volume
    }

    // PRIVATE INSTANCE METHODS

    private fun sameSide(v1:ThreeDVector,v2:ThreeDVector,v3:ThreeDVector,v4:ThreeDVector,p:ThreeDVector):Boolean {
        //https://stackoverflow.com/questions/25179693/how-to-check-whether-the-point-is-in-the-tetrahedron-or-not
        val normal = ThreeDVector.crossProduct(v2.substract(v1),v3.substract(v1))
        val dotV4  = ThreeDVector.dotProduct(normal, v4.substract(v1))
        val dotP   = ThreeDVector.dotProduct(normal, p.substract(v1))
        return sign(dotV4.toDouble()) == sign(dotP.toDouble())
    }

    /**
     * Calculates the volume of the tetrahedron
     */
    private fun calculateVolume() : Double {
        //https://www.had2know.org/academics/tetrahedron-volume-4-vertices.html

        val vectorMatrix = IntMatrix(4,4)

        for(i in vertices.indices) {
            val point = vertices[i]
            vectorMatrix.set(0,i,point.getComp(0))
            vectorMatrix.set(1,i,point.getComp(1))
            vectorMatrix.set(2,i,point.getComp(2))
            vectorMatrix.set(3,i,1)
        }

        return abs(vectorMatrix.det()) / 6.0
    }
}
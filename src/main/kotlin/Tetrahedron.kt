import kotlin.math.abs

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
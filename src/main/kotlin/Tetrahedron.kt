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
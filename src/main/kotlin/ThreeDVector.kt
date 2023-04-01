import java.lang.Exception
import java.lang.IndexOutOfBoundsException

open class ThreeDVector {
    companion object {
        // CLASS METHODS
        /**
         * Returns the cross product of the input vectors
         */
        fun crossProduct(a : ThreeDVector, b : ThreeDVector):ThreeDVector {
            //https://people.eecs.ku.edu/~jrmiller/Courses/VectorGeometry/VectorOperations.html
            val cA = a.getCrossProductMatrix()
            val listRes = cA.product(b.getAllComp())
            return ThreeDVector(listRes)
        }

        /**
         * Returns the dot product of the input vectors
         */
        fun dotProduct(a : ThreeDVector, b : ThreeDVector):Int {
            //https://people.eecs.ku.edu/~jrmiller/Courses/VectorGeometry/VectorOperations.html

            val aX = a.getComp(0)
            val aY = a.getComp(1)
            val aZ = a.getComp(2)
            val bX = b.getComp(0)
            val bY = b.getComp(1)
            val bZ = b.getComp(2)

            return aX*bX + aY*bY + aZ*bZ
        }
    }

    // INSTANCE ATTRIBUTES
    private val comp : MutableList<Int>

    // CONSTRUCTORS
    constructor(x : Int, y : Int, z : Int) {
        comp = mutableListOf(x,y,z)
    }


    constructor(source : List<Int>) {
        if (source.size != 3) {
            throw Exception("(ThreeDVector constructor) source's size is ${source.size}, where 3 is expected")
        }

        comp = mutableListOf(source[0],source[1],source[2])
    }

    // INSTANCE METHODS

    /**
     * Creates an instance of ThreeDVector with the same values as the called instance.
     */
    fun copy() : ThreeDVector {
        return ThreeDVector(comp[0],comp[1],comp[2])
    }

    /**
     * Adds the given vector to the current one, outputs a new instance
     */
    fun sum(other : ThreeDVector):ThreeDVector {
        val values :MutableList<Int> = mutableListOf()
        for(i in 0 until 3) {
            values += comp[i] + other.comp[i]
        }
        return ThreeDVector(values)
    }

    /**
     * Substracts the given vector to the current one, outputs a new instance
     */
    fun substract(other : ThreeDVector):ThreeDVector {
        val values :MutableList<Int> = mutableListOf()
        for(i in 0 until 3) {
            values += comp[i] - other.comp[i]
        }
        return ThreeDVector(values)
    }

    fun setComp(index : Int, value : Int) {
        if (index < 0 || index >= 3) {
            throw IndexOutOfBoundsException("(ThreeDVector.setComp) $index is an invalid index")
        }

        comp[index] = value
    }

    fun getComp(index : Int):Int {
        if (index < 0 || index >= 3) {
            throw IndexOutOfBoundsException("(ThreeDVector.getComp) $index is an invalid index")
        }

        return comp[index]
    }

    fun getAllComp() : List<Int> {
        return comp
    }

    fun getCrossProductMatrix():IntMatrix {
        val res = IntMatrix(3,3)

        res.set(0,1, -comp[2])
        res.set(0,2, comp[1])
        res.set(1,0, comp[2])
        res.set(1,2, -comp[0])
        res.set(2,0, -comp[1])
        res.set(2,1, comp[0])

        return res
    }
}
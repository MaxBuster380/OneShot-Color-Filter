import java.lang.IndexOutOfBoundsException

open class ThreeDVector {

    // INSTANCE ATTRIBUTES
    private val comp : MutableList<Int>

    // CONSTRUCTORS
    constructor(x : Int, y : Int, z : Int) {
        comp = mutableListOf(x,y,z)
    }

    // INSTANCE METHODS

    /**
     * Adds the given vector to the current one
     */
    fun sum(other : ThreeDVector) {
        for(i in 0 until 3) {
            comp[i] += other.comp[i]
        }
    }

    /**
     * Substracts the given vector to the current one
     */
    fun difference(other : ThreeDVector) {
        for(i in 0 until 3) {
            comp[i] -= other.comp[i]
        }
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
}
import java.lang.IndexOutOfBoundsException

class TwoDVector {

    // INSTANCE ATTRIBUTES
    private val comp : MutableList<Int>

    // CONSTRUCTORS
    constructor(x : Int, y : Int) {
        comp = mutableListOf(x,y)
    }

    // INSTANCE METHODS
    fun getComp(index : Int):Int {
        if (index < 0 || index >= 2) {
            throw IndexOutOfBoundsException("(TwoDVector.getComp) $index is an invalid index")
        }

        return comp[index]
    }

    fun getAllComp() : List<Int> {
        return comp
    }

    override fun toString(): String {
        return "(${comp[0]},${comp[1]},${comp[2]})"
    }
}
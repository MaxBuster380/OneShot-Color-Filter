class ThreeDVector {

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
}
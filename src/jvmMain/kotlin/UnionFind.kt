/**
 * Implementation of a disjoint-set data structure.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Disjoint-set_data_structure">Wikipedia</a>
 */
class UnionFind<T> {

    // INSTANCE ATTRIBUTES
    private val value : T
    private var rank : Int
    private var father : UnionFind<T>

    // CONSTRUCTORS
    constructor(value : T) {
        this.value = value
        this.rank = 0
        father = this
    }

    // INSTANCE METHODS

    /**
     * Unify two sets.
     */
    fun union(other:UnionFind<T>) {
        val u = this.find()
        val v = other.find()

        if (u.rank > v.rank) {
            v.father = u
        }else{
            u.father = v
        }

        if (u.rank == v.rank) {
            v.rank += 1
        }
    }

    /**
     * Finds the representative of a member.
     */
    fun find():UnionFind<T> {
        if (this != this.father) {
            this.father = this.father.find()
        }
        return this.father
    }

    /**
     * Returns the value of a member.
     */
    fun getValue():T {
        return value
    }

    /**
     * Returns true if the member is the representative of their set.
     */
    fun isRepresentative():Boolean {
        return this == father
    }
}
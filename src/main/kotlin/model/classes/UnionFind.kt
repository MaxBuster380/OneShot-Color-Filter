package model.classes

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

    fun union(other: UnionFind<T>) {


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
    fun find(): UnionFind<T> {
        if (this != this.father) {
            this.father = this.father.find()
        }
        return this.father
    }

    fun getValue():T {
        return value
    }

    fun isRepresentative():Boolean {
        return this == father
    }
}
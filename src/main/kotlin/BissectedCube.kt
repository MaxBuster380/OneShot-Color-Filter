class BissectedCube {

    // INSTANCE ATTRIBUTES
    private val sections : MutableList<Tetrahedron> = mutableListOf()

    // CONSTRUCTORS
    constructor(
        zero : ThreeDVector,
        x  :   ThreeDVector,
        y  :   ThreeDVector,
        z  :   ThreeDVector,
        xy :   ThreeDVector,
        xz :   ThreeDVector,
        yz :   ThreeDVector,
        xyz:   ThreeDVector
    ){
        sections += Tetrahedron(zero, xyz, xy, x)
        sections += Tetrahedron(zero, xyz, xy, y)
        sections += Tetrahedron(zero, xyz, xz, x)
        sections += Tetrahedron(zero, xyz, xz, z)
        sections += Tetrahedron(zero, xyz, yz, y)
        sections += Tetrahedron(zero, xyz, yz, z)
    }

    // INSTANCE METHODS

    /**
     * Cuts all sections that contain the given point
     */
    fun bissectOn(newPoint : ThreeDVector) {

        val sectionsWithNewPoint = mutableListOf<Tetrahedron>()
        for(i in sections.indices) {
            if (sections[i].contains(newPoint)) {
                sectionsWithNewPoint += sections[i]
            }
        }

        for(section in sectionsWithNewPoint) {
            cutAndDelete(section, newPoint)
        }
    }

    /**
     * Finds a tetrahedron in the cube in which lies the given point
     */
    fun getSectionOf(target : ThreeDVector):Tetrahedron? {
        var res : Tetrahedron? = null

        var i = 0
        var currentSection = sections[0]
        while(i < getNbSections() && res == null) {
            if (currentSection.contains(target)) {
                res = currentSection
            }

            i += 1
            if (i != getNbSections()) {
                currentSection = sections[i]
            }
        }

        return res
    }

    fun getNbSections():Int {
        return sections.size
    }

    // PRIVATE INSTANCE METHODS
    private fun cutAndDelete(target : Tetrahedron, cutter : ThreeDVector) {
        val subSections = target.cut(cutter)

        sections.remove(target)
        for(subSection in subSections) {
            if (subSection.getVolume() != 0)
            sections += subSection
        }
    }
}
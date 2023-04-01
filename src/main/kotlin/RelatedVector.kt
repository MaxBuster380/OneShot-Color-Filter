class RelatedVector : ThreeDVector{

    // INSTANCE ATTRIBUTES
    private val outputVector : ThreeDVector

    // CONSTRUCTORS
    constructor(x1 : Int, y1 : Int, z1 : Int, x2:Int, y2:Int, z2:Int):super(x1,y1,z1) {
        outputVector = ThreeDVector(x2,y2,z2)
    }

    constructor(paramVector : ThreeDVector, outputVector : ThreeDVector):
                super(
                    paramVector.getComp(0),
                    paramVector.getComp(1),
                    paramVector.getComp(2)
                    ) {
        this.outputVector = outputVector
    }

    // INSTANCE METHODS
    fun getInputVector():ThreeDVector {
        return this as ThreeDVector
    }

    fun getOutputVector():ThreeDVector {
        return outputVector
    }

}
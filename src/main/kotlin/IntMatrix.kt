import java.lang.Exception
import java.lang.IndexOutOfBoundsException

/**
 * Integer Matrix
 * @author MaxBuster
 */
class IntMatrix {

    // ATTRIBUTES
    private val values : MutableList<MutableList<Int>>
    private val nbRows : Int
    private val nbColumns : Int

    // CONSTRUCTORS
    constructor(nbRows : Int, nbColumns : Int) {
        if (nbRows <= 0) {
            throw Exception("(Matrix constructor) Number of rows should be strictly positive, $nbRows given")
        }
        if (nbColumns <= 0) {
            throw Exception("(Matrix constructor) Number of columns should be strictly positive, $nbColumns given")
        }

        this.nbRows = nbRows
        this.nbColumns = nbColumns

        values = mutableListOf()
        for(i in 0 until nbColumns) {
            val newRow : MutableList<Int> = mutableListOf()
            for (j in 0 until nbRows) {
                newRow.add(0)
            }
            values.add(newRow)
        }
    }

    //METHODS

    /**
     * Redefines a value on the matrix
     * @param rowIndex Row identifier
     * @param columnIndex Column identifier
     * @param value New value
     */
    fun set(rowIndex : Int, columnIndex : Int, value : Int) {
        if (rowIndex < 0 || rowIndex >= nbRows) {
            throw IndexOutOfBoundsException("(Matrix.set) Row $rowIndex is out of range")
        }
        if (columnIndex < 0 || columnIndex >= nbColumns) {
            throw IndexOutOfBoundsException("(Matrix.set) Column $columnIndex is out of range")
        }

        values[rowIndex][columnIndex] = value
    }

    /**
     * Returns the requested value on the matrix
     * @param rowIndex Row identifier
     * @param columnIndex Column identifier
     * @return Value at rowIndex : columnIndex
     */
    fun get(rowIndex : Int, columnIndex : Int):Int {
        if (rowIndex < 0 || rowIndex >= nbRows) {
            throw IndexOutOfBoundsException("(Matrix.get) Row $rowIndex is out of range")
        }
        if (columnIndex < 0 || columnIndex >= nbColumns) {
            throw IndexOutOfBoundsException("(Matrix.get) Column $columnIndex is out of range")
        }

        return values[rowIndex][columnIndex]
    }

    /**
     * Replaces a row with a new one
     * @param rowIndex Row to replace
     * @param newRow New row
     */
    fun setRow(rowIndex : Int, newRow : MutableList<Int>) {
        if (rowIndex < 0 || rowIndex >= nbRows) {
            throw IndexOutOfBoundsException("(Matrix.setRow) Row $rowIndex is out of range")
        }
        if (newRow.size != nbColumns) {
            throw Exception("(Matrix.setRow) New row's size doesn't match matrix size (${newRow.size} given, $nbColumns expected)")
        }

        values[rowIndex] = newRow
    }

    /**
     * Prints the matrix
     */
    fun print() {
        for(row in 0 until nbRows) {
            var line = "|\t"
            for(column in 0 until nbColumns) {
                line = line + values[row][column] + "\t"
            }
            line = "$line|"
            println(line)
        }
    }

    /**
     * Returns the submatrix used to calculate the determinant from a given row
     */
    private fun getSubMatrix(rowIndex : Int) : IntMatrix {
        if (nbRows != nbColumns) {
            throw Exception("(Matrix.getSubMatrix) Can't get submatrix of a non-square matrix")
        }
        if (nbRows <= 2) {
            throw Exception("(Matrix.getSubMatrix) Can't get submatrix of a matrix smaller than 3x3, currently $nbRows:$nbColumns")
        }
        if (rowIndex < 0 || rowIndex >= nbRows) {
            throw IndexOutOfBoundsException("(Matrix.getSubMatrix) Row $rowIndex is out of range")
        }

        val res = IntMatrix(nbRows-1,nbColumns-1)

        var resRow = 0
        for(row in 0 until nbRows) {
            if (row != rowIndex) {
                for (column in 1 until nbColumns) {
                    val value = get(row,column)
                    res.set(resRow,column-1,value)
                }
                resRow++
            }
        }
        return res
    }

    /**
     * Returns the matrix's determinant
     */
    fun det() : Double {
        if (nbRows != nbColumns) {
            throw Exception("(Matrix.det) Can't get determinant of a non-square matrix")
        }

        if (nbRows == 1) {
            return (values[0][0]).toDouble()
        }

        if (nbRows == 2) {
            val a = values[0][0]
            val b = values[0][1]
            val c = values[1][0]
            val d = values[1][1]

            return (a*d - b*c).toDouble()
        }

        // if nbRows > 2
        var res = 0.0
        var sign = 1
        for(row in 0 until nbRows) {
            val factor = values[row][0]
            val subDet = getSubMatrix(row).det()
            res += sign * factor * subDet

            sign = -sign // Flips between 1 and -1

        }

        return res
    }
}
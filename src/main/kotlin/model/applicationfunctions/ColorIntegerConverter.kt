package model.applicationfunctions

import model.classes.ThreeDVector

class ColorIntegerConverter {
	companion object {
		fun vectorToIntColor(vector : ThreeDVector):Int {
			val comps = vector.getAllComp()
			var res = 255
			for(comp in comps) {
				res = (res shl 8) or (comp and 255)
			}
			return res
		}
		fun intColorToVector(color : Int): ThreeDVector {
			val red = (color and (255 shl 16)) shr 16
			val green = (color and (255 shl 8)) shr 8
			val blue = (color and 255)
			return ThreeDVector(red, green, blue)
		}
	}
}
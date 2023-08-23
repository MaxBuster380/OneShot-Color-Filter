package model.applicationfunctions

import model.classes.ThreeDVector

class ColorIntegerConverter {
	companion object {
		private const val TRANSPARENCY_MASK = 255 shl 24
		private const val RGB_MASK = 0.inv() xor TRANSPARENCY_MASK

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



		/**
		 * Adds one color's transparency to another.
		 * @param donorColor Color to take the transparency from.
		 * @param recipientColor to apply the transparency onto.
		 * @return The recipient color with the donor color's transparency.
		 */
		fun copyTransparency(donorColor : Int, recipientColor : Int) : Int {
			return (TRANSPARENCY_MASK and donorColor) or (RGB_MASK and recipientColor)
		}
	}
}
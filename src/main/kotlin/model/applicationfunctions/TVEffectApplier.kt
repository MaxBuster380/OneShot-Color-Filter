package model.applicationfunctions

import java.awt.Color
import java.util.*

class TVEffectApplier {
	companion object {
		fun apply(source: WorkshopImage, thickness: Int) {
			var y = 0
			var targetY = thickness
			while (y < source.getImage().height) {

				for (x in 0..<source.getImage().width) {
					val inputColor = source.getImage().getRGB(x, y)
					val tvColorInt = getTVColor(Color(inputColor))
					val outputColor = ColorIntegerConverter.copyTransparency(inputColor, tvColorInt)
					source.getImage().setRGB(x, y, outputColor)
				}

				y += 1
				if (y == targetY) {
					y += thickness
					targetY += thickness * 2
				}
			}
		}

		private fun getTVColor(inColor: Color): Int {
			val red = inColor.red;
			val green = inColor.green;
			val blue = inColor.blue

			val darkeningFactor = (5f / 255f)

			val hsb = Color.RGBtoHSB(red, green, blue, null)
			val h = hsb[0];
			val s = hsb[1];
			val b = Collections.max(listOf((hsb[2] - darkeningFactor), 0f))

			return Color.HSBtoRGB(h, s, b)
		}
	}

}
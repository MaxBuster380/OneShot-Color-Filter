package model.applicationfunctions

import model.classes.UnionFind
import model.classes.BissectedCube
import model.classes.RelatedVector
import model.classes.Tetrahedron
import model.classes.ThreeDVector
import model.classes.TwoDVector
import java.awt.image.BufferedImage
import java.lang.Exception
import javax.swing.JProgressBar
import kotlin.math.roundToInt

class ColorFilterApplier {
	companion object {
		fun applyOnImage(
			inputImage: WorkshopImage,
			rGBCube: BissectedCube,
			progressBar: JProgressBar?
		): WorkshopImage {
			return applyColorWithHashMap(inputImage, rGBCube, progressBar)
		}

		private fun applyFilter(rGBCube: BissectedCube, target: ThreeDVector): ThreeDVector {
			val tetra =
				rGBCube.getSectionOf(target) ?: throw Exception("(applyFilter) No section found for $target")

			val barycentricCoords = tetra.getBarycentricCoordinates(target)
			val vertices = tetra.getVertices() as List<RelatedVector>

			val outputTetra = Tetrahedron(
				vertices[0].getOutputVector(),
				vertices[1].getOutputVector(),
				vertices[2].getOutputVector(),
				vertices[3].getOutputVector()
			)

			return outputTetra.translate(barycentricCoords)
		}

		/**
		 * Recolors the image. Uses a HashMap to log all already computed colors.
		 */
		private fun applyColorWithHashMap(
			inputImage: WorkshopImage,
			rGBCube: BissectedCube,
			progressBar: JProgressBar?
		) : WorkshopImage {
			// This runs faster than applyColorWithUnionFind :
			// 4x faster on a medium-sized image
			// 13x faster on a large image
			val outputImage = createOutputImage(inputImage)
			val imageWidth = inputImage.getImage().width
			val imageHeight = inputImage.getImage().height

			val computedColors = hashMapOf<Int, Int>()

			for (x in 0..<imageWidth) {
				for (y in 0..<imageHeight) {
					val currentPixelCoordinates = TwoDVector(x,y)
					val inputColor = inputImage.colorOf(currentPixelCoordinates)

					if (!computedColors.containsKey(inputColor)) {
						val newColor = calculateColor(inputColor, rGBCube)
						computedColors[inputColor] = newColor
					}

					var outputColor = computedColors[inputColor]!!
					outputColor = copyTransparency(inputColor, outputColor)

					outputImage.setColorOf(currentPixelCoordinates, outputColor)
				}
				if (progressBar != null) {
					progressBar.value = (x.toFloat() * 100f / imageWidth.toFloat()).toInt()
				}
			}

			return outputImage
		}

		/**
		 * Recolors the image by creating UnionFind forests and coloring them.
		 */
		private fun applyColorWithUnionFind(inputImage: WorkshopImage, rGBCube: BissectedCube, progressBar: JProgressBar?) : WorkshopImage {
			val outputImage = createOutputImage(inputImage)

			// Set up model.classes.UnionFind field
			val unionFindForrest = prepareForest(inputImage)

			// Form zones
			formZones(inputImage, unionFindForrest)

			// Calculate the color of the representatives
			calculateRepresentativeColors(outputImage, rGBCube, inputImage, unionFindForrest, progressBar)

			// Assign the color of the representative
			assignColorFromRepresentative(unionFindForrest, outputImage)

			if (progressBar != null) {
				progressBar.value = 100
			}

			return outputImage
		}

		/**
		 * Creates a new empty image with the same dimension as the inputted image.
		 */
		private fun createOutputImage(inputImage: WorkshopImage): WorkshopImage {
			val imageWidth = inputImage.getImage().width
			val imageHeight = inputImage.getImage().height

			val rawOutputImage = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)
			return WorkshopImage(rawOutputImage)
		}

		/**
		 * @see applyColorWithUnionFind
		 */
		private fun prepareForest(inputImage:WorkshopImage):MutableList<UnionFind<TwoDVector>> {
			val unionFindForrest: MutableList<UnionFind<TwoDVector>> = mutableListOf()
			val imageWidth = inputImage.getImage().width
			val imageHeight = inputImage.getImage().height
			for (x in 0 until imageWidth) {
				for (y in 0 until imageHeight) {
					unionFindForrest += UnionFind(TwoDVector(x, y))
				}
			}
			return unionFindForrest
		}

		/**
		 * @see applyColorWithUnionFind
		 */
		private fun formZones(inputImage : WorkshopImage, unionFindForrest : MutableList<UnionFind<TwoDVector>>) {
			val imageWidth = inputImage.getImage().width
			for (i in unionFindForrest.indices) {
				val elt1 = unionFindForrest[i]

				if (i + 1 < unionFindForrest.size) {
					val elt2 = unionFindForrest[i + 1]
					if (inputImage.colorOf(elt1) == inputImage.colorOf(elt2)) {
						elt1.union(elt2)
					}
				}

				if (i + imageWidth < unionFindForrest.size) {
					val elt2 = unionFindForrest[i + imageWidth]
					if (inputImage.colorOf(elt1) == inputImage.colorOf(elt2)) {
						elt1.union(elt2)
					}
				}
			}
		}

		/**
		 * @see applyColorWithUnionFind
		 */
		private fun calculateRepresentativeColors(outputImage : WorkshopImage, rGBCube : BissectedCube, inputImage : WorkshopImage, unionFindForrest : MutableList<UnionFind<TwoDVector>>, progressBar: JProgressBar?) {
			val progressUpdate = unionFindForrest.size / 100
			var progressIterator = 0
			var allProgress = 0
			for (elt in unionFindForrest) {
				if (elt.isRepresentative()) {
					val inputColor = inputImage.colorOf(elt)
					val outputColor = calculateColor(inputColor, rGBCube)
					outputImage.setColorOf(elt, outputColor)
				}
				progressIterator += 1
				allProgress += 1
				if (progressIterator >= progressUpdate) {
					progressIterator = 0
					val progressPercent = (allProgress * 100.0 / unionFindForrest.size)
					if (progressBar != null) {
						progressBar.value = progressPercent.roundToInt()
					}
				}
			}
		}

		/**
		 * @see applyColorWithUnionFind
		 */
		private fun assignColorFromRepresentative(unionFindForrest : MutableList<UnionFind<TwoDVector>>, outputImage : WorkshopImage) {
			for (elt in unionFindForrest) {
				outputImage.setColorOf(elt, outputImage.colorOf(elt.find()))
			}
		}

		private fun calculateColor(inputColor: Int, rGBCube: BissectedCube): Int {
			val inputVector = ColorIntegerConverter.intColorToVector(inputColor)

			val outputVector = applyFilter(rGBCube, inputVector)
			return ColorIntegerConverter.vectorToIntColor(outputVector)
		}

		/**
		 * Adds one color's transparency to another.
		 * @param donorColor Color to take the transparency from.
		 * @param recipientColor to apply the transparency onto.
		 * @return The recipient color with the donor color's transparency.
		 */
		private fun copyTransparency(donorColor : Int, recipientColor : Int) : Int {
			val transparencyMask = 255 shl 24
			val rgbMask = 0.inv() xor transparencyMask
			return (transparencyMask and donorColor) or (rgbMask and recipientColor)
		}
	}
}
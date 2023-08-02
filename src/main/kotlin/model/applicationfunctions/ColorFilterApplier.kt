package model.applicationfunctions

import model.classes.UnionFind
import model.classes.BissectedCube
import model.classes.RelatedVector
import model.classes.Tetrahedron
import model.classes.ThreeDVector
import model.classes.TwoDVector
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Exception
import java.sql.Timestamp
import javax.imageio.ImageIO

class ColorFilterApplier() {
	companion object {
		fun applyOnImage(pathIn: String, pathOut: String, rGBCube: BissectedCube):WorkshopImage {
			val rawInputImage: BufferedImage = ImageIO.read(File(pathIn))
			val inputImage = WorkshopImage(rawInputImage)

			val imageWidth = inputImage.getImage().width
			val imageHeight = inputImage.getImage().height

			val rawOutputImage = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)
			val outputImage = WorkshopImage(rawOutputImage)

			// Set up model.classes.UnionFind field
			val unionFindForrest: MutableList<UnionFind<TwoDVector>> = mutableListOf()
			for (x in 0 until imageWidth) {
				for (y in 0 until imageHeight) {
					unionFindForrest += UnionFind(TwoDVector(x, y))
				}
			}

			// Form zones
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

			val progressUpdate = 30000
			var progressIterator = 0
			var allProgress = 0
			// Calculate the color of the representatives
			for (elt in unionFindForrest) {
				if (elt.isRepresentative()) {
					val inputColor = inputImage.colorOf(elt)
					val inputVector = ColorIntegerConverter.intColorToVector(inputColor)

					val outputVector = applyFilter(rGBCube, inputVector)
					val outputColor = ColorIntegerConverter.vectorToIntColor(outputVector)

					outputImage.setColorOf(elt, outputColor)
				}
				progressIterator += 1
				allProgress += 1
				if (progressIterator >= progressUpdate) {
					progressIterator = 0
					println(
						"Update \t " +
							"${Timestamp(System.currentTimeMillis())} \t " +
							"${
								(allProgress * 100.0 / unionFindForrest.size).toString().substring(0, 5)
							}% done."
					)
				}
			}

			// Assign the color of the representative
			for (elt in unionFindForrest) {
				outputImage.setColorOf(elt, outputImage.colorOf(elt.find()))
			}

			//TVEffectApplier.apply(outputImage, tvThickness)
			return outputImage
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
	}
}
package model.applicationfunctions

import model.classes.BissectedCube
import java.beans.PropertyChangeSupport
import java.lang.Exception

class SwingModel() {
	companion object {
		const val DEFAULT_TV_EFFECT_SIZE = 2
	}

	private var inputPath:String = ""
	private var tvEffectSize = DEFAULT_TV_EFFECT_SIZE
	private var outputPath:String = ""

	private var unfilteredImage : WorkshopImage? = null
	private var filteredNoTvImage : WorkshopImage? = null
	private var filteredWithTvImage : WorkshopImage? = null

	private val rGBCube: BissectedCube = RGBCubeBuilder.getRGBCube("dataColors.txt")

	private val propertyChange = PropertyChangeSupport(this)
	fun applyCommand(pathIn: String, pathOut: String) {
		try {
			val inputImage = ColorFilterApplier.loadImage(pathIn)
			val recoloredImage = ColorFilterApplier.applyOnImage(inputImage, rGBCube)
			recoloredImage.save(pathOut)
			println("Image successfully created")
		} catch (e: Exception) {
			println("An error has occurred : $e")
		}
	}

	fun generateFilteredNoTvImage() {
		assert(unfilteredImage != null)

		filteredNoTvImage = ColorFilterApplier.applyOnImage(unfilteredImage!!, rGBCube)
	}

	fun generateFilteredWithTvImage() {
		assert(filteredNoTvImage != null)

		filteredWithTvImage = filteredNoTvImage!!.copy()
		TVEffectApplier.apply(filteredWithTvImage!!, tvEffectSize)
	}

	fun saveFilteredWithTvImage(pathOut:String) {
		assert(filteredWithTvImage != null)

		filteredWithTvImage!!.save(pathOut)
	}
}
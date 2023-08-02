package model.applicationfunctions

import model.classes.BissectedCube
import java.awt.Color
import java.beans.PropertyChangeSupport
import java.io.File
import java.lang.Exception

class SwingModel() {
	companion object {
		const val DEFAULT_TV_EFFECT_SIZE = 2
	}

	private var inputPath:String = ""
	private var outputPath:String = ""
	private var tvEffectSize = DEFAULT_TV_EFFECT_SIZE
	private var autoGenerateOutputPath = true

	private var unfilteredImage : WorkshopImage? = null
	private var filteredNoTvImage : WorkshopImage? = null
	private var filteredWithTvImage : WorkshopImage? = null

	private val rGBCube: BissectedCube = RGBCubeBuilder.getRGBCube("dataColors.txt")

	private val propertyChange = PropertyChangeSupport(this)

	fun setInputPath(source:String) {
		inputPath = source
	}

	fun setOutputPath(source:String) {
		outputPath = source
	}

	fun setTvEffectSize(source:Int) {
		assert(source >= 0)

		tvEffectSize = source
	}

	fun setAutoGenerateOutputPath(source:Boolean) {
		autoGenerateOutputPath = source
	}

	fun loadUnfilteredImage() {
		assert(inputPath != "")

		unfilteredImage = ColorFilterApplier.loadImage(inputPath)
	}

	fun generateFilteredNoTvImage() {
		assert(unfilteredImage != null)

		filteredNoTvImage = ColorFilterApplier.applyOnImage(unfilteredImage!!, rGBCube)
	}

	fun generateOutputPath() {
		assert(inputPath != "")
	}

	fun generateFilteredWithTvImage() {
		assert(filteredNoTvImage != null)

		filteredWithTvImage = filteredNoTvImage!!.copy()
		TVEffectApplier.apply(filteredWithTvImage!!, tvEffectSize)
	}

	fun saveFilteredWithTvImage() {
		assert(filteredWithTvImage != null)

		filteredWithTvImage!!.save(outputPath)
	}
}
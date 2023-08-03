package model.applicationfunctions

import model.classes.BissectedCube
import java.awt.Color
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.io.File
import java.lang.Exception

class SwingModel {
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

	fun addPropertyChangeListener(listener:PropertyChangeListener) {
		propertyChange.addPropertyChangeListener(listener)
	}

	fun setInputPath(newValue:String) {
		val oldValue = inputPath
		inputPath = newValue
		propertyChange.firePropertyChange("inputPath", oldValue, newValue)
		loadUnfilteredImage()
	}

	fun setOutputPath(source:String) {
		outputPath = source
	}

	fun setTvEffectSize(newValue:Int) {
		assert(newValue >= 0)

		val oldValue = tvEffectSize
		tvEffectSize = newValue
		propertyChange.firePropertyChange("tvEffectSize", oldValue, newValue)
	}

	fun setAutoGenerateOutputPath(source:Boolean) {
		autoGenerateOutputPath = source
	}

	fun loadUnfilteredImage() {
		assert(inputPath != "")

		unfilteredImage = FileFetcher.loadImage(inputPath)
		filteredNoTvImage = null
		filteredWithTvImage = null
		propertyChange.firePropertyChange("unfilteredImage", null, null)
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
		propertyChange.firePropertyChange("filteredWithTvImage", null, null)
	}

	fun saveFilteredWithTvImage() {
		assert(filteredWithTvImage != null)

		filteredWithTvImage!!.save(outputPath)
	}

	fun getInputPath():String {
		return inputPath
	}

	fun getUnfilteredImage():WorkshopImage? {
		return unfilteredImage
	}

	fun getFilteredWithTvImage():WorkshopImage? {
		return filteredWithTvImage
	}

	fun getTvEffectSize():Int {
		return tvEffectSize
	}

	fun getOutputPath():String {
		return outputPath
	}
}
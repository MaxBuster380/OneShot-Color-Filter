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

	private var inputFile:File? = null
	private var outputFile:File? = null
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

	fun setInputPath(path:String) {
		val oldValue = getInputPath()
		inputFile = File(path)
		val newValue = getInputPath()
		loadUnfilteredImage()
		propertyChange.firePropertyChange("inputPath", oldValue, newValue)
	}

	fun setOutputPath(path:String) {
		outputFile = File(path)
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
		assert(inputFile != null)

		unfilteredImage = FileFetcher.loadImage(getInputPath())
		filteredNoTvImage = null
		filteredWithTvImage = null
		propertyChange.firePropertyChange("unfilteredImage", null, null)
	}

	fun generateFilteredNoTvImage() {
		assert(unfilteredImage != null)

		filteredNoTvImage = ColorFilterApplier.applyOnImage(unfilteredImage!!, rGBCube)
	}

	fun generateFilteredWithTvImage() {
		assert(filteredNoTvImage != null)

		filteredWithTvImage = filteredNoTvImage!!.copy()
		TVEffectApplier.apply(filteredWithTvImage!!, tvEffectSize)
		propertyChange.firePropertyChange("filteredWithTvImage", null, null)
	}

	fun saveFilteredWithTvImage() {
		assert(filteredWithTvImage != null)

		filteredWithTvImage!!.save(getOutputPath())
	}

	fun getInputPath():String {
		return if (inputFile != null) {
			inputFile!!.absolutePath
		}else{
			""
		}
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
		return if (outputFile != null) {
			outputFile!!.absolutePath
		}else{
			""
		}
	}
}
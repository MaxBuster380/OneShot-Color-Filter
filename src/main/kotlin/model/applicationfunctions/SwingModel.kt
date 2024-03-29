package model.applicationfunctions

import model.classes.BissectedCube
import java.awt.Color
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.io.File
import java.lang.Exception
import javax.swing.JProgressBar

class SwingModel {
	companion object {
		const val DEFAULT_TV_EFFECT_SIZE = 2
	}

	private var inputFile:File? = null
	private var outputFile:File? = null
	private var tvEffectSize = DEFAULT_TV_EFFECT_SIZE

	private var unfilteredImage : WorkshopImage? = null
	private var filteredNoTvImage : WorkshopImage? = null
	private var filteredWithTvImage : WorkshopImage? = null

	private val rGBCube: BissectedCube = RGBCubeBuilder.getRGBCube("dataColors.txt")

	private val propertyChange = PropertyChangeSupport(this)

	private var working = false

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
		val oldValue = getOutputPath()
		outputFile = File(path)
		val newValue = getOutputPath()
		propertyChange.firePropertyChange("outputPath",oldValue,newValue)
	}

	fun setTvEffectSize(newValue:Int) {
		assert(newValue >= 0)

		val oldValue = tvEffectSize
		tvEffectSize = newValue
		propertyChange.firePropertyChange("tvEffectSize", oldValue, newValue)
	}

	fun loadUnfilteredImage() {
		assert(inputFile != null)

		try {
			unfilteredImage = FileFetcher.loadImage(getInputPath())
			filteredNoTvImage = null
			filteredWithTvImage = null
			propertyChange.firePropertyChange("unfilteredImage", null, null)
		}catch(e:Exception) {
			propertyChange.firePropertyChange("import_failure", null, e)
		}
	}

	fun generateFilteredImage(progressBar: JProgressBar) {
		Thread {
			setWorking(true)
			if (getFilteredWithTvImage() == null) {
				generateFilteredNoTvImage(progressBar)
			}
			generateFilteredWithTvImage()
			setWorking(false)
		}.start()
	}

	private fun generateFilteredNoTvImage(progressBar:JProgressBar) {
		assert(unfilteredImage != null)
		filteredNoTvImage = ColorFilterApplier.applyOnImage(unfilteredImage!!, rGBCube, progressBar)
	}

	private fun generateFilteredWithTvImage() {
		assert(filteredNoTvImage != null)

		filteredWithTvImage = filteredNoTvImage!!.copy()
		TVEffectApplier.apply(filteredWithTvImage!!, tvEffectSize)
		propertyChange.firePropertyChange("filteredWithTvImage", null, null)
	}

	fun saveFilteredWithTvImage() {
		assert(filteredWithTvImage != null)

		try {
			filteredWithTvImage!!.save(getOutputPath())
			propertyChange.firePropertyChange("export", 0, 1)
		}catch(e:Exception) {
			propertyChange.firePropertyChange("failure_export",null, e)
		}
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

	fun getInputFile():File? {
		return inputFile
	}

	fun isWorking():Boolean {
		return working
	}

	private fun setWorking(newValue:Boolean) {
		val oldValue = working
		working = newValue
		propertyChange.firePropertyChange("working",oldValue,newValue)
	}
}
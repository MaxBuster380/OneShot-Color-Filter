package view

import model.applicationfunctions.SwingModel
import model.applicationfunctions.WorkshopImage
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.Image
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel
import kotlin.math.min


class ImagePanel(private val model: SwingModel):JPanel(),PropertyChangeListener {

	private val imageLabel = JLabel()
	init {
		model.addPropertyChangeListener(this)
		layout = GridLayout(1,1)
		preferredSize = Dimension(632, height)
		add(imageLabel)
	}

	private fun displayImage(image: WorkshopImage) {
		val imageCopy = image.copy()
		val resizedImage = resizeImage(imageCopy)
		imageLabel.icon = ImageIcon(resizedImage)
	}

	private fun resizeImage(sourceImage:WorkshopImage):Image {
		val resizedImage = sourceImage.getImage()
		val ratio : Double = resizedImage.width.toDouble() / resizedImage.height.toDouble()

		var newImageWidth:Int
		var newImageHeight:Int
		if (ratio > 1) {
			newImageWidth = min(resizedImage.width, this.width)
			newImageHeight = (newImageWidth / ratio).toInt()
		}else{
			newImageHeight = min(resizedImage.height, this.height)
			newImageWidth = (newImageHeight * ratio).toInt()
		}
		return resizedImage.getScaledInstance(newImageWidth,newImageHeight,Image.SCALE_DEFAULT)
	}

	override fun propertyChange(evt: PropertyChangeEvent) {
		if (evt.propertyName == "unfilteredImage") {
			displayImage(model.getUnfilteredImage()!!)
		}
		if (evt.propertyName == "filteredWithTvImage") {
			displayImage(model.getFilteredWithTvImage()!!)
		}
	}
}
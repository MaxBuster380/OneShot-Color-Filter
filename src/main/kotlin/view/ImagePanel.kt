package view

import model.applicationfunctions.SwingModel
import java.awt.FlowLayout
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel


class ImagePanel(private val model: SwingModel):JPanel() {
	init {
	}

	fun displayImage(image: BufferedImage) {
		layout = FlowLayout()
		add(JLabel(ImageIcon(image)))
	}
}
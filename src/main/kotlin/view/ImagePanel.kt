package view

import java.awt.FlowLayout
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel


class ImagePanel:JPanel() {
	init {
	}

	fun displayImage(image: BufferedImage) {
		layout = FlowLayout()
		add(JLabel(ImageIcon(image)))
	}
}
package view

import model.applicationfunctions.SwingModel
import java.awt.BorderLayout
import javax.swing.ImageIcon
import javax.swing.JFrame

class ParameterFrame(private val model : SwingModel) : JFrame() {
	companion object {
		const val WIDTH = 400
		const val HEIGHT = 600
	}
	init {
		title = "${StringsManager.get("application_title")} - ${StringsManager.get("parameters_title")}"
		setSize(WIDTH, HEIGHT)
		isResizable = false

		createIconImage()

		defaultCloseOperation = DISPOSE_ON_CLOSE

		isVisible = true
	}

	private fun createIconImage() {
		val img = ImageIcon(this::class.java.classLoader.getResource("application_icon.png"))
		iconImage = img.image
	}
}
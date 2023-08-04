package view

import model.applicationfunctions.SwingModel
import java.awt.BorderLayout
import javax.swing.ImageIcon
import javax.swing.JFrame


class OSCFFrame: JFrame() {
	companion object {
		const val WIDTH = 809
		const val HEIGHT = 500
	}

	private val model = SwingModel()
	init {
		title = StringsManager.get("application_title")
		setSize(WIDTH, HEIGHT)
		isResizable = false

		createIconImage()

		layout = BorderLayout()
		add(HeaderPanel(model), BorderLayout.NORTH)
		add(WindowPanel(model), BorderLayout.CENTER)

		defaultCloseOperation = DISPOSE_ON_CLOSE

		isVisible = true
	}

	private fun createIconImage() {
		val img = ImageIcon(this::class.java.classLoader.getResource("application_icon.png"))
		iconImage = img.image
	}
}
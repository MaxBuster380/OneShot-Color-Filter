package view

import java.awt.BorderLayout
import javax.swing.JFrame


class OSCFFrame: JFrame() {
	companion object {
		const val WIDTH = 809
		const val HEIGHT = 500
	}
	init {
		title = "PLACEHOLDER TITLE"
		setSize(WIDTH, HEIGHT)
		layout = BorderLayout()

		add(HeaderPanel(), BorderLayout.NORTH)
		add(WindowPanel(), BorderLayout.CENTER)

		defaultCloseOperation = DISPOSE_ON_CLOSE

		isVisible = true
	}
}
package view

import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.border.Border


class OSCFFrame: JFrame() {
	companion object {
		const val WIDTH = 809
		const val HEIGHT = 500
	}
	init {
		title = "PLACEHOLDER TITLE"
		setSize(WIDTH, HEIGHT)
		layout = BorderLayout()

		add(OSCFHeaderPanel(), BorderLayout.NORTH)

		isVisible = true
	}
}
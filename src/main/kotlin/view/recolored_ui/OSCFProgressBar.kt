package view.recolored_ui

import java.awt.Color
import java.awt.Graphics
import javax.swing.BorderFactory
import javax.swing.JProgressBar

class OSCFProgressBar() : JProgressBar() {
	companion object {
		val FOREGROUND_COLOR = Color.BLACK
		val BACKGROUND_COLOR = Color.GREEN
	}

	init {
		border = BorderFactory.createEmptyBorder()
	}

	override fun paintComponent(g: Graphics) {
		super.paintComponent(g)

		foreground = FOREGROUND_COLOR
		background = BACKGROUND_COLOR
	}
}
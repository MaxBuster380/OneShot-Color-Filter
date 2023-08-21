package view.recolored_ui

import java.awt.Color
import java.awt.Graphics
import javax.swing.BorderFactory
import javax.swing.JProgressBar

class OSCFProgressBar() : JProgressBar() {
	companion object {
		val FOREGROUND_COLOR = Color(60,60,75)
		val BACKGROUND_COLOR = Color(85,85,85)
	}

	init {
		border = BorderFactory.createEmptyBorder()
	}

	override fun paintComponent(g: Graphics) {
		super.paintComponent(g)

		foreground = FOREGROUND_COLOR
		background = BACKGROUND_COLOR
		isBorderPainted = false
	}
}
package view.recolored_ui

import java.awt.Color
import java.awt.Graphics
import javax.swing.BorderFactory
import javax.swing.JPanel

open class OSCFPanel() : JPanel() {
	companion object {
		val BACKGROUND_COLOR = Color.BLUE
	}

	init {
		border = BorderFactory.createEmptyBorder()
	}

	override fun paintComponent(g: Graphics) {
		super.paintComponent(g)

		background = BACKGROUND_COLOR
	}
}
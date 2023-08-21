package view.recolored_ui

import java.awt.Color
import java.awt.Graphics
import javax.swing.BorderFactory
import javax.swing.JTextField

class OSCFTextField(text : String) : JTextField(text) {
	companion object {
		val BACKGROUND_COLOR = Color.RED
		val TEXT_COLOR = Color.BLUE
	}

	constructor():this("")

	init {
		border = BorderFactory.createEmptyBorder()
	}

	override fun paintComponent(g: Graphics) {
		super.paintComponent(g)

		background = BACKGROUND_COLOR
		foreground = TEXT_COLOR
	}
}
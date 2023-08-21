package view.recolored_ui

import java.awt.Color
import java.awt.Graphics
import javax.swing.BorderFactory
import javax.swing.JLabel

class OSCFLabel(text:String) : JLabel(text) {
	companion object {
		val BACKGROUND_COLOR = Color(	42,42,42)
		val TEXT_COLOR = Color.WHITE
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
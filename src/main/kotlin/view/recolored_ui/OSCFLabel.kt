package view.recolored_ui

import java.awt.Color
import java.awt.Graphics
import javax.swing.BorderFactory
import javax.swing.JLabel

class OSCFLabel(text:String) : JLabel(text) {
	companion object {
		val BACKGROUND_COLOR = OSCFColors.BACKGROUND
		val TEXT_COLOR = OSCFColors.TEXT
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
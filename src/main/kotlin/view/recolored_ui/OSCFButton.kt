package view.recolored_ui

import java.awt.Color
import java.awt.Graphics
import javax.swing.BorderFactory
import javax.swing.JButton


class OSCFButton(text: String) : JButton(text) {
	companion object {
		val HOVERED_COLOR = Color(80,80,80)
		val CLICKED_COLOR = Color(64,64,64)
		val ENABLED_TEXT_COLOR = OSCFColors.TEXT
		val UNHOVERED_COLOR = Color(96,96,96)
		val DISABLED_COLOR = UNHOVERED_COLOR
		val DISABLED_TEXT_COLOR = Color(192,192,192)
	}

	init {
		border = BorderFactory.createEmptyBorder()
		super.setContentAreaFilled(true)
	}

	override fun paintComponent(g: Graphics) {
		super.paintComponent(g)

		background = if (!isEnabled) {
			DISABLED_COLOR
		} else if (getModel().isPressed) {
			CLICKED_COLOR
		} else if (getModel().isRollover) {
			HOVERED_COLOR
		} else {
			UNHOVERED_COLOR
		}

		foreground = if (isEnabled) {
			ENABLED_TEXT_COLOR
		} else {
			DISABLED_TEXT_COLOR
		}
	}

	override fun setContentAreaFilled(b: Boolean) {}
}
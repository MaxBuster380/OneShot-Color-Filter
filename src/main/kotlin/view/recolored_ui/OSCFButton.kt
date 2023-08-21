package view.recolored_ui

import java.awt.Color
import java.awt.Graphics
import javax.swing.BorderFactory
import javax.swing.JButton


class OSCFButton(text: String) : JButton(text) {
	companion object {
		val UNHOVERED_COLOR = Color(96,96,96)
		val HOVERED_COLOR = Color(80,80,80)
		val CLICKED_COLOR = Color(64,64,64)
		val DISABLED_COLOR = Color(144,144,144)
	}

	init {
		border = BorderFactory.createEmptyBorder()
		super.setContentAreaFilled(false)
	}

	override fun paintComponents(g: Graphics) {
		if (isEnabled) {
			g.color = DISABLED_COLOR
		} else if (getModel().isPressed) {
			g.color = CLICKED_COLOR
		} else if (getModel().isRollover) {
			g.color = HOVERED_COLOR
		} else {
			g.color = UNHOVERED_COLOR
		}
		g.fillRect(0, 0, width, height);
		super.paintComponents(g)
	}

	override fun setContentAreaFilled(b: Boolean) {}
}
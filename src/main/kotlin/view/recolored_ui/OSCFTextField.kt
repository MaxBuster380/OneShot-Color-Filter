package view.recolored_ui

import java.awt.Color
import java.awt.Graphics
import javax.swing.BorderFactory
import javax.swing.JTextField

class OSCFTextField(text : String) : JTextField(text) {
	companion object {
		val EDITABLE_TEXT_COLOR : Color = OSCFColors.TEXT
		val EDITABLE_BACKGROUND_COLOR = Color(96,96,96)

		val UNEDITABLE_TEXT_COLOR = Color(96,96,96)
		val UNEDITABLE_BACKGROUND_COLOR = Color(64,64,64)

		val DISABLED_TEXT_COLOR = UNEDITABLE_TEXT_COLOR
		val DISABLED_BACKGROUND_COLOR = UNEDITABLE_BACKGROUND_COLOR
	}

	constructor():this("")

	init {
		border = BorderFactory.createEmptyBorder()
	}

	override fun paintComponent(g: Graphics) {
		super.paintComponent(g)
		if (!isEnabled) {
			background = DISABLED_BACKGROUND_COLOR
			foreground = DISABLED_TEXT_COLOR
		}else	if (isEditable) {
			background = EDITABLE_BACKGROUND_COLOR
			foreground = EDITABLE_TEXT_COLOR
		}else{
			background = UNEDITABLE_BACKGROUND_COLOR
			foreground = UNEDITABLE_TEXT_COLOR
		}
	}
}
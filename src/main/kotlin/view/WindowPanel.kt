package view

import java.awt.BorderLayout
import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.JPanel


class WindowPanel: JPanel() {
	init {
		layout = BorderLayout()

		add(ImagePanel(), BorderLayout.CENTER)
		add(ControlPanel(), BorderLayout.EAST)
	}
}
package view

import java.awt.BorderLayout
import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.JPanel


class WindowPanel: JPanel() {
	init {
		layout = BorderLayout()
		border = BorderFactory.createLineBorder(Color(187, 173, 160), 4)

		add(JPanel(), BorderLayout.CENTER)
		add(ControlPanel(), BorderLayout.EAST)
	}
}
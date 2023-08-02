package view

import java.awt.Color
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JPanel

class ControlPanel : JPanel() {
	init {
		layout = GridLayout(3,1)

		border = BorderFactory.createLineBorder(Color(128, 128, 128), 4)

		add(ImportImagePanel())
		add(ColorManipulationPanel())
		add(ExportImagePanel())
	}
}
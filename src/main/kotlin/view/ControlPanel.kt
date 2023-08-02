package view

import java.awt.Color
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JPanel

class ControlPanel : JPanel() {
	init {
		layout = GridLayout(3,1)

		add(ImportImagePanel())
		add(ColorManipulationPanel())
		add(ExportImagePanel())
	}
}
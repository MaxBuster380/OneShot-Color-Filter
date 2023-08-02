package view

import model.applicationfunctions.SwingModel
import java.awt.Color
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JPanel

class ControlPanel(private val model: SwingModel) : JPanel() {
	init {
		layout = GridLayout(3,1)

		add(ImportImagePanel())
		add(ColorManipulationPanel())
		add(ExportImagePanel())
	}
}
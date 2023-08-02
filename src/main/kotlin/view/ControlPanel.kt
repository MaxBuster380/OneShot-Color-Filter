package view

import model.applicationfunctions.SwingModel
import java.awt.Color
import java.awt.Dimension
import java.awt.Frame
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JPanel

class ControlPanel(model: SwingModel) : JPanel() {
	init {
		layout = GridLayout(3,1)
		preferredSize = Dimension(177, height)

		add(ImportImagePanel(model))
		add(ColorManipulationPanel(model))
		add(ExportImagePanel(model))
	}
}
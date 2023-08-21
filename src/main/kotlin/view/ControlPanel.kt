package view

import model.applicationfunctions.SwingModel
import view.recolored_ui.OSCFPanel
import java.awt.Color
import java.awt.Dimension
import java.awt.Frame
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JPanel

class ControlPanel(model: SwingModel) : OSCFPanel() {
	init {
		layout = GridLayout(3,1)
		preferredSize = Dimension(177, height)

		add(ImportImagePanel(model))
		add(ColorManipulationPanel(model))
		add(ExportImagePanel(model))
	}
}
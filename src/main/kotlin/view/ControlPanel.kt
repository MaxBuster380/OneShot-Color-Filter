package view

import model.applicationfunctions.SwingModel
import java.awt.Color
import java.awt.Frame
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JPanel

class ControlPanel(private val model: SwingModel, frame: Frame) : JPanel() {
	init {
		layout = GridLayout(3,1)

		add(ImportImagePanel(model, frame))
		add(ColorManipulationPanel(model))
		add(ExportImagePanel(model, frame))
	}
}
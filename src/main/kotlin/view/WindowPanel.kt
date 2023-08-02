package view

import model.applicationfunctions.SwingModel
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.JPanel


class WindowPanel(private val model: SwingModel): JPanel() {
	init {
		layout = BorderLayout()

		add(ImagePanel(model), BorderLayout.CENTER)
		add(ControlPanel(model), BorderLayout.EAST)
	}
}
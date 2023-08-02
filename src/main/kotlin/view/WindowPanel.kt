package view

import model.applicationfunctions.SwingModel
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Frame
import javax.swing.BorderFactory
import javax.swing.JPanel


class WindowPanel(model: SwingModel): JPanel() {
	init {
		layout = BorderLayout()

		add(ImagePanel(model), BorderLayout.CENTER)
		add(ControlPanel(model), BorderLayout.EAST)
	}
}
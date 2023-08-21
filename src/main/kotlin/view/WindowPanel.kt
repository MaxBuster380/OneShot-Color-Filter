package view

import model.applicationfunctions.SwingModel
import view.recolored_ui.OSCFPanel
import java.awt.BorderLayout


class WindowPanel(model: SwingModel): OSCFPanel() {
	init {
		layout = BorderLayout()

		add(ImagePanel(model), BorderLayout.CENTER)
		add(ControlPanel(model), BorderLayout.EAST)
	}
}
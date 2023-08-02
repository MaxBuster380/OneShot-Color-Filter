package view

import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.JPanel

class ImagePanel:JPanel() {
	init {
		border = BorderFactory.createLineBorder(Color(64, 0, 0), 4)
	}
}
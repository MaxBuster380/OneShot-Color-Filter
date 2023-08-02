package view

import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class OSCFHeaderPanel: JPanel() {
	init {
		layout = GridLayout(1,3)

		add(createTitleLabel())
		add(JPanel())
		add(createRepositoryButton())
	}

	private fun createTitleLabel():JLabel {
		val res = JLabel("PLACEHOLDER TITLE")
		res.isOpaque = true
		return res
	}

	private fun createRepositoryButton(): JButton {
		val res = JButton()
		return res
	}
}
package view

import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class HeaderPanel: JPanel() {
	init {
		layout = GridLayout(1,3)

		add(createTitleLabel())
		add(JPanel())
		add(createRepositoryButton())
	}

	private fun createTitleLabel():JLabel {
		val res = JLabel(
			StringsManager.get("application_title")
		)
		res.isEnabled = true
		return res
	}

	private fun createRepositoryButton(): JButton {
		val res = JButton(
			StringsManager.get("go_to_repository")
		)
		res.isEnabled = true
		return res
	}
}
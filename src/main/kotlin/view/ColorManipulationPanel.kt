package view

import java.awt.Color
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.*

class ColorManipulationPanel: JPanel() {

	private val tvEffectSizeTextField = createTvEffectSizeTextField()
	private val applyButton = createApplyButton()

	init {
		layout = GridLayout(2,1)

		add(createTvEffectSizePanel())
		add(applyButton)

		border = BorderFactory.createLineBorder(Color(0, 0, 0), 4)
	}

	private fun createTvEffectSizeTextField(): JTextField {
		val res = JTextField("2")
		return res
	}

	private fun createApplyButton(): JButton {
		val res = JButton("PLACEHOLDER APPLY BUTTON")
		return res
	}

	private fun createTvEffectSizePanel():JPanel {
		val res = JPanel()
		res.layout = GridLayout(2,1)
		res.add(
			JLabel("PLACEHOLDER TV EFFECT SIZE")
		)
		res.add(tvEffectSizeTextField)
		return res
	}
}
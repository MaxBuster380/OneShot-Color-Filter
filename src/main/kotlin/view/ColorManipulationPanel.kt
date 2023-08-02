package view

import java.awt.Color
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.*

class ColorManipulationPanel: JPanel() {
	companion object {
		const val DEFAULT_TV_EFFECT_SIZE = 2
	}

	private val tvEffectSizeTextField = createTvEffectSizeTextField()
	private val applyButton = createApplyButton()

	init {
		layout = GridLayout(2,1)

		add(createTvEffectSizePanel())
		add(applyButton)

		border = BorderFactory.createLineBorder(Color(0, 0, 0), 4)
	}

	private fun createTvEffectSizeTextField(): JTextField {
		val res = JTextField("$DEFAULT_TV_EFFECT_SIZE")
		res.isEnabled = true
		return res
	}

	private fun createApplyButton(): JButton {
		val res = JButton(
			StringsManager.get("apply_color_effects")
		)
		res.isEnabled = true
		return res
	}

	private fun createTvEffectSizePanel():JPanel {
		val res = JPanel()
		res.layout = GridLayout(2,1)
		res.add(
			JLabel(
				StringsManager.get("set_tv_effect_size")
			)
		)
		res.add(tvEffectSizeTextField)
		return res
	}
}
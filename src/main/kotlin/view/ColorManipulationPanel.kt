package view

import java.awt.Color
import java.awt.GridLayout
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener


class ColorManipulationPanel: JPanel() {
	companion object {
		const val DEFAULT_TV_EFFECT_SIZE = 2
	}

	private val tvEffectSizeTextField = createTvEffectSizeTextField()
	private val applyButton = createApplyButton()

	private var tvEffectSize = DEFAULT_TV_EFFECT_SIZE

	init {
		layout = GridLayout(2,1)

		add(createTvEffectSizePanel())
		add(applyButton)

		border = BorderFactory.createLineBorder(Color(0, 0, 0), 4)
	}

	private fun createTvEffectSizeTextField(): JTextField {
		val res = JTextField("$DEFAULT_TV_EFFECT_SIZE")
		res.isEnabled = true

		res.addFocusListener(object : FocusListener {
			override fun focusGained(e: FocusEvent?) {}

			override fun focusLost(e: FocusEvent?) {
				changeTvEffectSize(res.text)
			}
		})

		return res
	}

	private fun changeTvEffectSize(rawNewValue:String) {
		try {
			val newValue = if (rawNewValue != "") {
				val temp = rawNewValue.toInt()
				if (temp < 0) {
					throw Exception("Can't have negative TV effect size.")
				}
				temp
			}else {
				0 // If empty, set to 0
			}
			println("Changed TV Effect Size from $tvEffectSize to $newValue.")
			tvEffectSize = newValue
		}catch(_:Exception) {}
		tvEffectSizeTextField.text = "$tvEffectSize"
	}

	private fun createApplyButton(): JButton {
		val res = JButton(
			StringsManager.get("apply_color_effects")
		)

		res.isEnabled = true

		res.addActionListener {
			println("ApplyButton pressed.")
		}

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
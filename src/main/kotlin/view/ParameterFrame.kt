package view

import model.applicationfunctions.SwingModel
import view.recolored_ui.OSCFButton
import view.recolored_ui.OSCFLabel
import view.recolored_ui.OSCFPanel
import view.recolored_ui.OSCFTextField
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.*

class ParameterFrame(private val model : SwingModel) : JFrame(), UpdatableComponent {
	companion object {
		const val WIDTH = 400
		const val HEIGHT = 600
	}

	private val tvEffectSizeTextField = createTvEffectSizeTextField()
	private val confirmButton = createConfirmButton()

	init {
		title = "${StringsManager.get("application_title")} - ${StringsManager.get("parameters_title")}"
		setSize(WIDTH, HEIGHT)
		isResizable = false
		layout = GridLayout(6,1)

		createIconImage()
		//add(createTitlePanel())
		add(createTvEffectSizePanel())
		add(OSCFPanel())
		add(OSCFPanel())
		add(OSCFPanel())
		add(OSCFPanel())
		add(confirmButton)

		defaultCloseOperation = DISPOSE_ON_CLOSE

		isVisible = true

		update()
	}

	override fun update() {
		tvEffectSizeTextField.text = "${model.getTvEffectSize()}"
	}

	// PRIVATE INSTANCE METHODS

	private fun createTitlePanel() : JPanel {
		val res = OSCFPanel()
		res.layout = BorderLayout()
		res.add(
			OSCFLabel(
				StringsManager.get("parameters_title")
			), BorderLayout.CENTER
		)
		return res
	}

	private fun createTvEffectSizeTextField(): JTextField {
		val res = OSCFTextField()

		/*
		res.addFocusListener(object : FocusListener {
			override fun focusGained(e: FocusEvent?) {}

			override fun focusLost(e: FocusEvent?) {
				changeTvEffectSize(res.text)
			}
		})

		res.addActionListener {
			changeTvEffectSize(res.text)
		}
		*/

		return res
	}

	private fun createConfirmButton() : JButton {
		val res = OSCFButton(
			StringsManager.get("confirm_parameters_change_button")
		)

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

			model.setTvEffectSize(newValue)
		}catch(_:Exception) {}
		tvEffectSizeTextField.text = "${model.getTvEffectSize()}"
	}

	private fun createTvEffectSizePanel():JPanel {
		val res = OSCFPanel()
		res.layout = GridLayout(1,2)
		res.add(
			OSCFLabel(StringsManager.get("set_tv_effect_size"))
		)
		res.add(tvEffectSizeTextField)
		return res
	}

	private fun createIconImage() {
		val img = ImageIcon(this::class.java.classLoader.getResource("application_icon.png"))
		iconImage = img.image
	}
}
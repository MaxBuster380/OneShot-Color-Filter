package view

import model.applicationfunctions.SwingModel
import view.applicationstates.ApplicationRunner
import view.recolored_ui.*
import java.awt.GridLayout
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.*
import view.applicationstates.ApplicationEvents.CHANGE_PARAMETER
import view.applicationstates.ApplicationEvents.APPLY_FILTER


class ColorManipulationPanel(private val model: SwingModel): OSCFPanel(), PropertyChangeListener, UpdatableComponent {

	//private val tvEffectSizeTextField = createTvEffectSizeTextField()
	private val parameterMenuButton = createParameterMenuButton()
	private val progressBar = createProgressBar()
	private val applyButton = createApplyButton()

	init {
		model.addPropertyChangeListener(this)

		layout = GridLayout(3,1)

		add(createTvEffectSizePanel())
		add(progressBar)
		add(applyButton)

		update()
	}

	/*
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
		/tvEffectSizeTextField.text = "${model.getTvEffectSize()}"
	}
	*/

	private fun createParameterMenuButton() : JButton {
		val res = OSCFButton(
			StringsManager.get("parameters_menu_button")
		)

		res.addActionListener {
			ParameterFrame(model)
		}

		return res
	}

	private fun createProgressBar():JProgressBar {
		val res = OSCFProgressBar()
		res.value = 0
		return res
	}

	/*
	private fun createTvEffectSizeTextField(): JTextField {
		val res = OSCFTextField()

		res.addFocusListener(object : FocusListener {
			override fun focusGained(e: FocusEvent?) {}

			override fun focusLost(e: FocusEvent?) {
				changeTvEffectSize(res.text)
			}
		})

		res.addActionListener {
			changeTvEffectSize(res.text)
		}

		return res
	}
	*/

	private fun createApplyButton(): JButton {
		val res = OSCFButton(
			StringsManager.get("apply_color_effects")
		)

		res.addActionListener {
			model.generateFilteredImage(progressBar)
		}

		return res
	}


	private fun createTvEffectSizePanel():JPanel {
		val res = OSCFPanel()
		res.layout = GridLayout(2,1)
		res.add(
			OSCFLabel (
				StringsManager.get("set_tv_effect_size")
			)
		)
		//res.add(tvEffectSizeTextField)
		res.add(parameterMenuButton)
		return res
	}


	override fun propertyChange(evt: PropertyChangeEvent?) {
		update()
	}

	override fun update() {
		val runner = ApplicationRunner.getInstance()
		//tvEffectSizeTextField.isEnabled = runner.canApply(CHANGE_PARAMETER)
		applyButton.isEnabled = runner.canApply(APPLY_FILTER)

		//tvEffectSizeTextField.text = "${model.getTvEffectSize()}"
	}
}
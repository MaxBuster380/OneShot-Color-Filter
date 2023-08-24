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

	private val parameterMenuButton = createParameterMenuButton()
	private val progressBar = createProgressBar()
	private val applyButton = createApplyButton()

	init {
		model.addPropertyChangeListener(this)

		layout = GridLayout(3,1)

		add(parameterMenuButton)
		add(progressBar)
		add(applyButton)

		update()
	}

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

	private fun createApplyButton(): JButton {
		val res = OSCFButton(
			StringsManager.get("apply_color_effects")
		)

		res.addActionListener {
			model.generateFilteredImage(progressBar)
		}

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
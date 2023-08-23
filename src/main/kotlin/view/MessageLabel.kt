package view

import model.applicationfunctions.SwingModel
import view.applicationstates.ApplicationRunner
import view.recolored_ui.OSCFLabel
import view.recolored_ui.OSCFPanel
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.JLabel
import view.applicationstates.ApplicationStates.IMAGE_APPLYING

class MessageLabel(private val model:SwingModel): OSCFPanel(), PropertyChangeListener{

	private val messageLabel = createMessageLabel()
	init {
		model.addPropertyChangeListener(this)
		add(messageLabel)
	}

	private fun createMessageLabel():JLabel {
		val res = OSCFLabel(
			StringsManager.get("message_app_started")
		)

		return res
	}

	override fun propertyChange(evt: PropertyChangeEvent?) {
		val runner = ApplicationRunner.getInstance()

		messageLabel.text = when (evt!!.propertyName) {
			"inputPath" -> StringsManager.get("message_image_inputted")
			"tvEffectSize" -> StringsManager.get("message_changed_tf_effect_size")
			"working" -> if (runner.getState() == IMAGE_APPLYING) {
				StringsManager.get("message_working")
			}else{
				StringsManager.get("message_done_applying")
			}
			"export" -> StringsManager.get("message_exported")
			"failure_import" -> "${StringsManager.get("message_import_failed")} ${evt.newValue}"
			"failure_export" -> "${StringsManager.get("message_export_failed")} ${evt.newValue}"
			else -> messageLabel.text
		}
	}
}
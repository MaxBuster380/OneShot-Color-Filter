package view

import model.applicationfunctions.SwingModel
import java.awt.Color
import java.awt.FlowLayout
import java.awt.GridLayout
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel

class MessageLabel(private val model:SwingModel): JPanel(), PropertyChangeListener{

	private val messageLabel = createMessageLabel()
	init {
		model.addPropertyChangeListener(this)
		add(messageLabel)
	}

	private fun createMessageLabel():JLabel {
		val res = JLabel(
			StringsManager.get("message_app_started")
		)

		return res
	}

	override fun propertyChange(evt: PropertyChangeEvent?) {
		messageLabel.text = when (evt!!.propertyName) {
			"inputPath" -> StringsManager.get("message_image_inputted")
			"tvEffectSize" -> StringsManager.get("message_changed_tf_effect_size")
			"working" -> if (model.isWorking()) {
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
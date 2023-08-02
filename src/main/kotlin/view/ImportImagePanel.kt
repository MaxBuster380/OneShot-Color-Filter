package view

import model.applicationfunctions.SwingModel
import java.awt.GridLayout
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JTextField

class ImportImagePanel(private val model: SwingModel):JPanel(),PropertyChangeListener {

	private val importedFilePathTextField = createImportedFilePathTextField()
	private val pickNewFileButton = createPickNewFileButton()

	init {
		layout = GridLayout(2,1)

		add(importedFilePathTextField)
		add(pickNewFileButton)
	}

	private fun createImportedFilePathTextField():JTextField {
		val res = JTextField(
			StringsManager.get("no_input_file_picked")
		)
		res.isEnabled = false

		return res
	}

	private fun createPickNewFileButton(): JButton {
		val res = JButton(
			StringsManager.get("pick_input_file")
		)

		res.addActionListener {
			println("PickNewFileButton pressed.")
		}

		res.isEnabled = true
		return res
	}

	override fun propertyChange(evt: PropertyChangeEvent?) {
		val newPath = model.getInputPath()
		importedFilePathTextField.text  = if (newPath != "") {
			newPath
		}else{
			StringsManager.get("no_input_file_picked")
		}
	}
}
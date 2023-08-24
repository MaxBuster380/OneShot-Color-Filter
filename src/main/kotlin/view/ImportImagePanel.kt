package view

import model.applicationfunctions.FileFetcher
import model.applicationfunctions.SwingModel
import view.applicationstates.ApplicationEvents.SELECT_IMAGE
import view.applicationstates.ApplicationRunner
import view.recolored_ui.OSCFButton
import view.recolored_ui.OSCFPanel
import view.recolored_ui.OSCFTextField
import java.awt.GridLayout
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.JButton
import javax.swing.JTextField

class ImportImagePanel(private val model: SwingModel):OSCFPanel(),PropertyChangeListener,UpdatableComponent {

	private val importedFilePathTextField = createImportedFilePathTextField()
	private val pickNewFileButton = createPickNewFileButton()

	init {
		model.addPropertyChangeListener(this)
		layout = GridLayout(2,1)

		add(importedFilePathTextField)
		add(pickNewFileButton)

		update()
	}

	private fun createImportedFilePathTextField():JTextField {
		val res = OSCFTextField()
		res.isEnabled = true
		res.isEditable = false

		return res
	}

	private fun createPickNewFileButton(): JButton {
		val res = OSCFButton(
			StringsManager.get("pick_input_file")
		)

		res.addActionListener {
			val inputPath = FileFetcher.pickPngFile()
			if (inputPath != null) {
				model.setInputPath(inputPath)
			}
		}

		res.isEnabled = true
		return res
	}

	override fun propertyChange(evt: PropertyChangeEvent) {
		update()
	}

	override fun update() {
		val runner = ApplicationRunner.getInstance()
		pickNewFileButton.isEnabled = runner.canApply(SELECT_IMAGE)

		importedFilePathTextField.text = if (model.getInputPath() != "") {
			model.getInputPath()
		}else{
			StringsManager.get("no_input_file_picked")
		}
	}
}
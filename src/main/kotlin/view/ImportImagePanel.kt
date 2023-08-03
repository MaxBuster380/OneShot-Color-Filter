package view

import model.applicationfunctions.FileFetcher
import model.applicationfunctions.SwingModel
import java.awt.Frame
import java.awt.GridLayout
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JTextField

class ImportImagePanel(private val model: SwingModel):JPanel(),PropertyChangeListener,UpdatableComponent {

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
		val res = JTextField()
		res.isEnabled = false

		return res
	}

	private fun createPickNewFileButton(): JButton {
		val res = JButton(
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
		val propertiesToUpdateOn = listOf("inputPath","working")
		if (evt.propertyName in propertiesToUpdateOn) {
			update()
		}
	}

	override fun update() {
		pickNewFileButton.isEnabled = !model.isWorking()

		importedFilePathTextField.text = if (model.getInputPath() != "") {
			model.getInputPath()
		}else{
			StringsManager.get("no_input_file_picked")
		}
	}
}
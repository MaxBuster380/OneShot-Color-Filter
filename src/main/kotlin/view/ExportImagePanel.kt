package view

import model.applicationfunctions.SwingModel
import view.recolored_ui.OSCFButton
import view.recolored_ui.OSCFLabel
import view.recolored_ui.OSCFPanel
import view.recolored_ui.OSCFTextField
import java.awt.FlowLayout
import java.awt.GridLayout
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.*

class ExportImagePanel(private val model: SwingModel): OSCFPanel(),PropertyChangeListener,UpdatableComponent {

	private val exportedFilePathTextField = createExportedFilePathTextField()
	private val pickExportDirectoryButton = createPickExportDirectoryButton()
	private val autoGeneratePathCheckBox = createAutoGeneratePathButton()
	private val exportImageButton = createExportImageButton()

	private var checkedAutoGeneratePath = true

	init {
		model.addPropertyChangeListener(this)
		layout = GridLayout(2,1)

		add(exportedFilePathTextField)
		add(exportImageButton)

		update()
	}

	private fun getAutoGeneratedPath():String? {
		val PREFIX = "OSCF_"
		val inputFile = model.getInputFile()
		return if (inputFile != null) {
			val os = System.getProperty("os.name").toLowerCase()
			when {
				os.contains("win") -> {
					"${inputFile.parent}\\${PREFIX}${inputFile.name}"
				}
				os.contains("nix") || os.contains("nux") || os.contains("aix") -> {
					"${inputFile.parent}/${PREFIX}${inputFile.name}"
				}
				else -> null
			}
		}else{
			null
		}
	}

	private fun pickAutoGeneratedPath() {
		val path = getAutoGeneratedPath()
		if (path != null) {
			model.setOutputPath(path)
		}
	}

	private fun createExportedFilePathTextField():JTextField {
		val res = OSCFTextField()
		res.isEnabled = true
		res.isEditable = false

		return res
	}

	private fun createPickExportDirectoryButton():JButton {
		val res = JButton(
			StringsManager.get("pick_output_directory")
		)

		return res
	}

	private fun createAutoGeneratePathButton():JCheckBox {
		val res = JCheckBox()

		res.isEnabled = true

		res.addActionListener {
			checkedAutoGeneratePath = res.isSelected
			update()
		}

		return res
	}

	private fun createExportImageButton(): JButton {
		val res = OSCFButton(StringsManager.get("export_image"))

		res.isEnabled = true

		res.addActionListener {
			model.saveFilteredWithTvImage()
		}

		return res
	}

	private fun createAutoPathPanel():JPanel {
		val res = OSCFPanel()
		res.layout = FlowLayout()
		res.add(autoGeneratePathCheckBox)
		res.add(
			OSCFLabel(
				StringsManager.get("option_auto_generate_output_path")
			)
		)
		return res
	}
	override fun propertyChange(evt: PropertyChangeEvent) {
		val propertiesToUpdateOn = listOf("outputPath","filteredWithTvImage", "inputPath","working", "unfilteredImage")
		if (evt.propertyName in propertiesToUpdateOn) {
			update()
		}
	}

	override fun update() {
		autoGeneratePathCheckBox.isSelected = checkedAutoGeneratePath
		pickExportDirectoryButton.isEnabled = !checkedAutoGeneratePath
		exportImageButton.isEnabled = model.getFilteredWithTvImage() != null && !model.isWorking()

		exportedFilePathTextField.text = if (model.getOutputPath() != "") {
			model.getOutputPath()
		}else{
			StringsManager.get("no_output_path_picked")
		}

		if (checkedAutoGeneratePath) {
			pickAutoGeneratedPath()
		}
	}

}
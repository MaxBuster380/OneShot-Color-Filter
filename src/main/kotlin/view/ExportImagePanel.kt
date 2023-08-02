package view

import model.applicationfunctions.SwingModel
import java.awt.Color
import java.awt.FlowLayout
import java.awt.Frame
import java.awt.GridLayout
import javax.swing.*

class ExportImagePanel(
	private val model: SwingModel,
	private val frame: Frame
):JPanel() {

	private val exportedFilePathTextField = createExportedFilePathTextField()
	private val autoGeneratePathButton = createAutoGeneratePathButton()
	private val exportImageButton = createExportImageButton()

	init {
		layout = GridLayout(3,1)

		add(exportedFilePathTextField)
		add(createAutoPathPanel())
		add(exportImageButton)
	}

	private fun createExportedFilePathTextField():JTextField {
		val res = JTextField(
			StringsManager.get("no_output_path_selected")
		)
		res.isEnabled = false

		return res
	}

	private fun createAutoGeneratePathButton():JCheckBox {
		val res = JCheckBox()
		res.isEnabled = true


		res.addActionListener {
			println("AutoGeneratePathButton checked.")
		}

		return res
	}

	private fun createExportImageButton(): JButton {
		val res = JButton(
			StringsManager.get("export_image")
		)
		res.isEnabled = true

		res.addActionListener {
			println("ExportButton pressed.")
		}

		return res
	}

	private fun createAutoPathPanel():JPanel {
		val res = JPanel()
		res.layout = FlowLayout()
		res.add(autoGeneratePathButton)
		res.add(
			JLabel(
				StringsManager.get("option_auto_generate_output_path")
			)
		)
		return res
	}
}
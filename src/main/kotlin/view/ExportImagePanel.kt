package view

import java.awt.Color
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.*

class ExportImagePanel:JPanel() {

	private val exportedFilePathTextField = createExportedFilePathTextField()
	private val autoGeneratePathButton = createAutoGeneratePathButton()
	private val exportImageButton = createExportImageButton()

	init {
		layout = GridLayout(3,1)

		add(exportedFilePathTextField)
		add(createAutoPathPanel())
		add(exportImageButton)

		border = BorderFactory.createLineBorder(Color(0, 0, 0), 4)
	}

	private fun createExportedFilePathTextField():JTextField {
		val res = JTextField("PLACEHOLDER OUTPUT IMAGE PATH")
		res.isEditable = false
		return res
	}

	private fun createAutoGeneratePathButton():JCheckBox {
		val res = JCheckBox()
		return res
	}

	private fun createExportImageButton(): JButton {
		val res = JButton("PLACEHOLDER EXPORT BUTTON")
		return res
	}

	private fun createAutoPathPanel():JPanel {
		val res = JPanel()
		res.layout = FlowLayout()
		res.add(autoGeneratePathButton)
		res.add(
			JLabel("PLACEHOLDER AUTO GENERATE PATH")
		)
		return res
	}
}
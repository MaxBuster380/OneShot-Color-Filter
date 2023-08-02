package view

import java.awt.Color
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JTextField

class ImportImagePanel:JPanel() {

	private val importedFilePathTextField = createImportedFilePathTextField()
	private val pickNewFileButton = createPickNewFileButton()

	init {
		layout = GridLayout(2,1)

		add(importedFilePathTextField)
		add(pickNewFileButton)

		border = BorderFactory.createLineBorder(Color(0, 0, 0), 4)
	}

	private fun createImportedFilePathTextField():JTextField {
		val res = JTextField("PLACEHOLDER INPUT IMAGE PATH")
		res.isEnabled = true
		return res
	}

	private fun createPickNewFileButton(): JButton {
		val res = JButton("PLACEHOLDER IMPORT BUTTON")
		res.isEnabled = true
		return res
	}
}
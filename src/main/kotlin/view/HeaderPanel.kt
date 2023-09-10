package view

import model.applicationfunctions.SwingModel
import model.applicationfunctions.urlopener.URLOpener
import model.applicationfunctions.urlopener.WindowsURLOpener
import view.recolored_ui.OSCFButton
import view.recolored_ui.OSCFLabel
import view.recolored_ui.OSCFPanel
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class HeaderPanel(model: SwingModel): OSCFPanel() {
	init {
		layout = GridLayout(2,1)

		add(createTopLayer())
		add(MessageLabel(model))
	}

	private fun createTitleLabel():JLabel {
		val res = OSCFLabel(
			StringsManager.get("application_title")
		)
		res.text = res.text.uppercase()
		res.isEnabled = true
		return res
	}

	private fun createRepositoryButton(): JButton {
		val res = OSCFButton(
			StringsManager.get("go_to_repository")
		)

		val repositoryUrl = "https://github.com/MaxBuster380/OneShot-Color-Filter"

		res.isEnabled = true

		res.addActionListener {
			val urlOpener : URLOpener = WindowsURLOpener()
			urlOpener.open(repositoryUrl)
		}

		return res
	}

	private fun createTopLayer():JPanel {
		val res = OSCFPanel()

		res.layout = GridLayout(1,3)

		res.add(createTitleLabel())
		res.add(OSCFLabel("1.1.1"))
		res.add(createRepositoryButton())

		return res
	}
}
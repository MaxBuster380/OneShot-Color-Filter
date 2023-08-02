package model.applicationfunctions

import java.awt.FileDialog
import java.awt.Frame
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame


class FileFetcher {
	companion object {

		fun loadImage(pathIn: String): WorkshopImage {
			val rawImage: BufferedImage = ImageIO.read(File(pathIn))
			return WorkshopImage(rawImage)
		}
		fun openFileDialog(window: Frame): String {
			val dialog = FileDialog(null as Frame?, "Select File to Open")
			dialog.mode = FileDialog.LOAD
			dialog.isVisible = true
			val file: String = dialog.directory + dialog.file
			dialog.dispose()
			return file
		}
	}
}
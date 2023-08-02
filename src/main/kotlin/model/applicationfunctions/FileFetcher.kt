package model.applicationfunctions

import java.awt.FileDialog
import java.awt.Frame
import java.awt.image.BufferedImage
import java.awt.image.ImageFilter
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFileChooser


class FileFetcher {
	companion object {

		fun loadImage(pathIn: String): WorkshopImage {
			val rawImage: BufferedImage = ImageIO.read(File(pathIn))
			return WorkshopImage(rawImage)
		}
		fun pickPngFile(): String? {
			val allowedExtensions = listOf(".png")

			val dialog = FileDialog(null as Frame?, "", FileDialog.LOAD).apply {
				// windows
				file = allowedExtensions.joinToString(";") { "*$it" } // e.g. '*.jpg'

				// linux
				setFilenameFilter { _, name ->
					allowedExtensions.any {
						name.endsWith(it)
					}
				}

				isVisible = true
			}

			val res = if (dialog.file != null){
				dialog.directory + dialog.file
			}else{
				null
			}

			dialog.dispose()

			return res
		}
	}
}
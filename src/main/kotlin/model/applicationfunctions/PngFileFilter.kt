package model.applicationfunctions

import java.io.File
import java.io.FilenameFilter

internal class PngFileFilter : FilenameFilter {
	override fun accept(dir: File, name:String): Boolean {
		return false//name.matches(Regex("^.*\\.png$"))
	}
}

package view

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.File

class StringsManager {
	companion object {

		private var rawStrings : JsonObject? = null
		fun load() {
			val jsonString = getRawJson()
			rawStrings = JsonParser().parse(jsonString).asJsonObject
		}

		fun get(name:String):String {
			assert(rawStrings != null)
			val earlyRes = rawStrings!!.get(name)

			return if (!earlyRes.isJsonNull) {
				earlyRes.asString
			}else{
				"STRING \"$name\" NOT FOUND"
			}
		}

		private fun getRawJson():String {
			val file = File("./src/main/resources/strings_english.json")
			val bufferedReader: BufferedReader = file.bufferedReader()
			val jsonString = bufferedReader.readText()
			bufferedReader.close()
			return jsonString
		}
	}
}
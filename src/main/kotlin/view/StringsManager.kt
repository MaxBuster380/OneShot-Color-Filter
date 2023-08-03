package view

import com.google.gson.JsonObject
import com.google.gson.JsonParser

class StringsManager {
	companion object {

		private val Companion: Unit = Unit

		private var rawStrings : JsonObject? = null
		fun load() {
			val jsonString = getRawJson()
			rawStrings = JsonParser().parse(jsonString).asJsonObject
		}

		fun get(name:String):String {
			assert(rawStrings != null)
			val earlyRes = rawStrings!!.get(name)

			return if (earlyRes != null) {
				earlyRes.asString
			}else{
				"STRING \"$name\" NOT FOUND"
			}
		}

		private fun getRawJson(): String {

			val res = this.Companion::class.java.classLoader.getResource("strings_english.json")?.readText()

			return res ?: throw NullPointerException("Couldn't find string file.")
		}
	}
}
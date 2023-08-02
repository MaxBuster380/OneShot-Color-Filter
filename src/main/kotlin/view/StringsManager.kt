package view

import com.google.gson.JsonObject
import com.google.gson.JsonParser

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

		private fun getRawJson(): String {
			val javaclass = {}.javaClass
			println("javaclass=$javaclass")
			val url = javaclass.getResource("strings_english.json")
			println("url=$url")
			return url?.readText()!!
		}
	}
}
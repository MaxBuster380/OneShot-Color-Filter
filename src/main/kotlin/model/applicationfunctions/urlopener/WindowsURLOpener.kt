package model.applicationfunctions.urlopener

/**
 * Open a URL in a browser from Windows.
 */
class WindowsURLOpener : URLOpener {

    override fun open(url : String) {
        val rt = Runtime.getRuntime()
        rt.exec("rundll32 url.dll,FileProtocolHandler $url")
    }
}
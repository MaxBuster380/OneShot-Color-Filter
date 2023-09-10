package model.applicationfunctions.urlopener

/**
 * Factory for instances of URLOpener based on the user's Operating System.
 */
class URLOpenerFactory {
    companion object {
        /**
         * Gives an instance of URLOpener fit for the user's Operating System.
         */
        fun get() : URLOpener {
            val operatingSystemName = System.getProperty("os.name")

            if (operatingSystemName.matches(Regex("^[Ww][Ii][Nn].*$"))) {
                return WindowsURLOpener()
            }

            return DefaultURLOpener()
        }
    }
}
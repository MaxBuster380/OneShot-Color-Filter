package model.applicationfunctions.urlopener

import java.awt.Desktop
import java.net.URI

/**
 * Implementation of URLOpener with no specific OS specified. Written for Linux support.
 */
class DefaultURLOpener : URLOpener {
    override fun open(url: String) {

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI(url))
        }
    }
}
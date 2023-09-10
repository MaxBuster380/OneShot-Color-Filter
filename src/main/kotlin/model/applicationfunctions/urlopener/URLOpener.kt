package model.applicationfunctions.urlopener

/**
 * Interface for opening a link in a browser.
 */
interface URLOpener {
    /**
     * Opens the URL address in a new tab in the browser.
     * @param url URL address to open in browser.
     */
    fun open(url : String)
}
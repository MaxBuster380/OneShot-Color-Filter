package view.applicationstates

import events.Event

enum class ApplicationEvents : Event {

	/**
	 * Event when the user picks an image to work on.
	 */
	SELECT_IMAGE,

	/**
	 * Event when the user changes at least one parameter.
	 */
	CHANGE_PARAMETER,

	/**
	 * Event when the user applies the filter.
	 */
	APPLY_FILTER,

	/**
	 * Event when the system is done with a task.
	 */
	DONE,

	/**
	 * Event when the user exports their work.
	 */
	EXPORT,

	/**
	 * Event when the user opens the recoloring parameters page.
	 */
	OPEN_RECOLORING_PARAMETERS,

	/**
	 * Event when the user closes the recoloring parameters page.
	 */
	CLOSE_RECOLORING_PARAMETERS
}
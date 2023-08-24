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
	EXPORT
}
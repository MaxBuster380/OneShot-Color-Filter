package view.applicationstates

import states.TerminalState

enum class ApplicationStates : TerminalState {

	/**
	 * Starting state of the application, with no file selected.
	 */
	NO_FILE,

	/**
	 * State where the user selected an image to work on, filter not applied.
	 */
	IMAGE_UNAPPLIED,

	/**
	 * State where the user selected an image to work on, filter is currently being applied.
	 */
	IMAGE_APPLYING,

	/**
	 * State where the user selected an image to work on, filter applied.
	 */
	IMAGE_APPLIED,

	/**
	 * State where the recoloring parameters menu is opened from NO_FILE.
	 * @see NO_FILE
	 */
	RECOLORING_PARAMETERS_NO_FILE,

	/**
	 * State where the recoloring parameters menu is opened from IMAGE_UNAPPLIED.
	 * @see IMAGE_UNAPPLIED
	 */
	RECOLORING_PARAMETERS_IMAGE_UNAPPLIED,

	/**
	 * State where the recoloring parameters menu is opened from IMAGE_APPLIED.
	 * @see IMAGE_APPLIED
	 */
	RECOLORING_PARAMETERS_IMAGE_APPLIED
}
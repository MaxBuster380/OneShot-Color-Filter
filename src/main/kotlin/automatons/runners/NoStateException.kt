package runners

/**
 * Exception thrown when a runner has no current states, and thus can not run anymore.
 */
class NoStateException(text : String) : IllegalStateException(text) {
	constructor():this("")
}
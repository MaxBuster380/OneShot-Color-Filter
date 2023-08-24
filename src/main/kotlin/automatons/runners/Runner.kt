package runners

import automatons.Automaton
import events.Event
import states.State
import states.TerminalState

/**
 * Interface for runners.
 * A runner is an object that runs an automaton, keeping track of the current states and changing them based on the events it receives.
 * @see Automaton
 * @see State
 * @see Event
 */
interface Runner {
	/**
	 * Changes the current states by derivating with an event.
	 * @param event Event to derivate with.
	 * @throws NoStateException when the runner has no current states after derivating.
	 */
	fun apply(event : Event)

	/**
	 * Checks if changing the current states by an event leads to an error.
	 * @param event Event to test on.
	 * @return True if using apply(event) throws a NoStateException.
	 */
	fun canApply(event : Event) : Boolean

	/**
	 * Checks if the automaton can be terminated correctly.
	 * @return True if one of the current states is a terminal state.
	 * @see TerminalState
	 */
	fun isOver() : Boolean

	/**
	 * Returns the current states of the runner.
	 * @return the states the runner is currently in.
	 */
	fun getCurrentStates() : Set<State>

	/**
	 * Returns the runner's automaton.
	 * @return The automaton given in the constructor of the instance.
	 */
	fun getAutomaton() : Automaton
}
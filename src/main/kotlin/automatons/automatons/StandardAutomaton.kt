package automatons

import events.Event
import states.State

/**
 * Basic, unoptimized implementation of Automaton.
 * @see Automaton
 */
class StandardAutomaton : Automaton {
	data class Transition(val startState : State, val event : Event, val endState : State)
	private val transitions = mutableListOf<Transition>()

	// PUBLIC INSTANCE METHODS

	override fun add(startState : State, event : Event, endState : State) : Automaton {
		val newTransition = Transition(startState, event, endState)

		if (!isRedundant(newTransition)) {
			transitions += newTransition
		}

		return this
	}

	override fun derivate(startState : State, event : Event) : Set<State> {
		val res = mutableSetOf<State>()

		for(currentTransition in transitions) {
			if (currentTransition.startState == startState && currentTransition.event == event) {
				res.add(currentTransition.endState)
			}
		}

		return res
	}

	override fun getEventsFrom(startState : State) : Set<Event> {
		val res = mutableSetOf<Event>()

		for(currentTransition in transitions) {
			if (currentTransition.startState == startState) {
				res.add(currentTransition.event)
			}
		}

		return res
	}

	override fun getEventsTo(endState : State) : Set<Event> {
		val res = mutableSetOf<Event>()

		for(currentTransition in transitions) {
			if (currentTransition.endState == endState) {
				res.add(currentTransition.event)
			}
		}

		return res
	}

	override fun integrate(event : Event, endState : State) : Set<State> {
		val res = mutableSetOf<State>()

		for(currentTransition in transitions) {
			if (currentTransition.event == event && currentTransition.endState == endState) {
				res.add(currentTransition.startState)
			}
		}

		return res
	}

	override fun getEventsBetween(startState : State, endState : State) : Set<Event> {
		val res = mutableSetOf<Event>()

		for(currentTransition in transitions) {
			if (currentTransition.startState == startState && currentTransition.endState == endState) {
				res.add(currentTransition.event)
			}
		}

		return res
	}

	override fun getEndStatesFrom(startState : State) : Set<State> {
		val res = mutableSetOf<State>()

		for(currentTransition in transitions) {
			if (currentTransition.startState == startState) {
				res.add(currentTransition.endState)
			}
		}

		return res
	}

	override fun getStartStatesFrom(endState : State) : Set<State> {
		val res = mutableSetOf<State>()

		for(currentTransition in transitions) {
			if (currentTransition.endState == endState) {
				res.add(currentTransition.startState)
			}
		}

		return res
	}

	override fun getEventDestinations(event : Event) : Set<State> {
		val res = mutableSetOf<State>()

		for(currentTransition in transitions) {
			if (currentTransition.event == event) {
				res.add(currentTransition.endState)
			}
		}

		return res
	}

	override fun getEventOrigins(event : Event) : Set<State> {
		val res = mutableSetOf<State>()

		for(currentTransition in transitions) {
			if (currentTransition.event == event) {
				res.add(currentTransition.startState)
			}
		}

		return res
	}

	// PRIVATE INSTANCE METHODS

	/**
	 * Checks if the transition has already been added.
	 */
	private fun isRedundant(newTransition : Transition) : Boolean {
		for(currentTransition in transitions) {
			if (currentTransition == newTransition) {
				return true
			}
		}
		return false
	}
}
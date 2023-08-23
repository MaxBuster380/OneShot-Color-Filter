package view.applicationstates

import automatons.Automaton
import events.Event
import runners.Runner
import runners.StandardRunner
import states.State
import view.applicationstates.ApplicationStates.NO_FILE

/**
 * Singleton for the Runner of the application's state.
 */
class ApplicationRunner : Runner {

	// SINGLETON SETUP
	private var instance : ApplicationRunner? = null
	/**
	 * Returns the only instance of ApplicationRunner.
	 */
	fun getInstance() : ApplicationRunner {
		if (instance == null) {
			instance = ApplicationRunner()
		}
		return instance!!
	}

	private val subRunner : Runner

	init {
		val automaton = ApplicationAutomatonFactory.create()
		subRunner = StandardRunner(automaton = automaton, setOf(NO_FILE))
	}

	// PUBLIC INSTANCE METHODS

	// PUBLIC INSTANCE METHODS - INTERFACE Runner

	override fun apply(event: Event) {
		subRunner.apply(event)
	}

	override fun canApply(event: Event): Boolean {
		return subRunner.canApply(event)
	}

	override fun isOver(): Boolean {
		return subRunner.isOver()
	}

	override fun getCurrentStates(): Set<State> {
		return subRunner.getCurrentStates()
	}

	override fun getAutomaton(): Automaton {
		return subRunner.getAutomaton()
	}
}
package states

import automatons.Automaton

/**
 * Interface for terminal states.
 * A terminal state is a state in which a runner should be at the end of its usage.
 * @see Automaton
 * @see State
 */
interface TerminalState : State
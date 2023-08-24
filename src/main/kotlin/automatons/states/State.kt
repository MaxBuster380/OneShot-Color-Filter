package states

import automatons.Automaton
import runners.Runner

/**
 * Interface for states an automaton can have and a runner can be in.
 * This is a generic state, in which a runner should not finish in.
 * @see Automaton
 * @see Runner
 * @see TerminalState
 */
interface State
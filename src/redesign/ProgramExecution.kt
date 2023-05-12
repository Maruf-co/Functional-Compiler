package redesign

import program.ProgramExecution
import program.ProgramState
import program.SyntaxException
import tokens.Identifier
import tokens.LiteralToken


public class ProgramExecution {
    @kotlin.Throws(IllegalStateException::class, SyntaxException::class)
    fun evaluateElement(element: Element, state: ProgramState?): LiteralToken? {
        return if (element.isTerminal) {
            val token = element.data
            if (token.isIdentifier) {
                ProgramExecution.evaluateIdentifier(token as Identifier, state)
            } else {
                token as LiteralToken
            }
        } else {
            ProgramExecution.evaluateElements(element, state)
        }
    }

    @kotlin.Throws(SyntaxException::class)
    fun evaluateIdentifier(identifier: Identifier, state: ProgramState): LiteralToken? {
        return if (state.isDefined(identifier)) {
            state.getValue(identifier)
        } else {
            throw SyntaxException("Variable " + identifier.value + " is not defined")
        }
    }
}

fun executeProgram(program: List) {
    val state = ProgramState(HashMap())
    var result: Element = Unit()
    for (element in program.elements) {
        result = element.evaluate(state)
    }
}
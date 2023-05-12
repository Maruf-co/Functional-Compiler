package redesign

import program.SyntaxException
import tokens.LiteralToken

abstract class Element() {
    abstract fun evaluate(state: ProgramState, input: kotlin.collections.List<Element>? = null): Element
}

class List(val elements: ArrayList<Element>) : Element() {

    override fun evaluate(state: ProgramState, input: kotlin.collections.List<Element>?): Element {
        when (val firstElement = elements[0].evaluate(state)) {
            is Identifier -> {
                if (isSpecialForm(firstElement.name)) {
                    return evaluateSpecialForm(this, state)
                }
                if (isBuiltIn(firstElement.name)) {
                    return evaluateBuiltId(firstElement, elements.drop(1) as ArrayList<Element>, state)
                }
            }
            is Function -> {
                return firstElement.evaluate(state, elements.drop(1))
            }
        }
        throw SyntaxException("Can't apply such type")
    }
}

class Identifier(val name: String) : Element() {
    override fun evaluate(state: ProgramState, input: kotlin.collections.List<Element>?): Element {
        if (state.variables.containsKey(name)) {
            return state.variables[name]!!
        }
        return this
    }
}

class Literal(val value: LiteralToken): Element() {
    override fun evaluate(state: ProgramState, input: kotlin.collections.List<Element>?): Element {
        return this
    }

}

class Function(val arguments: List, val body: Element, private val name: String?): Element() {
    override fun evaluate(state: ProgramState, input: kotlin.collections.List<Element>?): Element {
        if (arguments.elements.size != input!!.size) {
            throw SyntaxException(if (name != null)
                "$name function accepts ${arguments.elements.size} arguments, given: ${input.size}"
            else "Function accepts ${arguments.elements.size} arguments, given: ${input.size}" )
        }
        val localState = ProgramState(HashMap(state.variables))
        for (i in 0..arguments.elements.size) {
            val argument = arguments.elements[i] as Identifier
            localState.variables[argument.name] = input[i]
        }
        return body.evaluate(state, input)
    }

}
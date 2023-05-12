package redesign

import program.SyntaxException
import tokens.LiteralToken

abstract class Element() {
    abstract fun evaluate(state: ProgramState, input: kotlin.collections.List<Element>? = null): Element

    abstract fun print(): String
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

    override fun print(): String {
        return "List"
    }
}

class Identifier(val name: String) : Element() {
    override fun evaluate(state: ProgramState, input: kotlin.collections.List<Element>?): Element {
        if (state.variables.containsKey(name)) {
            return state.variables[name]!!
        }
        return this
    }

    override fun print(): String {
        return "Identifier"
    }
}

class Literal(val value: LiteralToken): Element() {
    override fun evaluate(state: ProgramState, input: kotlin.collections.List<Element>?): Element {
        return this
    }

    override fun print(): String {
        return value.value.toString()
    }

}

class Function(private val arguments: ArrayList<Identifier>, private val body: Element, private val name: String?): Element() {
    override fun evaluate(state: ProgramState, input: kotlin.collections.List<Element>?): Element {
        if (arguments.size != input!!.size) {
            throw SyntaxException(if (name != null)
                "$name function accepts ${arguments.size} arguments, given: ${input.size}"
            else "Function accepts ${arguments.size} arguments, given: ${input.size}" )
        }
        val localState = ProgramState(HashMap(state.variables))
        for (i in 0..arguments.size) {
            val argument = arguments[i]
            localState.variables[argument.name] = input[i]
        }
        return body.evaluate(state, input)
    }

    override fun print(): String {
       if (name != null) return name
        return "Print"
    }

}

class Unit(): Element() {
    override fun evaluate(state: ProgramState, input: kotlin.collections.List<Element>?): Element {
        return this;
    }

    override fun print(): String {
        return "Unit"
    }

}
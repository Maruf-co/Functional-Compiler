package redesign

import program.SyntaxException
import tokens.LiteralToken
import kotlin.collections.List


fun isKeyword(name: String): Boolean {
    return isBuiltIn(name) || isSpecialForm(name)
}

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
        if (isKeyword(name)) {
            return this
        }
        if (!isKeyword(name) && state.variables.containsKey(name)) {
            return state.variables[name]!!
        }
        throw SyntaxException("Variable $name undefined")
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

class Function(private val arguments: ArrayList<Identifier>, private val body: Element, private val name: String? = null): Element() {
    override fun evaluate(state: ProgramState, input: kotlin.collections.List<Element>?): Element {
        if (arguments.size != input!!.size) {
            throw SyntaxException(if (name != null)
                "$name function accepts ${arguments.size} arguments, given: ${input.size}"
            else "Function accepts ${arguments.size} arguments, given: ${input.size}" )
        }
        val localState = ProgramState(HashMap(state.variables))
        addArgumentsToState(arguments, input, localState)
        return body.evaluate(localState, input)
    }

    override fun print(): String {
       if (name != null) return name
        return "Print"
    }

}

class Unit(): Element() {
    override fun evaluate(state: ProgramState, input: kotlin.collections.List<Element>?): Element {
        return this
    }

    override fun print(): String {
        return "Unit"
    }

}

class Break(): Element() {
    override fun evaluate(state: ProgramState, input: List<Element>?): Element {
        return this
    }

    override fun print(): String {
        return "Break"
    }
}

class ReturnElement(val value: Element) : Element() {
    override fun evaluate(state: ProgramState, input: List<Element>?): Element {
        return this
    }

    override fun print(): String {
        return "Should not be printed"
    }
}

class NullElement: Element() {
    override fun evaluate(state: ProgramState, input: List<Element>?): Element {
        return this
    }

    override fun print(): String {
        return "null"
    }

}
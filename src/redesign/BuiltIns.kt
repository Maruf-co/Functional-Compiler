package redesign

import program.SyntaxException
import tokens.NumberLiteral

var builIns = setOf(
        "plus"
)

fun isBuiltIn(name: String): Boolean {
    return builIns.contains(name)
}

fun evaluateBuiltId(identifier: Identifier, elements: ArrayList<Element>, state: ProgramState): Element {
    when (identifier.name) {
        "plus" -> {
            if (elements.size != 2) {
                throw SyntaxException("plus requires two arguments")
            }

            val tokens = elements.map { it.evaluate(state) }
            val isInvalid = tokens.all { it is Literal && it.value is NumberLiteral }
            if (isInvalid) {
                throw SyntaxException("plus requires all arguments to be numbers")
            }

            val first = ((tokens[0] as Literal).value as NumberLiteral).value
            val second = ((tokens[1] as Literal).value as NumberLiteral).value
            return Literal(NumberLiteral(first + second))
        }
    }
    throw SyntaxException("${identifier.name} is not a built-in function")
}
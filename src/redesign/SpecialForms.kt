package redesign

import program.SyntaxException
import redesign.Unit

var specialForms = setOf(
        "quote",
        "setq",
        "func"
)

fun isSpecialForm(name: String): Boolean {
    return specialForms.contains(name)
}

fun evaluateSpecialForm(element: Element, state: ProgramState): Element {
    when (element) {
        is List -> {
            when ((element.elements[0] as Identifier).name) {
                "quote" -> {
                    if (element.elements.size != 2) {
                        throw SyntaxException("Quote requires one element.")
                    }

                    return element.elements[1]
                }
                "setq" -> {
                    if (element.elements.size != 3) {
                        throw SyntaxException("Setq requires two elements.")
                    }
                    if (element.elements[1] !is Identifier) {
                        throw SyntaxException("First argument of set must be an Atom.")
                    }

                    val identifier = element.elements[1] as Identifier
                    val value = element.elements[2].evaluate(state)
                    state.variables[identifier.name] = value
                    return Unit()
                }
                "func" -> {
                    if (element.elements.size != 4) {
                        throw SyntaxException("Func requires three elements.")
                    }
                    if (element.elements[1] !is Identifier) {
                        throw SyntaxException("First argument of func must be an Atom.")
                    }
                    val identifier = element.elements[1] as Identifier
                    if (element.elements[2] !is List) {
                        throw SyntaxException("Second argument of func must be a List")
                    }
                    val argList = element.elements[2] as List

                    val arguments = ArrayList<Identifier>()
                    for (arg in argList.elements) {
                        when (arg) {
                            is Identifier -> {
                                arguments.add(arg)
                            }
                            else -> {
                                throw SyntaxException("All arguments must be Atoms in function declaration.")
                            }
                        }
                    }

                    state.variables[identifier.name] = Function(arguments, element.elements[3], identifier.name)
                    return Unit()
                }
            }
        }
    }
}
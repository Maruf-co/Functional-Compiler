package redesign

import program.SyntaxException

var specialForms = setOf(
    "quote",
    "setq",
    "func",
    "lambda",
    "prog"
)

fun isSpecialForm(name: String): Boolean {
    return specialForms.contains(name)
}

fun checkAndGetArgumentsList(list: Element, keyword: String): ArrayList<Identifier> {
    if (list !is List) {
        throw SyntaxException("Second argument of $keyword must be a List")
    }

    val arguments = ArrayList<Identifier>()
    for (arg in list.elements) {
        when (arg) {
            is Identifier -> {
                arguments.add(arg)
            }
            else -> {
                throw SyntaxException("All arguments must be Atoms in $keyword declaration.")
            }
        }
    }
    return arguments
}

fun checkAndCreateFunction(list: Element, body: Element, keyword: String): Function {
    val arguments = checkAndGetArgumentsList(list, keyword)
    return Function(arguments, body)
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
                    if (isKeyword(identifier.name)) {
                        throw SyntaxException("Func name shadows the keyword")
                    }

                    state.variables[identifier.name] = checkAndCreateFunction(element.elements[2], element.elements[3], "func")
                    return Unit()
                }
                "lambda" -> {
                    if (element.elements.size != 3) {
                        throw SyntaxException("Lambda requires three elements.")
                    }
                    return checkAndCreateFunction(element.elements[1], element.elements[2], "lambda")
                }
                "prog" -> {
                    if (element.elements.size != 3) {
                        throw SyntaxException("Prog requires three elements.")
                    }
                    if (element.elements[2] !is List) {
                        throw SyntaxException("Prog requires list as a second argument.")
                    }

                    val list = element.elements[1]
                    val arguments = checkAndGetArgumentsList(list, "prog")
                    val values = arguments.map { NullElement() }
                    val localState = ProgramState(HashMap(state.variables))
                    addArgumentsToState(arguments, values, localState)

                    val elements = element.elements[2] as List
                    for (element in elements.elements) {
                        val result = element.evaluate(localState)
                        if (result is ReturnElement) {
                            return result.value
                        }
                    }
                    return Unit()
                }
            }
        }
    }
    throw SyntaxException("Not a special form")
}
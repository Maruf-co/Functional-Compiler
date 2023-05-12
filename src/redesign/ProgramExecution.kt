package redesign

import syntax.LISPParser

fun executeProgram(program: List) {
    val state = ProgramState(HashMap())
    var result: Element = Unit()
    for (element in program.elements) {
        result = element.evaluate(state)
    }
}
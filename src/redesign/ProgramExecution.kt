package redesign

fun executeProgram(program: List): String {
    val state = ProgramState(HashMap())
    var result: Element = Unit()
    for (element in program.elements) {
        result = element.evaluate(state)
    }
    return result.print()
}
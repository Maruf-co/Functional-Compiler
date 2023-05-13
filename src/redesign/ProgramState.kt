package redesign

data class ProgramState(
        val variables: HashMap<String, Element>
)

fun addArgumentsToState(
        arguments: ArrayList<Identifier>,
        input: kotlin.collections.List<Element>,
        state: ProgramState
) {
        for (i in 0 until arguments.size) {
                val argument = arguments[i]
                state.variables[argument.name] = input[i]
        }
}
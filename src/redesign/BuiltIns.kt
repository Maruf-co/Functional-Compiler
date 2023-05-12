package redesign

var builIns = setOf(
        "quote"
)

fun isBuiltIn(name: String): Boolean {
    return builIns.contains(name)
}

fun evaluateBuiltId(identifier: Identifier, element: ArrayList<Element>, state: ProgramState): Element {

}
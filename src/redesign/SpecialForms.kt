package redesign

var specialForms = setOf(
        "quote"
)

fun isSpecialForm(name: String): Boolean {
    return specialForms.contains(name)
}

fun evaluateSpecialForm(element: Element, state: ProgramState): Element {

}
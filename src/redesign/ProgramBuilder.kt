package redesign

import syntax.LISPParser
import syntax.LISPParser.TreeNode
import tokens.IdentifierToken
import tokens.LiteralToken

fun getProgram(node: LISPParser.TreeNode): Element {
    if (node.isTerminal) {
        when (val value = node.data) {
            is LiteralToken -> {
                return Literal(value)
            }
            is IdentifierToken -> {
                return Identifier(value.identifier)
            }
        }
    }
    val elements = ArrayList<Element>()
    for (node in node.children) {
        elements.add(getProgram(node))
    }
    return List(elements)
}
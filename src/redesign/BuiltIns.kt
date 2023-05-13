package redesign

import program.SyntaxException
import tokens.BooleanLiteralToken
import tokens.LiteralToken
import tokens.NumberLiteral

var builIns = setOf(
    "plus",
    "minus",
    "times",
    "divide",
    "isbool",
    "isreal",
    "and",
    "or",
    "xor",
    "not",
    "equal",
    "nonequal",
    "less",
    "lesseq",
    "greater",
    "greatereq"
)

fun isBuiltIn(name: String): Boolean {
    return builIns.contains(name)
}

fun evaluateBuiltId(identifier: Identifier, elements: ArrayList<Element>, state: ProgramState): Element {
    when (identifier.name) {
        "plus" -> {
            if (elements.size != 2) {
                throw SyntaxException("plus requires two arguments, provided ${elements.size}")
            }

            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && it.value is NumberLiteral }
            if (!isValid) {
                throw SyntaxException("plus requires all arguments to be numbers")
            }

            val first = ((tokens[0] as Literal).value as NumberLiteral).value
            val second = ((tokens[1] as Literal).value as NumberLiteral).value
            return Literal(NumberLiteral(first + second))
        }
        "minus" -> {
            if (elements.size != 2) {
                throw SyntaxException("minus requires two arguments, provided ${elements.size}")
            }

            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && it.value is NumberLiteral }
            if (!isValid) {
                throw SyntaxException("minus requires all arguments to be numbers")
            }

            val first = ((tokens[0] as Literal).value as NumberLiteral).value
            val second = ((tokens[1] as Literal).value as NumberLiteral).value
            return Literal(NumberLiteral(first - second))
        }
        "times" -> {
            if (elements.size != 2) {
                throw SyntaxException("times requires two arguments, provided ${elements.size}")
            }

            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && it.value is NumberLiteral }
            if (!isValid) {
                throw SyntaxException("times requires all arguments to be numbers")
            }

            val first = ((tokens[0] as Literal).value as NumberLiteral).value
            val second = ((tokens[1] as Literal).value as NumberLiteral).value
            return Literal(NumberLiteral(first * second))
        }
        "divide" -> {
            if (elements.size != 2) {
                throw SyntaxException("divide requires two arguments, provided ${elements.size}")
            }

            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && it.value is NumberLiteral }
            if (!isValid) {
                throw SyntaxException("divide requires all arguments to be numbers")
            }

            val first = ((tokens[0] as Literal).value as NumberLiteral).value
            val second = ((tokens[1] as Literal).value as NumberLiteral).value
            return Literal(NumberLiteral(first / second))
        }
        "and" -> {
            if(elements.size != 2){
                throw SyntaxException("and requires two arguments, provided ${elements.size}")
            }

            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && it.value is BooleanLiteralToken }
            if (!isValid) {
                throw SyntaxException("and requires all arguments to be bools")
            }

            val first = ((tokens[0] as Literal).value as BooleanLiteralToken).value
            val second = ((tokens[1] as Literal).value as BooleanLiteralToken).value
            return Literal(BooleanLiteralToken(first and second))
        }
        "or" -> {
            if(elements.size != 2){
                throw SyntaxException("or requires two arguments, provided ${elements.size}")
            }

            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && it.value is BooleanLiteralToken }
            if (!isValid) {
                throw SyntaxException("or requires all arguments to be bools")
            }

            val first = ((tokens[0] as Literal).value as BooleanLiteralToken).value
            val second = ((tokens[1] as Literal).value as BooleanLiteralToken).value
            return Literal(BooleanLiteralToken(first or second))
        }
        "xor" -> {
            if(elements.size != 2){
                throw SyntaxException("xor requires two arguments, provided ${elements.size}")
            }

            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && it.value is BooleanLiteralToken }
            if (!isValid) {
                throw SyntaxException("xor requires all arguments to be bools")
            }

            val first = ((tokens[0] as Literal).value as BooleanLiteralToken).value
            val second = ((tokens[1] as Literal).value as BooleanLiteralToken).value
            return Literal(BooleanLiteralToken(first xor second))
        }
        "not" -> {
            if(elements.size != 1){
                throw SyntaxException("not requires one argument, provided ${elements.size}")
            }

            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && it.value is BooleanLiteralToken }
            if (!isValid) {
                throw SyntaxException("not requires argument to be bool")
            }

            val first = ((tokens[0] as Literal).value as BooleanLiteralToken).value

            return Literal(BooleanLiteralToken(!first))
        }
        "equal" -> {
            if(elements.size != 2){
                throw SyntaxException("equal requires two arguments, provided ${elements.size}")
            }
            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && (it.value is NumberLiteral || it.value is BooleanLiteralToken)}

            if (!isValid) {
                throw SyntaxException("equal requires argument to be bool or number")
            }

            val first = (tokens[0] as Literal).value
            val second = (tokens[1] as Literal).value

            if(first.literalType == LiteralToken.LiteralType.BOOLEAN && second.literalType == LiteralToken.LiteralType.BOOLEAN){
                return Literal(BooleanLiteralToken(first == second))
            }
            else if(first.literalType == LiteralToken.LiteralType.NUMBER && second.literalType == LiteralToken.LiteralType.NUMBER){
                return Literal(BooleanLiteralToken(first == second))
            }
            else throw SyntaxException("equal requires arguments to be the same type")
        }
        "nonequal" -> {
            if(elements.size != 2){
                throw SyntaxException("nonequal requires two arguments, provided ${elements.size}")
            }
            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && (it.value is NumberLiteral || it.value is BooleanLiteralToken)}

            if (!isValid) {
                throw SyntaxException("nonequal requires argument to be bool or number")
            }

            val first = (tokens[0] as Literal).value
            val second = (tokens[1] as Literal).value

            if(first.literalType == LiteralToken.LiteralType.BOOLEAN && second.literalType == LiteralToken.LiteralType.BOOLEAN){
                return Literal(BooleanLiteralToken(first != second))
            }
            else if(first.literalType == LiteralToken.LiteralType.NUMBER && second.literalType == LiteralToken.LiteralType.NUMBER){
                return Literal(BooleanLiteralToken(first != second))
            }
            else throw SyntaxException("nonequal requires arguments to be the same type")
        }
        "less" -> {
            if(elements.size != 2){
                throw SyntaxException("less requires two arguments, provided ${elements.size}")
            }
            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && it.value is NumberLiteral}

            if (!isValid) {
                throw SyntaxException("less requires argument to be a number")
            }

            val first = ((tokens[0] as Literal).value as NumberLiteral).value
            val second = ((tokens[1] as Literal).value as NumberLiteral).value

            return Literal(BooleanLiteralToken(first < second))
        }
        "lesseq" -> {
            if(elements.size != 2){
                throw SyntaxException("lesseq requires two arguments, provided ${elements.size}")
            }
            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && it.value is NumberLiteral}

            if (!isValid) {
                throw SyntaxException("lesseq requires argument to be a number")
            }

            val first = ((tokens[0] as Literal).value as NumberLiteral).value
            val second = ((tokens[1] as Literal).value as NumberLiteral).value

            return Literal(BooleanLiteralToken(first <= second))
        }
        "greater" -> {
            if(elements.size != 2){
                throw SyntaxException("greater requires two arguments, provided ${elements.size}")
            }
            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && it.value is NumberLiteral}

            if (!isValid) {
                throw SyntaxException("greater requires argument to be a number")
            }

            val first = ((tokens[0] as Literal).value as NumberLiteral).value
            val second = ((tokens[1] as Literal).value as NumberLiteral).value

            return Literal(BooleanLiteralToken(first > second))
        }
        "greatereq" -> {
            if(elements.size != 2){
                throw SyntaxException("greatereq requires two arguments, provided ${elements.size}")
            }
            val tokens = elements.map { it.evaluate(state) }
            val isValid = tokens.all { it is Literal && it.value is NumberLiteral}

            if (!isValid) {
                throw SyntaxException("greatereq requires argument to be a number")
            }

            val first = ((tokens[0] as Literal).value as NumberLiteral).value
            val second = ((tokens[1] as Literal).value as NumberLiteral).value

            return Literal(BooleanLiteralToken(first >= second))
        }
    }
    throw SyntaxException("${identifier.name} is not a built-in function")
}
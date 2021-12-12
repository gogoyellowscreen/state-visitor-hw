package visitors

import tokens.*
import java.util.*

class CalcVisitor : TokenVisitor {
    private val stack = Stack<NumberToken>()

    override fun visit(token: AddToken) = visitBinaryOperation { left, right -> left + right }
    override fun visit(token: MultiplyToken) = visitBinaryOperation { left, right -> left * right }
    override fun visit(token: SubtractToken) = visitBinaryOperation { left, right -> left - right }
    override fun visit(token: DivideToken) = visitBinaryOperation { left, right -> left / right }

    override fun visit(token: NumberToken) {
        stack.push(token)
    }

    override fun visit(token: LeftBraceToken) = throw IllegalStateException("Polish notation shouldn't contain braces")
    override fun visit(token: RightBraceToken) = throw IllegalStateException("Polish notation shouldn't contain braces")
    override fun visit(token: EndToken) = assert(stack.isEmpty())
    override fun visit(token: ErrorToken) = throw IllegalStateException("Valid expression expected.")
    override fun visit(token: StartToken) = assert(stack.isEmpty())

    fun visit(tokens: List<Token>) : Int {
        tokens.forEach { it.accept(this) }
        val result = stack.pop()
        assert(stack.isEmpty())
        return result.number
    }

    private fun visitBinaryOperation(binaryOperation: (Int, Int) -> Int) {
        val right = stack.pop().number
        val left = stack.pop().number
        stack.push(NumberToken { binaryOperation(left, right) })
    }
}

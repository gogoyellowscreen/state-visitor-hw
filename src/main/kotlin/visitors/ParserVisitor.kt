package visitors

import tokens.AddToken
import tokens.DivideToken
import tokens.EndToken
import tokens.ErrorToken
import tokens.LeftBraceToken
import tokens.MultiplyToken
import tokens.NumberToken
import tokens.OperationToken
import ParseException
import tokens.RightBraceToken
import tokens.StartToken
import tokens.SubtractToken
import tokens.Token
import java.util.*

class ParserVisitor : TokenVisitor {
    private val stack = Stack<OperationToken>()
    private val result = mutableListOf<Token>()

    override fun visit(token: AddToken) = visitOperation(token)

    override fun visit(token: MultiplyToken) = visitOperation(token)

    override fun visit(token: SubtractToken) = visitOperation(token)

    override fun visit(token: DivideToken) = visitOperation(token)

    override fun visit(token: NumberToken) {
        result.add(token)
    }

    override fun visit(token: LeftBraceToken) {
        stack.push(token)
    }

    override fun visit(token: RightBraceToken) {
        while (stack.isNotEmpty() && stack.peek() !is LeftBraceToken) {
            result.add(stack.pop())
        }
        if (stack.isNotEmpty()) stack.pop()
    }

    override fun visit(token: EndToken) {
        while (stack.isNotEmpty()) {
            result.add(stack.pop())
        }
    }

    override fun visit(token: ErrorToken) = throw ParseException(token.message)

    override fun visit(token: StartToken) = Unit

    fun visit(tokens: List<Token>) : List<Token> {
        tokens.forEach { it.accept(this) }
        return result
    }

    private fun visitOperation(token: OperationToken) {
        while (stack.isNotEmpty() && stack.peek().priority >= token.priority) {
            result.add(stack.pop())
        }
        stack.push(token)
    }
}

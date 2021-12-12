package visitors

import tokens.AddToken
import tokens.DivideToken
import tokens.EndToken
import tokens.ErrorToken
import tokens.LeftBraceToken
import tokens.MultiplyToken
import tokens.NumberToken
import tokens.RightBraceToken
import tokens.StartToken
import tokens.SubtractToken

interface TokenVisitor {
    fun visit(token: AddToken)
    fun visit(token: MultiplyToken)
    fun visit(token: SubtractToken)
    fun visit(token: DivideToken)
    fun visit(token: NumberToken)
    fun visit(token: LeftBraceToken)
    fun visit(token: RightBraceToken)
    fun visit(token: EndToken)
    fun visit(token: ErrorToken)
    fun visit(token: StartToken)
}

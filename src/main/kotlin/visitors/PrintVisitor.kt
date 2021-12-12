package visitors

import tokens.*
import java.io.OutputStream

class PrintVisitor(private val out: OutputStream) : TokenVisitor {

    override fun visit(token: AddToken) = out.write("PLUS ")

    override fun visit(token: MultiplyToken) = out.write("MUL ")

    override fun visit(token: SubtractToken) = out.write("SUB ")

    override fun visit(token: DivideToken) = out.write("DIV ")

    override fun visit(token: NumberToken) = out.write("NUMBER(${token.number}) ")

    override fun visit(token: LeftBraceToken) = out.write("LEFT ")

    override fun visit(token: RightBraceToken) = out.write("RIGHT ")

    override fun visit(token: EndToken) = Unit

    override fun visit(token: ErrorToken) = out.write("ERROR(\"${token.message}\") ")

    override fun visit(token: StartToken) = Unit

    fun visit(tokens: List<Token>) {
        tokens.forEach { token -> token.accept(this) }
        out.write("\n")
    }

    private fun OutputStream.write(string: String) = write(string.toByteArray())
}

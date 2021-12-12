import tokens.*
import visitors.CalcVisitor
import visitors.ParserVisitor
import visitors.PrintVisitor
import java.util.*

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    while (sc.hasNextLine()) {
        val input = sc.nextLine()
        val tokens = Tokenizer(input).getTokens()
        try {
            val polishTokens = ParserVisitor().visit(tokens)
            PrintVisitor(System.out).visit(polishTokens)
            val result = CalcVisitor().visit(polishTokens)
            println(result)
        } catch (e: Exception) {
            println(e.message)
        }
    }
}

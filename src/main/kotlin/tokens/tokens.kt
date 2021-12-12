package tokens

import visitors.TokenVisitor

interface Token {
    fun accept(visitor: TokenVisitor)
}

abstract class OperationToken : Token {
    abstract val priority: Int
}

class StartToken : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

class AddToken : OperationToken() {
    override val priority = 1

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

class SubtractToken : OperationToken() {
    override val priority = 1

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

class MultiplyToken : OperationToken() {
    override val priority = 2

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

class DivideToken : OperationToken() {
    override val priority = 2

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

class LeftBraceToken : OperationToken() {
    override val priority = 0

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

class RightBraceToken : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

class NumberToken(private val valueProvider: () -> Int) : Token {
    val number get() = valueProvider()

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

class EndToken : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

class ErrorToken(val message: String) : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

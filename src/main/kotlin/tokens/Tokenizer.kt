package tokens

import reader.InputReader

class Tokenizer(private val input: String) {
    private val tokens = mutableListOf<Token>()
    var currentState: ExpressionState = Start(this)
        set(value) {
            tokens.add(value.createToken())
            field = value
        }

    fun getTokens() : List<Token> {
        if (tokens.isNotEmpty()) return tokens

        val reader = InputReader(input)
        while (currentState !is End && currentState !is Error) {
            currentState.handleNext(reader.nextChar())
        }

        return tokens
    }
}

abstract class ExpressionState(protected val tokenizer: Tokenizer) {
    abstract fun handleNext(char: Char?)
    abstract fun createToken() : Token
}

class Start(tokenizer: Tokenizer) : ExpressionState(tokenizer) {
    override fun handleNext(char: Char?) {
        tokenizer.currentState = when {
            char == null -> End(tokenizer)
            char == '(' -> LeftBrace(tokenizer)
            char.isDigit() -> Number(char, tokenizer)
            else -> Error(tokenizer, "Expression should starts with left brace or number.")
        }
    }

    override fun createToken() = StartToken()
}

class LeftBrace(tokenizer: Tokenizer) : ExpressionState(tokenizer) {
    override fun handleNext(char: Char?) {
        tokenizer.currentState = when {
            char?.isDigit() == true -> Number(char, tokenizer)
            else -> Error(tokenizer, "tokens.Number expected")
        }
    }

    override fun createToken() = LeftBraceToken()
}

class RightBrace(tokenizer: Tokenizer) : ExpressionState(tokenizer) {
    override fun handleNext(char: Char?) {
        tokenizer.currentState = when (char) {
            null -> End(tokenizer)
            '+' -> Add(tokenizer)
            '-' -> Subtract(tokenizer)
            '*' -> Multiply(tokenizer)
            '/' -> Divide(tokenizer)
            else -> Error(tokenizer, "Some binary operation expected.")
        }
    }

    override fun createToken() = RightBraceToken()
}

abstract class BinaryOperationState(tokenizer: Tokenizer) : ExpressionState(tokenizer) {
    override fun handleNext(char: Char?) {
        tokenizer.currentState = when {
            char == '(' -> LeftBrace(tokenizer)
            char?.isDigit() == true -> Number(char, tokenizer)
            else -> Error(tokenizer, "Expected some number or left brace.")
        }
    }
}

class Add(tokenizer: Tokenizer) : BinaryOperationState(tokenizer) {
    override fun createToken() = AddToken()
}

class Multiply(tokenizer: Tokenizer) : BinaryOperationState(tokenizer) {
    override fun createToken() = MultiplyToken()
}

class Divide(tokenizer: Tokenizer) : BinaryOperationState(tokenizer) {
    override fun createToken() = DivideToken()
}

class Subtract(tokenizer: Tokenizer) : BinaryOperationState(tokenizer) {
    override fun createToken() = SubtractToken()
}

class Number(firstChar: Char, tokenizer: Tokenizer) : ExpressionState(tokenizer) {
    private val numberBuilder = StringBuilder().append(firstChar)
    private val number get() = numberBuilder.toString().toInt()

    override fun handleNext(char: Char?) {
        if (char?.isDigit() == true) {
            numberBuilder.append(char)
            return
        }

        tokenizer.currentState = when (char) {
            null -> End(tokenizer)
            '+' -> Add(tokenizer)
            '-' -> Subtract(tokenizer)
            '*' -> Multiply(tokenizer)
            '/' -> Divide(tokenizer)
            ')' -> RightBrace(tokenizer)
            else -> Error(tokenizer, "Some binary operation expected.")
        }
    }

    override fun createToken() = NumberToken { number }
}

class End(tokenizer: Tokenizer) : ExpressionState(tokenizer) {
    override fun handleNext(char: Char?) = Unit
    override fun createToken() = EndToken()
}

class Error(tokenizer: Tokenizer, private val message: String) : ExpressionState(tokenizer) {
    override fun handleNext(char: Char?) = Unit
    override fun createToken() = ErrorToken(message)
}

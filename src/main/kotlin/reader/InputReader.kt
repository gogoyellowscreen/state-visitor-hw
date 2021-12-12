package reader

class InputReader(private val input: String) {
    private var index = 0

    /**
     * @return next non-ws char or null if eof.
     */
    fun nextChar() : Char? {
        if (index >= input.length) return null

        var next = input[index++]
        while (index < input.length && next.isWhitespace()) {
            next = input[index++]
        }

        if (next.isWhitespace()) return null
        return next
    }
}

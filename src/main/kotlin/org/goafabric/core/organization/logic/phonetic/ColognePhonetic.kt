package org.goafabric.core.organization.logic.phonetic

import java.util.Locale

class ColognePhonetic {

    companion object {
        private val AEIJOUY = charArrayOf('A', 'E', 'I', 'J', 'O', 'U', 'Y')
        private val CSZ = charArrayOf('C', 'S', 'Z')
        private val FPVW = charArrayOf('F', 'P', 'V', 'W')
        private val GKQ = charArrayOf('G', 'K', 'Q')
        private val CKQ = charArrayOf('C', 'K', 'Q')
        private val AHKLOQRUX = charArrayOf('A', 'H', 'K', 'L', 'O', 'Q', 'R', 'U', 'X')
        private val SZ = charArrayOf('S', 'Z')
        private val AHKOQUX = charArrayOf('A', 'H', 'K', 'O', 'Q', 'U', 'X')
        private val DTX = charArrayOf('D', 'T', 'X')
        private const val CHAR_IGNORE = '-'

        private fun arrayContains(arr: CharArray, key: Char): Boolean {
            for (element in arr) { if (element == key) return true }
            return false
        }
    }

    private inner class CologneOutputBuffer(buffSize: Int) {
        val data: CharArray = CharArray(buffSize)
        var length = 0
        var lastCode: Char = '/'

        fun put(code: Char) {
            if (code != CHAR_IGNORE && lastCode != code && (code != '0' || length == 0)) {
                data[length] = code
                length++
            }
            lastCode = code
        }

        fun isEmpty() = length == 0

        override fun toString() = String(data, 0, length)
    }

    private inner class CologneInputBuffer(val data: CharArray) {
        var length = data.size

        fun getNextChar(): Char = data[data.size - length]
        fun isEmpty() = length == 0
        fun removeNext(): Char {
            val ch = getNextChar()
            length--
            return ch
        }
    }

    fun colognePhonetic(text: String?): String? {
        if (text == null) return null
        val input = CologneInputBuffer(preprocess(text))
        val output = CologneOutputBuffer(input.length * 2)
        var nextChar: Char
        var lastChar = CHAR_IGNORE
        var chr: Char
        while (!input.isEmpty()) {
            chr = input.removeNext()
            nextChar = if (!input.isEmpty()) input.getNextChar() else CHAR_IGNORE
            if (chr < 'A' || chr > 'Z') continue
            when {
                arrayContains(AEIJOUY, chr) -> output.put('0')
                chr == 'B' || (chr == 'P' && nextChar != 'H') -> output.put('1')
                (chr == 'D' || chr == 'T') && !arrayContains(CSZ, nextChar) -> output.put('2')
                arrayContains(FPVW, chr) -> output.put('3')
                arrayContains(GKQ, chr) -> output.put('4')
                chr == 'X' && !arrayContains(CKQ, lastChar) -> { output.put('4'); output.put('8') }
                chr == 'S' || chr == 'Z' -> output.put('8')
                chr == 'C' -> {
                    if (output.isEmpty()) {
                        output.put(if (arrayContains(AHKLOQRUX, nextChar)) '4' else '8')
                    } else if (arrayContains(SZ, lastChar) || !arrayContains(AHKOQUX, nextChar)) {
                        output.put('8')
                    } else {
                        output.put('4')
                    }
                }
                arrayContains(DTX, chr) -> output.put('8')
                else -> when (chr) {
                    'R' -> output.put('7')
                    'L' -> output.put('5')
                    'M', 'N' -> output.put('6')
                    'H' -> output.put(CHAR_IGNORE)
                    else -> {}
                }
            }
            lastChar = chr
        }
        return output.toString()
    }

    fun encode(text: String?): String? = colognePhonetic(text)

    fun isEncodeEqual(text1: String, text2: String): Boolean =
        colognePhonetic(text1) == colognePhonetic(text2)

    private fun preprocess(text: String): CharArray {
        val chrs = text.uppercase(Locale.GERMAN).toCharArray()
        for (index in chrs.indices) {
            when (chrs[index]) {
                '\u00C4' -> chrs[index] = 'A'  // Ä
                '\u00DC' -> chrs[index] = 'U'  // Ü
                '\u00D6' -> chrs[index] = 'O'  // Ö
                else -> {}
            }
        }
        return chrs
    }
}

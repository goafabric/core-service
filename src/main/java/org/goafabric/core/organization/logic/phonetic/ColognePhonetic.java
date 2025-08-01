package org.goafabric.core.organization.logic.phonetic;


import java.util.Arrays;
import java.util.Locale;

public class ColognePhonetic {

    // Predefined char arrays for better performance and less GC load
    private static final char[] AEIJOUY = { 'A', 'E', 'I', 'J', 'O', 'U', 'Y' };
    private static final char[] CSZ = { 'C', 'S', 'Z' };
    private static final char[] FPVW = { 'F', 'P', 'V', 'W' };
    private static final char[] GKQ = { 'G', 'K', 'Q' };
    private static final char[] CKQ = { 'C', 'K', 'Q' };
    private static final char[] AHKLOQRUX = { 'A', 'H', 'K', 'L', 'O', 'Q', 'R', 'U', 'X' };
    private static final char[] SZ = { 'S', 'Z' };
    private static final char[] AHKOQUX = { 'A', 'H', 'K', 'O', 'Q', 'U', 'X' };
    private static final char[] DTX = { 'D', 'T', 'X' };

    private static final char CHAR_IGNORE = '-';    // is this character to be ignored?

    /**
     * This class is not thread-safe; the field {@link #length} is mutable.
     * However, it is not shared between threads, as it is constructed on demand
     * by the method {@link org.apache.commons.codec.language.ColognePhonetic#colognePhonetic(String)}
     */
    abstract static class CologneBuffer {

        protected final char[] data;

        protected int length = 0;

        public CologneBuffer(final char[] data) {
            this.data = data;
            this.length = data.length;
        }

        protected CologneBuffer(final int buffSize) {
            this.data = new char[buffSize];
            this.length = 0;
        }

        protected abstract char[] copyData(int start, int length);

        public int length() {
            return length;
        }

        @Override
        public String toString() {
            return new String(copyData(0, length));
        }

        public boolean isEmpty() {
            return length() == 0;
        }
    }

    private class CologneOutputBuffer extends CologneBuffer {

        private char lastCode;

        public CologneOutputBuffer(final int buffSize) {
            super(buffSize);
            lastCode = '/'; // impossible value
        }

        /**
         * Stores the next code in the output buffer, keeping track of the previous code.
         * '0' is only stored if it is the first entry.
         * Ignored chars are never stored.
         * If the code is the same as the last code (whether stored or not) it is not stored.
         *
         * @param code the code to store.
         */
        public void put(final char code) {
            if (code != CHAR_IGNORE && lastCode != code && (code != '0' || length == 0)) {
                data[length] = code;
                length++;
            }
            lastCode = code;
        }

        @Override
        protected char[] copyData(final int start, final int length) {
            return Arrays.copyOfRange(data, start, length);
        }
    }

    private class CologneInputBuffer extends CologneBuffer {

        public CologneInputBuffer(final char[] data) {
            super(data);
        }

        @Override
        protected char[] copyData(final int start, final int length) {
            final char[] newData = new char[length];
            System.arraycopy(data, data.length - this.length + start, newData, 0, length);
            return newData;
        }

        public char getNextChar() {
            return data[getNextPos()];
        }

        protected int getNextPos() {
            return data.length - length;
        }

        public char removeNext() {
            final char ch = getNextChar();
            length--;
            return ch;
        }
    }

    /*
     * Returns whether the array contains the key, or not.
     */
    private static boolean arrayContains(final char[] arr, final char key) {
        for (final char element : arr) {
            if (element == key) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Implements the <i>K&ouml;lner Phonetik</i> algorithm.
     * </p>
     * <p>
     * In contrast to the initial description of the algorithm, this implementation does the encoding in one pass.
     * </p>
     *
     * @param text The source text to encode
     * @return the corresponding encoding according to the <i>K&ouml;lner Phonetik</i> algorithm
     */
    public String colognePhonetic(final String text) {
        if (text == null) {
            return null;
        }

        final CologneInputBuffer input = new CologneInputBuffer(preprocess(text));
        final CologneOutputBuffer output = new CologneOutputBuffer(input.length() * 2);

        char nextChar;

        char lastChar = CHAR_IGNORE;
        char chr;

        while (!input.isEmpty()) {
            chr = input.removeNext();

            if (!input.isEmpty()) {
                nextChar = input.getNextChar();
            } else {
                nextChar = CHAR_IGNORE;
            }

            if (chr < 'A' || chr > 'Z') {
                continue; // ignore unwanted characters
            }

            if (arrayContains(AEIJOUY, chr)) {
                output.put('0');
            } else if (chr == 'B' || (chr == 'P' && nextChar != 'H')) {
                output.put('1');
            } else if ((chr == 'D' || chr == 'T') && !arrayContains(CSZ, nextChar)) {
                output.put('2');
            } else if (arrayContains(FPVW, chr)) {
                output.put('3');
            } else if (arrayContains(GKQ, chr)) {
                output.put('4');
            } else if (chr == 'X' && !arrayContains(CKQ, lastChar)) {
                output.put('4');
                output.put('8');
            } else if (chr == 'S' || chr == 'Z') {
                output.put('8');
            } else if (chr == 'C') {
                if (output.isEmpty()) {
                    if (arrayContains(AHKLOQRUX, nextChar)) {
                        output.put('4');
                    } else {
                        output.put('8');
                    }
                } else if (arrayContains(SZ, lastChar) || !arrayContains(AHKOQUX, nextChar)) {
                    output.put('8');
                } else {
                    output.put('4');
                }
            } else if (arrayContains(DTX, chr)) {
                output.put('8');
            } else {
                switch (chr) {
                    case 'R':
                        output.put('7');
                        break;
                    case 'L':
                        output.put('5');
                        break;
                    case 'M':
                    case 'N':
                        output.put('6');
                        break;
                    case 'H':
                        output.put(CHAR_IGNORE); // needed by put
                        break;
                    default:
                        break;
                }
            }

            lastChar = chr;
        }
        return output.toString();
    }

    public Object encode(final Object object) throws IllegalAccessException {
        if (!(object instanceof String)) {
            throw new IllegalAccessException("This method's parameter was expected to be of the type " +
                    String.class.getName() +
                    ". But actually it was of the type " +
                    object.getClass().getName() +
                    ".");
        }
        return encode((String) object);
    }

    public String encode(final String text) {
        return colognePhonetic(text);
    }

    /**
     * Compares the first encoded string to the second encoded string.
     *
     * @param text1 source text to encode before testing for equality.
     * @param text2 source text to encode before testing for equality.
     * @return {@code true} if the encoding the first string equals the encoding of the second string, {@code false}
     *         otherwise
     */
    public boolean isEncodeEqual(final String text1, final String text2) {
        return colognePhonetic(text1).equals(colognePhonetic(text2));
    }

    /**
     * Converts the string to upper case and replaces Germanic umlaut characters
     * The following characters are mapped:
     * <ul>
     * <li>capital A, umlaut mark</li>
     * <li>capital U, umlaut mark</li>
     * <li>capital O, umlaut mark</li>
     * <li>small sharp s, German</li>
     * </ul>
     */
    private char[] preprocess(final String text) {
        // This converts German small sharp s (Eszett) to SS
        final char[] chrs = text.toUpperCase(Locale.GERMAN).toCharArray();

        for (int index = 0; index < chrs.length; index++) {
            switch (chrs[index]) {
                case '\u00C4': // capital A, umlaut mark
                    chrs[index] = 'A';
                    break;
                case '\u00DC': // capital U, umlaut mark
                    chrs[index] = 'U';
                    break;
                case '\u00D6': // capital O, umlaut mark
                    chrs[index] = 'O';
                    break;
                default:
                    break;
            }
        }
        return chrs;
    }
}


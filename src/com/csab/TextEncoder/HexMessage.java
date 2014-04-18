package com.csab.TextEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

public class HexMessage extends Message {

    private long[] longHexArray;

    public HexMessage(byte[] inputArray) {
        super(inputArray);
    }

    public HexMessage(String inputString) throws DecoderException, MessageConstructException {
        inputString = inputString.replaceAll("\\s","");
        if (!isValid(inputString)) {
            throw new MessageConstructException(Message.INVALID_INPUT_MESSAGE);
        }
        char[] charArray = inputString.toCharArray();
        setCharacterCodeArray((byte[])new Hex().decode(charArray));
    }

    public HexMessage(long[] inputArray) {
        longHexArray = inputArray;
    }

    public AsciiMessage toAsciiMessage() {
        return new AsciiMessage(getCharacterCodeArray());
    }

    public BinaryMessage toBinaryMessage() throws MessageConstructException {
        return new BinaryMessage(longHexArray);
    }

    public DecimalMessage toDecimalMessage() {
        return new DecimalMessage(longHexArray);
    }

    public OctalMessage toOctalMessage() {
        return new OctalMessage(longHexArray);
    }

    @Override
    public String toString() {
        if (getCharacterCodeArray() != null) {
            return StringUtils.newStringUsAscii(new Hex().encode(getCharacterCodeArray()));
        } else {
            String result = "";
            for (int i = 0; i < longHexArray.length; i++) {
                result += Long.toHexString(longHexArray[i]);
            }
            return  result;
        }
    }

    private boolean isValid(String inputString) {
        if (inputString.length() % 2 != 0) {
            return false;
        } else if (!org.apache.commons.lang3.StringUtils.isAlphanumeric(inputString)) {
            return false;
        } else {
            return true;
        }
    }
}

package com.csab.TextEncoder;

import org.apache.commons.codec.binary.StringUtils;
import java.util.Arrays;

public class AsciiMessage extends Message {

    private static final String INVALID_ASCII_MESSAGE = "Value outside ASCII code range (127)";

    public AsciiMessage(byte[] inputArray) {
        super(inputArray);
    }

    public AsciiMessage(String inputString) {
        super(inputString);
    }

    public AsciiMessage(long[] inputArray) throws MessageConstructException {
        long[] sortedArray = Arrays.copyOf(inputArray, inputArray.length);
        Arrays.sort(sortedArray);
        if (sortedArray[sortedArray.length - 1] > Byte.MAX_VALUE) {
            throw new MessageConstructException(INVALID_ASCII_MESSAGE);
        } else {
            byte[] byteArray = new byte[inputArray.length];
            for (int i = 0; i < inputArray.length; i++) {
                byteArray[i] = (byte) inputArray[i];
            }
            setCharacterCodeArray(byteArray);
        }
    }

    public BinaryMessage toBinaryMessage() throws MessageConstructException {
        return new BinaryMessage(getCharacterCodeArray());
    }

    public DecimalMessage toDecimalMessage() {
        return new DecimalMessage(getCharacterCodeArray());
    }

    public HexMessage toHexMessage() {
        return new HexMessage(getCharacterCodeArray());
    }

    @Override
    public String toString() {
        return StringUtils.newStringUsAscii(getCharacterCodeArray());
    }
}
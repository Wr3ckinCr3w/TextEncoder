package com.csab.TextEncoder;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class DecimalMessage extends Message {

    private long[] longDecimalArray;

    public DecimalMessage(byte[] inputArray) {
        super(inputArray);
        longDecimalArray = new long[inputArray.length];
        for (int i = 0; i < longDecimalArray.length; i++) {
            longDecimalArray[i] = Long.parseLong(Long.toBinaryString((long) inputArray[i]), 2);
        }
    }

    public DecimalMessage(String inputString) throws MessageConstructException {
        if (!isValid(inputString)) {
            throw new MessageConstructException(Message.INVALID_INPUT_MESSAGE);
        }
        String[] stringArray = inputString.split("\\s+");
        longDecimalArray = new long[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            longDecimalArray[i] = Long.parseLong(stringArray[i]);
        }
    }

    public DecimalMessage(long[] inputArray) {
        longDecimalArray = inputArray;
    }

    public AsciiMessage toAsciiMessage() throws MessageConstructException {
        if (getCharacterCodeArray() != null) {
            return new AsciiMessage(getCharacterCodeArray());
        } else {
            return new AsciiMessage(longDecimalArray);
        }

    }

    public BinaryMessage toBinaryMessage() {
        return new BinaryMessage(longDecimalArray);
    }

    public HexMessage toHexMessage() {
        return new HexMessage(longDecimalArray);
    }

    @Override
    public String toString() {
        return Arrays.toString(longDecimalArray).replaceAll("[^0-9\\s]", "");
    }

    private boolean isValid(String inputString) {
        return StringUtils.isNumeric(inputString.replaceAll("\\s", ""));
    }

}

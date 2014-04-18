package com.csab.TextEncoder;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class Base64Message extends Message {

    private long[] longBase64Array;

    public Base64Message(byte[] inputArray) {
        super(inputArray);
        longBase64Array = new long[inputArray.length];
        for (int i = 0; i < longBase64Array.length; i++) {
            longBase64Array[i] = Long.parseLong(Long.toBinaryString((long) inputArray[i]), 8);
        }
    }

    public Base64Message(String inputString) throws MessageConstructException {
        if (!isValid(inputString)) {
            throw new MessageConstructException(Message.INVALID_INPUT_MESSAGE);
        }
        String[] stringArray = inputString.split("\\s+");
        longBase64Array = new long[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            longBase64Array[i] = Long.parseLong(stringArray[i], 8);
        }
    }

    public Base64Message(long[] inputArray) {
        longBase64Array = inputArray;
    }

    public AsciiMessage toAsciiMessage() throws MessageConstructException {
        if (getCharacterCodeArray() != null) {
            return new AsciiMessage(getCharacterCodeArray());
        } else {
            return new AsciiMessage(longBase64Array);
        }
    }

    public BinaryMessage toBinaryMessage() {
        return new BinaryMessage(longBase64Array);
    }

    public DecimalMessage toDecimalMessage() {
        return new DecimalMessage(longBase64Array);
    }

    public HexMessage toHexMessage() {
        return new HexMessage(longBase64Array);
    }

    public OctalMessage toOctalMessage() {
        return new OctalMessage(longBase64Array);
    }

    @Override
    public String toString() {
        return Arrays.toString(longBase64Array).replaceAll("[^0-9\\s]", "");
    }

    private boolean isValid(String inputString) {
        return StringUtils.isNumeric(inputString.replaceAll("\\s", ""));
    }

}

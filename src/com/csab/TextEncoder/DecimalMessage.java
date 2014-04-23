package com.csab.TextEncoder;

import java.util.Arrays;

public class DecimalMessage extends Message {

    public DecimalMessage(byte[] inputArray) throws MessageConstructException {
        super(inputArray);
    }

    public DecimalMessage(String inputString) throws MessageConstructException, NumberFormatException {
        super(inputString);
        checkValid(getStringValuesArray());
        long[] tempArray = new long[getStringValuesArray().length];
        for (int i = 0; i < getStringValuesArray().length; i++) {
            tempArray[i] = Long.parseLong(getStringValuesArray()[i]);
        }
        setLongValuesArray(tempArray);
    }

    public DecimalMessage(long[] inputArray) {
        super(inputArray);
    }

    public AsciiMessage toAsciiMessage() throws MessageConstructException {
        if (getByteValuesArray() != null) {
            return new AsciiMessage(getByteValuesArray());
        } else {
            return new AsciiMessage(getLongValuesArray());
        }
    }

    public Base64Message toBase64Message() {
        return new Base64Message(getLongValuesArray());
    }

    public BinaryMessage toBinaryMessage() {
        return new BinaryMessage(getLongValuesArray());
    }

    public HexMessage toHexMessage() {
        return new HexMessage(getLongValuesArray());
    }

    public OctalMessage toOctalMesage() {
        return new OctalMessage(getLongValuesArray());
    }

    @Override
    public String toString() {
        return Arrays.toString(getLongValuesArray()).replaceAll("[^0-9\\s]","");
    }

    private boolean checkValid(String[] array) throws MessageConstructException {
        for (String s : array) {
            if (!s.matches("^[0-9]+$")) {
                throw new MessageConstructException(MessageConstructException.INVALID_INPUT_MESSAGE);
            }
        }
        return true;
    }
}

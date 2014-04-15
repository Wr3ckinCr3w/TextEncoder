package com.csab.TextEncoder;

import org.apache.commons.codec.binary.StringUtils;

public class AsciiMessage extends Message {

    public AsciiMessage(byte[] inputArray) {
        super(inputArray);
    }

    public AsciiMessage(String inputString) {
        super(inputString);
    }

    public AsciiMessage(long[] inputArray) {
        byte[] byteArray = new byte[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            byteArray[i] = (byte) inputArray[i];
        }
        setCharacterCodeArray(byteArray);
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
package com.csab.TextEncoder;

import org.apache.commons.codec.binary.StringUtils;

public class AsciiMessage extends Message {

    public AsciiMessage(byte[] inputArray) {
        super(inputArray);
    }

    public AsciiMessage(String inputString) {
        super(inputString);
    }

    public DecimalMessage toDecimalMessage() {
        return new DecimalMessage(getCharacterCodeArray());
    }

    public HexMessage toHexadecimalMessage() {
        return new HexMessage(getCharacterCodeArray());
    }

    @Override
    public String toString() {
        return StringUtils.newStringUsAscii(getCharacterCodeArray());
    }
}
package com.csab.TextEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

public class HexMessage extends Message {

    private long[] longHexArray;

    public HexMessage(byte[] inputArray) {
        super(inputArray);
    }

    public HexMessage(String inputString) throws DecoderException {
        inputString = inputString.replaceAll("\\s", "");
        setCharacterCodeArray(new Hex().decode(inputString.getBytes()));
        longHexArray = new long[getCharacterCodeArray().length];
        for (int i = 0; i < longHexArray.length; i++) {
            longHexArray[i] = (long) getCharacterCodeArray()[i];
        }
    }

    public HexMessage(long[] inputArray) {
        longHexArray = inputArray;
    }

    public AsciiMessage toAsciiMessage() {
        return new AsciiMessage(getCharacterCodeArray());
    }

    public Base64Message toBase64Message() throws MessageConstructException {
        return new Base64Message(getCharacterCodeArray());
    }

    public BinaryMessage toBinaryMessage() throws MessageConstructException {
        return new BinaryMessage(getCharacterCodeArray());
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
}

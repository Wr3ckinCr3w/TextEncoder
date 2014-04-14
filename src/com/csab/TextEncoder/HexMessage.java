package com.csab.TextEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

public class HexMessage extends Message {

    public HexMessage(byte[] inputArray) {
        super(inputArray);
    }

    public HexMessage(String inputString) {
        char[] charArray = inputString.toCharArray();
        byte[] byteArray = null;
        try {
            byteArray = (byte[]) new Hex().decode(charArray);
        } catch (DecoderException dx) {
            dx.printStackTrace();
        }
        setCharacterCodeArray(byteArray);
    }

    public AsciiMessage toAsciiMessage() {
        return new AsciiMessage(getCharacterCodeArray());
    }

    /*
    public BinaryMessage toBinaryMessage() {
        //
        return new BinaryMessage();
    }

    public DecimalMessage toDecimalMessage() {
        //
        return new DecimalMessage();
    }
    */

    @Override
    public String toString() {
        return StringUtils.newStringUsAscii(new Hex().encode(getCharacterCodeArray()));
    }
}

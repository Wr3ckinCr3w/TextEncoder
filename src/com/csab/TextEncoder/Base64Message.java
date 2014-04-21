package com.csab.TextEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.StringUtils;
import android.util.Base64;

public class Base64Message extends Message {

    private long[] longBase64Array;

    public Base64Message(byte[] inputArray) {
        super(inputArray);
    }

    public Base64Message(String inputString) throws DecoderException {
        setCharacterCodeArray(Base64.decode(inputString, Base64.DEFAULT));
    }

    public Base64Message(long[] inputArray) {
        longBase64Array = inputArray;
    }

    public AsciiMessage toAsciiMessage() {
        return new AsciiMessage(getCharacterCodeArray());
    }

    public BinaryMessage toBinaryMessage() {
        return new BinaryMessage(getCharacterCodeArray());
    }

    public DecimalMessage toDecimalMessage() throws MessageConstructException {
        return new DecimalMessage(getCharacterCodeArray());
    }

    public HexMessage toHexMessage() {
        return new HexMessage(getCharacterCodeArray());
    }

    public OctalMessage toOctalMessage() {
        return new OctalMessage(getCharacterCodeArray());
    }

    @Override
    public String toString() {
        if (getCharacterCodeArray() != null) {
            return StringUtils.newStringUsAscii(Base64.encode(getCharacterCodeArray(), Base64.DEFAULT));
        } else {
            String result = "";
            for (int i = 0; i < longBase64Array.length; i++) {
                result += StringUtils.newStringUsAscii(
                        Base64.encode(String.valueOf(longBase64Array[i]).getBytes(), Base64.NO_WRAP));
                if (i != longBase64Array.length - 1)
                    result += " ";
            }
            return result;
        }
    }


}

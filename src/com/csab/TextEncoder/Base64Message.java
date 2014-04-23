package com.csab.TextEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.StringUtils;
import android.util.Base64;

public class Base64Message extends Message {

    public Base64Message(byte[] inputArray) {
        super(inputArray);
    }

    public Base64Message(String inputString) throws DecoderException {
        setByteValuesArray(Base64.decode(inputString, Base64.DEFAULT));
    }

    public Base64Message(long[] inputArray) {
        super(inputArray);
    }

    public AsciiMessage toAsciiMessage() {
        return new AsciiMessage(getByteValuesArray());
    }

    public BinaryMessage toBinaryMessage() {
        return new BinaryMessage(getByteValuesArray());
    }

    public DecimalMessage toDecimalMessage() throws MessageConstructException {
        return new DecimalMessage(getByteValuesArray());
    }

    public HexMessage toHexMessage() {
        return new HexMessage(getByteValuesArray());
    }

    public OctalMessage toOctalMessage() {
        return new OctalMessage(getByteValuesArray());
    }

    @Override
    public String toString() {
        if (getByteValuesArray() != null) {
            return StringUtils.newStringUsAscii(Base64.encode(getByteValuesArray(), Base64.DEFAULT));
        } else {
            String result = "";
            for (int i = 0; i < getLongValuesArray().length; i++) {
                result += StringUtils.newStringUsAscii(
                        Base64.encode(String.valueOf(getLongValuesArray()[i]).getBytes(), Base64.NO_WRAP));
                if (i != getLongValuesArray().length - 1)
                    result += " ";
            }
            return result;
        }
    }
}

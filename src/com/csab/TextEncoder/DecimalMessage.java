package com.csab.TextEncoder;

import java.math.BigInteger;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class DecimalMessage extends Message {

    private long[] longDecimalArray;

    public DecimalMessage(byte[] inputArray) throws MessageConstructException {
        super(inputArray);
        longDecimalArray = new long[inputArray.length];
        for (int i = 0; i < longDecimalArray.length; i++) {
            longDecimalArray[i] = Long.parseLong(String.valueOf(inputArray[i]));
        }
    }

    public DecimalMessage(String inputString) throws MessageConstructException {
        String[] stringArray = inputString.split("\\s+");
        isValid(stringArray);
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

    public Base64Message toBase64Message() {
        return new Base64Message(longDecimalArray);
    }

    public BinaryMessage toBinaryMessage() {
        return new BinaryMessage(longDecimalArray);
    }

    public HexMessage toHexMessage() {
        return new HexMessage(longDecimalArray);
    }

    public OctalMessage toOctalMesage() {
        return new OctalMessage(longDecimalArray);
    }

    @Override
    public String toString() {
        return Arrays.toString(longDecimalArray).replaceAll("[^0-9\\s]","");
    }

    private boolean isValid(String[] stringArray) throws MessageConstructException {
        for (String s : stringArray) {
            if (!StringUtils.isNumeric(s)) {
                throw new MessageConstructException(Message.INVALID_INPUT_MESSAGE);
            } else if (new BigInteger(s).longValue() >= Long.MAX_VALUE) {
                throw new MessageConstructException("Number input too large");
            }
        }
        return true;
    }

}

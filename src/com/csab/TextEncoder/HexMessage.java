package com.csab.TextEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

public class HexMessage extends Message {

    public HexMessage(byte[] inputArray) {
        super(inputArray);
    }

    public HexMessage(String inputString) throws DecoderException, MessageConstructException {
        if (!isValid(inputString)) {
            throw new MessageConstructException(Message.EXCEPTION_MESSAGE);
        }
        char[] charArray = inputString.toCharArray();
        byte[] byteArray =  (byte[]) new Hex().decode(charArray);
        setCharacterCodeArray(byteArray);
    }

    public HexMessage(long[] inputArray) {
        byte[] byteArray = new byte[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            byteArray[i] = (byte) inputArray[i];
        }
        setCharacterCodeArray(byteArray);
    }

    public AsciiMessage toAsciiMessage() {
        return new AsciiMessage(getCharacterCodeArray());
    }

    public BinaryMessage toBinaryMessage() throws MessageConstructException {
        return new BinaryMessage(getCharacterCodeArray());
    }

    public DecimalMessage toDecimalMessage() {
        return new DecimalMessage(getCharacterCodeArray());
    }

    @Override
    public String toString() {
        return StringUtils.newStringUsAscii(new Hex().encode(getCharacterCodeArray()));
    }

    private boolean isValid(String inputString) {
        inputString = inputString.replace(" ","");
        if (inputString.length() % 2 != 0) {
            return false;
        } else if (!org.apache.commons.lang3.StringUtils.isAlphanumeric(inputString)) {
            return false;
        }
        return true;
    }
}

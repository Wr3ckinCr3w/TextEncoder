package com.csab.TextEncoder;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class DecimalMessage extends Message {

    public DecimalMessage(byte[] inputArray) {
        super(inputArray);
    }

    public DecimalMessage(String inputString) throws MessageConstructException {
        if (!isValid(inputString)) {
            throw new MessageConstructException(Message.EXCEPTION_MESSAGE);
        }
        String[] stringArray = inputString.split("\\s");
        byte[] byteArray = new byte[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            byteArray[i] = (byte) Integer.parseInt(stringArray[i]);
        }
        setCharacterCodeArray(byteArray);
    }

    public DecimalMessage(long[] inputArray) {
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

    public HexMessage toHexMessage() {
        return new HexMessage(getCharacterCodeArray());
    }

    @Override
    public String toString() {
        return (Arrays.toString(getCharacterCodeArray())).replaceAll("[^0-9\\s]", "");
    }

    private boolean isValid(String inputString) {
        return StringUtils.isNumeric(inputString.replace(" ",""));
    }

}

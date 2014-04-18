package com.csab.TextEncoder;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class OctalMessage extends Message {

    private long[] longOctalArray;

    public OctalMessage(byte[] inputArray) {
        super(inputArray);
        longOctalArray = new long[inputArray.length];
        for (int i = 0; i < longOctalArray.length; i++) {
            longOctalArray[i] = Long.parseLong(calcOctalForm(longOctalArray[i]));
        }
    }

    public OctalMessage(String inputString) throws MessageConstructException {
        if (!isValid(inputString)) {
            throw new MessageConstructException(Message.INVALID_INPUT_MESSAGE);
        }
        String[] stringArray = inputString.split("\\s+");
        longOctalArray = new long[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            longOctalArray[i] = Long.parseLong(calcOctalForm(Long.parseLong(stringArray[i])));
        }
    }

    public OctalMessage(long[] inputArray) {
        longOctalArray = inputArray;
        for (int i = 0; i < longOctalArray.length; i++) {
            longOctalArray[i] = Long.parseLong(calcOctalForm(longOctalArray[i]));
        }
    }

    public AsciiMessage toAsciiMessage() throws MessageConstructException {
        if (getCharacterCodeArray() != null) {
            return new AsciiMessage(getCharacterCodeArray());
        } else {
            return new AsciiMessage(longOctalArray);
        }
    }

    public Base64Message toBase64Message() {
        return new Base64Message(longOctalArray);
    }

    public BinaryMessage toBinaryMessage() {
        return new BinaryMessage(longOctalArray);
    }

    public DecimalMessage toDecimalMessage() {
        return new DecimalMessage(longOctalArray);
    }

    public HexMessage toHexMessage() {
        return new HexMessage(longOctalArray);
    }

    private String calcOctalForm(long value) {
        String result = "";
        while (value > 0) {
            long rem = value % 8;
            value /= 8;
            result = rem + result;
        }
        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(longOctalArray).replaceAll("[^0-9\\s]", "");
    }

    private boolean isValid(String inputString) {
        return StringUtils.isNumeric(inputString.replaceAll("\\s",""));
    }
}

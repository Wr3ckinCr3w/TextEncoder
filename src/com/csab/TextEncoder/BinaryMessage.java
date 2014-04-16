package com.csab.TextEncoder;

import java.util.List;
import com.google.common.base.Splitter;

public class BinaryMessage extends Message {

    private long[] longArrayOfBytes;
    private static final int NUMBER_OF_BITS = 8;

    public BinaryMessage(byte[] inputArray) throws MessageConstructException {
        super(inputArray);
        longArrayOfBytes = new long[inputArray.length];
        for (int i = 0; i < longArrayOfBytes.length; i++) {
            longArrayOfBytes[i] = Long.valueOf(Long.toBinaryString((long) inputArray[i]), 2);
        }
    }

    public BinaryMessage(String inputString) throws MessageConstructException {
        inputString = inputString.replaceAll("\\s","");
        if (!isValid(inputString)) {
            throw new MessageConstructException(Message.EXCEPTION_MESSAGE);
        }
        List<String> list = Splitter.fixedLength(8).splitToList(inputString);
        String[] stringArrayOfBytes = new String[list.size()];
        stringArrayOfBytes = list.toArray(stringArrayOfBytes);

        longArrayOfBytes = new long[stringArrayOfBytes.length];
        for (int i = 0; i < longArrayOfBytes.length; i++) {
            longArrayOfBytes[i] = Long.valueOf(stringArrayOfBytes[i], 2);
        }
    }

    public AsciiMessage toAsciiMessage() {
        return new AsciiMessage(longArrayOfBytes);
    }

    public DecimalMessage toDecimalMessage() {
        return new DecimalMessage(longArrayOfBytes);
    }

    public HexMessage toHexMessage() {
        return new HexMessage(longArrayOfBytes);
    }

    @Override
    public String toString() {
        String[] binaryStringArray = new String[longArrayOfBytes.length];
        String result = "";
        for (int i = 0; i < longArrayOfBytes.length; i++) {
            binaryStringArray[i] = Long.toBinaryString(longArrayOfBytes[i]);
            while (binaryStringArray[i].length() < NUMBER_OF_BITS) {
                binaryStringArray[i] = "0" + binaryStringArray[i];
            }
            if (i == longArrayOfBytes.length - 1) {
                result += binaryStringArray[i];
            } else {
                result += binaryStringArray[i] + " ";
            }
        }
        return result;
    }

    private boolean isValid(String inputString) {
        if (inputString.length() % NUMBER_OF_BITS != 0) {
            return false;
        }
        for (char ch : inputString.toCharArray()) {
            if (ch == '1'  || ch == '0') {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
}

package com.csab.TextEncoder;

import android.content.Context;
import android.util.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class Base64Message extends Message {

    public Base64Message(byte[] inputArray) {
        setByteValuesArray(inputArray);
    }

    public Base64Message(String inputString, Context context) {
        setByteValuesArray(Base64.decode(inputString, Base64.DEFAULT));
    }

    public <T> T convert(Class<T> clazz) throws Exception {
        return clazz.getConstructor(byte[].class).newInstance(getByteValuesArray());
    }

    @Override
    public String toString() {
        return StringUtils.newStringUsAscii(Base64.encode(getByteValuesArray(), Base64.DEFAULT));
    }
}

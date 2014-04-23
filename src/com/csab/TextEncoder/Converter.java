package com.csab.TextEncoder;

public interface Converter<T,U> {
    <T> T convert(Class<T> clazz) throws Exception;
}
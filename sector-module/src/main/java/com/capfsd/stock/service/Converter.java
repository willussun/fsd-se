package com.capfsd.stock.service;

@FunctionalInterface
public interface Converter<S, T> {
    T convert(S source);
}

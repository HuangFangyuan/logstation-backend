package com.hfy.logstation.util;

@FunctionalInterface
public interface HealthAlgorithm {
    int score(long info, long warn, long error);
}

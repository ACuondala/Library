package com.example.nada.Helpers;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class NormalizedName {

    private NormalizedName() {}
        // Private constructor to prevent instantiation

     public static String normalizeName(String name) {
        return Arrays.stream(name.trim().toLowerCase().split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));
    }

}

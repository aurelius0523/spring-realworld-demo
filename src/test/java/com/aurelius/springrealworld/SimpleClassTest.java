package com.aurelius.springrealworld;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SimpleClassTest {

    @Test
    public void reverseString() {
        String str = "abcdef";
        char[] characters = str.toCharArray();
        char[] reversedChar = new char[str.length()];

        int j = 0;
        for (int i = str.length() -1 ; i >= 0; i--) {
            reversedChar[j] = characters[i];
            j++;
        }

        System.out.println(reversedChar);
    }
}

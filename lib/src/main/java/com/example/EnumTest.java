package com.example;

/**
 * Created by cool on 16-9-21.
 */
public enum EnumTest {
    A, B, C {
        int test() {
            return 1;
        }
    };

    int test(){
        return C.test();
    }
}

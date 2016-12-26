package com.example;

import com.sun.istack.internal.NotNull;

/**
 * Created by cool on 16-8-30.
 */
public class C1 extends S {

    public static void main(String[] args){
        C1 c = new C1();
        c.emptyTest(null);
    }

    private void emptyTest(@NotNull String empty){
        System.out.println("empty--->"+empty);
    }

    @Override
    public void b() {

        C1 c = null;
        if (!(c instanceof  C1)){

        }
        System.out.println(c);
    }
}

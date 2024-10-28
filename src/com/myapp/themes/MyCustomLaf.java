
package com.myapp.themes;

import com.formdev.flatlaf.FlatDarkLaf;

public class MyCustomLaf extends FlatDarkLaf {
    public static boolean setup() {
        return setup( new MyCustomLaf() );
    }

    @Override
    public String getName() {
        return "MyDarkerLaf";
    }
}
package com.bmpr;

import static java.lang.System.*;
import java.lang.System;

public class Main {

    public static void main(String[] args) {
        //var okno = new Okno("sin(x)");
        //Testowanie funkcji trygonometrycznych w bibliotece polynomial
        //Testujemy sinusa od -1000 do 1000
        var p = new Polynomial("sin(x)");
        for (int i = -1000; i <= 1000; i++) {
            out.println("Polynomial: " + p.ValueOf(i) + "; Java: " + Math.sin(i * (180 / Math.PI)));
        }
    }
}

package com.bmpr;

import java.sql.Array;

public class Extras {
    public static double ExtractDoubleFromString(char[] arr) { //Funkcja przyjmuje char[], ponieważ Java jest tak ułomnym językiem że nie obsługuje funkcji zwracających kilku zmiennych...
        //Stworzymy pustą zmienną alfanumeryczną, którą następnie sprasujemy do typu double
        String toParse = "";

        //Teraz kolejno sprawdzimy każdy znak znajdujący się w argumencie wejściowym -> przy pomocy nowo utworzonej tablicy
        int i = 0;
        for (i = 0; i < arr.length; i++) { //Celowo posługujemy się w pętli for, już istniejącą zmienną i - aby była dostępna później poza pętlą
            if (Character.isDigit(arr[i])) {
                toParse += arr[i];
                continue;
            }

            if (arr[i] == '.' || arr[i] == ',') {
                toParse += '.'; //Musimy wziąć pod uwagę że może być to przecinek
                continue;
            }

            //Jeżeli doszliśmy do tego miejsca, oznacza że już skończyły się interesujące nas znaki
            break;
        }

        //Patrzymy na jakim jesteśmy etapie, i tworzymy nową tablicę
        char[] newArr = new char[arr.length - i];
        for (int q = 0; q < newArr.length; q++)
            newArr[q] = arr[i++];

        //Jeżeli nowoutworzona tablica jest pusta, ustawiamy jej referencję na null
        if (newArr.length == 0)
            arr = null;
        else //W przeciwnym wypadku ustawiamy jej referencje na nowo utworzoną tablicę - bez wyciętych cyfr
            arr = newArr;

        if (toParse.length() == 0)
            return 1.0; //Oznacza to że pierwszym znakiem jednomianu jest oznaczenie zmiennej.

        return Double.parseDouble(toParse); //Funkcja została zaprojektowana tak, aby nie zwracała żadnych wyjątków
    }

    public enum MathFunctionType {
        Sinus, Cosinus, Tangens, Cotangens, Logarithm;
    }
}

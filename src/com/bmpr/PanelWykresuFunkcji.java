package com.bmpr;

import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLOutput;

// Klasa reprezentuj±ca panel - element grupujacy, implementujaca słuchacza akcji
class PanelWykresuFunkcji extends JPanel implements ActionListener {

    final static Color kolorRamki = Color.red;                              // Specyfikacja stałych reprezentuj±cych kolory
    final static Color kolorTla   = Color.white;                            //*
    final static Color kolorLiter = Color.black;                            //*

    String funkcja;                                                         //String tekstowe z zapisaną funkcja

    public PanelWykresuFunkcji(String f) {                                  // Konstruktor tworzący panel graficzny
        funkcja = f; }                                                      // Zachowuje przekazane przez klasę Okno zmienna String

    public void actionPerformed(ActionEvent e) {
                getParent().repaint(); }         //Nie wolno wywoływać metody paint(), ani paintComponent() bezpośrednio, nalezy na jego instancji wywolac repaint() by odświerzyć

    public void paint(Graphics g) {
        Dimension d = getSize();
        g.setColor(kolorTla);                                                 // Wypełnia panel kolorem tła
        g.fillRect(0, 0, d.width - 1, d.height - 1);            //*
        g.setColor(kolorRamki);                                               // Rysuje ramkę dookoła panela
        g.drawRect(0, 0, d.width - 1, d.height - 1);     //*
        g.translate (d.width/2,d.height/2);                            //ustawia początek kontekstu graficznego na punkt ( d.width/2, d.height/2 )

        int iloscPrzedzialekX = 50;                                           // okreslenie liczby przedzialow na osi OX
        int przedzialkaX = d.width/iloscPrzedzialekX;                         // określenie odstepu miedzy przedziałkami na osi X
        int iloscPrzedzialekY = 40;                                           // okreslenie liczby przedzialow na osi OY
        int przedzialkaY = d.height/iloscPrzedzialekY;                        // określenie odstepu miedzy przedziałkami na osi Y

        g.setColor(kolorTla.darker().darker().darker());                // OS oX
        g.drawLine(0,0,d.width/2-1,0);                          // rysuje pol osi OX
        int tmp = 0;                                                          // zmienna dodatkowa potrzebna do wypisania etykiet przedziałek
        for(int i=0; i<d.width/2-1; i=i+przedzialkaX){
            g.drawLine(i,3,i,-3);                                      // rysuje przedzialke na osi OX
            g.drawString(String.valueOf(tmp), i-4, 15);                 // wypisuje etykiety przedzialek na osi OX
            tmp++; }
        g.drawLine(0,0,d.width/-2+1,0);                         // rysuje pol osi OX
        tmp = 0;                                                              // zmienna dodatkowa potrzebna do wypisania etykiet przedziałek
        for(int i=0; i>d.width/-2+1; i=i-przedzialkaX){
            g.drawLine(i,3,i,-3);                                      // rysuje przedzialke na osi OX
            if(tmp != 0) g.drawString(String.valueOf(tmp), i-4, 15);    // wypisuje etykiety przedzialek na osi OX
            tmp--; }
        g.drawLine(d.width/2-1,0, d.width/2-11, 10);             //grot na osi OX
        g.drawLine(d.width/2-1,0, d.width/2-11, -10);            // *
        g.drawLine(0,0,0,d.height/-2+1);                    // OS oY
        tmp = 0;
        for(int i=0; i>d.height/-2+1; i=i-przedzialkaY){
            g.drawLine(-3,i,3,i);                                       // rysuje przedzialke na osi OY co odstep przedzialkaY(int)
            if(tmp != 0) g.drawString(String.valueOf(tmp),6,i+4);       // wypisuje etykiety przedzialek na osi OX
            tmp++; }
        g.drawLine(0,0,0,d.height/2-1);
        tmp = 0;
        for(int i=0; i<d.height/2-1; i=i+przedzialkaY){
            g.drawLine(-3,i,3,i);                                       // rysuje przedzialke na osi OY co odstep przedzialkaY(int)
            if(tmp != 0) g.drawString(String.valueOf(tmp),6,i+4);       // wypisuje etykiety przedzialek na osi OX
            tmp++; }
        g.drawLine(0,d.height/-2+1,10,d.height/-2+11);           // grot na osi OY
        g.drawLine(0,d.height/-2+1,-10,d.height/-2+11);          // *

        g.setColor(Color.green);
        double x = 0;
        //double y = Math.sin((x*Math.PI/180)*35)*10; p.ValueOf(1)                   //FUNKCJA
        Polynomial p = new Polynomial(funkcja);
        double y = p.ValueOf(x);
        //double y = Double.parseDouble(funkcja);

        double yTmp = y;
        double xTmp = 0;
        for(x = 1; x < iloscPrzedzialekX/2+1; x++){
            //y = y = Math.sin((x*Math.PI/180)*35)*10;                       //FUNKCJA
            y = p.ValueOf(x);
            //y = Double.parseDouble(funkcja);
            g.drawLine(((int)xTmp*przedzialkaX),((int)yTmp*przedzialkaY)*-1, ((int)x*przedzialkaX),((int)y*przedzialkaY)*-1);
            yTmp = y;
            xTmp = x; }
        x = 0;
        //y = y = Math.sin((x*Math.PI/180)*35)*10;                           //FUNKCJA
        y = p.ValueOf(x);
        //y = Double.parseDouble(funkcja);
        yTmp = y;
        xTmp = 0;
        for(x = 1; x > iloscPrzedzialekX/-2-1; x--){
            //y = y = Math.sin((x*Math.PI/180)*35)*10;                      //FUNKCJA
            y = p.ValueOf(x);
            //y = Double.parseDouble(funkcja);
            g.drawLine(((int)xTmp*przedzialkaX),((int)yTmp*przedzialkaY)*-1,((int)x*przedzialkaX),((int)y*przedzialkaY)*-1);
            yTmp = y;
            xTmp = x; }



    }

}
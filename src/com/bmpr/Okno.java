package com.bmpr;

import javafx.scene.layout.Pane;

import java.awt.*;
import javax.swing.*;

public class Okno {
    final JFrame okno = new JFrame("Wykres funkcji");                                           //okno dla aplikacji
    JLabel etykieta = new JLabel("Wykres funkcji", JLabel.CENTER);                              //etykieta tekstowa z automatycznym centrowaniem tekstu
    JPanel panelKontrolny = new JPanel();                                                            //panel kontrolny:
    JLabel opis1 = new JLabel(" f(x) = ", JLabel.RIGHT);                                        //element panelu kontrolnego
    JTextField poleTextFunkcji = new JTextField("2x^2+3x+1");                                        //element panelu kontrolnego, do ktorego wpisuje się funkcje
    JButton przyciskRysuj = new JButton("Rysuj");                                               //przycisk służący do odswiezania rysowania wykresu
    PanelWykresuFunkcji panelWykresuFunkcji = new PanelWykresuFunkcji(poleTextFunkcji.getText());    // Tworzy nowy PanelWykresuFunkcji, udostępniając mu pole tekstowe z parametrami funkcji
    Container wnetrzeOkna = okno.getContentPane();                                                   //kontener okna



    public Okno() {
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                             //w momencie zamkniecia okna cala aplikacja ma byc zamknieta
        etykieta.setPreferredSize(new Dimension(400,25));                  //ustawia wymiar etykiety
        panelKontrolny.setLayout(new GridLayout(1,6));                        // Dodaje elementy do panelu kontrolnego by były obok siebie
        panelKontrolny.add(opis1);                                                       // *
        panelKontrolny.add(poleTextFunkcji);                                             // *
        panelKontrolny.add(przyciskRysuj);                                               // *
        przyciskRysuj.addActionListener(panelWykresuFunkcji);                            //przypisuje przyciskowi rysuj sluchacza akcji, który odświezy wykres
        wnetrzeOkna.add(etykieta, BorderLayout.NORTH);                                   //ustawienie elementow wewnatrz kontenera - góra okna
        wnetrzeOkna.add(panelWykresuFunkcji, BorderLayout.CENTER);                       //środek okna
        wnetrzeOkna.add(panelKontrolny, BorderLayout.SOUTH);                             //dół okna
        okno.setSize(new Dimension(((Integer)Toolkit.getDefaultToolkit().getScreenSize().width)*4/5,((Integer)Toolkit.getDefaultToolkit().getScreenSize().height)*4/5));//poczatkowe wymiary okna 80% wysokosci i szerokosci ekranu
        okno.setVisible(true);
        okno.setLocationRelativeTo(null);                                                //domyślne wyświetlanie na srodku
    }

    public Okno(String równanie) { //Dodałem sobie aby było wygodniej testować moją bibliotekę
        poleTextFunkcji.setText(równanie);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                             //w momencie zamkniecia okna cala aplikacja ma byc zamknieta
        etykieta.setPreferredSize(new Dimension(400,25));                  //ustawia wymiar etykiety
        panelKontrolny.setLayout(new GridLayout(1,6));                        // Dodaje elementy do panelu kontrolnego by były obok siebie
        panelKontrolny.add(opis1);                                                       // *
        panelKontrolny.add(poleTextFunkcji);                                             // *
        panelKontrolny.add(przyciskRysuj);                                               // *
        przyciskRysuj.addActionListener(e -> RysujFx());                            //przypisuje przyciskowi rysuj sluchacza akcji, który odświezy wykres
        wnetrzeOkna.add(etykieta, BorderLayout.NORTH);                                   //ustawienie elementow wewnatrz kontenera - góra okna
        wnetrzeOkna.add(panelWykresuFunkcji, BorderLayout.CENTER);                       //środek okna
        wnetrzeOkna.add(panelKontrolny, BorderLayout.SOUTH);                             //dół okna
        okno.setSize(new Dimension(((Integer)Toolkit.getDefaultToolkit().getScreenSize().width)*4/5,((Integer)Toolkit.getDefaultToolkit().getScreenSize().height)*4/5));//poczatkowe wymiary okna 80% wysokosci i szerokosci ekranu
        okno.setVisible(true);
        okno.setLocationRelativeTo(null);
    }

    public void RysujFx(String równanie) {
        panelWykresuFunkcji.funkcja = równanie;
        panelWykresuFunkcji.repaint();
    }

    public void RysujFx() {
        RysujFx(poleTextFunkcji.getText());
    }

    public static void main(String args[]) {
        Okno okienko = new Okno();
        System.out.println(okienko.poleTextFunkcji.getText());
    }
}
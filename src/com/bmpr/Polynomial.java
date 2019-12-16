package com.bmpr;//Bartek Miąskowski
import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    private class Monomial {
        private double rank;
        private double base;
        private char variable;

        public  Monomial(String expression) {
            //Zakładamy że przyjmujemy pojedynczy składnik jako wyrażenie, np. 2x^3 - jest to string w takiej właśnie formie
            char[] charArr = expression.toCharArray();
            base = Extras.ExtractDoubleFromString(charArr); // <- Mamy już podstawę jednomianu

            //Teraz musimy się zająć zmienną towarzyszącą naszemu jednomianowi - czyli w przypadku "2x^3" będzie to 'x' - zakładamy że jest to jeden znak
            if (charArr != null)
                variable = charArr[0]; //Siłą rzeczy musi być na tej pozycji
            //W przeciwnym wypadku zostawiamy tę zmienną jako ' '
            else
                variable = ' ';

            //Teraz zajmiemy się stopniem wielomianu - zakładamy że stopień zaczyna się znakiem potęgi '^'
            String rankStr = "";
            for (int i = 1; i < charArr.length; i++) {
                if (charArr[i] == '^')
                    continue; //Po prostu pomijamy

                if (charArr[i] == '-' && i == 2) { //Minus może być tylko na drugiej pozycji
                    rankStr += charArr[i];
                    continue;
                }

                if (Character.isDigit(charArr[i])) {
                    rankStr += charArr[i];
                    continue;
                }

                if (charArr[i] == ',' || charArr[i] == '.') {
                    rankStr += '.';
                    continue;
                }

                //Jeżeli doszliśmy do tego momentu, oznacza że wprowadziliśmy jakieś śmieci w jednomianie, więc możemy sobie wyplć wyjątek
                //throw new BadPolynomialException(); <- Do zrobienia później
            }

            //Po zakońćzeniu się pętli, sprawdzamy czy wykładnik nie jest zerowy lub nie jest samym minusem
            if (rankStr.length() == 0)
                rank = 1; //Do potęgi pierwszej

            else { //Musimy to zrobić aby uniknąć wyjątku
                if (rankStr.length() == 1 && rankStr.charAt(0) == '-')
                    rank = 1;
                else
                    rank = Double.parseDouble(rankStr);
            }
        }

        public double ValueOf(double y) {
            if (variable != ' ') {
                return (base * (Math.pow(y, rank)));
            }

            else
                return base;
        }
    }

    private class MathFunction {
        private Monomial monomialContent;
        private MathFunction functionContent; //Ponieważ funkcje mogą być zagnieżdżone
        private Extras.MathFunctionType functionType;
        private boolean isContentFunc;

        public MathFunction(String expression) {
            //W pierwszej kolejności sprawdzamy jaka to funkcja
            functionType = DetermineFunctionType(expression);

            //Następnie wypakowujemy funkcję do zmiennej
            String extracted = ExtractFunction(expression);

            //Teraz sprawdzamy czy zagnieżdżenie jest funkcją - jeżeli nie, jest jednomianem
            try { //Używamy try ponieważ później wywoływana funkcja wypieprzy nam wyjątek
                if (DetermineFunctionType(extracted) == null) { //Jest jednomianem
                    isContentFunc = false;
                    monomialContent = new Monomial(extracted);
                }

                else {
                    isContentFunc = true;
                    functionContent = new MathFunction(extracted);
                }
            }

            catch(RuntimeException e) {
                //Od razu wiemy że jest jednomianem
                isContentFunc = false;
                monomialContent = new Monomial(extracted);
            }
        }

        private Extras.MathFunctionType DetermineFunctionType(String fun) {
            //Jeżeli parametr ma mniej niż 2 znaki, zwracamy wyjątek
            //throw ...

            //Nie możemy użyć metody contains, ponieważ funkcje mogą być zagnieżdżone, ale weźmy pod uwagę że ich nazwy składają się z 2 i 3 znaków
            String cache = fun.charAt(0) + "" + fun.charAt(1); //Cudzysłów jest po to, aby zmienne typu char się nie zsumowały - ponieważ pod nimi ukryty jest integer
            if (cache == "tg")
                return Extras.MathFunctionType.Tangens;

            //if (fun.length() < 3)
            //Wypluwamy wyjątek

            cache += fun.charAt(2);
            switch (cache) {
                case "sin":
                    return Extras.MathFunctionType.Sinus;

                case "cos":
                    return Extras.MathFunctionType.Cosinus;

                case "ctg":
                    return Extras.MathFunctionType.Cotangens;

                case "log":
                    return Extras.MathFunctionType.Logarithm;

                    default:
                        throw new RuntimeException();
            }
        }
        private String ExtractFunction(String fun) {
            //Robimy to w najprostszy możliwy sposób - usuwamy wszystko do pierwszego nawiasu otwierającego, a od końca usuwamy zamykający (czyli ostatni znak)
            String str = "";
            char[] arr = fun.toCharArray();
            boolean funcHeaderEnded = false;

            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] == '(' && !funcHeaderEnded) {
                    funcHeaderEnded = true;
                    continue;
                }

                if (funcHeaderEnded)
                    str += arr[i];
            }

            return str; //Zwracamy wyodrębnioną zawartość funkcji - co by to nie było
        }

        public double ValueOf(double y) {
            switch (functionType) {
                case Sinus:
                    return Math.sin(ValueOfInternal(y));

                case Cosinus:
                    return Math.cos(ValueOfInternal(y));

                case Tangens:
                    return Math.tan(ValueOfInternal(y));

                case Cotangens:
                    return 1.0 / Math.tan(ValueOfInternal(y));

                case Logarithm:
                    return Math.log(ValueOfInternal(y));
            }

            //Wyjątek!
            return 1.0;
        }

        private double ValueOfInternal(double y) { //Zwraca wartość wyrażenia zagnieżdżonego
            if (isContentFunc)
                return functionContent.ValueOf(y);
            else
                return monomialContent.ValueOf(y);
        }
    }

    private ArrayList<String> Expressions; // <- wiadomo, do zmiany
    private ArrayList<String> operatory;
    private ArrayList<Object> Ingredients;

    public double ValueOf(double y) {
        //Teraz zaczyna się zabawa! :D
        double[] results = new double[Ingredients.size()];

        for (int i = 0; i < Ingredients.size(); i++) {
            if (Expressions.get(i) == "f")
                results[i] = ((MathFunction) Ingredients.get(i)).ValueOf(y);
            else
                results[i] = ((Monomial) Ingredients.get(i)).ValueOf(y);
        }

        //Znaków jest o jeden mniej aniżeli rezultatów
        double result = results[0];
        for (int i = 0; i < operatory.size(); i++) {
            switch (operatory.get(i)) { //Dopracować kolejność wykonywania działań w tym miejscu
                case "+":
                    result += results[i + 1];
                    break;

                case "-":
                    result -= results[i + 1];
                    break;

                case "*":
                    result *= results[i + 1];
                    break;

                case "/":
                    result /= results[i + 1];
                    break;
            }
        }

        return result;
    }

    public Polynomial(String polynomialExpression) {
        //W pierwszej kolejności usuwamy wszystkie spacje <- do późniejszej implementacji
        //...

        //Inicjujemy listy
        Expressions = new ArrayList<String>();
        operatory = new ArrayList<String>();
        Ingredients = new ArrayList<Object>();

        //Teraz dzielimy nasze wyrażenie na pojedyncze wielomiany
        ArrayList<String> polynomials = new ArrayList<String>();
        int openMarks = 0;
        int counter = 0;
        for (int i = 0; i < polynomialExpression.length(); i++) {
            if (IsThisArythmeticOperator(polynomialExpression.charAt(i)) && (i != 0 && polynomialExpression.charAt(i) != '-') && openMarks == 0) {
                operatory.add(polynomialExpression.charAt(i) + ""); //Ponieważ lista nie może zawierać typów prostych, a String nim nie jest

                //Wszystko co było do tego momentu, traktujemy pojedynczym wielomianem
                String p = "";
                while (counter < i) {
                    p += polynomialExpression.charAt(counter);
                    counter++;
                }
                counter++; //Przeskakujemy pozycję na której jest operator

                Expressions.add(p);
                continue;
            }

            if (polynomialExpression.charAt(i) == '(')
                openMarks++;

            if (polynomialExpression.charAt(i) == ')')
                openMarks--;
        }

        //Teraz dodajemy ostatnie wyrażenie do listy
        String s = "";
        for (int i = counter; i < polynomialExpression.length(); i++)
            s += polynomialExpression.charAt(i);

        //Ostatnim znakiem nie może być operator, więc jest nim wyrażenie - jednomian lub funkcja
        Expressions.add(s);

        //Teraz dodamy sobie wszystko elegancko do listy obiektów - żeby pobawić się rzutowaniem
        for (int i = 0; i < Expressions.size(); i++) {
            if (IsThisFunction(Expressions.get(i))) {
                Ingredients.add(new MathFunction(Expressions.get(i)));
                Expressions.set(i, "f");
            }

            else {
                Ingredients.add(new Monomial(Expressions.get(i)));
                Expressions.set(i, "m");
            }
        }
    }

    private boolean IsThisArythmeticOperator(char character) {
        switch (character) {
            case '+':
                return true;

            case '-':
                return true;

            case '*':
                return true;

            case '/':
                return true;
        }

        return false;
    }

    private boolean IsThisFunction(String exp) {
        //Robimy to w najbardziej łopatologiczny sposób - potem można to zrobić lepiej
        try {
            var func = new MathFunction(exp);
            return true;
        }

        catch (RuntimeException e) {
            return false;
        }
    }
}

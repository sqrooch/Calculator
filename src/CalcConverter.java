import exceptions.InvalidNumberFormatException;

class CalcConverter {
    boolean checkNumberSystem(String number) throws InvalidNumberFormatException {
        if (number.matches("[1-9]|10")) {
            return true;
        } else if (number.matches("I{1,3}|IV|VI{0,3}|I?X")) {
            return false;
        } else {
            throw new InvalidNumberFormatException("""
                    Неверный формат ввода чисел.
                    Числа должны содержать целые значения от 1 до 10 и быть корректно записанны.
                    Подробнее о римских цифрах здесь: https://planetcalc.ru/378/
                    """);
        }
    }

    int fromRomanToArabian(String romanNumber) {
        char[] romanNumberArray = romanNumber.toCharArray();
        int[] arabicNumberArray = new int[romanNumber.length()];

        for (int i = 0; i < romanNumberArray.length; i++) {
            for (Dict symbol : Dict.values()) {
                if (romanNumberArray[i] == symbol.name().charAt(0)) {
                    arabicNumberArray[i] = symbol.getEquivalent();
                }
            }
        }

        int arabicNumber = arabicNumberArray[0];
        for (int i = 0, j = 1; j < arabicNumberArray.length; i++, j++) {
            arabicNumber = (arabicNumberArray[i] >= arabicNumberArray[j]) ? (arabicNumber + arabicNumberArray[j]) :
                    (arabicNumberArray[j] - arabicNumber);
        }
        return arabicNumber;
    }

    String fromArabianToRoman(int arabicNumber) {
        StringBuilder romanNumber = new StringBuilder();
        for (int i = Dict.values().length - 1; i >= 0; i--) {
            romanNumber.append(Dict.values()[i].name().repeat(arabicNumber / Dict.values()[i].getEquivalent()));
            arabicNumber %= Dict.values()[i].getEquivalent();
        }

        String tempSymbol;

        for (int matchCounter = 0, i = 0; i < romanNumber.length() - 1; i++) {
            tempSymbol = romanNumber.substring(i, i + 1);

            matchCounter = (romanNumber.substring(i + 1, i + 2).equals(tempSymbol)) ? (matchCounter + 1) : 0;

            if (matchCounter == 3 && (tempSymbol.equals(Dict.I.name()) || tempSymbol.equals(Dict.X.name()))) {
                romanNumber.replace(i - 1, i + 2, Dict.values()[Dict.valueOf(tempSymbol).ordinal() + 1].name());
                i = 0;
                matchCounter = 0;
            }
        }

        for (int i = 0; i < romanNumber.length() - 1; i++) {
            tempSymbol = romanNumber.substring(i, i + 1);
            if (romanNumber.length() >= i + 3) {
                if ((tempSymbol.equals(Dict.V.name()) || tempSymbol.equals(Dict.L.name())) &&
                        tempSymbol.equals(romanNumber.substring(i + 2, i + 3))) {
                    romanNumber.delete(i, i + 1);
                    romanNumber.replace(i + 1, i + 2, Dict.values()[Dict.valueOf(tempSymbol).ordinal() + 1].name());
                }
            }
        }
        return romanNumber.toString();
    }
}

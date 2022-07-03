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

    int fromRomanToArabian(String inputNumber) {
        char[] inputNumberArray = inputNumber.toCharArray();
        int[] resultNumberArray = new int[inputNumber.length()];

        for (int i = 0; i < inputNumberArray.length; i++) {
            for (Dict el : Dict.values()) {
                if (inputNumberArray[i] == el.name().toCharArray()[0]) {
                    resultNumberArray[i] = el.getEquivalent();
                }
            }
        }

        int resultNumber = resultNumberArray[0];
        for (int i = 0, j = 1; j < resultNumberArray.length; i++, j++) {
            if (resultNumberArray[i] >= resultNumberArray[j]) {
                resultNumber += resultNumberArray[j];
            } else {
                resultNumber = resultNumberArray[j] - resultNumber;
            }
        }
        return resultNumber;
    }
}

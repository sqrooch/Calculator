import exceptions.NonMathOperationException;
import exceptions.TooManyOperandsException;

class CalcAssistant {
    char findOperator(String input) throws NonMathOperationException {
        char operator = ' ';
        for (String ch : new String[]{"/", "*", "-", "+"}) {
            if (input.contains(ch)) {
                operator = ch.toCharArray()[0];
                break;
            }
        }
        if (operator == ' ') throw new NonMathOperationException("Строка не является математической операцией.");

        return operator;
    }

    String formatInputData(String input) throws TooManyOperandsException {
        input = input.toUpperCase();
        if (!input.matches("[IVX\\d\\s]+[/*+-][IVX\\d\\s]+")) {
            throw new TooManyOperandsException("Формат математической операции не удовлетворяет заданию.\n" +
                    "В строке ввода должно быть два операнда и один оператор (+, -, /, *).");
        }

        return input;
    }
}

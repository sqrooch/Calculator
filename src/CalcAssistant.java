import exceptions.NonMathOperationException;
import exceptions.TooManyOperandsException;

/**
 * Помогает методу calc совершать попутные неосновные операции.
 */
class CalcAssistant {
    /**
     * Помогает отпределить тип арифметической операции в выражении.
     *
     * @param input Принимает строку типа String, введённую пользователем.
     * @return Возвращает символ оператора типа char.
     * @throws NonMathOperationException Отсутствие оператора в выражении.
     */
    char findOperator(String input) throws NonMathOperationException {
        char operator = ' ';
        for (String ch : new String[]{"/", "*", "-", "+"}) {
            if (input.contains(ch)) {
                operator = ch.charAt(0);
                break;
            }
        }
        if (operator == ' ') throw new NonMathOperationException("Строка не является математической операцией.");

        return operator;
    }

    /**
     * Переводит строку от пользователя в верхний регистр и проверяет на корректность ввода.
     *
     * @param input Принимает строку типа String, введённую пользователем.
     * @return Возвращает ту же строку типа String, но в верхнем регистре.
     * @throws TooManyOperandsException Запрет использования более двух операндов в выражении.
     */
    String formatInputData(String input) throws TooManyOperandsException {
        input = input.toUpperCase();
        if (!input.matches("[IVX\\d\\s]+[/*+-][IVX\\d\\s]+")) {
            throw new TooManyOperandsException("Формат математической операции не удовлетворяет заданию.\n" +
                    "В строке ввода должно быть два операнда и один оператор (+, -, /, *).");
        }

        return input;
    }
}

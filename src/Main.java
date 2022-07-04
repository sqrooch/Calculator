import java.util.Scanner;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import exceptions.*;

/**
 * @author sqrooch.
 * Консольный калькулятор для римской и арабской систем счислений.
 */
public class Main {
    /**
     * Основной метод, который запускает программу калькулятора.
     * Запрашивает от пользователя строку в виде математического выражения.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        PrintStream err = new PrintStream(System.err, true, StandardCharsets.UTF_8);
        out.print("Введите выражение сюда -> ");
        String userInput = in.nextLine();
        out.print("Результат: ");
        try {
            out.println(calc(userInput));
        } catch (TooManyOperandsException | NonMathOperationException |
                 InvalidNumberFormatException | DifferentNumberSystemsException | NegativeResultException ex) {
            err.println(ex.getMessage());
        }
    }

    /**
     * Метод для подсчёта результата математического выражения. Работает с арабскими и римскими цифрами.
     *
     * @param input Принимает строку типа String, введённую пользователем.
     * @return Возвращает строку типа String, как результат арифметической операции.
     * @throws TooManyOperandsException        Запрет использования более двух операндов в выражении.
     * @throws NonMathOperationException       Отсутствие оператора в выражении.
     * @throws InvalidNumberFormatException    Формат выражения не удовлетворяет условиям задачи.
     * @throws DifferentNumberSystemsException Одновременное использование разных Систем Счисления.
     * @throws NegativeResultException         Отрицательный результат арифметического выражения при использовании римской СС.
     */
    public static String calc(String input)
            throws TooManyOperandsException, NonMathOperationException,
            InvalidNumberFormatException, DifferentNumberSystemsException, NegativeResultException {
        CalcAssistant assistant = new CalcAssistant();
        input = assistant.formatInputData(input);
        char operator = assistant.findOperator(input);

        String[] numbers = input.split("[*+/-]");
        String firstNumber = numbers[0].trim();
        String secondNumber = numbers[1].trim();

        CalcConverter converter = new CalcConverter();
        boolean arabianFormat = converter.checkNumberSystem(firstNumber);
        if (arabianFormat != converter.checkNumberSystem(secondNumber)) {
            throw new DifferentNumberSystemsException("Нельзя использовать разные системы счисления одновременно.");
        }

        int firstValue, secondValue, result = 0;
        if (arabianFormat) {
            firstValue = Integer.parseInt(firstNumber);
            secondValue = Integer.parseInt(secondNumber);
        } else {
            firstValue = converter.fromRomanToArabian(firstNumber);
            secondValue = converter.fromRomanToArabian(secondNumber);
        }

        switch (operator) {
            case '/' -> result = firstValue / secondValue;
            case '*' -> result = firstValue * secondValue;
            case '+' -> result = firstValue + secondValue;
            case '-' -> result = firstValue - secondValue;
        }

        String output;
        if (arabianFormat) {
            output = String.valueOf(result);
        } else {
            if (result <= 0) {
                throw new NegativeResultException("В римской системе нет отрицательных чисел.");
            } else {
                output = converter.fromArabianToRoman(result);
            }
        }
        return output;
    }
}

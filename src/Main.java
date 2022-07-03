//import java.util.Scanner;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import exceptions.*;

public class Main {
    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        PrintStream err = new PrintStream(System.err, true, StandardCharsets.UTF_8);
        try {
            //System.out.println(calc(in.nextLine()));
            System.out.println(calc("iX * x"));
        } catch (TooManyOperandsException | NonMathOperationException |
                 InvalidNumberFormatException | DifferentNumberSystemsException | NegativeResultException ex) {
            err.println(ex.getMessage());
        }
    }

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
                output = firstNumber;
            }
        }
        return output;
    }
}

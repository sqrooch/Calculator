//import java.util.Scanner;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import exceptions.*;

public class Main
{
    public static boolean arabianFormat = false;

    public static void main(String[] args)
    {
        //Scanner in = new Scanner(System.in);
        //PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        PrintStream err = new PrintStream(System.err, true, StandardCharsets.UTF_8);
        try
        {
            //System.out.println(calc(in.nextLine()));
            System.out.println(calc("ix+9"));
        }
        catch (TooManyOperandsException | NonMathOperationException |
               InvalidNumberFormatException | DifferentNumberSystemsException ex)
        {
            err.println(ex.getMessage());
        }
    }

    public static String calc(String input)
            throws TooManyOperandsException, NonMathOperationException,
            InvalidNumberFormatException, DifferentNumberSystemsException
    {
        char operator = ' ';
        for (String ch:new String[]{"/", "*", "-", "+"})
        {
            if (input.contains(ch))
            {
                operator = ch.toCharArray()[0];
                break;
            }
        }
        if (operator == ' ') throw new NonMathOperationException("Строка не является математической операцией.");

        input = input.toUpperCase();
        if (!input.matches("[IVX\\d\\s]+[/*+-][IVX\\d\\s]+"))
        {
            throw new TooManyOperandsException("Формат математической операции не удовлетворяет заданию.\n" +
                    "В строке ввода должно быть два операнда и один оператор (+, -, /, *).");
        }

        String[] numbers = input.split("[*+/-]");
        String firstNumber = numbers[0].trim();
        String secondNumber = numbers[1].trim();

        if (checkNumberSystem(firstNumber) != checkNumberSystem(secondNumber))
        {
            throw new DifferentNumberSystemsException("Нельзя использовать разные системы счисления одновременно.");
        }

        int firstValue, secondValue, result;
        if (arabianFormat)
        {
            firstValue = Integer.parseInt(firstNumber);
            secondValue = Integer.parseInt(secondNumber);
        }
        else
        {
            firstValue = fromRomanToArabian(firstNumber);
            secondValue = fromRomanToArabian(secondNumber);
        }

        return firstNumber;
    }

    public static boolean checkNumberSystem(String number) throws InvalidNumberFormatException
    {
        if (number.matches("[1-9]|10"))
        {
            arabianFormat = true;
            return true;
        }
        else if (number.matches("I{1,3}|IV|VI{0,3}|I?X"))
        {
            return false;
        }
        else
        {
            throw new InvalidNumberFormatException("""
                    Неверный формат ввода чисел.
                    Числа должны содержать целые значения от 1 до 10 и быть корректно записанны.
                    Подробнее о римских цифрах здесь: https://planetcalc.ru/378/
                    """);
        }
    }

    public static int fromRomanToArabian(String inputNumber)
    {
        char[] inputNumberArray = inputNumber.toCharArray();
        int[] resultNumberArray = new int[inputNumber.length()];

        char[] romanDict = new char[]{'I', 'V', 'X', 'L', 'C'};
        int[] arabianDict = new int[]{1, 5, 10, 50, 100};

        for (int i = 0; i < inputNumberArray.length; i++)
        {
            for (int j = 0; j < romanDict.length; j++)
            {
                if (inputNumberArray[i] == romanDict[j])
                {
                    resultNumberArray[i] = arabianDict[j];
                }
            }
        }

        int resultNumber = resultNumberArray[0];
        for (int i=0, j=1; j < resultNumberArray.length; i++, j++)
        {
            if (resultNumberArray[i] >= resultNumberArray[j])
            {
                resultNumber += resultNumberArray[j];
            }
            else
            {
                resultNumber = resultNumberArray[j] - resultNumber;
            }
        }
        System.out.println(resultNumber);
        return resultNumber;
    }
}

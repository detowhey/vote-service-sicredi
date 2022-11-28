package br.com.sicredi.api.util;

import java.util.Random;

public class CpfGenerator {

    public static String generateCpf() {
        int digit1;
        int digit2;
        int division;
        Random random = new Random();

        int n1 = random.nextInt(10);
        int n2 = random.nextInt(10);
        int n3 = random.nextInt(10);
        int n4 = random.nextInt(10);
        int n5 = random.nextInt(10);
        int n6 = random.nextInt(10);
        int n7 = random.nextInt(10);
        int n8 = random.nextInt(10);
        int n9 = random.nextInt(10);
        int sum = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;
        int value = (sum / 11) * 11;

        digit1 = sum - value;
        division = digit1 % 11;
        digit1 = digit1 < 2 ? 0 : 11 - division;

        int soma2 = digit1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;
        int valor2 = (soma2 / 11) * 11;

        digit2 = soma2 - valor2;
        division = digit2 % 11;
        digit2 = digit1 < 2 ? 0 : 11 - division;

        String textNumber = String.valueOf(n1) + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9;
        String resultWithDigits = String.valueOf(digit1) + digit2;
        return textNumber + resultWithDigits;
    }
}

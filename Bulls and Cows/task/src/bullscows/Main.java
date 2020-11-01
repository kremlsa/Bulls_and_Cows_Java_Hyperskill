package bullscows;

import java.util.Scanner;

public class Main {
    static String secret = "";
    static Scanner scanner = new Scanner(System.in);
    static Boolean isGameOver = false;
    static Boolean isError = false;
    public static void main(String[] args) {
        generateSecret();
        if (isError) {
            return;
        }
        System.out.println("Okay, let's start a game!");
        int turn = 1;
        while(!isGameOver) {
            System.out.println("Turn " + turn + ":");
            playGame();
            turn++;
        }

    }

    public static void generateSecret() {
        String input = "";
        System.out.println("Input the length of the secret code:");
        input  = scanner.nextLine();
        if (!input.matches("\\d+")) {
            System.out.print("Error: \"" + input + "\" isn't a valid number.");
            isError = true;
            return;
        }
        int secretLength = Integer.parseInt(input);
        System.out.println("Input the number of possible symbols in the code:");
        input  = scanner.nextLine();
        if (!input.matches("\\d+")) {
            System.out.print("Error: \"" + input + "\" isn't a valid number.");
            isError = true;
            return;
        }
        int symbolsRange = Integer.parseInt(input);
        if (symbolsRange < secretLength) {
            System.out.println("Error: it's not possible to generate a code with a length of " +
                    secretLength + " with " + symbolsRange + " unique symbols.");
            isError = true;
            return;
        }
        if (symbolsRange > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            isError = true;
            return;
        }
        if (secretLength < 1) {
            System.out.println("Error: incorrect length.");
            isError = true;
            return;
        }
        String stars = "*".repeat(secretLength);
        while (secretLength > 0) {
            int random = (int) (Math.random() * symbolsRange - 1);
            char symbol = Character.forDigit(random, symbolsRange);
            if (!secret.contains(Character.toString(symbol))) {
                   secret += symbol;
                   secretLength--;
            }

        }
        String range = "";
        if (symbolsRange == 10) {
            range = "(0-9, a).";
        } else if (symbolsRange > 10) {
            range = "(0-9, a-" + Character.forDigit(symbolsRange - 1, symbolsRange) + ").";
        } else {
            range = "(0-9).";
        }
        System.out.println("The secret is prepared: " + stars + " " + range);
    }

    public static void playGame() {
        int bulls = 0;
        int cows = 0;
        String answer = scanner.nextLine();
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == secret.charAt(i)) {
                bulls++;
            } else {
                if (secret.indexOf(answer.charAt(i)) != -1) {
                    cows++;
                }
            }
        }
        String bullOrBulls = bulls > 1 ? "bulls" : "bull";
        String cowOrCows = cows > 1 ? "cows" : "cow";;
        if (bulls == secret.length()) {
            System.out.println("Grade: " + bulls + " " + bullOrBulls +
                    "Congratulations! You guessed the secret code.");
            isGameOver = true;
        } else if (bulls != 0 && cows != 0) {
            System.out.println("Grade: " + bulls + " " + bullOrBulls + " and " +
                    cows + " " + cowOrCows + ".");
        } else if (bulls == 0 && cows != 0) {
            System.out.println("Grade: " + cows + " " + cowOrCows + ".");
        } else if (bulls != 0) {
            System.out.println("Grade: " + bulls + " " + bullOrBulls + ".");
        } else {
            System.out.println("Grade: None.");
        }
    }
}

import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int maxRounds = 5;
        int maxAttempts = 10;
        int totalScore = 0;

        System.out.println("Welcome to the Guess the Number Game!");
        System.out.println("You have " + maxRounds + " rounds to play.");
        System.out.println("Each round, you have " + maxAttempts + " attempts to guess the number.");

        for (int round = 1; round <= maxRounds; round++) {
            int numberToGuess = random.nextInt(100) + 1;
            int attempts = 0;
            boolean hasGuessedCorrectly = false;

            System.out.println("\nRound " + round);
            System.out.println("I have generated a number between 1 and 100. Try to guess it!");

            while (attempts < maxAttempts && !hasGuessedCorrectly) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == numberToGuess) {
                    hasGuessedCorrectly = true;
                    int points = (maxAttempts - attempts + 1) * 10;
                    totalScore += points;
                    System.out.println("Congratulations! You've guessed the number in " + attempts + " attempts.");
                    System.out.println("You earned " + points + " points. Your total score is now " + totalScore + ".");
                } else if (userGuess < numberToGuess) {
                    System.out.println("Your guess is too low.");
                } else {
                    System.out.println("Your guess is too high.");
                }
            }

            if (!hasGuessedCorrectly) {
                System.out.println("Sorry, you've used all your attempts. The number was " + numberToGuess + ".");
            }
        }

        System.out.println("\nGame Over! Your final score is " + totalScore + ".");
        scanner.close();
    }
}



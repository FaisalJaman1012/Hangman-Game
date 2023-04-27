import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class hang {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner keyboard = new Scanner(System.in);

        Scanner scanner = new Scanner(new File("wordlist.txt"));
        List<String> words = new ArrayList<>();

        while (scanner.hasNext()) {
            words.add(scanner.nextLine());
        }

        Random rand = new Random();
        String word = words.get(rand.nextInt(words.size()));
        System.out.println(word);
        System.out.println("Hint: The word starts with the letter '" + word.charAt(0) + "'");

        List<Character> playerGuesses = new ArrayList<>();

        Integer wrongCount = 0;

        while (true) {
            printHangedMan(wrongCount);

            if (wrongCount >= 6) {
                System.out.println("You lose!");
                System.out.println("The word was: " + word);
                break;
            }

            if (printWordState(word, playerGuesses)) {
                System.out.println("You win!");
                break;
            }

            if (!getPlayerGuess(keyboard, word, playerGuesses, wrongCount)) {
                wrongCount++;
                System.out.println("Wrong guess! Chances left: " + (6 - wrongCount));
            }
        }

        keyboard.close();
    }

    private static void printHangedMan(Integer wrongCount) {
        System.out.println(" -------");
        System.out.println(" |     |");
        if (wrongCount >= 1) {

            System.out.println(" O");
        }

        if (wrongCount >= 2) {

            System.out.print("\\ ");
            if (wrongCount >= 3) {

                System.out.println("/");
            } else {
                System.out.println("");
            }
        }

        if (wrongCount >= 4) {

            System.out.println(" |");
        }

        if (wrongCount >= 5) {

            System.out.print("/ ");
            if (wrongCount >= 6) {
                System.out.println("\\");
            } else {
                System.out.println("");
            }
        }
        System.out.println("");
        System.out.println("");
    }

    private static boolean getPlayerGuess(Scanner keyboard, String word, List<Character> playerGuesses,
            Integer wrongCount) {
        System.out.println("Please enter a letter or the full word:");
        String guess = keyboard.nextLine();
        guess = guess.toLowerCase();

        if (guess.equals(word.toLowerCase())) {
            for (int i = 0; i < guess.length(); i++) {
                playerGuesses.add(guess.charAt(i));
            }
            return true;
        }

        if (guess.length() > 1) {
            System.out.println("Invalid input, please try again.");
            return false;
        }

        char letterGuess = guess.charAt(0);
        playerGuesses.add(letterGuess);

        if (!word.toLowerCase().contains(guess)) {
            return false;
        }
        return true;
    }

    private static boolean printWordState(String word, List<Character> playerGuesses) {
        int correctCount = 0;
        for (int i = 0; i < word.length(); i++) {
            if (playerGuesses.contains(word.charAt(i))) {
                System.out.print(word.charAt(i));
                correctCount++;
            } else {
                System.out.print("-");
            }
        }
        System.out.println();

        return (word.length() == correctCount);
    }
}

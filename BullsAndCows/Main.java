import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BullsAndCows provider = new BullsAndCows(new String[]{"abc213", "abc123", "abc234"}, new String[]{"password", "jklqr", "20fj1"});
        provider.welcome();
        String[] credentials = provider.acceptCredentials(sc);
        provider.validateCredentials(credentials);
        boolean shouldRepeat = false;
        do {
            final boolean victory = provider.initialiseGame(sc);
            System.out.println(victory ? "Do you want to play again? Try beating the game with less guesses(Yes/No)." : "Try again?(Yes/No)");
            char response = provider.acceptShouldRepeat(sc);
            shouldRepeat = response == 'Y';
            if(!shouldRepeat) {
                System.out.println("Goodbye!");
            }
        } while(shouldRepeat);
        sc.close();
    }
}

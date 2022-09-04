import java.util.Scanner;

class BullsAndCows 
{
    String[] usernames = new String[3];
    String[] passwords = new String[3];
    
    BullsAndCows(String[] usernames, String[] passwords) {
        this.usernames = usernames;
        this.passwords = passwords;
    }

    private String acceptString(Scanner sc, String type, String message) {
        boolean isValid = true;
        String output = "";
        do {
            System.out.print(message);
            output = sc.next();
            for(int i = 0; i < output.length(); i++) {
                if(type.equals("alphanumeric")) {
                    if(!Character.isLetterOrDigit(output.charAt(i))) {
                        System.out.println("Only letters or numbers are allowed!");
                        isValid = false;
                        break;
                    } else isValid = true;
                } else if(type.equals("alphabet")) {
                    if(!Character.isLetter(output.charAt(i))) {
                        System.out.println("Only letters are allowed!");
                        isValid = false;
                        break;
                    } else isValid = true;
                } else if(type.equals("number")) {
                    if(!Character.isDigit(output.charAt(i))) {
                        System.out.println("Only numbers are allowed!");
                        isValid = false;
                        break;
                    } else isValid = true;
                }
            }
        } while(!isValid);
        System.out.println();
        return output;
    }

    public char acceptShouldRepeat(Scanner sc) {
        boolean isValid = false;
        char response = ' ';
        do {
            response = Character.toUpperCase(sc.next().charAt(0));
            isValid = response == 'Y' || response == 'N';
            if(!isValid) {
                System.out.println("Please enter only yes or no");
            }
        } while(!isValid);
        return response;
    }

    public String[] acceptCredentials(Scanner sc) {
        String username = acceptString(sc, "alphanumeric", "Enter your username: ");
        String password = acceptString(sc, "alphanumeric", "Enter your password: ");
        String[] credentials = {username, password};
        return credentials;
    }

    public void validateCredentials(String[] credentials) {
        String username = credentials[0]; // the 0th index of the credentials array stores the username
        String password = credentials[1]; // the 1st index of the credentials array stores the password
        boolean isUsernameCorrect = false;
        boolean isPasswordCorrect = false;

        // loop to authenticate the user
        for(int i = 0; i < usernames.length; i++) { // iterates through all valid usernames
            isUsernameCorrect = username.equals(usernames[i]);
            if(isUsernameCorrect) {
                isPasswordCorrect = passwords[i].equals(password); // check if password given by user is equal to that account's password
                break; // stops looping(since user is now authenticated)
            }
        }

        if(isUsernameCorrect && isPasswordCorrect){
            System.out.println("Welcome back " + credentials[0]);
        } else {
            System.out.println("Incorrect credentials! Exiting program...");
            System.exit(0); // exits the program
        }
    }

    private String generateTarget() {
        boolean isUnique = false;
        boolean isFourDigit = false;
        int num = 0;
        do {
            num = (int)(Math.random() * 9999); // generates a random number from 1-9999
            isFourDigit = num >= 1000 && num <= 9999; // checks whether the random number is a 4 digit number
            isUnique = isUnique(num);
        } while(!isUnique || !isFourDigit);
        return Integer.toString(num); // returns String form of number generated
    }

    private boolean isUnique(int num) {
        String n = Integer.toString(num);

        // nested loop to check if all characters of the string are unique
        for(int i = 0; i < n.length(); i++) {
            for(int j = 0; j < n.length(); j++) {
                if(n.charAt(i) == n.charAt(j) && i != j) { // checks if character at i is repeated in the string
                    return false;
                }
            }
        }
        return true; // if no characters are repeated(i.e no checks in the previous loop pass), the string is unique
    }

    private boolean validateGuess(String target, String guess) {
        if(Integer.parseInt(guess) <= 0) {
            System.out.println("Guesses can only be positive four digit unique numbers!");
            return false;
        }
        if(!isUnique(Integer.parseInt(guess))) {
            System.out.println("Your guesses must have unique digits!");
            return false;
        }
        if(guess.length() == 4) {
            boolean isGuessCorrect = true;
            int bulls = 0;
            int cows = 0;

            for(int i = 0; i < target.length(); i++) { // iterates through the target string
                for(int j = 0; j < guess.length(); j++) { // iterates through guess string
                    final char targetChar = target.charAt(i);
                    final char guessChar = guess.charAt(j);

                    if(targetChar == guessChar && i == j) { // checks if the digits are matching and their position is the same(i.e if there is a bull)
                        bulls++;
                    } 

                    if(targetChar == guessChar && i != j) { // checks if the digits are matching and their position is different(i.e if there is a cow)
                        cows++;
                        isGuessCorrect = false; // since a character of the string does not match, the guess is incorrect
                    }

                    if(targetChar != guessChar && i == j){ // checks if adjacent characters are not equal
                        isGuessCorrect = false; // since a character of the string does not match, the guess is incorrect 
                    }

                    
                }
            }
            
            if(isGuessCorrect) {
                System.out.println("Congratulations! Your guess is correct.");
            } else {
                System.out.println("Incorrect guess! " + bulls + " bulls and " + cows + " cows"); 
            }
            return isGuessCorrect;
        } else {
            System.out.println("Your guess must be a 4 digit number.");
            return false;
        }
        
    }

    public boolean initialiseGame(Scanner sc) {
        String target = generateTarget();
        boolean guessValidated = false;
        int attempts = 1;
        do {
            System.out.println("\nAttempt " + attempts);
            String guess = acceptString(sc, "number", "Guess what the number is: ");
            guessValidated = validateGuess(target, guess);
            attempts++;
        } while(!guessValidated && attempts <= 7);
        if(attempts > 7) {
            System.out.println("Game Over!");
            System.out.println("The number was " + target);
            return false;
        }
        System.out.println("You took " + attempts + " guesses.");
        return true;
    }

    public void welcome() {
        drawLine(132); // prints dashes if no character is specified
        drawBanner();
        drawLine(132);
        drawLine(132, "Welcome", ' ');
        drawLine(132, "Rules:", ' ');
        drawLine(132, "You have to guess a random unique 4 digit positive number", ' ');
        drawLine(132, "If the matching digits are in the right position, they are 'bulls'", ' ');
        drawLine(132, "If the matching digits are in different positions, they are 'cows'", ' ');
        drawLine(132, "You have a maximum of 7 attempts", ' ');
    }

    private void drawLine(int length) {
        for(int i = 0; i < length; i++) { // loops 'length' number of times
            System.out.print('-');
        }
        System.out.println(); // leaves a new line
    }

    private void drawLine(int length, String message, char charToDisplay) {
        int messageStartIndex = (length/2)-((int)(Math.round((double)(message.length())/2))); // calculates the most appropriate starting index for the message to be centred
        for(int i = 0; i < length; i++) {
            if(i >= messageStartIndex && i <= messageStartIndex+message.length()-1) { // if the loop is iterating through the positions where a message should be
                System.out.print(message.charAt(i-messageStartIndex)); // prints characters of the string
            }
            else {
                System.out.print(charToDisplay);
            }
        }
        System.out.println(); // leaves a new line
    }

    public void drawBanner() {
        System.out.println("|" + "'########::'##::::'##:'##:::::::'##::::::::'######::::::::'###::::'##::: ##:'########::::::'######:::'#######::'##:::::'##::'######::" + "|");
        System.out.println("|" + " ##.... ##: ##:::: ##: ##::::::: ##:::::::'##... ##::::::'## ##::: ###:: ##: ##.... ##::::'##... ##:'##.... ##: ##:'##: ##:'##... ##:" + "|");
        System.out.println("|" + " ##:::: ##: ##:::: ##: ##::::::: ##::::::: ##:::..::::::'##:. ##:: ####: ##: ##:::: ##:::: ##:::..:: ##:::: ##: ##: ##: ##: ##:::..::" + "|");
        System.out.println("|" + " ########:: ##:::: ##: ##::::::: ##:::::::. ######:::::'##:::. ##: ## ## ##: ##:::: ##:::: ##::::::: ##:::: ##: ##: ##: ##:. ######::" + "|");
        System.out.println("|" + " ##.... ##: ##:::: ##: ##::::::: ##::::::::..... ##:::: #########: ##. ####: ##:::: ##:::: ##::::::: ##:::: ##: ##: ##: ##::..... ##:" + "|");
        System.out.println("|" + " ##.... ##: ##:::: ##: ##::::::: ##::::::::..... ##:::: #########: ##. ####: ##:::: ##:::: ##::::::: ##:::: ##: ##: ##: ##::..... ##:" + "|");
        System.out.println("|" + " ##:::: ##: ##:::: ##: ##::::::: ##:::::::'##::: ##:::: ##.... ##: ##:. ###: ##:::: ##:::: ##::: ##: ##:::: ##: ##: ##: ##:'##::: ##:" + "|");
        System.out.println("|" + " ########::. #######:: ########: ########:. ######::::: ##:::: ##: ##::. ##: ########:::::. ######::. #######::. ###. ###::. ######::" + "|");
        System.out.println("|" + "........::::.......:::........::........:::......::::::..:::::..::..::::..::........:::::::......::::.......::::...::...::::......:::" + "|");
    }
}

/***
 *                  VARIABLE DESCRIPTION TABLE
 * Global:
 * name:               data type:            purpose/description:
 * usernames           Array of strings      Stores all valid usernames
 * passwords           Array of strings      Stores all valid passwords
 * 
 * acceptString:
 * name:               data type:            purpose/description:
 * sc                  Scanner               Accepts String input from the user
 * type                String                Type of data being accepted(alphanumeric/alphabet/number)
 * message             String                Message to be displayed before asking for input
 * isValid             boolean               Stores whether the input is valid
 * output              String                Stores the eventual output of the function
 * 
 * 
 * acceptCredentials:
 * name:               data type:            purpose/description:
 * sc                  Scanner               Accepts String input from the user
 * username            String                Stores the username given by the user
 * password            String                Stores the password given by the user
 * credentials         Array of strings      Array with username at index 0 and password at index 1
 * 
 * 
 * validateCredentials:
 * name:               data type:            purpose/description:
 * username            String                Stores the username given by the user
 * password            String                Stores the password given by the user
 * isUsernameCorrect   boolean               Stores whether the username is correct
 * isPasswordCorrect   boolean               Stores whether the password is correct
 * 
 * 
 * generateTarget:
 * name:               data type:            purpose/description:
 * isUnique            boolean               Stores whether the number generated is unique
 * isFourDigit         boolean               Stores whether the number generated is a 4 digit number
 * 
 * 
 * isUnique:
 * name:               data type:            purpose/description:
 * num                 int                   Stores the number for which the isUnique check must be done          
 * n                   String                Stores String form of n
 * i                   int                   Loop control variable for loop which iterates through n the first time
 * j                   int                   Loop control variable for loop which iterates through n the second time
 * 
 * 
 * validateGuess:
 * name:               data type:            purpose/description:
 * target              String                Stores the number which the user must guess
 * guess               String                Stores the guess given by the user
 * isGuessCorrect      boolean               Stores whether the guess is correct
 * bulls               int                   Stores the number of bulls
 * cows                int                   Stores the number of cows
 * i                   int                   Loop control variable for the loop iterating through target
 * j                   int                   Loop control variable for the loop iterating through guess
 * targetChar          char                  Stores the character at the i'th index of target
 * guessChar           char                  Stores the character at the j'th index of guess
 * 
 * 
 * initialiseGame:
 * name:               data type:            purpose/description:
 * target              String                Stores the generated target for the user to guess
 * guessValidated      String                Stores whether the guess is correct or not
 * attempts            int                   Stores the number of attempts/guesses
 * guess               String                Stores a guess given by the user;
 * 
 * 
 * drawLine(all overloaded forms):
 * name:              data type:             purpose/description:
 * i                  int                    Loop control variable for the loop iterating length number of times
 * length             int                    Stores the length of the line to draw
 * charToDisplay      char                   Stores the character to display
 * message            String                 Stores the message to display centred in the line
 * messageStartIndex  int                    Stores the index at which the message should start being displayed

 * 
 */
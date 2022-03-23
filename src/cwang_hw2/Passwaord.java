package cwang_hw2;

import java.util.*;       // Needed for Random and LinkedList objects

/**
 * The Passwaord class generates a random password and then return it.
 */
public class Passwaord {

    /**
     * Generate a random password and then return it.
     *
     * @return the random password
     */
    public static String generateRandomPassword() {
        final int MAX = 15, MIN = 6;
        int length;

        Random random = new Random();
        length = random.nextInt(MAX - MIN + 1) + MIN;

        final String MARK = "!@#$%^&";
        final String LETTER =
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String NUMBER = "0123456789";

        StringBuilder sb = new StringBuilder();

        // how many marks
        int numberOfMark = random.nextInt(length - 2) + 1;
        for (int i = 0; i < numberOfMark; i++){
            int newMark = random.nextInt(MARK.length());
            sb.append(MARK.charAt(newMark));
        }

        // how many letters
        int numberOfLetter = random.nextInt(length - numberOfMark - 1) + 1;
        for (int k = 0; k < numberOfLetter; k++){
            int newLetter = random.nextInt(LETTER.length());
            sb.append(LETTER.charAt(newLetter));
        }

        // how many numbers
        int numberOfNumber = length - numberOfMark - numberOfLetter;
        for (int k = 0; k < numberOfNumber; k++){
            int newNumber = random.nextInt(NUMBER.length());
            sb.append(NUMBER.charAt(newNumber));
        }

        return shuffle(sb.toString());
    }

    /**
     * The shuffle function shuffles the generated password and then return it.
     *
     * @param text the generated password.
     * @return the shuffled password
     */
    public static String shuffle(String text){
        List<Character> characters = new LinkedList<>();
        StringBuilder result = new StringBuilder();

        // Convert String to char
        for(char c:text.toCharArray())
            characters.add(c);

        // shuffle the password
        for (int index = 0; index < text.length(); index++){
            int randomPosition = new Random().nextInt(characters.size());
            result.append(characters.get(randomPosition));
            characters.remove(randomPosition);
        }

        return result.toString();
    }
}

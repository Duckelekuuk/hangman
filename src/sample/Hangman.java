package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @AUTHOR Duco.
 * Description
 */
public class Hangman {

    private static final int AMOUNT_LIVES = 10;

    private char[] selectedWord;
    private Set<Character> guessedChars;

    private int lives = AMOUNT_LIVES;

    public Hangman() {
        this.guessedChars = new HashSet<>();

        List<String> words = getWords("C:/Users/Duco/Documents/School/Leerjaar 2/documenten/java/applications/GalgjeUI 2.0/src/sample/words.txt");
        if (words == null) {
            System.exit(0);
        }

        Collections.shuffle(words);

        System.out.println(words.get(0));

        this.selectedWord = words.get(0).toCharArray();
    }

    public String getSelectedWord() {
        return String.valueOf(selectedWord);
    }

    public boolean hasWon() {
        return (guessedChars.size() + 1) == selectedWord.length;
    }

    public boolean isDead() {
        return this.lives == 0;
    }

    public int getLives() {
        return this.lives;
    }

    public void removeLife() {
        this.lives--;
    }


    public boolean tryChar(char letter) {
        if (guessedChars.contains(letter) || (new String(selectedWord).indexOf(letter) < 0)) {
            return false;
        }

        guessedChars.add(letter);
        return true;
    }

    public String getOutput() {
        StringBuilder returnString = new StringBuilder();
        for (char charr : selectedWord) {
            returnString.append(guessedChars.contains(charr) ? charr : "_").append(" ");
        }

        return returnString.toString().trim();
    }

    private List<String> getWords(String fileName) {
        List<String> words = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            String word;
            while ((word = bufferedReader.readLine()) != null) {
                words.add(word);
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

        if (words.size() < 1) {
            System.out.println("Voer de woorden lijst in");
            return null;
        }

        return words;
    }
}

package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class Controller {

    private Hangman hangman;

    @FXML
    private Canvas canvas;

    @FXML
    private Text selectedWord;

    @FXML
    private TextField input;

    @FXML
    private Button submit;

    @FXML
    private Text feedback;

    @FXML
    protected void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        selectedWord.setText(hangman.getOutput());

        input.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                submit.fire();
            }
        });
    }

    public Controller() {
        this.hangman = new Hangman();
    }

    @FXML
    public void handleGuess(ActionEvent event) {
        if (hangman.isDead() || hangman.hasWon()) {
            return;
        }

        if (input.getLength() == 0) {
            feedback.setText("Voer een letter in!");
            return;
        }

        char guess = input.getCharacters().charAt(0);

        input.setText("");
        input.requestFocus();

        if (hangman.tryChar(guess)) {
            if (hangman.hasWon()) {
                feedback.setText("Gewonnen!");
                selectedWord.setText(hangman.getOutput());
                return;
            }

            feedback.setText("Goed!");
        } else {
            hangman.removeLife();
            updateLives();

            if (hangman.isDead()) {
                selectedWord.setText(hangman.getSelectedWord());
                feedback.setText("GAME OVER!");
                return;
            }
            feedback.setText("Fout!");
        }

        selectedWord.setText(hangman.getOutput());
    }

    private void updateLives() {
        canvas.getGraphicsContext2D().setFill(Color.BLACK);
        canvas.getGraphicsContext2D().setStroke(Color.BLACK);
        canvas.getGraphicsContext2D().setLineWidth(5);

        switch (hangman.getLives()) {
            case 9:
                //BASE
                canvas.getGraphicsContext2D().strokeLine(20, 490, 350, 490);
                break;

            case 8:
                //POLE
                canvas.getGraphicsContext2D().strokeLine(20, 490, 20, 50);
                break;

            case 7:
                //BAR
                canvas.getGraphicsContext2D().strokeLine(20, 50, 220, 50);
                break;

            case 6:
                //ROPE
                canvas.getGraphicsContext2D().strokeLine(200, 50, 200, 100);
                break;

            case 5:
                //HEAD
                canvas.getGraphicsContext2D().strokeOval(160, 100, 80, 80);
                break;

            case 4:
                //BODY
                canvas.getGraphicsContext2D().strokeLine(200, 180, 200, 300);
                break;

            case 3:
                //LEFT ARM
                canvas.getGraphicsContext2D().strokeLine(200, 200, 150, 250);
                break;

            case 2:
                //RIGHT ARM
                canvas.getGraphicsContext2D().strokeLine(200, 200, 250, 250);
                break;

            case 1:
                //LEFT Legg
                canvas.getGraphicsContext2D().strokeLine(200, 300, 150, 350);
                break;

            case 0:
                //RIGHT legg
                canvas.getGraphicsContext2D().strokeLine(200, 300, 250, 350);
                break;
        }
    }
}

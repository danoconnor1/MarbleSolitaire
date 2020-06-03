import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModelImpl;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModelImpl;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Main class to run games of Marble Solitaire.
 * This class simply instantiates a default game of Marble Solitaire and begins to play.
 */
public class MarbleSolitaireRunner {
  /**
   * Main method for Marble Solitaire game.
   *
   * @param args the necessary String[] args clause for the main method.
   */
  public static void main(String[] args) {
    List<String> argList = Arrays.asList(args);
    MarbleSolitaireModel model;
    String type;
    int row = -1;
    int col = -1;
    int size = -1;

    // Extract game type
    if (argList.contains("european")) {
      type = "european";
    }
    else if (argList.contains("english")) {
      type = "english";
    }
    else {
      type = "triangle";
    }

    // Extract size from args
    if (argList.contains("-size")) {
      int index = argList.indexOf("-size");
      size = Integer.valueOf(argList.get(index + 1));
    }
    // Extract hole from args
    if (argList.contains("-hole")) {
      int index = argList.indexOf("-hole");
      row = Integer.valueOf(argList.get(index + 1)) - 1;
      col = Integer.valueOf(argList.get(index + 2)) - 1;
    }

    model = createModel(type, size, row, col);

    Readable in = new InputStreamReader(System.in);
    MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(in, System.out);
    controller.playGame(model);
  }

  /**
   * Create the model of Marble Solitaire.
   *
   * @param type the type of Marble Solitaire game
   * @param size the armThickness of the game, -1 if not specified
   * @param row the row coordinate of the empty space, -1 if not specified
   * @param col the col coordinate of the empty space, -1 if not specified
   * @return a constructed model of English Marble Solitaire
   */
  private static MarbleSolitaireModel createModel(String type, int size, int row, int col) {
    if (type.equalsIgnoreCase("triangle")) {
      if (size < 0) {
        size = 5;
      }
      if (row < 0) {
        return new TriangleSolitaireModelImpl(size);
      }
      return new TriangleSolitaireModelImpl(size, row, col);
    }
    // if size and hole weren't specified, make them default
    if (size < 0) {
      size = 3;
    }
    if (row < 0) {
      row = (3 * size - 2) / 2;
    }
    if (type.equalsIgnoreCase("european")) {
      return new EuropeanSolitaireModelImpl(size, row, col);
    }
    return new MarbleSolitaireModelImpl(size, row, col);
  }
}

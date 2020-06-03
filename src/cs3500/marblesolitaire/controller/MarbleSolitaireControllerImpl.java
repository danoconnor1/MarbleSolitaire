package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
EDITS MADE:
used try-catch statement to catch null model instead of if-statement for efficiency reasons
 */

/**
 * Implementation of a Marble Solitaire Controller. Takes in input from the user and directs
 * gameplay to user.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {
  private final Readable rd;
  private final Appendable out;

  /**
   * Construct a Marble Solitaire Controller. This object directs gameplay to user.
   *
   * @param rd used to take in input from user
   * @param ap outputs and transmits information to user
   * @throws IllegalArgumentException if and only if either argument is null
   */
  public MarbleSolitaireControllerImpl(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Neither argument can be null.\n");
    }
    this.rd = rd;
    this.out = ap;
  }

  // Array to store necessary error messages
  private static String[] errorMsgs = {
    "From row is invalid.",
    "From col is invalid.",
    "To row is invalid.",
    "To col is invalid."
  };

  @Override
  public void playGame(MarbleSolitaireModel model)
          throws IllegalStateException, IllegalArgumentException {
    Scanner scan = new Scanner(this.rd);

    // Start the game, continue until game ends or user quits
    try {
      String quitSwitch = "";
      while (!model.isGameOver()) {
        // Output
        out.append(outputGameAndScore(model));
        outputMsg("Input your move as 4 numbers: fromRow fromCol toRow toCol");
        quitSwitch = processMove(model, scan);
        if (quitSwitch.equalsIgnoreCase("q")) {
          out.append("Game quit!\n");
          out.append("State of game when quit:\n");
          out.append(outputGameAndScore(model));
          break;
        }
      }
      // Output final game state only if user didn't quit
      if (!quitSwitch.equalsIgnoreCase("q")) {
        out.append("Game over!\n");
        out.append(outputGameAndScore(model));
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("I/O failed.");
    } catch (NullPointerException npe) {
      throw new IllegalArgumentException("Must input valid model");
    }
  }

  /**
   * Get move from user and relay info to model.
   *
   * @param model the {@link MarbleSolitaireModel} in use.
   * @return "q" if and only if the user wants to quit, return empty string otherwise
   * @throws IllegalStateException if output fails for any reason, or if input is too short
   */
  private String processMove(MarbleSolitaireModel model, Scanner scan) {
    int[] inputs = new int[4];
    String input;
    int inputAsNum;
    int i = 0;
    boolean properInput = true;
    while (i < 4) {
      // if this is first input, tell user how to input
      if (i == 0) {
        outputMsg("Enter move: ");
      }
      try {
        input = scan.next();
        // if user wants to quit, return "q"
        if (input.equalsIgnoreCase("q")) {
          return "q";
        }
        inputAsNum = Integer.parseInt(input);
        if (inputAsNum < 1) {
          if (properInput) {
            outputMsg(errorMsgs[i]);
            properInput = false;
          }
        } else {
          inputs[i] = inputAsNum;
          i++;
        }
      } catch (NumberFormatException nfe) {
        if (properInput) {
          outputMsg(errorMsgs[i]);
          properInput = false;
        }
      } catch (NoSuchElementException nsee) {
        throw new IllegalStateException("I/O failed.");
      }
      // if all inputs have been read, try to make move
      // if move fails, reset counter so input is redone
      if (i == 4) {
        try {
          model.move(inputs[0] - 1, inputs[1] - 1,
                  inputs[2] - 1, inputs[3] - 1);
        } catch (IllegalArgumentException iae) {
          i = 0;
          outputMsg(iae.getMessage());
        }
      }
    }
    return "";
  }

  /**
   * Output the provided message. Convenience method used to avoid single use try-catch.
   *
   * @param msg the message to output.
   * @throws IllegalStateException if output fails for whatever reason
   */
  private void outputMsg(String msg) {
    try {
      out.append(msg).append("\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("I/O failed.\n");
    }
  }

  /**
   * Return game state and score of the game as a formatted string.
   *
   * @param model the {@link MarbleSolitaireModel} in use
   * @return game state and score of the game as a formatted string.
   */
  private String outputGameAndScore(MarbleSolitaireModel model) {
    return model.getGameState() + "\n" + String.format("Score: %d", model.getScore()) + "\n";
  }
}

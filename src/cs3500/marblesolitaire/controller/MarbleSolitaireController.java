package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * This interface represents the operations offered by the marble solitaire controller:
 * takes input from the user and tells the model what to do.
 * One object of the controller is used for one game of marble solitaire.
 */
public interface MarbleSolitaireController {
  /**
   * Play a new game of Marble Solitaire. Take in user input and direct the
   * {@link MarbleSolitaireModel} accordingly.
   *
   * @param m the {@link MarbleSolitaireModel} to manage game state
   * @throws IllegalArgumentException if the provided model is null
   * @throws IllegalStateException only if the controller is unable to
   *      successfully receive input or transmit output.
   * @throws NumberFormatException if user enters anything other than "Q", "q", or an int
   */
  void playGame(MarbleSolitaireModel m) throws IllegalStateException, IllegalArgumentException;
}

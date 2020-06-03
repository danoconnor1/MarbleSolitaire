package cs3500.marblesolitaire.model.hw02;

import cs3500.marblesolitaire.model.hw04.AMarbleSolitaireModel;

/*
EDITS MADE FOR ABSTRACTION:
Abstractions from MarbleSolitaireModelImpl:
- abstracted constructors into AMarbleSolitaireModel class
- abstracted move method into AMarbleSolitaireModel class
- abstracted fillBoard method into AMarbleSolitaireModel class
- abstracted inDeadZone into AMarbleSolitaireModel class and changed name of method to outOfBounds
- abstracted isGameOver method into AMarbleSolitaireModel class
- abstracted canBeJumped into AMarbleSolitaireModel class
- abstracted getGameState into AMarbleSolitaireModel class
- abstracted getScore into AMarbleSolitaireModel class
PURPOSE: All of these methods have common characteristics to all
types of Marble Solitaire games, so it makes most sense to have them in an abstract base class
 */

/*
IMPLEMENTATION CHANGES:
- used the determineEndInd method to make loops more efficient
- removed verification of armThickness from fillBoard method, and used
    checkBaseThickness method instead (one method one goal)
- changed name of field armThickness to baseThickness for uniformity across all
    implementations of Marble Solitaire
- used the determineScore method to make constructor abstraction possible,
    allows score to be calculated in linear time rather than check each board position for a marble
 */

/**
 * Implementation of the Marble Solitaire Model.
 * Handles all functionality of the Marble Solitaire game.
 */
public class MarbleSolitaireModelImpl extends AMarbleSolitaireModel {
  /**
   * Construct a Marble Solitaire Model.
   * Defaults to a standard board with base thickness of 3 and the empty space in the center.
   */
  public MarbleSolitaireModelImpl() {
    super();
  }

  /**
   * Construct a Marble Solitaire Model.
   * Defaults to board with base thickness of 3, but with empty space specified by user.
   *
   * @param sRow the row coordinate of the empty space
   * @param sCol the column coordinate of the empty space
   * @throws IllegalArgumentException if the empty space is invalid
   */
  public MarbleSolitaireModelImpl(int sRow, int sCol) {
    super(sRow, sCol);
  }

  /**
   * Construct a Marble Solitaire Model.
   * Creates board with base thickness specified by user.
   * Empty space is in the center of the board.
   *
   * @param baseThickness the side length of the board's edge squares
   * @throws IllegalArgumentException if baseThickness is not an odd integer >= 3
   */
  public MarbleSolitaireModelImpl(int baseThickness) {
    super(baseThickness);
  }

  /**
   * Construct a Marble Solitaire Model.
   * Creates board with base thickness and empty space specified by user.
   *
   * @param baseThickness the side length of the board's edge squares.
   * @param sRow the row coordinate of the empty space
   * @param sCol the column coordinate of the empty space
   * @throws IllegalArgumentException if baseThickness is not an odd integer >= 3
   *     or if empty tile is invalid
   */
  public MarbleSolitaireModelImpl(int baseThickness, int sRow, int sCol) {
    super(baseThickness, sRow, sCol);
  }


  @Override
  protected int determineEndInd(int row, int maxLen) {
    if (row < baseThickness - 1 || row > 2 * baseThickness - 2) {
      return 2 * baseThickness - 2;
    }
    return maxLen - 1;
  }

  @Override
  protected int determineScore(int baseThickness) {
    return (int) (5 * Math.pow(baseThickness, 2) - 4 * baseThickness - 1);
  }
}

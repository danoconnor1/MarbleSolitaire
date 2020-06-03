package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.GameTile;

/**
 * Class for implementations of the European version of Marble Solitaire.
 * This version of the game is an octagon-shape.
 */
public class EuropeanSolitaireModelImpl extends AMarbleSolitaireModel {
  /**
   * Construct a European Marble Solitaire Model.
   * Defaults to a standard board with base thickness of 3 and the empty space in the center.
   */
  public EuropeanSolitaireModelImpl() {
    super();
  }

  /**
   * Construct a European Marble Solitaire Model.
   * Defaults to board with base thickness of 3, but with empty space specified by user.
   *
   * @param sRow the row coordinate of the empty space
   * @param sCol the column coordinate of the empty space
   * @throws IllegalArgumentException if the empty space is invalid
   */
  public EuropeanSolitaireModelImpl(int sRow, int sCol) {
    super(sRow, sCol);
  }

  /**
   * Construct a European Marble Solitaire Model.
   * Creates board with base thickness specified by user.
   * Empty space is in the center of the board.
   *
   * @param baseThickness the side length of the board's edge squares
   * @throws IllegalArgumentException if baseThickness is not an odd integer >= 3
   */
  public EuropeanSolitaireModelImpl(int baseThickness) {
    super(baseThickness);
  }

  /**
   * Construct a European Marble Solitaire Model.
   * Creates board with base thickness and empty space specified by user.
   *
   * @param baseThickness the side length of the board's edge squares.
   * @param sRow the row coordinate of the empty space
   * @param sCol the column coordinate of the empty space
   * @throws IllegalArgumentException if baseThickness is not an odd integer >= 3
   *     or if empty tile is invalid
   */
  public EuropeanSolitaireModelImpl(int baseThickness, int sRow, int sCol) {
    super(baseThickness, sRow, sCol);
  }

  @Override
  protected GameTile[][] fillBoard(int sRow, int sCol) {
    GameTile[][] almostBoard = super.fillBoard(sRow, sCol);
    int maxLen = 3 * baseThickness - 2;
    for (int i = 1; i < baseThickness - 1; i++) {
      int shortEnd = maxLen - determineEndInd(i, maxLen) - 1;
      for (int j = shortEnd; j < baseThickness - 1; j++) {
        almostBoard[i][j] = GameTile.MARBLE;
        almostBoard[maxLen - 1 - i][j] = GameTile.MARBLE;
        almostBoard[i][maxLen - 1 - i] = GameTile.MARBLE;
        almostBoard[maxLen - 1 - i][maxLen - 1 - i] = GameTile.MARBLE;
      }
    }
    almostBoard[sRow][sCol] = GameTile.EMPTY;
    return almostBoard;
  }

  @Override
  protected int determineEndInd(int row, int maxLen) {
    if (row < baseThickness - 1 || row > 2 * baseThickness - 2) {
      return 2 * baseThickness - 2 + Math.min(row, maxLen - row - 1);
    }
    return maxLen - 1;
  }

  @Override
  protected int determineScore(int baseThickness) {
    return (int) (7 * Math.pow(baseThickness, 2) - 10 * baseThickness + 3);
  }
}

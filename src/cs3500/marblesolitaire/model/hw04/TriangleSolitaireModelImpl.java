package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.GameTile;

/**
 * Class for implementation of the Triangle version of Marble Solitaire.
 * This game is similar to the one at Cracker Barrel.
 */
public class TriangleSolitaireModelImpl extends AMarbleSolitaireModel {
  /**
   * Construct a Triangle Marble Solitaire Model.
   * Defaults to a standard board of base length 5 and the empty space at the top.
   */
  public TriangleSolitaireModelImpl() {
    super(5, 0, 0);
  }

  /**
   * Construct a Triangle Marble Solitaire Model.
   * Defaults to a standard board of base length 5 and the empty space specified by user.
   *
   * @param sRow the row coordinate of the empty space
   * @param sCol the column coordinate of the empty space
   * @throws IllegalArgumentException if the empty space is invalid
   */
  public TriangleSolitaireModelImpl(int sRow, int sCol) {
    super(5, sRow, sCol);
  }

  /**
   * Construct a Triangle Marble Solitaire Model.
   * Creates board with base length specified by user.
   * Empty space is at the top of the board.
   *
   * @param baseThickness the side length of the board's edge squares
   * @throws IllegalArgumentException if baseThickness is not positive
   */
  public TriangleSolitaireModelImpl(int baseThickness) {
    super(baseThickness, 0, 0);
  }

  /**
   * Construct a Triangle Marble Solitaire Model.
   * Creates board with base thickness and empty space specified by user.
   *
   * @param baseThickness the side length of the board's edge squares.
   * @param sRow the row coordinate of the empty space
   * @param sCol the column coordinate of the empty space
   * @throws IllegalArgumentException if baseThickness is not positive
   *     or if empty tile is invalid
   */
  public TriangleSolitaireModelImpl(int baseThickness, int sRow, int sCol) {
    super(baseThickness, sRow, sCol);
  }

  @Override
  protected GameTile[][] fillBoard(int sRow, int sCol) {
    // find length of longest row and column
    int maxLen = baseThickness;

    // ensure empty space is valid
    if (outOfBounds(sRow, sCol, baseThickness - 1)) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow + "," + sCol + ")");
    }
    // fill in board
    GameTile[][] board = new GameTile[maxLen][maxLen];
    for (int i = 0; i < maxLen; i++) {
      for (int j = 0; j <= i; j++) {
        board[i][j] = GameTile.MARBLE;
      }
    }
    // overwrite tile at desired empty space to be empty
    board[sRow][sCol] = GameTile.EMPTY;
    return board;
  }


  @Override
  protected int determineEndInd(int row, int maxLen) {
    return row;
  }

  @Override
  protected int determineScore(int baseThickness) {
    return baseThickness * (baseThickness + 1) / 2 - 1;
  }

  @Override
  protected boolean outOfBounds(int row, int col, int maxLen) {
    return row >= baseThickness || col < 0 || col > row;
  }

  /**
   * A jump is proper in a game of Triangle Solitaire if.
   * <ul>
   * <li>row changes by 2 and col stays the same</li>
   * <li>col changes by 2 and row stays the same</li>
   * <li>both entities change by 2 in the same direction</li>
   * </ul>
   *
   * @param fromRow the row coordinate of the from position
   * @param fromCol the col coordinate of the column position
   * @param toRow the row coordinate of the to position
   * @param toCol the col coordinate of the to position
   *
   * @return true if the jump is proper
   */
  @Override
  protected boolean properJump(int fromRow, int fromCol, int toRow, int toCol) {
    return ((Math.abs(toRow - fromRow) == 2 && toCol == fromCol)
            || (Math.abs(toCol - fromCol) == 2 && toRow == fromRow)
            || (toCol - fromCol == toRow - fromRow && Math.abs(toCol - fromCol) == 2));
  }

  @Override
  public String getGameState() {
    StringBuilder gameState = new StringBuilder();
    for (int i = 0; i < baseThickness; i++) {
      gameState.append(" ".repeat(baseThickness - i - 1));
      for (int j = 0; j <= i; j++) {
        gameState.append(board[i][j]).append(" ");
      }
      gameState.deleteCharAt(gameState.length() - 1).append("\n");
    }
    return gameState.deleteCharAt(gameState.length() - 1).toString();
  }

  /**
   * A tile can be jumped if both potential neighbors are in bounds
   * and exactly one of them is a marble.
   * In a game of triangle solitaire, a marble can be jumped in 3 ways,
   * diagonally left to right, diagonally right to left, and orthogonally side to side.
   *
   * @param row the row coordinate of the specified position
   * @param col the column coordinate of the specified position
   * @return true if the space can legally be jumped
   */
  @Override
  protected boolean canBeJumped(int row, int col) {
    return ((!outOfBounds(row + 1, col + 1, baseThickness)
            && !outOfBounds(row - 1, col - 1, baseThickness)
            && board[row + 1][col + 1] == GameTile.MARBLE
            ^ board[row - 1][col - 1] == GameTile.MARBLE)
            ||
            (!outOfBounds(row, col - 1, baseThickness)
            && !outOfBounds(row, col + 1, baseThickness)
            && board[row][col + 1] == GameTile.MARBLE
            ^ board[row][col - 1] == GameTile.MARBLE)
            ||
            (!outOfBounds(row - 1, col, baseThickness)
            && !outOfBounds(row + 1, col, baseThickness)
            && board[row - 1][col] == GameTile.MARBLE
            ^ board[row + 1][col] == GameTile.MARBLE));
  }

  /**
   * Ensures that the base thickness is greater than 1.
   *
   * @param baseThickness the base thickness in question
   * @return baseThickness if it is greater than 1
   * @throws IllegalArgumentException if baseThickness is less than or equal to 1
   */
  @Override
  protected int checkBaseThickness(int baseThickness) {
    if (baseThickness <= 1) {
      throw new IllegalArgumentException("Base thickness must be greater than 1!");
    }
    return baseThickness;
  }
}

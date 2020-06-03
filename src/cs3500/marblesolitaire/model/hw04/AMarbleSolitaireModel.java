package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.GameTile;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/*
METHOD EDITS FROM PREVIOUS IMPLEMENTATIONS:
- fillBoard now uses helper method determineEndInd() for efficiency reasons
- changed field name armThickness to baseThickness to encourage uniformity between subclasses
- constructors now use checkBaseThickness() helper to validate baseThickness field
- move() method now uses helper properJump() for readability
- changed getGameState method to use StringBuilder for efficiency reasons
- changed name of method inDeadZone to outOfBounds because it is a more
    suitable and understandable name
 */

/**
 * Abstract base class for Marble Solitaire Models.
 * Provides and instantiates commonalities between the different variations.
 */
public abstract class AMarbleSolitaireModel implements MarbleSolitaireModel {
  protected GameTile[][] board;
  protected int baseThickness;
  protected int score;

  /**
   * Base constructor for Marble Solitaire game.
   * Specifies an base thickness of 3 and empty space in center.
   */
  public AMarbleSolitaireModel() {
    this.baseThickness = 3;
    this.board = fillBoard(3, 3);
    this.score = determineScore(3);
  }

  /**
   * Empty space constructor for Marble Solitaire game.
   * Specifies an base thickness of 3 and empty space based on input.
   *
   * @param sRow the row coordinate for the empty space
   * @param sCol the col coordinate for the empty space
   * @throws IllegalArgumentException if the empty space is invalid
   */
  public AMarbleSolitaireModel(int sRow, int sCol) {
    this.baseThickness = 3;
    this.board = fillBoard(sRow, sCol);
    this.score = determineScore(3);
  }

  /**
   * base thickness constructor for Marble Solitaire game.
   * Specifies base thickness based on user input and empty space in center
   *
   * @param baseThickness the side length of the board's edge squares
   * @throws IllegalArgumentException if baseThickness is not an odd integer >= 3
   */
  public AMarbleSolitaireModel(int baseThickness) {
    this.baseThickness = checkBaseThickness(baseThickness);
    int center = 3 * (baseThickness - 1) / 2;
    this.board = fillBoard(center, center);
    this.score = determineScore(baseThickness);
  }

  /**
   * Full custom constructor for Marble Solitaire game.
   * All parameters of the board are based on user input.
   *
   * @param baseThickness the side length of the board's edge squares.
   * @param sRow the row coordinate of the empty space
   * @param sCol the column coordinate of the empty space
   * @throws IllegalArgumentException if baseThickness is not an odd integer >= 3
   *     or if empty tile is invalid
   */
  public AMarbleSolitaireModel(int baseThickness, int sRow, int sCol) {
    this.baseThickness = checkBaseThickness(baseThickness);
    this.board = fillBoard(sRow, sCol);
    this.score = determineScore(baseThickness);
  }

  /**
   A move is illegal if it is coming from out of bounds,
   entering the dead zone, not jumping over a marble,
   there is no marble at the given "from" coordinates,
   the "to" coordinates are not empty,
   or if the to and from spaces are not one tile apart.
   **/
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) {
    // if game is over, can't move
    if (isGameOver()) {
      throw new IllegalArgumentException("Invalid move. Game is over.");
    }
    // first ensure that the move is orthogonal with only one tile in between
    if (! properJump(fromRow, fromCol, toRow, toCol)) {
      throw new IllegalArgumentException("Invalid move. Play again. Move must jump one tile " +
              "and be in a valid direction.");
    }

    int jumpRow = (fromRow + toRow) / 2;
    int jumpCol = (fromCol + toCol) / 2;

    /*
    a move is illegal if it is coming from out of bounds,
    entering the dead zone, not jumping over a marble,
    there is no marble at the given "from" coordinates,
    the "to" coordinates are not empty,
    or if the to and from spaces are not one tile apart
    */
    if (outOfBounds(fromRow, fromCol, board.length)
            || outOfBounds(toRow, toCol, board.length)) {
      String errorMsg = "Invalid move. Play again. Your move was out of bounds.";
      throw new IllegalArgumentException(errorMsg);
    }
    else if (this.board[fromRow][fromCol] != GameTile.MARBLE) {
      String errorMsg = "Invalid move. Play again. Your from position was not a marble.";
      throw new IllegalArgumentException(errorMsg);
    }
    else if (this.board[jumpRow][jumpCol] != GameTile.MARBLE) {
      String errorMsg = "Invalid move. Play again. You must jump over a marble.";
      throw new IllegalArgumentException(errorMsg);
    }
    else if (this.board[toRow][toCol] != GameTile.EMPTY) {
      String errorMsg = "Invalid move. Play again. Your to position was not empty.";
      throw new IllegalArgumentException(errorMsg);
    }

    // move is valid, so make the "from" coordinate and jumped marble empty,
    // and make the "to" coordinate a marble
    this.board[fromRow][fromCol] = GameTile.EMPTY;
    this.board[jumpRow][jumpCol] = GameTile.EMPTY;
    this.board[toRow][toCol] = GameTile.MARBLE;
    this.score--;
  }

  /**
   * Return true if coordinates are out of bounds or null space.
   *
   * @param row the row coordinate of the position in question
   * @param col the column coordinate of the position in question
   * @param maxLen the length of the longest row in the game (usually board length)
   * @return true if the position is out of bounds, false otherwise
   */
  protected boolean outOfBounds(int row, int col, int maxLen) {
    int upper = determineEndInd(row, maxLen);
    int lower = maxLen - determineEndInd(row, maxLen) - 1;
    return row < 0 || row >= maxLen || col < lower || col > upper;
  }

  @Override
  public boolean isGameOver() {
    for (int i = 0; i < board.length; i++) {
      int endInd = determineEndInd(i, board.length);
      for (int j = 0; j <= endInd; j++) {
        if (board[i][j] == GameTile.MARBLE && this.canBeJumped(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Determine if marble at specified position can be jumped over.
   * This method is only called if a marble is at specified position.
   *
   * @param row the row coordinate of the specified position
   * @param col the column coordinate of the specified position
   * @return true if tile can be jumped, false otherwise
   */
  protected boolean canBeJumped(int row, int col) {
    // if edge row only check side pieces
    if (row == 0 || row == this.board.length - 1) {
      return ((this.board[row][col - 1] == GameTile.MARBLE
              && this.board[row][col + 1] == GameTile.EMPTY)
              || (this.board[row][col - 1] == GameTile.EMPTY
              && this.board[row][col + 1] == GameTile.MARBLE));
    }
    // if edge column only check top and bottom pieces
    if (col == 0 || col == this.board.length - 1) {
      return ((this.board[row - 1][col] == GameTile.MARBLE
              && this.board[row + 1][col] == GameTile.EMPTY)
              || (this.board[row - 1][col] == GameTile.EMPTY
              && this.board[row + 1][col] == GameTile.MARBLE));
    }
    // check all 4 orthogonal neighbors otherwise
    return ((this.board[row - 1][col] == GameTile.MARBLE
            && this.board[row + 1][col] == GameTile.EMPTY)
            || (this.board[row - 1][col] == GameTile.EMPTY
            && this.board[row + 1][col] == GameTile.MARBLE)
            || (this.board[row][col - 1] == GameTile.MARBLE
            && this.board[row][col + 1] == GameTile.EMPTY)
            || (this.board[row][col - 1] == GameTile.EMPTY
            && this.board[row][col + 1] == GameTile.MARBLE));
  }

  @Override
  public String getGameState() {
    StringBuilder gameState = new StringBuilder();
    for (int i = 0; i < board.length; i++) {
      int endInd = determineEndInd(i, board.length);
      for (int j = 0; j <= endInd; j++) {
        gameState.append(board[i][j]).append(" ");
      }
      gameState.deleteCharAt(gameState.length() - 1).append("\n");
    }
    return gameState.deleteCharAt(gameState.length() - 1).toString();
  }

  /**
   * Determine and return the final potential marble position for the given row.
   *
   * @param row the row in question
   * @param maxLen the length of the longest row
   * @return the index of the final potential marble position for the row.
   */
  protected abstract int determineEndInd(int row, int maxLen);

  /**
   * Fill in a board with provided base thickness and desired empty tile.
   *
   * @param sRow row coordinate of desired empty tile
   * @param sCol column coordinate of desired empty tile
   * @return a filled in board with empty cell at { @code board[sRow][sCol] }
   * @throws IllegalArgumentException if empty tile is invalid
   */
  protected GameTile[][] fillBoard(int sRow, int sCol) {
    // find length of longest row and column
    int maxLen = 3 * baseThickness - 2;

    // ensure empty space is valid
    if (outOfBounds(sRow, sCol, maxLen)) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow + "," + sCol + ")");
    }

    // fill in board
    GameTile[][] board = new GameTile[maxLen][maxLen];
    for (int i = 0; i < maxLen; i++) {
      int endInd = determineEndInd(i, maxLen);
      for (int j = 0; j <= endInd; j++) {
        if (j < baseThickness - 1 && (i < baseThickness - 1 || i > 2 * baseThickness - 2)) {
          board[i][j] = GameTile.DEAD;
        }
        else {
          board[i][j] = GameTile.MARBLE;
        }
      }
    }
    // overwrite tile at desired empty space to be empty
    board[sRow][sCol] = GameTile.EMPTY;
    return board;
  }

  /**
   * Returns starting score based on game type and base thickness.
   *
   * @return the starting score for the game.
   */
  protected abstract int determineScore(int baseThickness);

  @Override
  public int getScore() {
    return this.score;
  }

  /**
   * Ensure that baseThickness is an odd int greater than or equal to 3.
   *
   * @param baseThickness the base thickness in question
   * @return the baseThickness if it is valid
   * @throws IllegalArgumentException if baseThickness if not odd int >= 3
   */
  protected int checkBaseThickness(int baseThickness) {
    // ensure base thickness is odd and greater than or equal to 3
    if (baseThickness < 3 || baseThickness % 2 == 0) {
      throw new IllegalArgumentException("Arm thickness must be an odd number greater than 2!");
    }
    return baseThickness;
  }

  /**
   * Ensure that the jump is of proper length.
   *
   * @param fromRow the row coordinate of the from position
   * @param fromCol the col coordinate of the column position
   * @param toRow the row coordinate of the to position
   * @param toCol the col coordinate of the to position
   *
   * @return true if jump is proper length
   * @throws IllegalArgumentException if the jump is not of proper length
   */
  protected boolean properJump(int fromRow, int fromCol, int toRow, int toCol) {
    return ((Math.abs(toRow - fromRow) == 2 && toCol == fromCol)
            || (Math.abs(toCol - fromCol) == 2 && toRow == fromRow));
  }
}

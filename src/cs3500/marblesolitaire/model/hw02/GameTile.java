package cs3500.marblesolitaire.model.hw02;

/**
 * Represents a tile in the Marble Solitaire game.
 * A GameTile is one of:
 * <li>
 *   <ul>" " - represents the "dead space" tiles that cannot hold marbles </ul>
 *   <ul>"O" - represents a tile holding a marble</ul>
 *   <ul>"_" - represents a valid space on the board that is not holding a marble</ul>
 * </li>
 */
public enum GameTile {
  DEAD(" "), MARBLE("O"), EMPTY("_");

  private final String disp;

  /**
   * Construct a GameTile.
   *
   *
   * @param disp the String that represents the tile on the board view
   */
  GameTile(String disp) {
    this.disp = disp;
  }

  @Override
  public String toString() {
    return disp;
  }

}

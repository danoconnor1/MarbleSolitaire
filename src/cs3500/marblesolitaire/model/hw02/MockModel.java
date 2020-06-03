package cs3500.marblesolitaire.model.hw02;

import java.util.Objects;

/**
 * Mock model used to test inputs of controller.
 */
public class MockModel extends MarbleSolitaireModelImpl {
  private final StringBuilder log;

  public MockModel(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  /**
   * The move method is used to confirm that the proper inputs have been recorded.
   *
   * @param fromRow row coordinate of from position
   * @param fromCol column coordinate of from position
   * @param toRow row coordinate of to position
   * @param toCol column coordinate of to position
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) {
    log.append(String.format("FromRow: %d, fromCol: %d, toRow: %d, toCol: %d",
            fromRow, fromCol, toRow, toCol));
  }

  /**
   * The game should never be over in the mock model, so it is always false.
   */
  @Override
  public boolean isGameOver() {
    return false;
  }
}

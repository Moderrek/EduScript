package pl.moderr.eduscript;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EsPosition implements Cloneable {

  private int row;
  private int col;
  private int raw;

  public EsPosition(int line, int column) {
    this.row = line;
    this.col = column;
    this.raw = 0;
  }

  public EsPosition() {
    this.row = 0;
    this.col = 0;
  }

  public int getCol() {
    return col;
  }

  public int getRow() {
    return row;
  }

  public int getLine() {
    return row;
  }

  public void setCol(int col) {
    this.col = col;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public void setLine(int line) {
    this.row = line;
  }

  public int line() {
    return row;
  }

  public EsPosition line(int line) {
    row = line;
    return this;
  }

  public int col() {
    return col;
  }

  public EsPosition col(int col) {
    this.col = col;
    return this;
  }

  public EsPosition move(char c) {
    raw += 1;
    if (c == '\n') {
      col = 0;
      row += 1;
    } else col += 1;
    return this;
  }

  public EsPosition moveLine(int line) {
    this.row += line;
    return this;
  }

  public EsPosition moveCol(int col) {
    this.col += col;
    return this;
  }

  public EsPosition moveLine() {
    this.row += 1;
    this.col = 0;
    return this;
  }

  public int index() {
    return raw;
  }

  @Contract(pure = true)
  @Override
  public @NotNull String toString() {
    return (this.row + 1) + ":" + (this.col + 1);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EsPosition that = (EsPosition) o;
    return row == that.row && col == that.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }

  @Override
  public EsPosition clone() {
    try {
      return (EsPosition) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}

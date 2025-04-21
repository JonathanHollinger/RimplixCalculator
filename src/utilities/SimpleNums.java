package utilities;

/**
 * Double wrapper, to be extended by ComplexNums.
 */
public class SimpleNums implements Nums 
{
  private double val;

  /**
   * default constructor.
   */
  public SimpleNums() 
  {
    val = 0;
  }

  /**
   * double constructor.
   * @param val val
   */
  public SimpleNums(final double val) 
  {
    this.val = val;
  }

  /**
   * int constructor.
   * @param val val
   */
  public SimpleNums(final int val) 
  {
    this.val = (double) val;
  }

  /**
   * basic getter.
   * @return val
   */
  public final double getVal() 
  {
    return val;

  }

  // LOTS of functions for flexible handling.
  public Nums mult(final SimpleNums other) {
    return new SimpleNums(this.getVal() * other.getVal());
  }

  public Nums add(final SimpleNums other) {
    return new SimpleNums(this.getVal() + other.getVal());
  }

  public Nums sub(final SimpleNums other) {
    return new SimpleNums(this.getVal() - other.getVal());
  }

  public Nums div(final SimpleNums other) {
    return new SimpleNums(this.getVal() / other.getVal());
  }

  // --- Overloaded methods for double ---
  public Nums mult(final double other) {
    return new SimpleNums(this.getVal() * other);
  }

  public Nums add(final double other) {
    return new SimpleNums(this.getVal() + other);
  }

  public Nums sub(final double other) {
    return new SimpleNums(this.getVal() - other);
  }

  public Nums div(final double other) {
    return new SimpleNums(this.getVal() / other);
  }

  // --- Overloaded methods for int ---
  public Nums mult(final int other) {
    return new SimpleNums(this.getVal() * other);
  }

  public Nums add(final int other) {
    return new SimpleNums(this.getVal() + other);
  }

  public Nums sub(final int other) {
    return new SimpleNums(this.getVal() - other);
  }

  public Nums div(final int other) {
    return new SimpleNums(this.getVal() / other);
  }
}

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

  @Override
  public Nums mult(final Nums other)
  {
    return new SimpleNums(this.getVal() * other.getVal());
  }
  
}

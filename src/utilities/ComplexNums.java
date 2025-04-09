package utilities;

/**
 * Extension of SimpleNums, holds val as a multiplier to i.
 */
public class ComplexNums implements Nums 
{
  private double iMult;
  
  /**
   * 
   */
  public ComplexNums() 
  {
    this.iMult = 0;
    
  }
  
  /**
   * double constructor.
   * @param val val
   */
  public ComplexNums(final double val) 
  {
    this.iMult = val;
  }
  
  /**
   * int constructor.
   * @param val val
   */
  public ComplexNums(final int val) 
  {
    this.iMult = (double) val;
  }

  @Override
  public Nums mult(final Nums other)
  {
    if (other == null) 
    {
      return null;
    }
    
    // Just needs to multiply the new value
    if (other instanceof SimpleNums) 
    {
      return new ComplexNums(this.getVal() * other.getVal());
    } else //Needs to make a new simple number
    {
      return new SimpleNums(this.getVal() * other.getVal() * -1);
    }
  }

  @Override
  public double getVal()
  {    
    return this.iMult;
  }
  
}

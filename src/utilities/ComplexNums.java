package utilities;

/**
 * Extension of SimpleNums, holds val as a multiplier to i.
 */
public class ComplexNums implements Nums 
{
  private double iMult;
  private double val;
  
  /**
   * 
   */
  public ComplexNums() 
  {
    this.iMult = 0;
    this.val = 0;
    
  }
  
  /**
   * double constructor.
   * @param val val
   */
  public ComplexNums(final double iMult, final double val)
  {
    this.iMult = iMult;
    this.val = val;
  }
  
  /**
   * int constructor.
   * @param val val
   */
  public ComplexNums(final int iMult, final int val) 
  {
    this.iMult = (double) iMult;
    this.val = (double) val;
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
      return new ComplexNums(this.getMult() * other.getVal(), this.getVal() * other.getVal());
    } else //Needs to make a new simple number
    {
      ComplexNums n = (ComplexNums) other;
      double newMult = (this.getMult() * n.getVal()) + this.getVal() * n.getMult();
      double newVal = (this.getVal() * other.getVal()) + (this.getMult() * n.getMult() * -1);
      return new ComplexNums(newMult, newVal);
    }
  }

  /**
   * basic getter.
   * @return mult
   */
  public double getMult()
  {    
    return this.iMult;
  }
  
  @Override
  public double getVal()
  {    
    return this.val;
  }
  
  @Override
  public String toString() 
  {
    if (getVal() % 1 == 0) {
      return (int) getVal() + "i";
    }
    return getVal() + "i";
  }
  
}

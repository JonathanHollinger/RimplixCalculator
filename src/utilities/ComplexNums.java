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
   * 
   * @param val val
   */
  public ComplexNums(final double val, final double iMult)
  {
    this.iMult = iMult;
    this.val = val;
  }

  /**
   * int constructor.
   * 
   * @param val val
   * @param iMult i modifier
   */
  public ComplexNums(final int val, final int iMult )
  {
    this.iMult = (double) iMult;
    this.val = (double) val;
  }

  /**
   * Addition function.
   * @param other
   * @return final number.
   */
  public Nums add(final Nums other)
  {
    if (other == null)
    {
      return null;
    }

    if (other instanceof SimpleNums)
    {
      return new ComplexNums(this.getMult(), this.getVal() + other.getVal());
    } else
    {
      ComplexNums n = (ComplexNums) other;
      double newMult = this.getMult() + n.getMult();
      double newVal = this.getVal() + other.getVal();
      return new ComplexNums(newVal, newMult);
    }
  }

  public Nums sub(final Nums other)
  {
    if (other == null)
    {
      return null;
    }

    if (other instanceof SimpleNums)
    {
      return this.add(new SimpleNums(other.getVal() * -1));
    } else
    {
      ComplexNums n = (ComplexNums) other;
      return this.add(new ComplexNums(n.getVal() * -1, n.getMult()));
    }
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
      return new ComplexNums(this.getMult() * other.getVal(),
          this.getVal() * other.getVal());
    } else // Needs to make a new simple number
    {
      ComplexNums n = (ComplexNums) other;
      double newMult = (this.getMult() * n.getVal()) + this.getVal() * n.getMult();
      double newVal = (this.getVal() * other.getVal())
          + (this.getMult() * n.getMult() * -1);
      return new ComplexNums(newVal, newMult);
    }
  }

  public Nums div(final Nums other)
  {
    if (other == null)
    {
      return null;
    }

    if (other instanceof SimpleNums)
    {
      double divisor = other.getVal();
      return new ComplexNums(this.getMult() / divisor, this.getVal() / divisor);
    } else
    {
      ComplexNums n = (ComplexNums) other;

      double a = this.getVal();
      double b = this.getMult();
      double c = n.getVal();
      double d = n.getMult();

      double denominator = c * c + d * d;

      double newVal = (a * c + b * d) / denominator;
      double newMult = (b * c - a * d) / denominator;

      return new ComplexNums(newVal, newMult);
    }
  }

  /**
   * basic getter.
   * 
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
    if (getVal() % 1 == 0)
    {
      return (int) getVal() + "i";
    }
    return getVal() + "i";
  }

}

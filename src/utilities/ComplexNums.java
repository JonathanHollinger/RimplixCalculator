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
	 * @param val   val
	 * @param iMult i modifier
	 */
	public ComplexNums(final double val, final double iMult)
	{
		this.iMult = iMult;
		this.val = val;
	}

	/**
	 * int constructor.
	 * 
	 * @param val   val
	 * @param iMult i modifier
	 */
	public ComplexNums(final int val, final int iMult)
	{
		this.iMult = (double) iMult;
		this.val = (double) val;
	}

	/**
	 * Addition function.
	 * 
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
			return new ComplexNums(this.getIConst(), this.getVal() + other.getVal());
		} else
		{
			ComplexNums n = (ComplexNums) other;
			double newMult = this.getIConst() + n.getIConst();
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
			return this.add(new ComplexNums(n.getVal() * -1, n.getIConst() * -1));
		}
	}

	public Nums mult(final Nums other)
	{
		if (other == null)
		{
			return null;
		}

		// Just needs to multiply the new value
		if (other instanceof SimpleNums)
		{
			return new ComplexNums(this.getIConst() * other.getVal(),
					this.getVal() * other.getVal());
		} else // Needs to make a new simple number
		{
			ComplexNums n = (ComplexNums) other;

			double a = this.getVal();
			double b = this.getIConst();

			double c = n.getVal();
			double d = n.getIConst();

			double r = a * c - b * d;
			double i = a * d + b * c;

			return new ComplexNums(r, i);
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
			return new ComplexNums(this.getIConst() / divisor, this.getVal() / divisor);
		} else
		{
			ComplexNums n = (ComplexNums) other;

			double a = this.getVal();
			double b = this.getIConst();
			double c = n.getVal();
			double d = n.getIConst();

			double denominator = c * c + d * d;

			double newVal = (a * c + b * d) / denominator;
			double newMult = (b * c - a * d) / denominator;

			return new ComplexNums(newVal, newMult);
		}
	}

	public Nums exponentiate(final Nums other)
	{
		if (other == null)
		{
			return null;
		}

		// Simple Nums are Complex without Imaginary parts.
		double valR = other.getVal();
		double valI = (other instanceof ComplexNums) ? ((ComplexNums) other).getIConst()
				: 0.0;

		// z = a + bi
		double a = this.getVal();
		double b = this.getIConst();

		// |z| and arg(z)
		// |z| = sqrt(a^2 + b^2)
		// arg(z) = atan2(b, a)
		double mod = Math.hypot(a, b);
		double arg = Math.atan2(b, a);

		// log(z) = ln|z| + i * arg
		double logR = Math.log(mod);
		double logI = arg;

		// w * log(z)
		// Product of the Real and Imaginary numbers
		double prodR = logR * valR - logI * valI;
		double prodI = logI * valR + logR * valI;

		// exp(prodR + i * prodI) = e^prodR * (cos(prodI) + i * sin(prodI))
		double exp = Math.exp(prodR);
		double newVal = exp * Math.cos(prodI);
		double newMult = exp * Math.sin(prodI);

		return new ComplexNums(newVal, newMult);
	}

	public Nums log(final Nums other)
	{
		if (other == null)
		{
			return null;
		}

		// Natural log of this: ln|z| + i * arg(z)
		double a = this.getVal();
		double b = this.getIConst();

		// |z| and arg(z)
		double mod1 = Math.hypot(a, b);
		double arg1 = Math.atan2(b, a);
		ComplexNums ln1 = new ComplexNums(Math.log(mod1), arg1);

		double c = other.getVal();
		double d = (other instanceof ComplexNums) ? ((ComplexNums) other).getIConst()
				: 0.0;

		double mod2 = Math.hypot(c, d);
		double arg2 = Math.atan2(d, c);
		ComplexNums ln2 = new ComplexNums(Math.log(mod2), arg2);

		return ln1.div(ln2);
	}

	/**
	 * basic getter.
	 * 
	 * @return mult
	 */
	public double getIConst()
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
	  Double i = getIConst();
	  Double r = getVal();

	  if (i > 0)
	  {
	    if (r == 0)
	    {
	      return i.toString() + "i";
	    }
	    return r.toString() + "+" + i.toString() + "i";
	  }
	  else if (i < 0)
	  {
	    if (r == 0)
	    {
	      return i.toString() + "i";
	    }
	    i *= -1;
	    return r.toString() + "-" + i.toString() + "i";
	  }

	  return r.toString();
	}
	
	public ComplexNums conjugate() {
	    return new ComplexNums(this.val, -this.iMult);
	}
	
	public ComplexNums invert() {
	    double denominator = val * val + iMult * iMult;
	    double real = val / denominator;
	    double imag = -iMult / denominator;
	    return new ComplexNums(real, imag);
	}
	
	/**
	 * Returns a string representation with 2 decimal places,
	 * formatted according to FormOfResult spec.
	 */
	public String formattedString()
	{
	    double i = getIConst();
	    double r = getVal();

	    String rStr = String.format("%.2f", r);
	    String iStr = String.format("%.2f", Math.abs(i));

	    if (i > 0)
	    {
	        return r == 0 ? iStr + "i" : rStr + "+" + iStr + "i";
	    }
	    else if (i < 0)
	    {
	        return r == 0 ? "-" + iStr + "i" : rStr + "-" + iStr + "i";
	    }

	    return rStr;
	}
	
}

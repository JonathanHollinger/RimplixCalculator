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
		Double i = getIConst(); // Imaginary Number.
		Double r = getVal(); // Real number.

		// Check if there is an imaginary number.
		if (i > 0)
		{
			if (r == 0) // If there is no real number.
			{
				return i.toString() + "i";
			}

			// r + i
			return r.toString() + "+" + i.toString() + "i";
		} else if (i < 0)
		{
			if (r == 0) // If there is no real number.
			{
				return i.toString() + "i";
			}

			// Switches polarity of i to match subtraction format.
			i *= -1;

			// r - i
			return r.toString() + "-" + i.toString() + "i";
		}

		return r.toString(); // If i is 0, return real number.
	}

}

package utilities;

/**
 * Performs a calculation matrix to decide the size of the ComplexPlane graph.
 */
public class ExpansionCalculation 
{
  
  /**
   * Helper method for complex plane's GUI. Determines graph size
   * @param n complex number
   * @return dimensions of the graph.
   */
  public static int calculateExpansion(final ComplexNums n) 
  {
    int highest = (int) Math.ceil(Math.max(n.getIConst(), n.getVal()));
    
    //Temporary metric. Will probably be changed
    while (highest % 5 != 0) 
    {
      highest++;
    }
    
    return highest;
  }
}

package utilities;

import java.util.ArrayList;

/**
 * A utility class that performs checks of various kinds.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class Check
{
  /**
   * Check to see if an array of String objects contains a particular String.
   * 
   * @param haystack
   *          The array of String objects to search through
   * @param needle
   *          The String object to search for
   * @return true if haystack contains needle; false otherwise
   */
  public static boolean isValid(String s)
  {
    char[] c = s.toCharArray();
    ArrayList<Character> stack = new ArrayList<>();

    if (c.length == 0 || c.length % 2 != 0)
    {
      return false;
    }

    for (char bracket : c)
    {
      if (bracket == '(' || bracket == '[' || bracket == '{')
      {
        stack.add(bracket);
      }
      else if (bracket == ')' && !stack.isEmpty() && stack.get(stack.size() - 1) == '(')
      {
        stack.remove(stack.size() - 1);
      }
      else if (bracket == ']' && !stack.isEmpty() && stack.get(stack.size() - 1) == '[')
      {
        stack.remove(stack.size() - 1);
      }
      else if (bracket == '}' && !stack.isEmpty() && stack.get(stack.size() - 1) == '{')
      {
        stack.remove(stack.size() - 1);
      }
      else
      {
        return false;
      }
    }
    return stack.isEmpty();
  }
}

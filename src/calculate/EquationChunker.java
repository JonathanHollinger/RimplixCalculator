package calculate;
import java.util.regex.*;



/**
 * Second Equation chunker, designed to work with gunner's implementation of ComplexCalculate.
 */
public class EquationChunker {

  public static String[] chunkEquation(String equation) {
    // Regular expression to match numbers, operators, and parentheses
    String pattern = "(\\d+\\.?\\d*|[-+*/^()])";

    // Create a Pattern object from the regex
    Pattern p = Pattern.compile(pattern);

    // Create a Matcher object to find matches
    Matcher m = p.matcher(equation);

    // Find the number of matches to initialize the array
    int matchCount = 0;
    while (m.find()) {
      matchCount++;
    }

    // Create an array to hold the chunks
    String[] chunks = new String[matchCount];

    // Reset the matcher to go through the string again
    m = p.matcher(equation);
    int index = 0;

    // Add matches to the array
    while (m.find()) {
      chunks[index++] = m.group();
    }

    return chunks;
  }
}
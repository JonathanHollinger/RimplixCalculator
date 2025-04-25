//package utilities;
//
//import java.util.ArrayDeque;
//
///**
// * Stores string to be taken from other points in the program.
// */
//public class StringStorage 
//{
//  private static ArrayDeque<String> arr;
//  
//  /**
//   * adds to storage.
//   * @param s input
//   */
//  public static void add(final String s) 
//  {
//    arr.add(s);
//  }
//  
//  /**
//   * removes from storage.
//   */
//  public static void remove() 
//  {
//    arr.pollLast();
//  }
//  
//  /**
//   * clears storage.
//   */
//  public static void clear() 
//  {
//    arr = new ArrayDeque<String>();
//  }
//  
//  /**
//   * Returns universal string storage.
//   * @return arr
//   */
//  public static ArrayDeque<String> getArr() 
//  {
//    return new ArrayDeque<String>(arr);
//  }
//  
//  /**
//   * Testing method to dump the whole array.
//   */
//  public void dumpString() 
//  {
//
//    for (String s: arr) 
//    {
//      System.out.print(s);
//    }
//    System.out.println();
//  }
//}

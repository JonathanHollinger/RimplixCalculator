package utilities.arithmetic;

public class Token
{
  public final Parser.Expression type;
  public final String value;

  public Token(Parser.Expression type, String value)
  {
    this.type = type;
    this.value = value;
  }

  @Override
  public String toString()
  {
    return type + (value != null ? "(" + value + ")" : "");
  }
}

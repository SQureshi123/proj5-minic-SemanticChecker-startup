public class Token
{
    public String lexeme;
    public int lineno = 1;
    public int column = 1;
    public Token(String lexeme)
    {

        this.lexeme = lexeme;
    }


}

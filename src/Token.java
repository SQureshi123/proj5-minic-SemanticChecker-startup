//Group Members: Shiza Qureshi & Tyler Page
public class Token {

    public String lexeme;
    public int lineno = 1;
    public int column = 1;

    // constructor to include line and column
    public Token(String lexeme) {
        this.lexeme = lexeme;
        this.lineno = lineno;
        this.column = column;
    }
}

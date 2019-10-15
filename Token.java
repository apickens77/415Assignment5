//CSC 415 Assignment 4
//Matthew Tennyson

public class Token
{
    private String lexeme;
    private String description;

    public Token(String lex, String desc)
    {
        lexeme = lex;
        description = desc;
    }

    public boolean equals(Token t)
    {
        if(t==null)
            return false;

        return description.equals(t.description);
    }

    public String getLexeme()
    {
        return lexeme;
    }

    public String getDescription()
    {
        return description;
    }

    public String toString()
    {
        return String.format("%1$30s | %2$s", lexeme, description);
    }
}

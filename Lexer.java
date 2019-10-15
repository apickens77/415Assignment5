//CSC 415 Assignment 4
//Matthew Tennyson

import java.io.File;
import java.util.Scanner;
import java.util.regex.*;

public class Lexer
{
    private static final String[][] TOKENS =
    {
        {"show", "\\bshow\\b", "show keyword"},
        {"num", "\\bnum\\b", "num keyword"},
        {"strkey", "\\bstring\\b", "string keyword"},
        {"if", "\\bif\\b", "if keyword"},
        {"repeat", "\\brepeat\\b", "repeat keyword"},
        {"strlit", "\\\'[^\\\n\\\']*\\\'", "string literal"},
        {"number", "0|[\\-]?[1-9][0-9]*", "numeric literal"},
        {"identifier", "[a-z][a-zA-Z0-9]*", "identifier"},
        {"comment", "\\$[^\\$]*\\$", "comment"},
        {"add", "\\+", "add/concatenate"},
        {"assignment", "\\=", "assignment"},
        {"equal", "\\:", "is equal to"},
        {"parenopen", "\\(", "open parenthesis"},
        {"parenclose", "\\)", "close parenthesis"},
        {"error", "[^\\s]+", "ERROR"}
    };

    private Scanner scanner;
    private Pattern pattern;

    public Lexer(String file)
    {
        String regex = "";
        for(int i=0; i<TOKENS.length; i++)
            regex += "(?<" + TOKENS[i][0] + ">" + TOKENS[i][1] + ")|";
        regex = regex.substring(0, regex.length()-1);

        try
        {
            scanner = new Scanner(new File(file));
            pattern = Pattern.compile(regex);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public Token lex()
    {
        String lexeme = scanner.findWithinHorizon(pattern, 0);

        if(lexeme!=null)
        {
            Matcher matcher = pattern.matcher(lexeme);
            matcher.matches();

            for(int i=0; i<TOKENS.length; i++)
                if(matcher.group(TOKENS[i][0])!=null)
                    return new Token(lexeme, TOKENS[i][2]);
        }

        return null;
    }

    public static void main(String[] args)
    {
        Lexer lex = new Lexer(args[0]);
        Token current = lex.lex();

        int count=0;
        while(current!=null)
        {
            System.out.println((++count) + ".\t" + current);
            current = lex.lex();
        }
    }
}

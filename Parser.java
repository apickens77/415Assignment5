
public class Parser
{
	private static Lexer lex;
	private static Token current;
	
	public Parser(String file)
	{
		try
		{
			lex = new Lexer(file);
			current = lex.lex();
		}
		catch(Exception e)
		{
			System.exit(0);
		}
	}
	
	void match(String token)
	{
		if(token.equals(current.getLexeme())) 
		{
			System.out.println("Matched " + token);
			
			if(current != null)
				current = lex.lex();
			else if(current.getDescription() == "ERROR")
			{
				System.out.println("ERROR ON TOKEN: " + token + "INVALID TOKEN");
				System.exit(0);
			}
		}
		else
		{
			System.out.println("SYNTAX ERROR ON TOKEN: \"" + current.getLexeme() + "\"\nEXPECTED: " + token);
			System.exit(0);
		}
	}
	
	void program()
	{
		System.out.println("Begin <program>");
		
		stmts();
	}
	
	void stmts()
	{
		System.out.println("Begin <stmts>");
		stmt();
		
		if(current != null)
		{
			stmt();
		}
	}
	
	void stmt()
	{
		System.out.println("Begin <stmt>");
		//System.out.println(current);
		try 
		{
			while(current != null) 
			{
				if(current.getDescription() == "num keyword" || current.getDescription() == "string keyword")
					declStmt();
				else if(current.getDescription() == "identifier")
					assStmt();
				else if(current.getDescription() == "repeat keyword")
					loopStmt();
				else if(current.getDescription() == "if keyword")
					ifStmt();
				else if(current.getDescription() == "show keyword")
					printStmt();
				else if(current.getDescription() == "comment")
					match(current.getLexeme());
				else if(current.getDescription() == "ERROR")
				{
					System.out.println("ERROR: TOKEN \"" + current.getLexeme() + "\" IS INVALID");
					System.exit(0);
				}
				else if(current.getDescription() == "close parenthesis")
					break;
				else
				{
					System.out.println("SYNTAX ERROR ON TOKEN: \"" + current.getLexeme() + "\", UNEXPECTED TOKEN");
					System.exit(0);
				}
			}
		}
		catch (NullPointerException e)
		{
			System.exit(0);
		}

	}
	
	void declStmt()
	{
		System.out.println("Begin <decl_stmt>");
		
		if(current.getDescription() == "num keyword")
			match("num");
		else
			match("string");
		
		if(current.getDescription() == "identifier")
			match(current.getLexeme());
		else
		{
			System.out.println("SYNTAX ERROR ON TOKEN \"" + current.getLexeme() + "\", EXPECTED IDENTIFIER");
			System.exit(0);
		}
			
	}
	
	void assStmt()
	{
		System.out.println("Begin <ass_stmt>");
	
		if (current.getDescription() == "identifier")
		{
			match(current.getLexeme());
			match("=");
			expr();
		}
		else
		{
			System.out.println("SYNTAX ERROR ON TOKEN \"" + current.getLexeme() + "\", EXPECTED IDENTIFIER");
			System.exit(0);
		}
		
	}
	
	void loopStmt()
	{
		System.out.println("Begin <loop_stmt>");
		
		match("repeat");
		
		expr();
		if (current.getDescription() == "is equal to")
		{
			match(":");
			expr();
		}
			
		
		match("(");
		stmts();
		match(")");
	}
	
	void ifStmt()
	{
		System.out.println("Begin <if_stmt>");
		
		match("if");
		boolExpr();
		match("(");
		stmts();
		match(")");
	}
	
	void printStmt()
	{
		System.out.println("Begin <show_stmt>");
		
		match("show");
		match("(");
		expr();
		match(")");
	}
	
	void expr()
	{
		System.out.println("Begin <expr>");
		
		val();
		if (current != null && current.getDescription() == "add/concatenate")
		{
			match("+");
			val();
		}
	}
	
	void boolExpr()
	{
		System.out.println("Begin <bool_expr>");
		
		expr();
		match(":");
		expr();
	}
	
	void val()
	{
		System.out.println("Begin <val>");
		
		if (current.getDescription() == "string literal")
			match(current.getLexeme());
		else if (current.getDescription() == "numeric literal")
			match(current.getLexeme());
		else
		{
			System.out.println("ERROR: Expected literal value");
			System.exit(0);
		}
	}
	
	public static void main(String[] args)
	{
		Parser parser = new Parser(args [0]);
		parser.program();
	}
}
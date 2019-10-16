import java.io.File;
import java.util.Scanner;

public class Parser
{
	private String next;
	private Scanner scan;
	
	public Parser(String file)
	{
		try
		{
			scan = new Scanner(new File(file));
			next = scan.next();
		}
		catch(Exception e)
		{
			System.exit(0);
		}
	}
	
	void match(String token)
	{
		if(token.equals(next)) 
		{
			System.out.println("Matched " + token);
			
			if(scan.hasNext())
				next = scan.next();
			else
				System.exit(0);
		}
		else
		{
			System.out.println("ERROR");
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
		
		while(scan.hasNext())
		{
			//match
		}
	}
	
	void stmt()
	{
		while(scan.hasNext())
		{
			if(next == "num" || next == "string")
				declStmt();
			else if(next == Token)
		}
	}
	
	void declStmt()
	{
		if(next == "num")
			match("num");
		else
			match("string");
		
		match("identifier");
	}
	
	void assStmt()
	{
		match("identifier");
		match("=");
		expr();
	}
	
	void loopStmt()
	{
		match("repeat");
		//expression or bool expression
		match("(");
		stmts();
		match(")");
	}
	
	void ifStmt()
	{
		match("if");
		boolExpr();
		match("(");
		stmts();
		match(")");
	}
	
	void printStmt()
	{
		match("show");
		match("(");
		stmts();
		match(")");
	}
	
	void boolExpr()
	{
		expr();
		match(":");
		expr();
	}
	
	void expr()
	{
		
	}
	
	public static void main(String[] args)
	{
		Parser parser = new Parser(args [0]);
		parser.program();
	}
}
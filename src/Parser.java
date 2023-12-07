/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

import java.util.*;
import ast.*;

public class Parser
{
	// For accumulating boolean variables identifiers
	public Stack<String> stackIdentBool = new Stack<String>();
	public Stack<String> stackIdentInt = new Stack<String>();
	public Stack<String> stackIdentFunc = new Stack<String>();
	public Stack<String> stackIdentArray = new Stack<String>();

	private Scanner scan;

	private Token currentTerminal;	
	
	public Parser( Scanner scan )
	{
		this.scan = scan;
		
		currentTerminal = scan.scan();
	}

	public Program parseProgram()
	{
		Block block = parseBlock();
		
		if( currentTerminal.kind != TokenKind.EOT )
			System.out.println( "Tokens found after end of program" );
		
		return new Program(block);
	}
	
	private Block parseBlock()
	{
		accept( TokenKind.DECL );
		Declarations decs = parseDeclarations();
		accept( TokenKind.START );
		Statements sts = parseStatements();
		accept( TokenKind.END );

		return new Block(decs, sts);
	}
	
	private Declarations parseDeclarations()
	{
		Declarations decs = new Declarations();
		
		while( currentTerminal.kind == TokenKind.INT  ||
			   currentTerminal.kind == TokenKind.BOOL  ||
		       currentTerminal.kind == TokenKind.FUNC  ||
			   currentTerminal.kind == TokenKind.ARRAY ){
			
			if (currentTerminal.kind == TokenKind.ARRAY){
				parseOneDeclaration(decs);
			}else {
				decs.dec.add(parseOneDeclaration(decs));		
			}
		}
		return decs;
	}
	

	private Declaration parseOneDeclaration(Declarations decs)
	{
		switch( currentTerminal.kind ) {
			case INT:
				accept( TokenKind.INT );
				Identifier intId = parseIdentifier();
				accept( TokenKind.DOT );

				stackIdentInt.push(intId.spelling);

				return new IntDeclaration( intId );

			case BOOL:
				accept( TokenKind.BOOL );
				Identifier boolId = parseIdentifier();				
				accept( TokenKind.DOT );

				stackIdentBool.push(boolId.spelling);

				return new BoolDeclaration( boolId );
				
			case FUNC:
				accept( TokenKind.FUNC );
				Identifier name = parseIdentifier();
				accept( TokenKind.LEFTPARAN );
				
				Declarations params = null;
				if( currentTerminal.kind == TokenKind.IDENTIFIER )
					params = parseIdList();
				else
					params = new Declarations();
					
				accept( TokenKind.RIGHTPARAN );
				Block block = parseBlock();
				accept( TokenKind.RETURN );
				Expression retExp = parseExpression();
				accept( TokenKind.DOT );

				stackIdentFunc.push(name.spelling);

				return new FunctionDeclaration( name, params, block, retExp );
				
			case ARRAY:
				accept( TokenKind.ARRAY );
				Identifier arrayId = parseIdentifier();
				accept( TokenKind.LEFTBR );
				IntegerLiteral size = parseIntegerLiteral();
				accept( TokenKind.RIGHTBR );	
				accept( TokenKind.DOT );

				stackIdentInt.push(arrayId.spelling);

				decs.dec.add(new ArrayDeclaration( arrayId, size ));
				// Creates an IntDeclaration for each size
				for (int index = 0; index < (Integer.parseInt(size.spelling)); index++){
					Identifier idplusvalue = new Identifier( arrayId.spelling + "[" + Integer.toString(index) + "]" );
					decs.dec.add(new IntDeclaration( idplusvalue ));
				}
				
				return null;

			default:
				System.out.println( "int, bool, func or array expected" );
				return null;
		}
	}
	
	private Identifier parseIdentifier()
	{
		if( currentTerminal.kind == TokenKind.IDENTIFIER ) {
			Identifier res = new Identifier( currentTerminal.spelling );
			currentTerminal = scan.scan();
			
			return res;
		} else {
			System.out.println( "Identifier expected" );
			
			return new Identifier( "???" );
		}
	}
	
	
	private IntegerLiteral parseIntegerLiteral()
	{
		if( currentTerminal.kind == TokenKind.INTEGERLITERAL ) {
			IntegerLiteral res = new IntegerLiteral( currentTerminal.spelling );
			currentTerminal = scan.scan();
			
			return res;
		} else {
			System.out.println( "Integer literal expected" );
			
			return new IntegerLiteral( "???" );
		}
	}
	
	private BooleanValue parseBooleanValue()
	{
		if( currentTerminal.kind == TokenKind.BOOLEANVALUE ) {
			BooleanValue res = new BooleanValue( currentTerminal.spelling );
			currentTerminal = scan.scan();
			
			return res;
		} else {
			System.out.println( "Boolean value expected" );
			
			return new BooleanValue( "???" );
		}
	}
	
	private AddOperator parseAddOperator()
	{
		if( currentTerminal.kind == TokenKind.ADDOPERATOR ) {
			AddOperator res = new AddOperator( currentTerminal.spelling );
			currentTerminal = scan.scan();
			
			return res;
		} else {
			System.out.println( "AddOperator expected" );
			
			return new AddOperator( "???" );
		}
	}
	
	private MulOperator parseMulOperator()
	{
		if( currentTerminal.kind == TokenKind.MULOPERATOR ) {
			MulOperator res = new MulOperator( currentTerminal.spelling );
			currentTerminal = scan.scan();
			
			return res;
		} else {
			System.out.println( "MulOperator expected" );
			
			return new MulOperator( "???" );
		}
	}
	
	private AsigOperator parseAsigOperator()
	{
		if( currentTerminal.kind == TokenKind.ASIGOPERATOR ) {
			AsigOperator res = new AsigOperator( currentTerminal.spelling );
			currentTerminal = scan.scan();
			
			return res;
		} else {
			System.out.println( "AsigOperator expected" );
			
			return new AsigOperator( "???" );
		}
	}

	private Declarations parseIdList()
	{
		Declarations list = new Declarations();
		
		list.dec.add( new IntDeclaration( parseIdentifier() ) );
		
		while( currentTerminal.kind == TokenKind.COMMA ) {
			accept( TokenKind.COMMA );
			list.dec.add( new IntDeclaration( parseIdentifier() ) );
		}
		return list;
	}
	
	
	private Statements parseStatements()
	{
		Statements stats = new Statements();
		
		while( currentTerminal.kind == TokenKind.READ ||
			   currentTerminal.kind == TokenKind.IDENTIFIER ||
			   currentTerminal.kind == TokenKind.ADDOPERATOR ||
			   currentTerminal.kind == TokenKind.MULOPERATOR ||
			   currentTerminal.kind == TokenKind.ASIGOPERATOR ||
		       currentTerminal.kind == TokenKind.INTEGERLITERAL ||
			   currentTerminal.kind == TokenKind.BOOLEANVALUE ||
		       currentTerminal.kind == TokenKind.LEFTPARAN ||
		       currentTerminal.kind == TokenKind.IF ||
		       currentTerminal.kind == TokenKind.WHILE ||
		       currentTerminal.kind == TokenKind.FOR ||
		       currentTerminal.kind == TokenKind.PRINT )
			stats.stat.add( parseOneStatement() );
		return stats;
	}
	
	
	private Statement parseOneStatement()
	{
		switch( currentTerminal.kind ) {
			case IDENTIFIER:
			case INTEGERLITERAL:
			case BOOLEANVALUE:
			case ADDOPERATOR:
			case MULOPERATOR:
			case ASIGOPERATOR:
			case LEFTPARAN:
			Expression exp = parseExpression();
				accept( TokenKind.DOT );
				return new ExpressionStatement( exp );
			
			case IF:
				accept( TokenKind.IF );
				Expression ifExp = parseExpression();
				Statements thenPart = parseStatements();
				
				Statements elsePart = null;
				if( currentTerminal.kind == TokenKind.ELSE ) {
					accept( TokenKind.ELSE );
					elsePart = parseStatements();
				} else
					elsePart = new Statements();
				
				accept( TokenKind.ENDIF );
				accept( TokenKind.DOT );
				
				return new IfStatement( ifExp, thenPart, elsePart );

			case WHILE:
				accept( TokenKind.WHILE );
				Expression whileExp = parseExpression();
				Statements stats = parseStatements();

				accept( TokenKind.ENDWHILE );
				accept( TokenKind.DOT );

				return new WhileStatement( whileExp, stats );

			case FOR:
				accept( TokenKind.FOR );
				Expression forExp = parseExpression();
				accept (TokenKind.COMMA );
				IntegerLiteral intCond = parseIntegerLiteral();
				Statements statements = parseStatements();

				accept( TokenKind.ENDFOR );
				accept( TokenKind.DOT );

				return new ForStatement( forExp, intCond, statements );
				
			case PRINT:
				accept( TokenKind.PRINT );
				Expression printExp = parseExpression();
				accept( TokenKind.DOT );

				return new PrintStatement( printExp );

			case READ:
				accept( TokenKind.READ );
				Identifier readId = parseIdentifier();
				accept( TokenKind.DOT );

				return new ReadStatement( readId );

			default:
				System.out.println( "Error in statement" );
				return null;
		}
	}

	private Expression parseExpression()
	{
		Expression res = parseExpression1();
		
		if( currentTerminal.isAssignOperator() ) {
			AsigOperator opAsig = parseAsigOperator();
			Expression tmp = parseExpression();
			
			res = new BinaryExpression( opAsig, res, tmp );
		}
		
		return res;
	}

	private Expression parseExpression1()
	{
		Expression res = parseExpression2();
		while( currentTerminal.isAddOperator() ) {
			AddOperator opAdd = parseAddOperator();
			Expression tmp = parseExpression2();
			
			res = new BinaryExpression( opAdd, res, tmp );
		}
		
		return res;
	}

	private Expression parseExpression2()
	{
		Expression res = parsePrimary();
		while( currentTerminal.isMulOperator() ) {
			MulOperator opMul = parseMulOperator();
			Expression tmp = parsePrimary();
			
			res = new BinaryExpression( opMul, res, tmp );
		}
		
		return res;
	}

	private Expression parsePrimary()
	{
		switch( currentTerminal.kind ) {
			case IDENTIFIER:
			Identifier name = parseIdentifier();

				if( currentTerminal.kind == TokenKind.LEFTPARAN ) {
					accept( TokenKind.LEFTPARAN );
					
					ExpList args;

					if( currentTerminal.kind == TokenKind.IDENTIFIER ||
					    currentTerminal.kind == TokenKind.INTEGERLITERAL ||
						currentTerminal.kind == TokenKind.BOOLEANVALUE ||
					    currentTerminal.kind == TokenKind.ADDOPERATOR ||
					    currentTerminal.kind == TokenKind.MULOPERATOR ||
					    currentTerminal.kind == TokenKind.ASIGOPERATOR ||
					    currentTerminal.kind == TokenKind.LEFTPARAN )
						
						args = parseExpressionList();
					else
						args = new ExpList();
						
					
					accept( TokenKind.RIGHTPARAN );
					return new CallExpression( name, args );
				}
				else if (stackIdentBool.contains(name.spelling.toString())){
					return new BoolExpression ( name );
				} else {
					return new IntExpression ( name );	
				}
				
			case INTEGERLITERAL:
				IntegerLiteral lit = parseIntegerLiteral();
				return new IntLitExpression( lit );

			case BOOLEANVALUE:
				BooleanValue boolValue = parseBooleanValue();
				return new BoolValueExpression( boolValue );
				
			case ADDOPERATOR:
				AddOperator opA = parseAddOperator();
				Expression expA = parsePrimary();
				return new UnaryExpression( opA, expA );

			case MULOPERATOR:
				MulOperator opM = parseMulOperator();
				Expression expM = parsePrimary();
				return new UnaryExpression( opM, expM );

			case ASIGOPERATOR:
				AsigOperator opAsig = parseAsigOperator();
				Expression expAsig = parsePrimary();
				return new UnaryExpression( opAsig, expAsig );
				
			case LEFTPARAN:
				accept( TokenKind.LEFTPARAN );
				Expression res = parseExpression();
				accept( TokenKind.RIGHTPARAN );
				return res;
				
			default:
				System.out.println( "Error in primary" );
				return null;
		}
	}
	
	
	private ExpList parseExpressionList()
	{
		ExpList exps = new ExpList();
		exps.exp.add(parseExpression());

		while( currentTerminal.kind == TokenKind.COMMA ) {
			accept( TokenKind.COMMA );
			exps.exp.add(parseExpression());
		}
		return exps;
	}
	
	
	private void accept( TokenKind expected )
	{
		if( currentTerminal.kind == expected )
		{	
			System.out.println( "Expected " + expected );
			currentTerminal = scan.scan();
		}
		else
			System.out.println( "Expected token of kind " + expected );
	}
}
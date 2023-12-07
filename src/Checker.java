/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

import ast.*;
import java.util.*;


public class Checker implements Visitor
{
	private IdentificationTable idTable = new IdentificationTable();
	
	
	public void check( Program p )
	{
		p.visit( this, null );
	}
	
	
	public Object visitProgram( Program p, Object arg )
	{
		idTable.openScope();
		
		p.block.visit( this, null );
		
		idTable.closeScope();
		
		return null;
	}
	
	
	public Object visitBlock( Block b, Object arg )
	{
		b.decs.visit( this, null );
		b.stats.visit( this, null );
		
		return null;
	}
	
	
	public Object visitDeclarations( Declarations d, Object arg )
	{
		for( Declaration decl: d.dec )
			decl.visit( this, null );
			
		return null;
	}
	
	public Object visitIntDeclaration( IntDeclaration i, Object arg )
	{
		String id = (String) i.id.visit( this, null );
		
		idTable.enter( id, i );
		
		return null;
	}

	public Object visitBoolDeclaration( BoolDeclaration b, Object arg )
	{
		String id = (String) b.id.visit( this, null );
		
		idTable.enter( id, b );
		
		return null;
	}
	
	
	public Object visitFunctionDeclaration( FunctionDeclaration f, Object arg )
	{
		String id = (String) f.name.visit( this, null );
		
		idTable.enter( id, f );
		idTable.openScope();
		
		f.params.visit( this, null );
		f.block.visit( this, null );
		f.retExp.visit( this, null );
		
		idTable.closeScope();
		
		return null;
	}
	
	
	public Object visitStatements( Statements s, Object arg )
	{
		for( Statement stat: s.stat )
			stat.visit( this, null );
			
		return null;
	}
	
	
	public Object visitExpressionStatement( ExpressionStatement e, Object arg )
	{

		e.exp.visit( this, null );
		
		return null;
	}
	
	
	public Object visitIfStatement( IfStatement i, Object arg )
	{
		i.exp.visit( this, null );
		i.thenPart.visit( this, null );
		i.elsePart.visit( this, null );
		
		return null;
	}
	
	
	public Object visitWhileStatement( WhileStatement w, Object arg )
	{
		w.exp.visit( this, null );
		w.stats.visit( this, null );
		
		return null;
	}
	
	
	public Object visitPrintStatement( PrintStatement p, Object arg )
	{
		p.exp.visit( this, null );
		
		return null;
	}
	
	
	public Object visitBinaryExpression( BinaryExpression b, Object arg )
	{
		Type t1 = (Type) b.operand1.visit( this, null );
		Type t2 = (Type) b.operand2.visit( this, null );
		String operator = null;
		if ( b.addOperator != null ) {
			operator = (String) b.addOperator.spelling;
		} else if ( b.mulOperator != null ) {
			operator = (String) b.mulOperator.spelling;
		} else {
			operator = (String) b.AsigOperator.spelling;
		}
		if( operator.equals( ":=" ) && t1.rvalueOnly ) {
			System.out.println( "Left-hand side of := must be a variable" );
		}

		if(b.operand1.getClass().toString().equals("class ast.BoolExpression") || b.operand2.getClass().toString().equals("class ast.BoolExpression")) {
			
			if(operator.equals(":=")) {

				if(! b.operand2.getClass().toString().equals("class ast.BoolValueExpression")){
					System.out.println("Right-hand side of the operation must be Boolean type");
				}
			} else {
				System.out.println("Operation forbidden for Boolean value");
			}
		}
			
		if(b.operand1.getClass().toString().equals("class ast.IntExpression")) {
			
			if( !b.operand2.getClass().toString().equals("class ast.IntLitExpression")
			    && !b.operand2.getClass().toString().equals("class ast.IntExpression")
				&& !b.operand2.getClass().toString().equals("class ast.BinaryExpression")
				&& !b.operand2.getClass().toString().equals("class ast.CallExpression")
				&& !b.operand2.getClass().toString().equals("class ast.UnaryExpression")
				&& !b.operand2.getClass().toString().equals("class ast.BoolValueExpression")
				){
				System.out.println("Right-hand side of the operation must be IntLitExpression, IntExpression, BinaryExpression, CallExpression, UnaryExpression or BoolValueExpression class");
			}
		}
		return new Type( true );
	}


	public Object visitIntExpression( IntExpression i, Object arg )
	{
		String id = (String) i.name.visit( this, null );

		Declaration d = idTable.retrieve( id );
		if( d == null )
			System.out.println( id + " is not declared" );
		else if( !( d instanceof IntDeclaration ) ){
			System.out.println( id + " is not an int variable" );
		}
		else{
			i.decl = (IntDeclaration) d;
		}

		return new Type( false );
	}
	

	public Object visitBoolExpression( BoolExpression b, Object arg )
	{
		String id = (String) b.name.visit( this, null );
		
		Declaration d = idTable.retrieve( id );
		if( d == null )
			System.out.println( id + " is not declared" );
		else if( !( d instanceof BoolDeclaration ) ) {
			System.out.println( id + " is not a bool variable" );
		}
		else{
			b.decl = (BoolDeclaration) d;
		}
			
		return new Type( false );
	}


	public Object visitCallExpression( CallExpression c, Object arg )
	{
		String id = (String) c.name.visit( this, null );
		Vector<Type> t = (Vector<Type>)( c.args.visit( this, null ) );
		
		Declaration d = idTable.retrieve( id );
		if( d == null )
			System.out.println( id + " is not declared" );
		else if( !( d instanceof FunctionDeclaration ) )
			System.out.println( id + " is not a function" );
		else {
			FunctionDeclaration f = (FunctionDeclaration) d;
			c.decl = f;
			
			if( f.params.dec.size() != t.size() )
				System.out.println( "Incorrect number of arguments in call to " + id );
		}
		
		return new Type( true );
	}
	
	
	public Object visitUnaryExpression( UnaryExpression u, Object arg )
	{
		u.operand.visit( this, null );
		String operator;

		if ( u.addOperator != null ) {
			operator = (String) u.addOperator.spelling;
		} else if ( u.mulOperator != null ) {
			operator = (String) u.mulOperator.spelling;
		} else {
			operator = (String) u.asigOperator.spelling;
		}

		if(u.operand.getClass().toString().equals("class ast.BoolExpression")) { 
			System.out.println( "+ or - is not allowed for Boolean expressions" );
		}
		
		if( !operator.equals( "+" ) && !operator.equals( "-" ) )
			System.out.println( "Only + or - is allowed as unary operator" );
		
		return new Type( true );
	}
	
	
	public Object visitIntLitExpression( IntLitExpression i, Object arg )
	{
		i.literal.visit( this, true );
		
		return new Type( true );
	}
	
	
	public Object visitExpList( ExpList e, Object arg )
	{
		Vector<Type> types = new Vector<Type>();
		
		for( Expression exp: e.exp )
			types.add( (Type) exp.visit( this, null ) );
			
		return types;
	}
	
	
	public Object visitIdentifier( Identifier i, Object arg )
	{
		return i.spelling;
	}
	
	
	public Object visitIntegerLiteral( IntegerLiteral i, Object arg )
	{
		return null;
	}
	
	
	public Object visitOperator( Operator o, Object arg )
	{
		return o.spelling;
	}


	public Object visitReadStatement(ReadStatement r, Object arg) {
		String id = (String) r.readId.visit( this, null );
		
		Declaration d = idTable.retrieve( id );
		if( d == null )
			System.out.println( id + " is not declared" );
		else if( !( d instanceof IntDeclaration ) )
			System.out.println( id + " is not an int variable" );
		else
			r.decl = (IntDeclaration) d;
		
		return new Type( false );
	}


	public Object visitForStatement(ForStatement f, Object arg) {

		f.exp.visit( this, null );
			
		if( !( f.intCond instanceof IntegerLiteral ) )
			System.out.println( f + " is not an int variable" );

		f.intCond.visit( this, null );
		f.stats.visit( this, null );
		
		return null;
	}

	public Object visitArrayDeclaration(ArrayDeclaration a, Object arg) {
		String id = (String) a.arrayId.visit( this, null );
		idTable.enter(id, a);

		return null;
	}

	public Object visitBoolValueExpression(BoolValueExpression b, Object arg) {
		return null;
	}

	public Object visitBoolValue(BooleanValue i, Object arg) {
		return null;
	}

	public Object visitAddOperator(AddOperator o, Object arg) {
		return null;
	}

	public Object visitAsigOperator(AsigOperator o, Object arg) {
		return null;
	}

	public Object visitMulOperator(MulOperator o, Object arg) {
		return null;
	}
}
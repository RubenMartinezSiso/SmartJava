/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package ast;


public interface Visitor
{
	public Object visitProgram( Program p, Object arg );
	
	public Object visitBlock( Block b, Object arg );
	
	public Object visitDeclarations( Declarations d, Object arg );
	
	public Object visitFunctionDeclaration( FunctionDeclaration f, Object arg );
	
	public Object visitStatements( Statements s, Object arg );
	
	public Object visitExpressionStatement( ExpressionStatement e, Object arg );
	
	public Object visitIfStatement( IfStatement i, Object arg );
	
	public Object visitWhileStatement( WhileStatement w, Object arg );
	
	public Object visitPrintStatement( PrintStatement p, Object arg );
	
	public Object visitBinaryExpression( BinaryExpression b, Object arg );
	
	public Object visitCallExpression( CallExpression c, Object arg );
	
	public Object visitUnaryExpression( UnaryExpression u, Object arg );
	
	public Object visitIntLitExpression( IntLitExpression i, Object arg );
	
	public Object visitExpList( ExpList e, Object arg );
	
	public Object visitIdentifier( Identifier i, Object arg );
	
	public Object visitIntegerLiteral( IntegerLiteral i, Object arg );
	
	public Object visitOperator( Operator o, Object arg );

	public Object visitForStatement( ForStatement f, Object arg );

	public Object visitReadStatement( ReadStatement r, Object arg );
	
	public Object visitBoolValueExpression( BoolValueExpression b, Object arg );

	public Object visitBoolValue( BooleanValue i, Object arg );


	public Object visitAddOperator( AddOperator o, Object arg );
	
	public Object visitAsigOperator( AsigOperator o, Object arg );

	public Object visitMulOperator( MulOperator o, Object arg );


	public Object visitArrayDeclaration( ArrayDeclaration a, Object arg );

	public Object visitIntDeclaration( IntDeclaration i, Object arg );

	public Object visitIntExpression( IntExpression i, Object arg );

	public Object visitBoolDeclaration( BoolDeclaration b, Object arg );

	public Object visitBoolExpression( BoolExpression b, Object arg );

}
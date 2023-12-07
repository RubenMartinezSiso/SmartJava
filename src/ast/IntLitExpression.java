/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;


public class IntLitExpression
	extends Expression
{
	public IntegerLiteral literal;
	
	
	public IntLitExpression( IntegerLiteral literal )
	{
		this.literal = literal;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitIntLitExpression( this, arg );
	}
}
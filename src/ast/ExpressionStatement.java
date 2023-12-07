/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;


public class ExpressionStatement
	extends Statement
{
	public Expression exp;
	
	
	public ExpressionStatement( Expression exp )
	{
		this.exp = exp;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitExpressionStatement( this, arg );
	}
}
/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package ast;


public class WhileStatement
	extends Statement
{
	public Expression exp;
	public Statements stats;
	
	
	public WhileStatement( Expression exp, Statements stats )
	{
		this.exp = exp;
		this.stats = stats;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitWhileStatement( this, arg );
	}
}
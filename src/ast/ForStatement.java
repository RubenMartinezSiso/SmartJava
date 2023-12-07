/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;


public class ForStatement
	extends Statement
{
	public Expression exp;
	public IntegerLiteral intCond;
	public Statements stats;
	public IntDeclaration decl;
	
	public ForStatement( Expression exp, IntegerLiteral intCond, Statements stats )
	{
		this.exp = exp;
		this.intCond = intCond;
		this.stats = stats;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitForStatement( this, arg );
	}
}
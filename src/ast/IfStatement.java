/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;


public class IfStatement
	extends Statement
{
	public Expression exp;
	public Statements thenPart;
	public Statements elsePart;
	
	
	public IfStatement( Expression exp, Statements thenPart, Statements elsePart )
	{
		this.exp = exp;
		this.thenPart = thenPart;
		this.elsePart = elsePart;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitIfStatement( this, arg );
	}

}
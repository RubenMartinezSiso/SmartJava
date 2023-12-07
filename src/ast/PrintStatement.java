/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package ast;


public class PrintStatement
	extends Statement
{
	public Expression exp;
	
	
	public PrintStatement( Expression exp )
	{
		this.exp = exp;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitPrintStatement( this, arg );
	}
}
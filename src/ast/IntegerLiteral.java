/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;


public class IntegerLiteral
	extends Terminal
{
	public IntegerLiteral( String spelling )
	{
		this.spelling = spelling;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitIntegerLiteral( this, arg );
	}
}
/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;


public class MulOperator
	extends Terminal
{
	public MulOperator( String spelling )
	{
		this.spelling = spelling;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitMulOperator( this, arg );
	}
}
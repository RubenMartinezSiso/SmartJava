/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package ast;


public class AddOperator
	extends Terminal
{
	public AddOperator( String spelling )
	{
		this.spelling = spelling;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitAddOperator( this, arg );
	}
	
}
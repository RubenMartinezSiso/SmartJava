/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;

public class AsigOperator
	extends Terminal
{
	public AsigOperator( String spelling )
	{
		this.spelling = spelling;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitAsigOperator( this, arg );
	}
}
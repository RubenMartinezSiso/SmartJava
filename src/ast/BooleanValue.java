/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package ast;

public class BooleanValue
	extends Terminal
{
	public BooleanValue( String spelling )
	{
		this.spelling = spelling;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitBoolValue( this, arg );
	}

}

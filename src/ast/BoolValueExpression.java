/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package ast;

public class BoolValueExpression
	extends Expression
{
	public BooleanValue boolValue;
	
	
	public BoolValueExpression( BooleanValue boolValue )
	{
		this.boolValue = boolValue;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitBoolValueExpression( this, arg );
	}
}
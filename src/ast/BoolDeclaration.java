/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;

public class BoolDeclaration
	extends Declaration
{
	public Identifier id;
	public Address adr;
	
	public BoolDeclaration( Identifier id )
	{
		this.id = id;	
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitBoolDeclaration( this, arg );
	}
}
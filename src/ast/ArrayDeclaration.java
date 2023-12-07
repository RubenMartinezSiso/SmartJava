/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package ast;

public class ArrayDeclaration
	extends Declaration
{
	public Identifier arrayId;
	public IntegerLiteral size;
	public ArrayDeclaration decl;
	public Address adr;

	
	public ArrayDeclaration( Identifier arrayId, IntegerLiteral size)
	{
		this.arrayId = arrayId;
        this.size = size;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitArrayDeclaration( this, arg );
	}
}

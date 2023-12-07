/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;


public class FunctionDeclaration
	extends Declaration
{
	public Identifier name;
	public Declarations params;
	public Block block;
	public Expression retExp;
	public Address address;
	
	
	public FunctionDeclaration( Identifier name, Declarations params,
	                            Block block, Expression retExp )
	{
		this.name = name;
		this.params = params;
		this.block = block;
		this.retExp = retExp;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitFunctionDeclaration( this, arg );
	}
}
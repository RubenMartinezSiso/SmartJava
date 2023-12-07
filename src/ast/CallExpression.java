/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;

public class CallExpression
	extends Expression
{
	public Identifier name;
	public ExpList args;
	public FunctionDeclaration decl;
	
	
	public CallExpression( Identifier name, ExpList args )
	{
		this.name = name;
		this.args = args;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitCallExpression( this, arg );
	}

}

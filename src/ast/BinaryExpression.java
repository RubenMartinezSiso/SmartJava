/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;


public class BinaryExpression
	extends Expression
{
	public AddOperator addOperator;
	public MulOperator mulOperator;
	public AsigOperator AsigOperator;
	public Expression operand1;
	public Expression operand2;
	
	
	public BinaryExpression( AddOperator operator, Expression operand1, Expression operand2 )
	{
		this.addOperator = operator;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	public BinaryExpression( MulOperator operator, Expression operand1, Expression operand2 )
	{
		this.mulOperator = operator;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	public BinaryExpression( AsigOperator operator, Expression operand1, Expression operand2 )
	{
		this.AsigOperator = operator;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitBinaryExpression( this, arg );
	}
}
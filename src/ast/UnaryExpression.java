/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package ast;

public class UnaryExpression
	extends Expression
{
	public AddOperator addOperator;
	public MulOperator mulOperator;
	public AsigOperator asigOperator;
	public Expression operand;
	
	
	public UnaryExpression( AddOperator operator, Expression operand )
	{
		this.addOperator = operator;
		this.operand = operand;
	}


    public UnaryExpression(MulOperator operator, Expression operand) {
		this.mulOperator = operator;
		this.operand = operand;
    }


    public UnaryExpression(AsigOperator operator, Expression operand) {
		this.asigOperator = operator;
		this.operand = operand;
    }
	
	public Object visit( Visitor v, Object arg )
	{
		return v.visitUnaryExpression( this, arg );
	}	
}
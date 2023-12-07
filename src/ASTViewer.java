/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

import ast.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;


public class ASTViewer
	extends JFrame
{
	private static final Font NODE_FONT = new Font( "Verdana", Font.PLAIN, 24 );
	
	
	public ASTViewer( AST ast )
	{
		super( "Abstract Syntax Tree" );
		
		DefaultMutableTreeNode root = createTree( ast );
		JTree tree = new JTree( root );
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setFont( NODE_FONT );
		tree.setCellRenderer( renderer );
		
		add( new JScrollPane( tree ) );
		
		setSize( 1024, 768 );
		setVisible( true );
		
		setDefaultCloseOperation( EXIT_ON_CLOSE );
	}
	
	
	private DefaultMutableTreeNode createTree( AST ast )
	{
		DefaultMutableTreeNode node = new DefaultMutableTreeNode( "*** WHAT??? ***" );
		
		if( ast == null )
			node.setUserObject( "*** NULL ***" );
		else if( ast instanceof Program ) {
			node.setUserObject( "SmartJava" );
			node.add( createTree( ((Program)ast).block ) );
		} else if( ast instanceof Block ) {
			node.setUserObject( "Block" );
			node.add( createTree( ((Block)ast).decs ) );
			node.add( createTree( ((Block)ast).stats ) );
		} else if( ast instanceof Declarations ) {
			node.setUserObject( "Declarations" );
			
			for( Declaration d: ((Declarations)ast).dec )
				node.add( createTree( d ) );
		 
		} else if( ast instanceof IntDeclaration ) {
			node.setUserObject( "IntDeclaration" );
			node.add( createTree( ((IntDeclaration)ast).id ) );

		} else if( ast instanceof BoolDeclaration ) {
			node.setUserObject( "BoolDeclaration" );
			node.add( createTree( ((BoolDeclaration)ast).id ) );
		
		} else if( ast instanceof FunctionDeclaration ) {
			node.setUserObject( "FunctionDeclaration" );
			node.add( createTree( ((FunctionDeclaration)ast).name ) );
			node.add( createTree( ((FunctionDeclaration)ast).block ) );
			node.add( createTree( ((FunctionDeclaration)ast).retExp ) );

		} else if( ast instanceof ArrayDeclaration ) {
			node.setUserObject( "ArrayDeclaration" );
			node.add( createTree( ((ArrayDeclaration)ast).arrayId ) );
			node.add( createTree( ((ArrayDeclaration)ast).size ) );

		} else if( ast instanceof Statements ) {
			node.setUserObject( "Statements" );
			
			for( Statement s: ((Statements)ast).stat )
				node.add( createTree( s ) );
		} else if( ast instanceof ExpressionStatement ) {
			node.setUserObject( "ExpressionStatement" );
			node.add( createTree( ((ExpressionStatement)ast).exp ) );
		} else if( ast instanceof IfStatement ) {
			node.setUserObject( "IfStatement" );
			node.add( createTree( ((IfStatement)ast).exp ) );
			node.add( createTree( ((IfStatement)ast).thenPart ) );

			if (((IfStatement)ast).elsePart != null) {
				node.add( createTree( ((IfStatement)ast).elsePart ) );
			}

		} else if( ast instanceof WhileStatement ) {
			node.setUserObject( "WhileStatement" );
			node.add( createTree( ((WhileStatement)ast).exp ) );
			node.add( createTree( ((WhileStatement)ast).stats ) );

		} else if( ast instanceof ForStatement ) {
			node.setUserObject( "ForStatement" );
			node.add( createTree( ((ForStatement)ast).exp ) );
			node.add( createTree( ((ForStatement)ast).intCond ) );
			node.add( createTree( ((ForStatement)ast).stats ) );

		} else if( ast instanceof ReadStatement ) {
			node.setUserObject( "ReadStatement" );
			node.add( createTree( ((ReadStatement)ast).readId ) );
		} else if( ast instanceof PrintStatement ) {
			node.setUserObject( "PrintStatement" );
			node.add( createTree( ((PrintStatement)ast).exp ) );

		} else if( ast instanceof BinaryExpression ) {
			node.setUserObject( "BinaryExpression" );
			if (((BinaryExpression)ast).AsigOperator != null) {
				node.add( createTree( ((BinaryExpression)ast).AsigOperator ) );
			}
			if (((BinaryExpression)ast).addOperator != null) {
				node.add( createTree( ((BinaryExpression)ast).addOperator ) );
			}
			if (((BinaryExpression)ast).mulOperator != null) {
				node.add( createTree( ((BinaryExpression)ast).mulOperator ) );
			}
			node.add( createTree( ((BinaryExpression)ast).operand1 ) );
			node.add( createTree( ((BinaryExpression)ast).operand2 ) );
		} else if( ast instanceof CallExpression ) {
			node.setUserObject( "CallExpression" );
			node.add( createTree( ((CallExpression)ast).name ) );
			node.add( createTree( ((CallExpression)ast).args ) );
		} else if( ast instanceof IntLitExpression ) {
			node.setUserObject( "IntLitExpression" );
			node.add( createTree( ((IntLitExpression)ast).literal ) );

		} else if( ast instanceof BoolValueExpression ) {
			node.setUserObject( "BoolValueExpression" );
			node.add( createTree( ((BoolValueExpression)ast).boolValue ) );
		
	
		} else if( ast instanceof UnaryExpression ) {
			node.setUserObject( "UnaryExpression" );
			if (((UnaryExpression)ast).asigOperator != null) {
				node.add( createTree( ((UnaryExpression)ast).asigOperator ) );
			}
			if (((UnaryExpression)ast).addOperator != null) {
				node.add( createTree( ((UnaryExpression)ast).addOperator ) );
			}
			if (((UnaryExpression)ast).mulOperator != null) {
				node.add( createTree( ((UnaryExpression)ast).mulOperator ) );
			}
			node.add( createTree( ((UnaryExpression)ast).operand ) );
		
		} else if( ast instanceof BoolExpression ) {
			node.setUserObject( "BoolExpression" );
			node.add( createTree( ((BoolExpression)ast).name ) );
		
		} else if( ast instanceof IntExpression ) {
			node.setUserObject( "IntExpression" );
			node.add( createTree( ((IntExpression)ast).name ) );

		} else if( ast instanceof ExpList ) {
			node.setUserObject( "ExpList" );
			for( Expression e: ((ExpList)ast).exp )
				node.add( createTree( e ) );
		} else if( ast instanceof Identifier ) {
			node.setUserObject( "Identifier " + ((Identifier)ast).spelling );
		} else if( ast instanceof IntegerLiteral ) {
			node.setUserObject( "IntegerLiteral " + ((IntegerLiteral)ast).spelling );
		
		} else if( ast instanceof BooleanValue ) {
			node.setUserObject( "BooleanValue " + ((BooleanValue)ast).spelling );
		
		} else if( ast instanceof AsigOperator ) {
			node.setUserObject( "AsigOperator " + ((AsigOperator)ast).spelling );
		} else if( ast instanceof AddOperator ) {
			node.setUserObject( "AddOperator " + ((AddOperator)ast).spelling );
		} else if( ast instanceof MulOperator ) {
			node.setUserObject( "MulOperator " + ((MulOperator)ast).spelling );
		}

		return node;
	}
}
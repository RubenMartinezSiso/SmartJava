/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/


import ast.*;

import javax.swing.*;


public class TestDriverAST
{
	//private static final String EXAMPLES_DIR = "C:\\Users\\ruben\\OneDrive\\Escritorio\\SmartJava\\src\\SmartJavaTests\\Tests SMARTJAVA ULTIMATE";
    private static final String EXAMPLES_DIR = "C:\\Users\\Usuario\\Desktop\\Universidad VIA\\CMC Project\\SmartJava\\src\\SmartJavaTests\\Tests SMARTJAVA ULTIMATE";
	
	public static void main( String args[] )
	{
		JFileChooser fc = new JFileChooser( EXAMPLES_DIR );
		
		if( fc.showOpenDialog( null ) == JFileChooser.APPROVE_OPTION ) {
			SourceFile in = new SourceFile( fc.getSelectedFile().getAbsolutePath() );
			Scanner s = new Scanner( in );
			Parser p = new Parser( s );
		
			AST ast = p.parseProgram();
			
			new ASTViewer( ast );
		}
	}
}
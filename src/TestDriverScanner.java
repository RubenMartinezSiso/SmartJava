/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

import javax.swing.*;

 
public class TestDriverScanner
{
	private static final String EXAMPLES_DIR = "C:\\Users\\ruben\\OneDrive\\Escritorio\\SmartJava\\src\\SmartJavaTests\\Tests SMARTJAVA ULTIMATE";
    //private static final String EXAMPLES_DIR = "C:\\Users\\Usuario\\Desktop\\Universidad VIA\\CMC Project\\SmartJava\\src\\SmartJavaTests\\Tests SMARTJAVA ULTIMATE";
	
	public static void main( String args[] )
	{
		JFileChooser fc = new JFileChooser( EXAMPLES_DIR );
		
		if( fc.showOpenDialog( null ) == JFileChooser.APPROVE_OPTION ) {
			SourceFile in = new SourceFile( fc.getSelectedFile().getAbsolutePath() );
			Scanner s = new Scanner( in );
		
			Token t = s.scan();
			while( t.kind != TokenKind.EOT ) {
				System.out.println( t.kind + " " + t.spelling );
			
				t = s.scan();
			}
		}
	}
}
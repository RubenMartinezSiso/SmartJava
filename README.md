# SmartJava

## Collaborators 
* Dand Marbà Sera
* Rubén Martínez Sisó
* Lucía Cárdenas Palacios

## Scanner
The Scanner goes through the entire program analyzing each of the different characters or strings of characters that it encounters. Together with the TokenKind class, it allows to identify and classify them into their corresponding token types. If, for example, it finds the symbol :=, the program will know how to identify that it is the assignment operator (ASIGOPERATOR).

## AST
With the AST, we can see the structure of our grammar developed in the form of a tree. Based on the Block structure, two sections are distinguished within it: one for declarations, differentiating between the different types (int, bool, array, function...); and another for the statements, where all the code instructions are broken down (expressions, while, for...).

## Checker
Thanks to the Checker, we can verify if the operation of our program is correct or not. In this section, we have declared a series of rules and restrictions that we consider appropriate and logical for our language. For each program, a plot of the different characters that are expected depending on the functionalities and expressions that the example takes (Expected IF, Expected ELSE...) is output. In case of breaking any of these rules or not following the structure of the grammar, it will be reported through an error message in the terminal.

## Compiler
The compiler also checks that the program correctly follows the course of the grammar. It converts the file of our example (with .txt format) into a .tam file, where it is transformed into ASCII language, and, later, it can be interpreted using the stacks. In this section, errors will also be detected in the event that some non-logical data or action is entered in the program to be executed.

## Interpreter
The interpreter is in charge of reading the instructions described in the .tam file in hexadecimal format, where the operation to be carried out (JUMP, STORE...), the register number, the length number of the operand and the data or address are indicated. to store (STORE), load (LOAD) or with which said operation is going to be carried out.
After executing it, the result of the entered program and a message indicating that the program has halted normally are obtained in the terminal. In case there is an error, we will be informed with a message about it.
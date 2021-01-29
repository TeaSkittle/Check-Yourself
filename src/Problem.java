/*
=========================================
By: Travis Dowd
Date: 1-19-2021

This class file is for the database storing and accesing of FEN positions an the thier solutions, also is the
core for file handling in this project.
=========================================
*/
package src;

import java.util.*;
import java.io.*;

public class Problem {
    public static final String PROBLEMS_FILE = "resources/Problems.txt";  // File containing all FEN probelsm and correct moves
    public static final String CURRENT_FILE  = "resources/Current.txt";   // File holding an int value indicating which problem user is on
    public static int problemNumber;
    /*
    ====================
    Constructors
     A default empty contructor, and one which takes the board position as an int[][] array.
    ====================
    */
    public Problem(){}
    public Problem( int problemNumber ){
        this.problemNumber = problemNumber;
    }
    /*
    ====================
    ReadCurrent
     Return current problem int value from CURRENT_FILE
    ====================
    */
    public static int ReadCurrent(){
        try {
            BufferedReader reader = new BufferedReader( new FileReader( CURRENT_FILE ));
            return Integer.parseInt( reader.readLine() );
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
            return 0;
        } catch ( IOException e ) {
            e.printStackTrace();
            return 0;
        } catch ( NumberFormatException e ) {
            e.printStackTrace();
            return 0;
        }
    }
    /*
    ====================
    WriteCurrent
     Write current problem as an int value to CURRENT_FILE, overwriting the file
    ====================
    */
    public static void WriteCurrent( int currentProblem ){
        if ( currentProblem <= FileSize()) {
            try {
                FileWriter writer = new FileWriter( CURRENT_FILE, false );
                writer.write( new Integer( currentProblem ).toString() );
                writer.close();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
    /*
    ====================
    IncrementCurrent
     Increment int value in CURRENT_FILE by one
    ====================
    */
    public static void IncrementCurrent(){
        WriteCurrent( ReadCurrent() + 1 );
    }
    /*
    ====================
    ReadPosition
     Return the FEN string from text file
    ====================
    */
    public static String ReadPosition( int problem ){
        try {
            BufferedReader reader = new BufferedReader( new FileReader( PROBLEMS_FILE ));
            for ( int i = 0; i < problem - 1; i++ ) { reader.readLine(); }
            String[] array = reader.readLine().split( ":" );
            return ( String ) array[ 0 ];
        } catch ( IOException e ) {
            e.printStackTrace();
            return "Error: IOException";
        } catch ( NullPointerException e ) {
            e.printStackTrace();
            return "Error: NullPointerException";
        }
    }
    /*
    ====================
    ReadMove
     Return the correct move from file as a String
    ====================
    */
    public static String ReadMove( int problem ){
        try {
            BufferedReader reader = new BufferedReader( new FileReader( PROBLEMS_FILE ));
            for ( int i = 0; i < problem - 1; i++ ) { reader.readLine(); }
            String[] array = reader.readLine().split( ":" );
            return ( String ) array[ 1 ];
        } catch ( IOException e ) {
            e.printStackTrace();
            return "Error: IOException";
        } catch ( NullPointerException e ) {
            e.printStackTrace();
            return "Error: NullPointerException";
        }
    }
    /*
    ====================
    ReadSquare
     Return the square on the board of the the correct move
    ====================
    */
    public static String ReadSquare( String move ){
        String[] array = move.split( "" );
        return ( String ) array[ array.length - 2 ] + ( String ) array[ array.length - 1 ];
    }
    /*
    ====================
    FileSize
     Return the number of lines in file as int value
    ====================
    */
    public static int FileSize(){
        try {
            BufferedReader reader = new BufferedReader( new FileReader( PROBLEMS_FILE ));
            int lines = 0;
            while ( reader.readLine() != null ) lines++;
            reader.close();
            return lines;
        } catch ( IOException e ) {
            e.printStackTrace();
            return 0;
        }
    }
    /*
    ====================
    Main
     Main method to run as a stand alone program and used for testing without a GUI.
    ====================
    */
    public static void main( String[] args ){
        problemNumber = 4;
        if ( problemNumber <= FileSize()) {
            System.out.println( "position: " + ReadPosition( problemNumber ));
            System.out.println( "correctMove: " + ReadMove( problemNumber ));
            System.out.println( "sqaure: " + ReadSquare( ReadMove( problemNumber )));
            WriteCurrent( problemNumber );
            int currentProblem = ReadCurrent();
            System.out.println( "ReadCurrent: " + currentProblem );
        } 
    }
}

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
    public static final String fileLocation = "resources/Problems.txt";
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
    ReadPosition
     Return the FEN string from text file
    ====================
    */
    public static String ReadPosition( int problem ){
        try {
            BufferedReader reader = new BufferedReader( new FileReader( fileLocation ));
            for ( int i = 0; i < problem - 1; i++ ) { reader.readLine(); }
            String line = reader.readLine();
            String[] moveArray = line.split( ":" );
            String position = moveArray[ 0 ];
            return position;
        } catch ( IOException e ) {
            e.printStackTrace();
            return "Error";
        } catch ( NullPointerException e ) {
            e.printStackTrace();
            return "Error";
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
            BufferedReader reader = new BufferedReader( new FileReader( fileLocation ));
            for ( int i = 0; i < problem - 1; i++ ) { reader.readLine(); }
            String line = reader.readLine();
            String[] moveArray = line.split( ":" );
            String correctMove = moveArray[ 1 ];
            return correctMove;
        } catch ( IOException e ) {
            e.printStackTrace();
            return "Error";
        } catch ( NullPointerException e ) {
            e.printStackTrace();
            return "Error";
        }
    }
    /*
    ====================
    ReadSquare
     Return the square on the board of the the correct move
    ====================
    */
    public static String ReadSquare( String move ){
        String[] stringArray = move.split( "" );
        String secondToLastString = stringArray[ stringArray.length - 2 ];
        String lastString = stringArray[ stringArray.length - 1 ];
        return secondToLastString + lastString;
    }
    /*
    ====================
    FileSize
     Return the number of lines in file as int value
    ====================
    */
    public static int FileSize(){
        try {
            BufferedReader reader = new BufferedReader( new FileReader( fileLocation ));
            int lines = 0;
            while (reader.readLine() != null) lines++;
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
        // Originally thought of using TreeMap, but raw text is wokring fine
        //TreeMap<String, String> treeMap = new TreeMap<String, String>();
        problemNumber = 2;
        if ( problemNumber <= FileSize()) {
            System.out.println( "position: " + ReadPosition( problemNumber ));
            System.out.println( "correctMove: " + ReadMove( problemNumber ));
            System.out.println( "sqaure: " + ReadSquare( ReadMove( problemNumber )));
        } 
    }
}
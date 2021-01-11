/*
=========================================
By: Travis Dowd
Date: 1-27-2021

This file is for the core logic of the game, meant to be ran without GUI
and to be used in a GUI program. This should run the entire game as a TUI
program first, then once that is working move on to creating a GUI with this
class file used as the core game logic.

Notes:
  - Will try to avoid void methods
  - Will try and use recursion where possible
  - Following John Carmack's DOOM 3 code style: https://fabiensanglard.net/fd_proxy/doom3/CodeStyleConventions.pdf
=========================================
 */

package src;

// Why is this imported????
//import javax.swing.text.*;

public class Game {
    /*
    ====================
     Debugging Variables
    ====================
    */
    private static int DEBUG;
    private static String startingPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    /*
    ====================
     Variables
    ====================
    */
    private static int[][] position;
    private static int W_PAWN   = 1;
    private static int W_KNIGHT = 2;
    private static int W_BISHOP = 3;
    private static int W_ROOK   = 4;
    private static int W_QUEEN  = 5;
    private static int W_KING   = 6;
    private static int B_PAWN   = 7;
    private static int B_KNIGHT = 8;
    private static int B_BISHOP = 9;
    private static int B_ROOK   = 10;
    private static int B_QUEEN  = 11;
    private static int B_KING   = 12;
    private static String W_PAWN_S   = "\u2659";
    private static String W_KNIGHT_S = "\u2658";
    private static String W_BISHOP_S = "\u2657";
    private static String W_ROOK_S   = "\u2656";
    private static String W_QUEEN_S  = "\u2655";
    private static String W_KING_S   = "\u2654";
    private static String B_PAWN_S   = "\u265F";
    private static String B_KNIGHT_S = "\u265E";
    private static String B_BISHOP_S = "\u265D";
    private static String B_ROOK_S   = "\u265C";
    private static String B_QUEEN_S  = "\u265B";
    private static String B_KING_S   = "\u265A";
    private static String fenPosition; // the string of characters showing the position of the pieces in FEN
    private static int turn;           // 0 = white, 1 = black
    private static int castle;         // 0 = white, 1 = black, 2 = neither can castle
    private static String enPassant;   // which square can be targetd with an En Passant ( ex "e3" )
    private static int halfMove;       // Deals with 50-move rule, will most likely not be used in this program 
    private static int fullMove;       // Incremented after every one of black's moves, will most likely not be used
    
    /*
    ====================
    Constructors
     A default empty contructor, and one which takes the board position as an int[][] array.
    ====================
    */
    public Game(){}
    public Game( int[][] position ){
        this.position = position;
    }
    
    /*
    ====================
    PrintPosition
     Print the int[][] position array as the actual int values,
     to be use primarily for testing.
    ====================
    */
    public static void PrintPosition( int[][] array ){
        for ( int i = 0; i < 8; i++ ) {
            for ( int j = 0; j < 8; j++ ) {
                System.out.print( array[ i ][ j ] + " " );
                if ( array[ i ][ j ] < 10 ){ System.out.printf( " " ); }
                if ( j == 7 ){ System.out.printf( "\n" ); }
            }
        }
    }
    
    /*
    ====================
    PrintPosition_r
     Recursive version of PrintPosition that prints the array as
     unicode chess symbols.
    ====================
    */
    public static int PrintPosition_r( int[][] array, int i, int j ){
        if( j >= 8 ){ return 0; }
        if( i >= 8 ){ return 1; }
        switch ( array[ i ][ j ] ) {
            case 1:  System.out.print( W_PAWN_S   + " " ); break; 
            case 2:  System.out.print( W_KNIGHT_S + " " ); break;
            case 3:  System.out.print( W_BISHOP_S + " " ); break;
            case 4:  System.out.print( W_ROOK_S   + " " ); break;
            case 5:  System.out.print( W_QUEEN_S  + " " ); break;
            case 6:  System.out.print( W_KING_S   + " " ); break;
            case 7:  System.out.print( B_PAWN_S   + " " ); break;
            case 8:  System.out.print( B_KNIGHT_S + " " ); break;
            case 9:  System.out.print( B_BISHOP_S + " " ); break;
            case 10: System.out.print( B_ROOK_S   + " " ); break;
            case 11: System.out.print( B_QUEEN_S  + " " ); break;
            case 12: System.out.print( B_KING_S   + " " ); break;
            default: System.out.print( "- " );
        } if ( j == 7 ){ System.out.printf( "\n" ); }
        if( PrintPosition_r( array, i, j + 1 ) == 1 ){ return 1; }
        return PrintPosition_r( array, i + 1, 0 );
    }

    /*
    ====================
    FenParser
     Take input data (raw text) from FEN ( Forsyth-Edwards Notation ) and 
     builds a two dimensional array of the board position.
    
     More info on FEN here: https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
    ====================
    */
    public static int[][] FenParser( String fenString ){
        // Starting position in FEN:
        //   rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
        // The above is an example of the string passed to this method
        int[][] array;
        
        return array;
    }

    /*
    ====================
    Main
     Main method to run as a stand alone program and 
     used for testing without a GUI.
    ====================
    */
    public static void main( String[] args ) {
        DEBUG = 0;
        int[][] position = { 
            { B_ROOK, B_KNIGHT, B_BISHOP, B_QUEEN, B_KING, B_BISHOP, B_KNIGHT, B_ROOK }, 
            { B_PAWN, B_PAWN, B_PAWN, B_PAWN, B_PAWN, B_PAWN, B_PAWN, B_PAWN },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { W_PAWN, W_PAWN, W_PAWN, W_PAWN, W_PAWN, W_PAWN, W_PAWN, W_PAWN },
            { W_ROOK, W_KNIGHT, W_BISHOP, W_QUEEN, W_KING, W_BISHOP, W_KNIGHT, W_ROOK }
        };
        if ( DEBUG == 1 ){
            System.out.print( "DEBUG INFO:\n"   );
            System.out.print( "===========\n\n" );
            
            // Debug code goes here
            PrintPosition( position );
            
            System.out.print( "\n===========\n\n" );
        }
        PrintPosition_r( position, 0, 0);
    }
}

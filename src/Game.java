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
    - Using switch/case over multiple if/else when needed
    - Following John Carmack's DOOM 3 code style: https://fabiensanglard.net/fd_proxy/doom3/CodeStyleConventions.pdf
=========================================
 */
package src;

import java.util.*;
import java.lang.*;

public class Game {
        private static int DEBUG;
        private static int board[][] = new int[ 8 ][ 8 ]; 
        private final static int W_PAWN   = 11;
        private final static int W_KNIGHT = 12;
        private final static int W_BISHOP = 13;
        private final static int W_ROOK   = 14;
        private final static int W_QUEEN  = 15;
        private final static int W_KING   = 16;
        private final static int B_PAWN   = 21;
        private final static int B_KNIGHT = 22;
        private final static int B_BISHOP = 23;
        private final static int B_ROOK   = 24;
        private final static int B_QUEEN  = 25;
        private final static int B_KING   = 26;
        private final static String W_PAWN_S   = "\u2659";
        private final static String W_KNIGHT_S = "\u2658";
        private final static String W_BISHOP_S = "\u2657";
        private final static String W_ROOK_S   = "\u2656";
        private final static String W_QUEEN_S  = "\u2655";
        private final static String W_KING_S   = "\u2654";
        private final static String B_PAWN_S   = "\u265F";
        private final static String B_KNIGHT_S = "\u265E";
        private final static String B_BISHOP_S = "\u265D";
        private final static String B_ROOK_S   = "\u265C";
        private final static String B_QUEEN_S  = "\u265B";
        private final static String B_KING_S   = "\u265A";
        private static String fenPosition; // the string of characters showing the position of the pieces in FEN
        private static char turn;          // 0 = white, 1 = black
        private static String castle;      // 0 = white, 1 = black, 2 = neither can castle
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
        public Game( int[][] board ){
            this.board = board;
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
                case W_PAWN:   System.out.print( W_PAWN_S   + " " ); break; 
                case W_KNIGHT: System.out.print( W_KNIGHT_S + " " ); break;
                case W_BISHOP: System.out.print( W_BISHOP_S + " " ); break;
                case W_ROOK:   System.out.print( W_ROOK_S   + " " ); break;
                case W_QUEEN:  System.out.print( W_QUEEN_S  + " " ); break;
                case W_KING:   System.out.print( W_KING_S   + " " ); break;
                case B_PAWN:   System.out.print( B_PAWN_S   + " " ); break;
                case B_KNIGHT: System.out.print( B_KNIGHT_S + " " ); break;
                case B_BISHOP: System.out.print( B_BISHOP_S + " " ); break;
                case B_ROOK:   System.out.print( B_ROOK_S   + " " ); break;
                case B_QUEEN:  System.out.print( B_QUEEN_S  + " " ); break;
                case B_KING:   System.out.print( B_KING_S   + " " ); break;
                default: System.out.print( "- " ); break;
            } if ( j == 7 ){ System.out.printf( "\n" ); }
            if ( PrintPosition_r( array, i, j + 1 ) == 1 ) { return 1; }
            return PrintPosition_r( array, i + 1, 0 );
        }
        /*
        ====================
        GetPiece
         Get int value of a piece from a FEN string.
        ====================
        */
        public static int GetPiece ( char c ) {
            // Lowercase = black, uppercase = white
            // These have to be reversed to print correctly for some reason for the parser?!?!?
            int piece = 0;
            switch( c ){
                case 'p': piece = W_PAWN;   break;
                case 'n': piece = W_KNIGHT; break;
                case 'b': piece = W_BISHOP; break;
                case 'r': piece = W_ROOK;   break;
                case 'q': piece = W_QUEEN;  break;
                case 'k': piece = W_KING;   break;
                case 'P': piece = B_PAWN;   break;
                case 'N': piece = B_KNIGHT; break;
                case 'B': piece = B_BISHOP; break;
                case 'R': piece = B_ROOK;   break;
                case 'Q': piece = B_QUEEN;  break;
                case 'K': piece = B_KING;   break;
                default: break;
            } return piece;
        }
        /*
        ====================
        getPosition
         Split a FEN string, assign elements to variable and return board position element of the string.
        ====================
        */
        public static String[] getPosition( String fenString ){
            String splitString[] = fenString.split(" ");           
            fenPosition          = splitString[ 0 ];
            turn                 = splitString[ 1 ].charAt(0);
            castle               = splitString[ 2 ];
            enPassant            = splitString[ 3 ];
            halfMove             = Integer.parseInt( splitString[ 4 ]);
            fullMove             = Integer.parseInt( splitString[ 5 ]);
            return fenPosition.split("/");
        }
        /*
        ====================
        FenParser
         Take input data ( raw text ) from FEN ( Forsyth-Edwards Notation ) and 
         builds a two dimensional int array of the board position.
        
         More info on FEN here: https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
         Parser based on https://github.com/njkevlani/FEN-Parser/blob/master/src/Main.java 
        ====================
        */
        public static int[][] FenParser( String fenString ) {
            String temp[] = getPosition( fenString );
            for ( int i = 0; i < 8; i++ ) {
                String str = temp[ i ];
                int k = 0;
                for ( int j = 0; j < str.length(); j++ ) {                   
                    int code = GetPiece( str.charAt( j ));                                                                               
                    if ( code == 0 ) {
                        int num = Integer.parseInt( ""+str.charAt( j ));
                        for ( int x = 0; x < num; x++) {
                            board[ 7-i ][ k ] = 0;
                            k++;
                        }                          
                    } else {                        
                        board[ 7-i ][ k ] = code;
                        k++;
                    }
                }                
            } Collections.reverse( Arrays.asList( board )); // needs to be reversed before returning
            return board;  
        }
        /*
        ====================
        GetInput
         Prompt for user to input using a Scanner object and return as a lowercase String.
        ====================
        */
        public static String GetInput() {
            Scanner input = new Scanner( System.in );
            System.out.print( "> " );
            return input.nextLine().toLowerCase();
        }
        /*
        ====================
        InputLoop
         Simple while loop that repeats wating for user input, this is sort of a 'game' loop.
        ====================
        */
        public static void InputLoop( ){
            while ( true ) {
                String input = GetInput();
                if ( input.equals( "exit" ) || input.equals( "quit" ) || input.equals( "q" )) { break; }
            }
        }
        /*
        ====================
        Main
         Main method to run as a stand alone program and used for testing without a GUI.
        ====================
        */
        public static void main( String[] args ) {
            DEBUG = 1;
            if ( DEBUG == 1 ){
                System.out.print( "DEBUG INFO:\n"   );
                System.out.print( "===========\n\n" );
                //
                // Debug code goes here
                //
                String test = "3k4/p2B2rp/3p4/1p3Q2/3q4/2R3P1/PP3P1P/6K1 w - - 1 33";
                int[][] fenString = FenParser( test );
                PrintPosition_r( fenString, 0, 0 );
                PrintPosition( fenString );
                System.out.println( "\nfenPosition: " + fenPosition );
                System.out.println( "turn: " + turn );
                System.out.println( "castle: " + castle );
                System.out.println( "enPassant: " + enPassant );
                System.out.println( "halfMove: " + halfMove );
                System.out.println( "fullMove: " + fullMove );
                System.out.print( "\n" );
                InputLoop();
                //
                // End debug code
                //
                System.out.print( "\n===========\n\n" );
            }
        }
}

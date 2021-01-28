/*
=========================================
By: Travis Dowd
Date: 1-27-2021

This file is for the core logic of the game, meant to be ran without GUI and to be used in a GUI program. 
This should run the entire game as a TUI program first, then once that is working move on to creating a 
GUI with this class file used as the core game logic.

Notes:
    - Will try to avoid using void methods
    - Use recursion where possible and helpful
    - Using switch/case over multiple if/else when needed
    - Following John Carmack's DOOM 3 code style: https://fabiensanglard.net/fd_proxy/doom3/CodeStyleConventions.pdf
=========================================
 */
package src;

import java.util.*;
import java.lang.*;
import java.io.*;

public class Game {
        private final static int W_PAWN   = 11;             // White Pawn   ** Int values for the pieces >= 10 for white, >= 20 for black
        private final static int W_KNIGHT = 12;             // White Knight
        private final static int W_BISHOP = 13;             // White Bishop
        private final static int W_ROOK   = 14;             // White Rook
        private final static int W_QUEEN  = 15;             // White Queen
        private final static int W_KING   = 16;             // White King
        private final static int B_PAWN   = 21;             // Black Pawn
        private final static int B_KNIGHT = 22;             // Black Knight
        private final static int B_BISHOP = 23;             // Black Bishop
        private final static int B_ROOK   = 24;             // Black Rook
        private final static int B_QUEEN  = 25;             // Black Queen
        private final static int B_KING   = 26;             // Black King         
        private final static String W_PAWN_S   = "\u2659";  // White Pawn   ** ASCII Codes for the chess peices
        private final static String W_KNIGHT_S = "\u2658";  // White Knight
        private final static String W_BISHOP_S = "\u2657";  // White Bishop
        private final static String W_ROOK_S   = "\u2656";  // White Rook
        private final static String W_QUEEN_S  = "\u2655";  // White Queen
        private final static String W_KING_S   = "\u2654";  // White King
        private final static String B_PAWN_S   = "\u265F";  // Black Pawn
        private final static String B_KNIGHT_S = "\u265E";  // Black Knight
        private final static String B_BISHOP_S = "\u265D";  // Black Bishop
        private final static String B_ROOK_S   = "\u265C";  // Back Rook
        private final static String B_QUEEN_S  = "\u265B";  // Black Queen
        private final static String B_KING_S   = "\u265A";  // Black King
        private static String  fenPosition;                 // the string of characters showing the position of the pieces in FEN
        private static char    turn;                        // 0 = white, 1 = black
        private static String  castle;                      // 0 = white, 1 = black, 2 = neither can castle
        private static String  enPassant;                   // which square can be targetd with an En Passant ( ex "e3" )
        private static int     halfMove;                    // Deals with 50-move rule, will most likely not be used in this program 
        private static int     fullMove;                    // Incremented after every one of black's moves, will most likely not be used
        private static String  correctMove;                 // the square for checkmate, for example: "e5"
        private static int     problemNumber;               // Which chess problem rhe user is currently on
        private static Problem problem = new Problem();     // Create a Problem object
        /*
        ====================
        Constructors
         A default empty contructor, and one which takes the board position as an int[][] array.
        ====================
        */
        public Game(){}
        /*
        ====================
        Getters 
        ====================
        */
        public String GetWhitePawn()   { return this.W_PAWN_S; }
        public String GetWhiteKnight() { return this.W_KNIGHT_S; }
        public String GetWhiteBishop() { return this.W_BISHOP_S; }
        public String GetWhiteRook()   { return this.W_ROOK_S; }
        public String GetWhiteQueen()  { return this.W_QUEEN_S; }
        public String GetWhiteKing()   { return this.W_KING_S; }
        public String GetBlackPawn()   { return this.B_PAWN_S; }
        public String GetBlackKnight() { return this.B_KNIGHT_S; }
        public String GetBlackBishop() { return this.B_BISHOP_S; }
        public String GetBlackRook()   { return this.B_ROOK_S; }
        public String GetBlackQueen()  { return this.B_QUEEN_S; }
        public String GetBlackKing()   { return this.B_KING_S; }
        public char GetTurn()          { return this.turn; }
        /*
        ====================
        GetMove
         Turn string for correct move into int[]. 
         *** Not being used *** 
        ====================
        */
        public static int[] GetMove( String input ){
            int rank = 0;
            int file = 0;
            switch ( input.charAt( 0 )) {
                case 'a': file = 1; break;
                case 'b': file = 2; break;
                case 'c': file = 3; break;
                case 'd': file = 4; break;
                case 'e': file = 5; break;
                case 'f': file = 6; break;
                case 'g': file = 7; break;
                case 'h': file = 8; break;
                default: System.out.println( "Please enter a proper value..." ); break;
            } try { rank = ( input.charAt( 1 ) - '0' ); } 
            catch ( StringIndexOutOfBoundsException e ) { e.printStackTrace(); }
            int[] move = new int[ 2 ];
            move[ 0 ] = file;
            move[ 1 ] = rank;
            return move;
        }
        /*
        ====================
        PrintPosition
         Print the int[][] position array as the actual int values, to be use primarily for testing.
        *** Not being used *** 
        ====================
        */
        public static void PrintPosition( int[][] array ){
            for ( int i = 0; i < 8; i++ ) {
                for ( int j = 0; j < 8; j++ ) {
                    System.out.print( array[ i ][ j ] + " " );
                    if ( array[ i ][ j ] < 10 ) { System.out.printf( " " ); }
                    if ( j == 7 ) { System.out.printf( "\n" ); }
                }
            }
        }
        /*
        ====================
        PrintPosition_r
         Recursive version of PrintPosition that prints the array as unicode chess symbols.
        ====================
        */
        public static int PrintPosition_r( int[][] array, int i, int j ){
            if ( j >= 8 ) { return 0; }
            if ( i >= 8 ) { 
                System.out.println( "  -----------------" );
                System.out.println( "    a b c d e f g h" ); 
                return 1; 
            } 
            if ( j == 0 ) { System.out.print( 8 - i + " | "); }
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
            } if ( j == 7 ) { System.out.printf( "\n" ); }
            if ( PrintPosition_r( array, i, j + 1 ) == 1 ) { return 1; }
            return PrintPosition_r( array, i + 1, 0 );
        }
        /*
        ====================
        GetPiece
         Get int value of a piece from a FEN string.
        ====================
        */
        public static int GetPiece( char c ){
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
            } return piece;
        }
        /*
        ====================
        GetPosition
         Split a FEN string, assign elements to variable and return board position element of the string.
        ====================
        */
        public static String[] GetPosition( String fenString ){
            String[] splitString = fenString.split(" ");           
            fenPosition          = splitString[ 0 ];
            turn                 = splitString[ 1 ].charAt( 0 );
            castle               = splitString[ 2 ];
            enPassant            = splitString[ 3 ];
            halfMove             = Integer.parseInt( splitString[ 4 ]);
            fullMove             = Integer.parseInt( splitString[ 5 ]);
            String[] finalString = fenPosition.split( "/" );
            Collections.reverse( Arrays.asList( finalString ));
            return finalString;
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
        public static int[][] FenParser( String fenString ){
            int board[][] = new int[ 8 ][ 8 ]; 
            String strArray[] = GetPosition( fenString );
            for ( int i = 0; i < 8; i++ ) {
                String str = strArray[ i ];
                int k = 0;
                for ( int j = 0; j < str.length(); j++ ) {                   
                    int code = GetPiece( str.charAt( j ));                                                                               
                    if ( code == 0 ) {
                        int num = Integer.parseInt( ""+str.charAt( j ));
                        for ( int x = 0; x < num; x++ ) {
                            board[ 7 - i ][ k ] = 0;
                            k++;
                        }                          
                    } else {                        
                        board[ 7 - i ][ k ] = code;
                        k++;
                    }
                }                
            } return board;  
        }
        /*
        ====================
        GetInput
         Prompt for user to input using a Scanner object and return as a lowercase String.
        ====================
        */
        public static String GetInput(){
            Scanner input = new Scanner( System.in );
            System.out.print( "> " );
            return input.nextLine();
        }
        /*
        ====================
        InputLoop
         Simple while loop that repeats wating for user input, this is sort of a 'game' loop.
        ====================
        */
        public static void InputLoop( String move ){
            while ( true ) {
                String input = GetInput();
                if ( input.equals( "exit" ) || input.equals( "quit" ) || input.equals( "q" )
                        || input.equals( "Exit" ) || input.equals( "Quit" ) || input.equals( "Q" )) { System.exit( 0 ); }
                try {
                    if ( input.equals( move )) {
                        System.out.println( "Correct!\n " );
                        problem.IncrementCurrent();
                        break;
                    } else { System.out.println( "Try again...\n" ); }
                } catch ( NullPointerException e ) { e.printStackTrace(); }
            }
        }
        /*
        ====================
        Main
         Main method to run as a stand alone program and used for testing without a GUI.
        ====================
        */
        public static void main( String[] args ){
            int debug = 1;       
            if ( debug == 1 ) {
                problemNumber = problem.ReadCurrent();
                String position = problem.ReadPosition( problemNumber );
                String correctMove = problem.ReadMove( problemNumber );
                int[][] fenArray = FenParser( position ); 
                System.out.print( "DEBUG INFO:\n"   );
                System.out.print( "===========\n\n" );
                //
                // Debug code goes here
                //  
                while ( problemNumber <= problem.FileSize() ) {
                    position = problem.ReadPosition( problemNumber );
                    correctMove = problem.ReadMove( problemNumber );
                    fenArray = FenParser( position ); 
                    PrintPosition_r( fenArray, 0, 0 );
                    if ( turn == 'b' ) { System.out.print( "\nBack to play."  ); }
                    if ( turn == 'w' ) { System.out.print( "\nWhite to play." ); }
                    System.out.println( " Which move is checkmate?" );
                    System.out.println( "**correctMove: " + correctMove );
                    InputLoop( correctMove );
                    problemNumber++;
                }
                //
                // End debug code
                //
                System.out.print( "\n===========\n\n" );
            }
        }
}

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
        private static String turn;           // 0 = white, 1 = black
        private static String castle;         // 0 = white, 1 = black, 2 = neither can castle
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
                        case W_PAWN:  System.out.print( W_PAWN_S   + " " ); break; 
                        case W_KNIGHT:  System.out.print( W_KNIGHT_S + " " ); break;
                        case W_BISHOP:  System.out.print( W_BISHOP_S + " " ); break;
                        case W_ROOK:  System.out.print( W_ROOK_S   + " " ); break;
                        case W_QUEEN:  System.out.print( W_QUEEN_S  + " " ); break;
                        case W_KING:  System.out.print( W_KING_S   + " " ); break;
                        case B_PAWN:  System.out.print( B_PAWN_S   + " " ); break;
                        case B_KNIGHT:  System.out.print( B_KNIGHT_S + " " ); break;
                        case B_BISHOP:  System.out.print( B_BISHOP_S + " " ); break;
                        case B_ROOK: System.out.print( B_ROOK_S   + " " ); break;
                        case B_QUEEN: System.out.print( B_QUEEN_S  + " " ); break;
                        case B_KING: System.out.print( B_KING_S   + " " ); break;
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
        //public static int[][] FenParser( String fenString ){
            public static void FenParser( String fenString ) {
                int[] startingArray = new int[ 64 ];
                int[][] finalArray = new int[ 8 ][ 8 ];
                fenString = fenString.replaceAll( "/", "" );
                String[] splitString = fenString.split( "\\s+" );
                fenPosition = splitString[ 0 ];
                turn = splitString[ 1 ];
                castle = splitString[ 2 ];
                enPassant = splitString[ 3 ];
                halfMove = Integer.parseInt( splitString[ 4 ] );
                fullMove = Integer.parseInt( splitString[ 5 ] );
                
                // Turn into int[] startingArray
                for ( int i = 0; i < splitString[ 0 ].length(); i++ ) {
                    switch( splitString[ 0 ].charAt( i ) ){
                        // Lowercase = black, uppercase = white
                        case 'P': startingArray[ i ] = W_PAWN;  break;
                        case 'N': startingArray[ i ] = W_KNIGHT;  break;
                        case 'B': startingArray[ i ] = W_BISHOP;  break;
                        case 'R': startingArray[ i ] = W_ROOK;  break;
                        case 'Q': startingArray[ i ] = W_QUEEN;  break;
                        case 'K': startingArray[ i ] = W_KNIGHT;  break;
                        case 'p': startingArray[ i ] = B_PAWN;  break;
                        case 'n': startingArray[ i ] = B_KNIGHT;  break;
                        case 'b': startingArray[ i ] = B_BISHOP;  break;
                        case 'r': startingArray[ i ] = B_ROOK; break;
                        case 'q': startingArray[ i ] = B_QUEEN; break;
                        case 'k': startingArray[ i ] = B_QUEEN; break;
                        default:
                            char temp = splitString[ 0 ].charAt( i );
                            int tempInt = temp - '0'; // -'0' needed here due to ASCII stuff
                            startingArray[ i ] = tempInt;
                            //i += startingArray[ i ];
                            break;
                    } 
                }
                
                //
                // TODO: Turn int[] into int[][] with proper empty space
                //
                int x = 0;
                for ( int i = 0; i < 8; i++ ) {
                    for ( int j = 0; j < 8; j++ ) {
                        finalArray[ i ][ j ] = startingArray[ x++ ];
                    }
                }
                
                // For debugging
                PrintPosition( finalArray );
                //return finalArray;
        }

        /*
        ====================
        Main
         Main method to run as a stand alone program and 
         used for testing without a GUI.
        ====================
        */
        public static void main( String[] args ) {
                DEBUG = 1;
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
                        //
                        // Debug code goes here
                        //
                        FenParser( startingPosition );
                        System.out.println( "\nfenPosition: " + fenPosition );
                        //System.out.println( "turn: " + turn );
                        //System.out.println( "castle: " + castle );
                        //System.out.println( "enPassant: " + enPassant );
                        //System.out.println( "halfMove: " + halfMove );
                        //System.out.println( "fullMove: " + fullMove );
                        
                        //PrintPosition_r( position, 0, 0);
                        //PrintPosition( position );
                        
                        //
                        // End debug code
                        //
                        System.out.print( "\n===========\n\n" );
                }
                
        }
}

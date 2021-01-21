/*
=========================================
By: Travis Dowd
Date: 1-7-2021

This is what will be launched, since it is Main

http://cs.armstrong.edu/liang/intro10e/html/EightQueens.html?
=========================================
 */
package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class Main extends Application {
    public static final int SIZE = 8;
    private static Game game = new Game();
    private static Problem problem = new Problem();

    @Override
    public void start( Stage primaryStage ) throws Exception {
        BorderPane pane = new BorderPane();
        GridPane chessBoard = new GridPane();
        chessBoard.setAlignment( Pos.CENTER );
        //chessBoard.setPadding(new Insets( 25,25,25,25 ));
        
        int problemNumber = problem.ReadCurrent();
        String position = problem.ReadPosition( problemNumber );
        String correctMove = problem.ReadMove( problemNumber );
        int[][] fenArray = game.FenParser( position ); 
        //game.PrintPosition( fenArray );
        
        Label[][] labels = new Label[ SIZE ][ SIZE ];
        for ( int i = 0; i < SIZE; i++ ) {
            for ( int j = 0; j < SIZE; j++ ) {
                chessBoard.add( labels[ i ][ j ] = new Label(), j, i );
                labels[ i ][ j ].setPrefSize( 55, 55 );
                // Black squares
                if ( i % 2 == 0 && j % 2 != 0 ) {
                    labels[ i ][ j ].setStyle( "-fx-border-color: black; -fx-background-color: black" );
                    labels[ i ][ j ] = new Label( "i");
                }
                if ( i % 2 != 0 && j % 2 == 0 ) {
                    labels[ i ][ j ].setStyle( "-fx-border-color: black; -fx-background-color: black" );
                }
                // White squares
                if ( i % 2 == 0 && j % 2 == 0 ) {
                    labels[ i ][ j ].setStyle( "-fx-border-color: black; -fx-background-color: white" );
                }
                if ( i % 2 != 0 && j % 2 != 0 ) {
                    labels[ i ][ j ].setStyle( "-fx-border-color: black; -fx-background-color: white" );
                }
                // Rank and file labels, makes more complex
                // SIZE needs to be 9 which is a handful here
                /*
                if ( i == 8 || j == 8 ) {
                    labels[ i ][ j ].setStyle( "-fx-border-color: white; -fx-background-color: white;" );
                    Text text = new Text( String.valueOf( 8 - i ) );
                    text.setFont( Font.font("Tahoma", FontWeight.NORMAL, 25 ));
                    if ( 8 - i == 0 ) {
                        text = new Text( "" );
                    }
                    switch ( j ) {
                        case 0: text = new Text( "a" ); break;
                        case 1: text = new Text( "b" ); break;
                        case 2: text = new Text( "c" ); break;
                        case 3: text = new Text( "d" ); break;
                        case 4: text = new Text( "e" ); break;
                        case 5: text = new Text( "f" ); break;
                        case 6: text = new Text( "g" ); break;
                        case 7: text = new Text( "h" ); break;
                        //case 8: text = new Text( "" );
                    }
                    
                    text.setFont( Font.font("Tahoma", FontWeight.NORMAL, 25 )); 
                    chessBoard.add( text, j, i );
                }*/
            }    
        }
        // White pieces
        Text whitePawn = new Text( game.GetWhitePawn() );
        whitePawn.setFont( Font.font("Tahoma", FontWeight.NORMAL, 45 ));
        Text whiteKnight = new Text( game.GetWhiteKnight() );
        whiteKnight.setFont( Font.font("Tahoma", FontWeight.NORMAL, 45 ));
        Text whiteBishop = new Text( game.GetWhiteBishop() );
        whiteBishop.setFont( Font.font("Tahoma", FontWeight.NORMAL, 45 ));
        Text whiteRook = new Text( game.GetWhiteRook() );
        whiteRook.setFont( Font.font("Tahoma", FontWeight.NORMAL, 45 ));
        Text whiteQueen = new Text( game.GetWhiteQueen() );
        whiteQueen.setFont( Font.font("Tahoma", FontWeight.NORMAL, 45 ));
        Text whiteKing = new Text( game.GetWhiteKing() );
        whiteKing.setFont( Font.font("Tahoma", FontWeight.NORMAL, 45 ));
        // Black pieces
        Text blackPawn = new Text( game.GetBlackPawn() );
        blackPawn.setFont( Font.font("Tahoma", FontWeight.NORMAL, 45 ));
        Text blackKnight = new Text( game.GetBlackKnight() );
        blackKnight.setFont( Font.font("Tahoma", FontWeight.NORMAL, 45 ));
        Text blackBishop = new Text( game.GetBlackBishop() );
        blackBishop.setFont( Font.font("Tahoma", FontWeight.NORMAL, 45 ));
        Text blackRook = new Text( game.GetBlackRook() );
        blackRook.setFont( Font.font("Tahoma", FontWeight.NORMAL, 45 ));
        Text blackQueen = new Text( game.GetBlackQueen() );
        blackQueen.setFont( Font.font("Tahoma", FontWeight.NORMAL, 45 ));
        Text blackKing = new Text( game.GetBlackKing() );
        blackKing.setFont( Font.font("Tahoma", FontWeight.NORMAL, 45 ));
        
        //chessBoard.add( blackRook, 0, 0 );
        //Group root = new Group();
        for ( int i = 0; i < SIZE; i++ ) {
            for ( int j = 0; j < SIZE; j++ ) {
                if ( fenArray[ i ][ j ] == 11 ) {
                    System.out.print( "11 " );
                }
            }
        }
        //labels[ i ][ j ] = new Label( game.GetWhitePawn() );
        // Scene
        Scene scene = new Scene( pane, 75 * SIZE, 75 * SIZE );
        pane.setCenter( chessBoard );
        primaryStage.setTitle( "Check Yourself" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }

    public static void main( String[] args ){
        launch( args );
    }
}

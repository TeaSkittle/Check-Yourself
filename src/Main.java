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
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class Main extends Application {
    public static final int SIZE = 8;
    private static Game game = new Game();
    private static Problem problem = new Problem();
    
    public static int problemNumber = problem.ReadCurrent();
    public static String position = problem.ReadPosition( problemNumber );
    public static String correctMove = problem.ReadMove( problemNumber );
    public static int[][] fenArray = game.FenParser( position ); 
    public static Label[][] labels = new Label[ SIZE ][ SIZE ];
    
    /*
    ====================
    Start
     Display all the JavaFX stuff
    ====================
    */
    @Override
    public void start( Stage primaryStage ) throws Exception {
        BorderPane pane = new BorderPane();
        GridPane chessBoard = new GridPane();
        TextField textField = new TextField();
        Label textLabel = new Label( "Move: " );
        HBox hbox = new HBox();
        hbox.getChildren().addAll( textLabel, textField );
        hbox.setAlignment( Pos.CENTER );
        hbox.setSpacing( 10 );
        hbox.setPadding( new Insets( 0, 40, 50, 0 ));
        chessBoard.setAlignment( Pos.CENTER );
        for ( int i = 0; i < SIZE; i++ ) {
            for ( int j = 0; j < SIZE; j++ ) {
                chessBoard.add( labels[ i ][ j ] = new Label(), j, i );
                labels[ i ][ j ].setPrefSize( 37, 37 );
                // Black squares
                if ( i % 2 == 0 && j % 2 != 0 ) {
                    labels[ i ][ j ].setStyle( "-fx-border-color: black; -fx-background-color: Peru" );
                }
                if ( i % 2 != 0 && j % 2 == 0 ) {
                    labels[ i ][ j ].setStyle( "-fx-border-color: black; -fx-background-color: Peru" );
                }
                // White squares
                if ( i % 2 == 0 && j % 2 == 0 ) {
                    labels[ i ][ j ].setStyle( "-fx-border-color: black; -fx-background-color: AntiqueWhite" );
                }
                if ( i % 2 != 0 && j % 2 != 0 ) {
                    labels[ i ][ j ].setStyle( "-fx-border-color: black; -fx-background-color: AntiqueWhite" );
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
        } PrintPieces( fenArray );
        Scene scene = new Scene( pane, 60 * SIZE, 60 * SIZE );
        pane.setCenter( chessBoard );
        pane.setBottom( hbox );
        primaryStage.setTitle( "Check Yourself" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }
    /*
    ====================
    PrintPieces
     Print the pieces of the problem to the board, the numbers are inverted for some reason but it works.
    ====================
    */
    private static void PrintPieces ( int[][] arr ) {
        for ( int i = 0; i < SIZE; i++ ) {
            for ( int j = 0; j < SIZE; j++ ) {
                switch ( fenArray[ i ][ j ] ) {
                    case 21: 
                        try {
                            Image image = new Image( new FileInputStream ("resources/whitepawn.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); }
                        break;
                    case 22:
                        try {
                            Image image = new Image( new FileInputStream ("resources/whiteknight.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); }
                        break;
                    case 23:
                        try {
                            Image image = new Image( new FileInputStream ("resources/whitebishop.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); }
                        break;
                    case 24:
                        try {
                            Image image = new Image( new FileInputStream ("resources/whiterook.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); }
                        break;
                    case 25:
                        try {
                            Image image = new Image( new FileInputStream ("resources/whitequeen.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); }
                        break;
                    case 26:
                        try {
                            Image image = new Image( new FileInputStream ("resources/whiteking.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); }
                        break;
                    case 11:
                        try {
                            Image image = new Image( new FileInputStream ("resources/blackpawn.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); }
                        break;
                    case 12:
                        try {
                            Image image = new Image( new FileInputStream ("resources/blackknight.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); }
                        break;
                    case 13:
                        try {
                            Image image = new Image( new FileInputStream ("resources/blackbishop.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); }
                        break;
                    case 14:
                        try {
                            Image image = new Image( new FileInputStream ("resources/blackrook.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); }
                        break;
                    case 15:
                        try {
                            Image image = new Image( new FileInputStream ("resources/blackqueen.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); }
                        break;
                    case 16:
                        try {
                            Image image = new Image( new FileInputStream ("resources/blackking.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); }
                        break;
                }
            }
        }
    }
    /*
    ====================
    Main
    ====================
    */
    public static void main( String[] args ){
        launch( args );
    }
}

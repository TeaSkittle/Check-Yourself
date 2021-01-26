/*
=========================================
By: Travis Dowd
Date: 1-7-2021

This is what will be launched, since it is Main

Todo:
    - Create a start page before game starts
    - Have options to disable/enable music and sounds
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
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.util.Duration;

public class Main extends Application {
    public static final int SIZE = 8;
    private static Game game = new Game();
    private static Problem problem = new Problem();
    public static int problemNumber = problem.ReadCurrent();
    public static String position = problem.ReadPosition( problemNumber );
    public static String correctMove = problem.ReadMove( problemNumber );
    public static int[][] fenArray = game.FenParser( position ); 
    public static Label[][] labels = new Label[ SIZE ][ SIZE ];
    public static GridPane chessBoard = new GridPane();
    public static VBox vbox = new VBox();
    public static Label vboxLabel = new Label( String.valueOf( problemNumber ));
    public static HBox topHbox = new HBox();
    public static Label topHboxLabel = game.GetTurn() == 'w' ? new Label( "White to play" ) : new Label( "Black to play" );
    /*
    ====================
    Start
     Display all the JavaFX stuff
    ====================
    */
    @Override
    public void start( Stage primaryStage ) throws Exception {
        BorderPane pane = new BorderPane();
        TextField textField = new TextField();
        Label textLabel = new Label( "Move: " );
        Button button = new Button( "Submit" );
        HBox hbox = new HBox();
        hbox.getChildren().addAll( textLabel, textField, button );
        hbox.setAlignment( Pos.CENTER );
        hbox.setSpacing( 10 );
        hbox.setPadding( new Insets( 0, 40, 50, 0 ));
        vbox.setPadding( new Insets( 0, -60, 0, 20 ));
        vbox.setAlignment( Pos.CENTER );
        vboxLabel.setFont( new Font( "IBM Plex Sans", 24 ));
        vbox.getChildren().add( vboxLabel );
        topHboxLabel.setFont( new Font( "IBM Plex Sans", 24 ));
        topHbox.setPadding( new Insets( 20, 0, 0, 0 ));
        topHbox.setAlignment( Pos.CENTER );
        topHbox.getChildren().add( topHboxLabel );
        chessBoard.setAlignment( Pos.CENTER );
        PlayMusic();
        PrintBoard();
        PrintPieces( fenArray );
        textField.setOnAction( e -> {
            String inputString = textField.getText();
            textField.clear();
            if ( inputString.equals( correctMove )) {
                UpdateBoard();
            } else {
                Media media = new Media( new File( "resources/piecesound.mp3" ).toURI().toString() );
                MediaPlayer mediaPlayer = new MediaPlayer( media );
                mediaPlayer.setAutoPlay( true );
            }
        }); 
        button.setOnAction( e -> {
            String inputString = textField.getText();
            textField.clear();
            if ( inputString.equals( correctMove )) {
                UpdateBoard();
            } else {
                Media media = new Media( new File( "resources/piecesound.mp3" ).toURI().toString() );
                MediaPlayer mediaPlayer = new MediaPlayer( media );
                mediaPlayer.setAutoPlay( true );
            }
        }); 
        Scene scene = new Scene( pane, 60 * SIZE, 60 * SIZE );
        pane.setCenter( chessBoard );
        pane.setBottom( hbox );
        pane.setLeft( vbox );
        pane.setTop( topHbox );
        primaryStage.setTitle( "Check Yourself" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }
    /*
    ====================
    UpdateBoard
     Clear and reprint the pieces with correct array, reassign values to match new problem, and play sound effect
    ====================
    */
    private static void PlayMusic(){
        Media media = new Media( new File( "resources/music.mp3" ).toURI().toString() );
        MediaPlayer a = new MediaPlayer( media );
        a.setOnEndOfMedia(new Runnable() {
            public void run() {
                a.seek( Duration.ZERO );
            }
        }); a.play();
    }
    /*
    ====================
    UpdateBoard
     Clear and reprint the pieces with correct array, reassign values to match new problem, and play sound effect
    ====================
    */
    private static void UpdateBoard(){
        problem.IncrementCurrent();
        problemNumber = problem.ReadCurrent();
        position = problem.ReadPosition( problemNumber );
        correctMove = problem.ReadMove( problemNumber );
        fenArray = game.FenParser( position );
        chessBoard.getChildren().clear();
        vbox.getChildren().clear();
        vboxLabel = new Label( String.valueOf( problemNumber ));
        vboxLabel.setFont( new Font( "IBM Plex Sans", 24 ));
        vbox.getChildren().add( vboxLabel );
        topHbox.getChildren().clear();
        topHboxLabel = game.GetTurn() == 'w' ? new Label( "White to play" ) : new Label( "Black to play" );
        topHboxLabel.setFont( new Font( "IBM Plex Sans", 24 ));
        topHbox.getChildren().add( topHboxLabel );
        PrintBoard();
        PrintPieces( fenArray );
        Media media = new Media( new File( "resources/checkmate.mp3" ).toURI().toString() );
        MediaPlayer mediaPlayer = new MediaPlayer( media );
        mediaPlayer.setAutoPlay( true );
    }
    /*
    ====================
    PrintBoard
     Print the checkerboard pattern for the background of the pieces
    ====================
    */
    private static void PrintBoard(){
        for ( int i = 0; i < SIZE; i++ ) {
            for ( int j = 0; j < SIZE; j++ ) {
                chessBoard.add( labels[ i ][ j ] = new Label(), j, i );
                labels[ i ][ j ].setPrefSize( 37, 37 );
                if ( i % 2 == 0 && j % 2 != 0 ) {
                    labels[ i ][ j ].setStyle( "-fx-border-color: black; -fx-background-color: Peru" );
                } if ( i % 2 != 0 && j % 2 == 0 ) {
                    labels[ i ][ j ].setStyle( "-fx-border-color: black; -fx-background-color: Peru" );
                } if ( i % 2 == 0 && j % 2 == 0 ) {
                    labels[ i ][ j ].setStyle( "-fx-border-color: black; -fx-background-color: AntiqueWhite" );
                } if ( i % 2 != 0 && j % 2 != 0 ) {
                    labels[ i ][ j ].setStyle( "-fx-border-color: black; -fx-background-color: AntiqueWhite" );
                }
            }    
        }
    }
    /*
    ====================
    PrintPieces
     Print the pieces of the problem to the board, the numbers are inverted for some reason but it works.
    ====================
    */
    private static void PrintPieces( int[][] arr ){
        for ( int i = 0; i < SIZE; i++ ) {
            for ( int j = 0; j < SIZE; j++ ) {
                switch ( fenArray[ i ][ j ] ) {
                    case 21: 
                        try {
                            Image image = new Image( new FileInputStream ("resources/whitepawn.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); } break;
                    case 22:
                        try {
                            Image image = new Image( new FileInputStream ("resources/whiteknight.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); } break;
                    case 23:
                        try {
                            Image image = new Image( new FileInputStream ("resources/whitebishop.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); } break;
                    case 24:
                        try {
                            Image image = new Image( new FileInputStream ("resources/whiterook.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); } break;
                    case 25:
                        try {
                            Image image = new Image( new FileInputStream ("resources/whitequeen.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); } break;
                    case 26:
                        try {
                            Image image = new Image( new FileInputStream ("resources/whiteking.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); } break;
                    case 11:
                        try {
                            Image image = new Image( new FileInputStream ("resources/blackpawn.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); } break;
                    case 12:
                        try {
                            Image image = new Image( new FileInputStream ("resources/blackknight.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); } break;
                    case 13:
                        try {
                            Image image = new Image( new FileInputStream ("resources/blackbishop.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); } break;
                    case 14:
                        try {
                            Image image = new Image( new FileInputStream ("resources/blackrook.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); } break;
                    case 15:
                        try {
                            Image image = new Image( new FileInputStream ("resources/blackqueen.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); } break;
                    case 16: try {
                            Image image = new Image( new FileInputStream ("resources/blackking.png") );
                            labels[ i ][ j ].setGraphic( new ImageView( image ));
                        } catch ( FileNotFoundException ex ) { ex.printStackTrace(); } break;
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

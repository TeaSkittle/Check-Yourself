/*
=========================================
By: Travis Dowd
Date: 1-7-2021

This is what will be launched, since it is Main
=========================================
 */
package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

    @Override
    public void start( Stage primaryStage ) throws Exception {
        BorderPane pane = new BorderPane();
        primaryStage.setTitle( "Check Yourself" );
        primaryStage.setScene( new Scene( pane, 800, 600 ));
        primaryStage.show();
    }

    public static void main( String[] args ) {
        launch( args );
    }
}

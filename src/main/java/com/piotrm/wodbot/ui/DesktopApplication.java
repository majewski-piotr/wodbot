package com.piotrm.wodbot.ui;

import com.piotrm.wodbot.DiscordBotApp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DesktopApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    public static void main (String [] args){
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws InterruptedException, IOException {

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(50);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(25,25,25,25));

        Scene scene = new Scene(gridPane, 800, 200);

        Label discordToken = new Label("Discord bot token:");
        TextField discordTokenField = new TextField();
        Label welcomeMessage = new Label("Welcome message");
        TextField welcomeMessageField = new TextField();

        gridPane.add(discordToken,0,1);
        gridPane.add(discordTokenField,1,1,11,1);
        gridPane.add(welcomeMessage,0,2);
        gridPane.add(welcomeMessageField,1,2,11,1);

        gridPane.setGridLinesVisible(false);

        Button button = new Button("Apply");
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_LEFT);
        hBox.getChildren().add(button);
        gridPane.add(hBox,0,3);

        button.setOnAction(event->System.out.println("clicked"));

        scene.getStylesheets().add(new ClassPathResource("setup.css").getURL().toExternalForm());
        stage.setTitle("Wodbot setup");
        stage.setScene(scene);
        stage.show();
        applicationContext = new SpringApplication(DiscordBotApp.class).run();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}

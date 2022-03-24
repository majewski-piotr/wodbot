package com.piotrm.wodbot.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

public class DiscordTokenSetup extends Application implements Runnable {

    public static final String DISCORD_TOKEN = "DISCORD_TOKEN";
    private final static Properties properties = new Properties();
    private static boolean isRunning = true;

    public static Properties getProperties() {
        return properties;
    }
    public static boolean isRunning(){return isRunning; }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Wodbot app");
        stage.setScene(getSetupScene(stage));
        stage.setOnCloseRequest((event) -> {
            isRunning = false;
            System.exit(0);
        });
        stage.show();
    }

    private Scene getSetupScene(Stage stage) throws IOException {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(50);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(gridPane, 900, 150);

        Label discordToken = new Label("Discord bot token:");
        TextField discordTokenField = new TextField();

        gridPane.add(discordToken, 0, 1);
        gridPane.add(discordTokenField, 1, 1, 12, 1);

        gridPane.setGridLinesVisible(false);

        Button button = new Button("Apply");
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_LEFT);
        hBox.getChildren().add(button);
        gridPane.add(hBox, 0, 2);

        button.setOnAction(event -> {
            properties.setProperty(DISCORD_TOKEN, discordTokenField.getText());
            try {
                stage.setScene(getExitScene());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        });

        scene.getStylesheets().add(new ClassPathResource("setup.css").getURL().toExternalForm());

        return scene;
    }

    private Scene getExitScene() throws IOException {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(50);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(gridPane, 250, 250);

        Button exit = new Button("Close Wodbot");
        exit.setOnAction(event1 -> {
            System.exit(0);
        });

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(exit);
        gridPane.add(hBox, 0, 0);

        scene.getStylesheets().add(new ClassPathResource("setup.css").getURL().toExternalForm());

        return scene;
    }

    @Override
    public void init() throws Exception {
        isRunning = true;
        super.init();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    @Override
    public void run() {
        isRunning = true;
        Application.launch(DiscordTokenSetup.class);
    }
}

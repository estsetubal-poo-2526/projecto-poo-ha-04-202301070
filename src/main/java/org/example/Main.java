package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage stage;
    private Game game;
    private GridPane board;
    private Label statusLabel;
    private Label messageLabel;
    private HBox inventoryBox;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Dungeon Escape");
        showStartScreen();
        stage.show();
    }

    private void showStartScreen() {
        Label titleLabel = createTitleLabel("Dungeon Escape");

        Button playButton = createMenuButton("Play");
        playButton.setOnAction(event -> showGameScreen());

        Button quitButton = createMenuButton("Quit");
        quitButton.setOnAction(event -> Platform.exit());

        VBox menu = new VBox(18, titleLabel, playButton, quitButton);
        menu.setAlignment(Pos.CENTER);

        stage.setScene(createScene(menu));
    }

    private void showGameScreen() {
        game = new Game();

        Label titleLabel = createTitleLabel("Dungeon Escape");

        statusLabel = new Label();
        statusLabel.setTextFill(Color.WHITE);
        statusLabel.setFont(Font.font("Consolas", 15));

        messageLabel = new Label();
        messageLabel.setTextFill(Color.web("#d5dbdb"));
        messageLabel.setFont(Font.font("Consolas", 14));

        board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setHgap(3);
        board.setVgap(3);

        inventoryBox = new HBox(8);
        inventoryBox.setAlignment(Pos.CENTER);

        VBox content = new VBox(16, titleLabel, statusLabel, messageLabel, board, inventoryBox);
        content.setAlignment(Pos.CENTER);

        Scene scene = createScene(content);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                move(0, -1);
            } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                move(0, 1);
            } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                move(-1, 0);
            } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                move(1, 0);
            }
        });

        stage.setScene(scene);
        updateView();
        content.requestFocus();
    }

    private void showEndScreen() {
        String resultText = game.isVictory() ? "Vitoria" : "Perdeste";

        Label resultLabel = createTitleLabel(resultText);

        Button restartButton = createMenuButton("Reiniciar");
        restartButton.setOnAction(event -> showGameScreen());

        Button quitButton = createMenuButton("Quit");
        quitButton.setOnAction(event -> Platform.exit());

        VBox endContent = new VBox(18, resultLabel, restartButton, quitButton);
        endContent.setAlignment(Pos.CENTER);

        stage.setScene(createScene(endContent));
    }

    private Scene createScene(VBox content) {
        StackPane root = new StackPane(content);
        root.setPadding(new Insets(24));
        root.setStyle("-fx-background-color: black;");
        return new Scene(root, 720, 560);
    }

    private Label createTitleLabel(String text) {
        Label titleLabel = new Label(text);
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Consolas", 42));
        return titleLabel;
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setMinWidth(160);
        button.setFocusTraversable(false);
        button.setStyle("-fx-background-color: #f4d03f; -fx-text-fill: black; -fx-font-weight: bold;");
        return button;
    }

    private void move(int dx, int dy) {
        game.movePlayer(dx, dy);
        updateView();
        showEndScreenIfNeeded();
    }

    private void updateView() {
        updateStatus();
        updateBoard();
        updateInventory();
    }

    private void showEndScreenIfNeeded() {
        if (game.isFinished()) {
            showEndScreen();
        }
    }

    private void updateStatus() {
        Player player = game.getPlayer();
        statusLabel.setText(
                "Vida: " + player.getHitPoints()
                        + " | Dano: " + player.getAttackDamage()
                        + " | Posicao: (" + player.getX() + ", " + player.getY() + ")"
                        + " | Sala: (" + game.getCurrentRoomX() + ", " + game.getCurrentRoomY() + ")"
                        + " | Chave: " + (player.isKeyUsed() ? "Usada" : "Nao usada")
        );
        messageLabel.setText(game.getMessage());
    }

    private void updateBoard() {
        board.getChildren().clear();
        Player player = game.getPlayer();

        for (int y = 0; y < Game.ROOM_HEIGHT; y++) {
            for (int x = 0; x < Game.ROOM_WIDTH; x++) {
                Label cellText = new Label(getCellText(player, x, y));
                cellText.setTextFill(Color.WHITE);
                cellText.setFont(Font.font("Consolas", 22));

                StackPane cell = new StackPane(cellText);
                cell.setPrefSize(56, 56);
                cell.setStyle(getCellStyle(player, x, y));
                board.add(cell, x, y);
            }
        }
    }

    private String getCellText(Player player, int x, int y) {
        if (player.getX() == x && player.getY() == y) {
            return player.getSymbol();
        }

        RoomItem roomItem = game.getCurrentRoom().getItemAt(x, y);
        if (roomItem != null) {
            return roomItem.getItem().getSymbol();
        }

        Enemy enemy = game.getCurrentRoom().getEnemyAt(x, y);
        if (enemy != null) {
            return enemy.getSymbol();
        }

        if (game.isExitTile(x, y)) {
            return game.isExitUnlocked() ? "S" : "X";
        }

        return "";
    }

    private String getCellStyle(Player player, int x, int y) {
        if (player.getX() == x && player.getY() == y) {
            return "-fx-background-color: #1f618d; -fx-border-color: white; -fx-border-width: 1;";
        }

        if (game.getCurrentRoom().getItemAt(x, y) != null) {
            return "-fx-background-color: #7d6608; -fx-border-color: #f4d03f; -fx-border-width: 1;";
        }

        if (game.getCurrentRoom().getEnemyAt(x, y) != null) {
            return "-fx-background-color: #641e16; -fx-border-color: #e74c3c; -fx-border-width: 1;";
        }

        if (game.isExitTile(x, y)) {
            if (game.isExitUnlocked()) {
                return "-fx-background-color: #196f3d; -fx-border-color: #2ecc71; -fx-border-width: 1;";
            }

            return "-fx-background-color: #424949; -fx-border-color: #95a5a6; -fx-border-width: 1;";
        }

        return "-fx-background-color: #111111; -fx-border-color: #444444; -fx-border-width: 1;";
    }

    private void updateInventory() {
        inventoryBox.getChildren().clear();

        if (game.getPlayer().getInventory().isEmpty()) {
            Label emptyInventoryLabel = new Label("Inventario vazio");
            emptyInventoryLabel.setTextFill(Color.LIGHTGRAY);
            emptyInventoryLabel.setFont(Font.font("Consolas", 14));
            inventoryBox.getChildren().add(emptyInventoryLabel);
            return;
        }

        for (int i = 0; i < game.getPlayer().getInventory().size(); i++) {
            Item item = game.getPlayer().getInventory().get(i);
            Button itemButton = createMenuButton("Usar " + item.getName());

            int itemIndex = i;
            itemButton.setOnAction(event -> {
                game.useItem(itemIndex);
                updateView();
                showEndScreenIfNeeded();
            });

            inventoryBox.getChildren().add(itemButton);
        }
    }
}

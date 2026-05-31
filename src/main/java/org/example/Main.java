package org.example;

import javafx.application.Application;
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
    private Game game;
    private GridPane board;
    private Label statusLabel;
    private Label messageLabel;
    private HBox inventoryBox;

    @Override
    public void start(Stage stage) {
        game = new Game();

        Label titleLabel = new Label("Dungeon Escape");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Consolas", 42));

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

        StackPane root = new StackPane(content);
        root.setPadding(new Insets(24));
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, 720, 560);
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

        stage.setTitle("Dungeon Escape");
        stage.setScene(scene);
        stage.show();

        updateView();
        root.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void move(int dx, int dy) {
        game.movePlayer(dx, dy);
        updateView();
    }

    private void updateView() {
        updateStatus();
        updateBoard();
        updateInventory();
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
            Button itemButton = new Button("Usar " + item.getName());
            itemButton.setFocusTraversable(false);
            itemButton.setStyle("-fx-background-color: #f4d03f; -fx-text-fill: black; -fx-font-weight: bold;");

            int itemIndex = i;
            itemButton.setOnAction(event -> {
                game.useItem(itemIndex);
                updateView();
            });

            inventoryBox.getChildren().add(itemButton);
        }
    }
}

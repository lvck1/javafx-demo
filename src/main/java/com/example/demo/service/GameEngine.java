package com.example.demo.service;

import com.example.demo.model.Food;
import com.example.demo.model.Snake;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameEngine {

    public enum Difficulty {
        EASY("初级", 150, true),
        MEDIUM("中级", 100, false),
        HARD("高级", 60, false);

        private final String name;
        private final long interval;
        private final boolean wallPass;

        Difficulty(String name, long interval, boolean wallPass) {
            this.name = name;
            this.interval = interval;
            this.wallPass = wallPass;
        }

        public String getName() { return name; }
        public long getInterval() { return interval; }
        public boolean isWallPass() { return wallPass; }
    }

    private Snake snake;
    private Food food;
    private Difficulty difficulty;
    private int boardWidth;
    private int boardHeight;
    private int score;
    private boolean gameOver;
    private boolean paused;
    private boolean autoMode;
    private AnimationTimer gameLoop;
    private long lastUpdate;
    private GameOverCallback gameOverCallback;

    public interface GameOverCallback {
        void onGameOver(int score);
    }

    public GameEngine(int boardWidth, int boardHeight, Difficulty difficulty) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.difficulty = difficulty;
        this.score = 0;
        this.gameOver = false;
        this.paused = false;
        initGame();
    }

    private void initGame() {
        snake = new Snake(boardWidth / 2, boardHeight / 2);
        food = new Food(boardWidth, boardHeight);
        score = 0;
        gameOver = false;
        paused = false;
        autoMode = false;
        lastUpdate = 0;
    }

    public void start(GraphicsContext gc) {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= difficulty.getInterval() * 1_000_000) {
                    if (!paused && !gameOver) {
                        update();
                        render(gc);
                    }
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }

    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    private void update() {
        if (autoMode) {
            autoMoveTowardsFood();
        }
        snake.move();
        checkWallCollision();
        checkFoodCollision();
        checkSelfCollision();
    }

    private void autoMoveTowardsFood() {
        int[] head = snake.getHead();
        int foodX = food.getX();
        int foodY = food.getY();

        Snake.Direction currentDir = snake.getDirection();
        Snake.Direction newDir = currentDir;

        if (foodX > head[0] && currentDir != Snake.Direction.LEFT) {
            newDir = Snake.Direction.RIGHT;
        } else if (foodX < head[0] && currentDir != Snake.Direction.RIGHT) {
            newDir = Snake.Direction.LEFT;
        } else if (foodY > head[1] && currentDir != Snake.Direction.UP) {
            newDir = Snake.Direction.DOWN;
        } else if (foodY < head[1] && currentDir != Snake.Direction.DOWN) {
            newDir = Snake.Direction.UP;
        }

        snake.setDirection(newDir);
    }

    public void toggleAutoMode() {
        autoMode = !autoMode;
    }

    public boolean isAutoMode() {
        return autoMode;
    }

    private void checkWallCollision() {
        int[] head = snake.getHead();
        int snakeSize = Snake.getSize();

        if (difficulty.isWallPass()) {
            if (head[0] < 0) {
                head[0] = boardWidth - snakeSize;
            } else if (head[0] >= boardWidth) {
                head[0] = 0;
            }
            if (head[1] < 0) {
                head[1] = boardHeight - snakeSize;
            } else if (head[1] >= boardHeight) {
                head[1] = 0;
            }
        } else {
            if (head[0] < 0 || head[0] >= boardWidth || head[1] < 0 || head[1] >= boardHeight) {
                setGameOver();
            }
        }
    }

    private void checkFoodCollision() {
        if (food.isEaten(snake.getHead())) {
            snake.grow();
            score += 10;
            food.generatePosition(boardWidth, boardHeight);
        }
    }

    private void checkSelfCollision() {
        if (snake.checkSelfCollision()) {
            setGameOver();
        }
    }

    private void setGameOver() {
        gameOver = true;
        if (gameOverCallback != null) {
            gameOverCallback.onGameOver(score);
        }
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, boardWidth, boardHeight);

        gc.setFill(Color.DARKGRAY);
        for (int x = 0; x < boardWidth; x += Snake.getSize()) {
            for (int y = 0; y < boardHeight; y += Snake.getSize()) {
                gc.strokeRect(x, y, Snake.getSize(), Snake.getSize());
            }
        }

        gc.setFill(Color.LIME);
        for (int[] segment : snake.getBody()) {
            gc.fillRect(segment[0] + 1, segment[1] + 1, Snake.getSize() - 2, Snake.getSize() - 2);
        }

        gc.setFill(Color.RED);
        gc.fillOval(food.getX() + 1, food.getY() + 1, Food.getSize() - 2, Food.getSize() - 2);

        if (autoMode) {
            gc.setFill(Color.GOLD);
            gc.setFont(Font.font(18));
            gc.fillText("幻神模式", boardWidth - 120, 25);
        }

        if (gameOver) {
            gc.setFill(Color.rgb(0, 0, 0, 0.7));
            gc.fillRect(0, 0, boardWidth, boardHeight);
            gc.setFill(Color.RED);
            gc.setFont(Font.font(50));
            gc.fillText("游戏结束", boardWidth / 2 - 100, boardHeight / 2 - 20);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(30));
            gc.fillText("最终分数: " + score, boardWidth / 2 - 80, boardHeight / 2 + 30);
        }

        if (paused && !gameOver) {
            gc.setFill(Color.rgb(0, 0, 0, 0.7));
            gc.fillRect(0, 0, boardWidth, boardHeight);
            gc.setFill(Color.YELLOW);
            gc.setFont(Font.font(50));
            gc.fillText("暂停", boardWidth / 2 - 50, boardHeight / 2);
        }
    }

    public void setSnakeDirection(Snake.Direction direction) {
        snake.setDirection(direction);
    }

    public void togglePause() {
        paused = !paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public void setGameOverCallback(GameOverCallback callback) {
        this.gameOverCallback = callback;
    }

    public void reset() {
        stop();
        initGame();
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}

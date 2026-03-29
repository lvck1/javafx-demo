package com.example.demo.model;

import java.util.LinkedList;
import java.util.Random;

public class Food {

    private int x;
    private int y;
    private static final int SIZE = 20;
    private Random random;

    public Food(int boardWidth, int boardHeight) {
        random = new Random();
        generatePosition(boardWidth, boardHeight);
    }

    public void generatePosition(int boardWidth, int boardHeight) {
        int maxX = boardWidth / SIZE;
        int maxY = boardHeight / SIZE;
        x = random.nextInt(maxX) * SIZE;
        y = random.nextInt(maxY) * SIZE;
    }

    public void generatePosition(int boardWidth, int boardHeight, LinkedList<int[]> snakeBody) {
        int maxX = boardWidth / SIZE;
        int maxY = boardHeight / SIZE;
        
        boolean validPosition = false;
        while (!validPosition) {
            x = random.nextInt(maxX) * SIZE;
            y = random.nextInt(maxY) * SIZE;
            
            validPosition = true;
            for (int[] segment : snakeBody) {
                if (segment[0] == x && segment[1] == y) {
                    validPosition = false;
                    break;
                }
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int getSize() {
        return SIZE;
    }

    public boolean isEaten(int[] snakeHead) {
        return snakeHead[0] == x && snakeHead[1] == y;
    }
}

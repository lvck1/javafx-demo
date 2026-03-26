package com.example.demo.model;

import javafx.scene.paint.Color;
import java.util.LinkedList;

public class Snake {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private LinkedList<int[]> body;
    private Direction direction;
    private Direction nextDirection;
    private static final int SIZE = 20;

    public Snake(int startX, int startY) {
        body = new LinkedList<>();
        body.add(new int[]{startX, startY});
        body.add(new int[]{startX - SIZE, startY});
        body.add(new int[]{startX - 2 * SIZE, startY});
        direction = Direction.RIGHT;
        nextDirection = Direction.RIGHT;
    }

    public LinkedList<int[]> getBody() {
        return body;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if ((this.direction == Direction.UP && direction != Direction.DOWN) ||
            (this.direction == Direction.DOWN && direction != Direction.UP) ||
            (this.direction == Direction.LEFT && direction != Direction.RIGHT) ||
            (this.direction == Direction.RIGHT && direction != Direction.LEFT)) {
            this.nextDirection = direction;
        }
    }

    public int[] getHead() {
        return body.getFirst();
    }

    public void move() {
        direction = nextDirection;
        int[] head = getHead();
        int newX = head[0];
        int newY = head[1];

        switch (direction) {
            case UP -> newY -= SIZE;
            case DOWN -> newY += SIZE;
            case LEFT -> newX -= SIZE;
            case RIGHT -> newX += SIZE;
        }

        body.addFirst(new int[]{newX, newY});
        body.removeLast();
    }

    public void grow() {
        direction = nextDirection;
        int[] head = getHead();
        int newX = head[0];
        int newY = head[1];

        switch (direction) {
            case UP -> newY -= SIZE;
            case DOWN -> newY += SIZE;
            case LEFT -> newX -= SIZE;
            case RIGHT -> newX += SIZE;
        }

        body.addFirst(new int[]{newX, newY});
    }

    public boolean checkSelfCollision() {
        int[] head = getHead();
        for (int i = 1; i < body.size(); i++) {
            int[] segment = body.get(i);
            if (head[0] == segment[0] && head[1] == segment[1]) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return body.size();
    }

    public static int getSize() {
        return SIZE;
    }
}

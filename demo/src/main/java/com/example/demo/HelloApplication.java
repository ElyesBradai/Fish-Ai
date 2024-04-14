package com.example.demo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.PriorityQueue;

public class HelloApplication extends Application {
    private int[][] map = {
            {2, 0, 1, 0, 0, 0},
            {0, 1, 1, 0, 1, 0},
            {0, 0, 0, 0, 1, 0},
            {0, 1, 1, 0, 1, 0},
            {0, 0, 0, 0, 1, 1},
            {0, 1, 1, 3, 0, 0}
    }; // 0 represents empty, 1 represents obstacle, 2 represents charge, 3 represents finish line

    private int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        int size = map.length;
        Rectangle[][] rectangles = new Rectangle[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                rectangles[i][j] = new Rectangle(50, 50, getColor(map[i][j]));
                gridPane.add(rectangles[i][j], j, i);
            }
        }

        Scene scene = new Scene(gridPane, size * 50, size * 50);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Charge Pathfinding");
        primaryStage.show();

        findShortestPath(rectangles);
    }

    private Color getColor(int value) {
        switch (value) {
            case 0:
                return Color.WHITE; // Empty
            case 1:
                return Color.BLACK; // Obstacle
            case 2:
                return Color.BLUE; // Charge
            case 3:
                return Color.GREEN; // Finish line
            default:
                return Color.WHITE;
        }
    }

    private void findShortestPath(Rectangle[][] rectangles) {
        int[][] distance = new int[map.length][map.length];
        for (int[] row : distance)
            Arrays.fill(row, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]); // {row, col, distance}

        int[] start = findStart();
        pq.offer(new int[]{start[0], start[1], 0});
        distance[start[0]][start[1]] = 0;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int row = current[0];
            int col = current[1];
            int dist = current[2];

            if (map[row][col] == 3) // Reached finish line
                break;

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                if (isValid(newRow, newCol) && map[newRow][newCol] != 1) {
                    int newDist = dist + 1;
                    if (newDist < distance[newRow][newCol]) {
                        distance[newRow][newCol] = newDist;
                        pq.offer(new int[]{newRow, newCol, newDist});
                    }
                }
            }
        }

        // Highlight shortest path
        int[] current = findFinish();
        int steps = distance[current[0]][current[1]];
        while (steps > 0) {
            for (int[] dir : directions) {
                int newRow = current[0] + dir[0];
                int newCol = current[1] + dir[1];
                if (isValid(newRow, newCol) && distance[newRow][newCol] == steps - 1) {
                    rectangles[newRow][newCol].setFill(Color.YELLOW);
                    current = new int[]{newRow, newCol};
                    steps--;
                    break;
                }
            }
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < map.length && col >= 0 && col < map.length;
    }

    private int[] findStart() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == 2) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; // Not found
    }

    private int[] findFinish() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == 3) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; // Not found
    }

    public static void main(String[] args) {
        launch(args);
    }
}

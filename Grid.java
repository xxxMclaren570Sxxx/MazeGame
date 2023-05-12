package Main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Grid {
    private static final int TILE_SIZE = 2;
    private int[][] outputGrid;
    private int[][][] tileSet;
    private boolean[][][] validDirections;
    private int gridSize;
    private Random random;
   public int startX;
   public int startY;

    public Grid(int gridSize) {
        this.gridSize = gridSize;
        this.random = new Random();

        // Create the tile set with all possible tile configurations
        createTileSet();

        // Initialize the output grid with -1
        this.outputGrid = new int[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            Arrays.fill(outputGrid[i], -1);
        }

        // Initialize the valid directions for each tile
        this.validDirections = new boolean[tileSet.length][4][4];
        for (int i = 0; i < tileSet.length; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    int dx = k - 1;
                    int dy = j - 1;
                    validDirections[i][j][k] = isValidDirection(tileSet[i], dx, dy);
                }
            }
        }
    }

    private void createTileSet() {
        List<int[][]> tileList = new ArrayList<>();

        // Create all possible tile configurations
        for (int i = 0; i < 16; i++) {
            int[][] tile = new int[TILE_SIZE][TILE_SIZE];
            for (int j = 0; j < TILE_SIZE; j++) {
                for (int k = 0; k < TILE_SIZE; k++) {
                    tile[j][k] = (i >> (j * TILE_SIZE + k)) & 1;
                }
            }
            tileList.add(tile);
        }

        // Convert the tile list to an array
        this.tileSet = tileList.toArray(new int[0][0][0]);
    }

    private boolean isValidDirection(int[][] tile, int dx, int dy) {
        int x = TILE_SIZE / 2 + dx;
        int y = TILE_SIZE / 2 + dy;
        if (x < 0 || x >= TILE_SIZE || y < 0 || y >= TILE_SIZE) {
            return false;
        }
        return tile[TILE_SIZE / 2][TILE_SIZE / 2] == tile[y][x];
    }

    public int[][] generateMaze() {
        // Choose a random tile to start with
         startX = random.nextInt(gridSize - TILE_SIZE + 1);
         startY = random.nextInt(gridSize - TILE_SIZE + 1);
        int[][] startTile = tileSet[random.nextInt(tileSet.length)];
        outputGrid[startX][startY] = random.nextInt(4);

        // Apply the WFC algorithm to generate the maze
        while (true) {
            // Find the tile with the smallest entropy
            int[] tileIndices = findLowestEntropyTile();

            // If no tile has an entropy of 0, choose one randomly
            if (tileIndices == null) {
                tileIndices = chooseRandomTile();
            }

            // If all tiles have been exhausted, return the output grid
            if (tileIndices == null) {
                return outputGrid;
            }

            int tileIndex = tileIndices[0];
            int rotation = tileIndices[1];
            // Choose a random direction for the tile
            int direction = chooseRandomDirection(tileIndex);

            // Try to place the tile in the output grid
            if (!placeTile(tileIndex, rotation, direction)) {
                // If it can't be placed, remove it from the tile set
                removeTile(tileIndex, rotation);
                continue;
            }

            // If a tile has been successfully placed, update the valid directions of adjacent tiles
            updateValidDirections(direction, startX, startY);
        }
    }

    private int[] findLowestEntropyTile() {
        int[] tileIndices = null;
        double lowestEntropy = Double.POSITIVE_INFINITY;

        // Find the tile with the lowest entropy
        for (int i = 0; i < tileSet.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (!hasValidDirections(i, j)) {
                    continue;
                }

                double entropy = getTileEntropy(i, j);
                if (entropy == 0) {
                    return new int[] {i, j};
                }

                if (entropy < lowestEntropy) {
                    lowestEntropy = entropy;
                    tileIndices = new int[] {i, j};
                }
            }
        }

        return tileIndices;
    }

    private int[] chooseRandomTile() {
        List<int[]> validTiles = new ArrayList<>();

        // Find all tiles with valid directions
        for (int i = 0; i < tileSet.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (hasValidDirections(i, j)) {
                    validTiles.add(new int[] {i, j});
                }
            }
        }

        // Choose a random tile from the list
        if (validTiles.isEmpty()) {
            return null;
        } else {
            return validTiles.get(random.nextInt(validTiles.size()));
        }
    }

    private boolean hasValidDirections(int tileIndex, int rotation) {
        for (int i = 0; i < 4; i++) {
            if (validDirections[tileIndex][(i + rotation) % 4][i]) {
                return true;
            }
        }
        return false;
    }

    private double getTileEntropy(int tileIndex, int rotation) {
        double entropy = 0;
        for (int i = 0; i < 4; i++) {
            if (validDirections[tileIndex][(i + rotation) % 4][i]) {
                entropy++;
            }
        }
        return entropy;
    }

    private int chooseRandomDirection(int tileIndex) {
        int[] validDirections = new int[4];
        int numValidDirections = 0;
        for (int i = 0; i < 4; i++) {
            if (this.validDirections[tileIndex][outputGrid[startX][startY]][i]) {
                validDirections[numValidDirections++] = i;
            }
        }
        return validDirections[random.nextInt(numValidDirections)];
    }

    private boolean placeTile(int tileIndex, int rotation, int direction) {
        int dx = 0;
        int dy = 0;

        switch (direction) {
            case 0:
                dy = -TILE_SIZE;
                break;
            case 1:
                dx = TILE_SIZE;
                break;
            case 2:
                dy = TILE_SIZE;
                break;
            case 3:
                dx = -TILE_SIZE;
                break;
        }

        int[][] tile = tileSet[tileIndex];
        int x = startX + dx;
        int y = startY + dy;

        // Check if the tile can be
        // placed in the output grid without overlapping existing tiles
        for (int i = 0; i < TILE_SIZE; i++) {
            for (int j = 0; j < TILE_SIZE; j++) {
                if (x + j < 0 || x + j >= outputGrid.length || y + i < 0 || y + i >= outputGrid[0].length) {
                    return false;
                }

                if (tile[i][j] != 0 && outputGrid[x + j][y + i] != 0) {
                    return false;
                }
            }
        }

        // Place the tile in the output grid
        for (int i = 0; i < TILE_SIZE; i++) {
            for (int j = 0; j < TILE_SIZE; j++) {
                if (tile[i][j] != 0) {
                    outputGrid[x + j][y + i] = tile[i][j];
                }
            }
        }

        // Update the starting coordinates for the next tile
        startX = x;
        startY = y;

        return true;
    }

    private void removeTile(int tileIndex, int rotation) {
        int dx = 0;
        int dy = 0;

        switch (outputGrid[startX][startY]) {
            case 0:
                dy = -TILE_SIZE;
                break;
            case 1:
                dx = TILE_SIZE;
                break;
            case 2:
                dy = TILE_SIZE;
                break;
            case 3:
                dx = -TILE_SIZE;
                break;
        }

        int[][] tile = tileSet[tileIndex];
        int x = startX + dx;
        int y = startY + dy;

        // Remove the tile from the output grid
        for (int i = 0; i < TILE_SIZE; i++) {
            for (int j = 0; j < TILE_SIZE; j++) {
                if (tile[i][j] != 0) {
                    outputGrid[x + j][y + i] = 0;
                }
            }
        }
    }

    private void updateValidDirections(int direction, int startX, int startY) {
        if (startX > 0) {
            validDirections[outputGrid[startX - 1][startY]][1][(direction + 2) % 4] = true;
        }
        if (startX + TILE_SIZE < outputGrid.length) {
            validDirections[outputGrid[startX + TILE_SIZE][startY]][3][(direction + 2) % 4] = true;
        }
        if (startY > 0) {
            validDirections[outputGrid[startX][startY - 1]][2][(direction + 2) % 4] = true;
        }
        if (startY + TILE_SIZE < outputGrid[0].length) {
            validDirections[outputGrid[startX][startY + TILE_SIZE]][0][(direction + 2) % 4] = true;
        }
    }

    public int[][] getOutputGrid() {
        return outputGrid;
    }
    
    public static void main(String[] args) {
		Grid grid = new Grid(100);
		grid.
	}
}

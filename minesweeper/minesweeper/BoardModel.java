import java.util.Random;

public class BoardModel {
    private final int rows, cols, mines;
    private final Cell[][] grid;
    private boolean firstClickSafeDone;
    private boolean exploded;
    private int flagsPlaced;
    private int revealedSafe;

    public BoardModel(int r, int c, int m, Cell[][] cells) {
        rows = r; cols = c; mines = m;
        grid = cells;
        firstClickSafeDone = false;
        exploded = false;
        flagsPlaced = 0;
        revealedSafe = 0;
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getMines() { return mines; }
    public int getFlagsPlaced() { return flagsPlaced; }
    public boolean isExploded() { return exploded; }

    public boolean inBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public void toggleFlag(int r, int c) {
        Cell cell = grid[r][c];
        if (cell.isRevealed()) return;
        boolean nowFlag = !cell.isFlagged();
        cell.setFlagged(nowFlag);
        flagsPlaced += nowFlag ? 1 : -1;
    }

    public void reveal(int r, int c) {
        Cell cell = grid[r][c];
        if (cell.isRevealed() || cell.isFlagged()) return;

        if (!firstClickSafeDone) {
            placeMinesAvoiding(r, c);
            computeAdjacency();
            firstClickSafeDone = true;
        }

        cell.reveal(true);
        if (cell.isMine()) {
            exploded = true;
            revealAllMines();
            return;
        }
        revealedSafe++;
        if (cell.getAdjacent() == 0) {
            floodReveal(r, c);
        }
    }

    private void placeMinesAvoiding(int safeR, int safeC) {
        Random rand = new Random();
        int placed = 0;
        while (placed < mines) {
            int rr = rand.nextInt(rows);
            int cc = rand.nextInt(cols);
            Cell cell = grid[rr][cc];
            if (cell.isMine()) continue;
            if (rr == safeR && cc == safeC) continue;
            cell.setMine(true);
            placed++;
        }
    }

    private void computeAdjacency() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c].isMine()) {
                    grid[r][c].setAdjacent(0);
                    continue;
                }
                int count = 0;
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        if (dr == 0 && dc == 0) continue;
                        int nr = r + dr, nc = c + dc;
                        if (inBounds(nr, nc) && grid[nr][nc].isMine()) count++;
                    }
                }
                grid[r][c].setAdjacent(count);
            }
        }
    }

    private void floodReveal(int r, int c) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nr = r + dr, nc = c + dc;
                if (!inBounds(nr, nc)) continue;
                Cell n = grid[nr][nc];
                if (n.isRevealed() || n.isFlagged() || n.isMine()) continue;
                n.reveal(true);
                revealedSafe++;
                if (n.getAdjacent() == 0) floodReveal(nr, nc);
            }
        }
    }

    public boolean isWin() {
        int totalSafe = rows * cols - mines;
        return !exploded && firstClickSafeDone && revealedSafe == totalSafe;
    }

    public void revealAllMines() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c].isMine()) grid[r][c].reveal(true);
                else grid[r][c].updateAppearance(true);
            }
        }
    }

    public void reset() {
        exploded = false;
        firstClickSafeDone = false;
        flagsPlaced = 0;
        revealedSafe = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c].reset();
            }
        }
    }
}

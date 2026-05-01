import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MinesweeperGUI extends JPanel {
    private final int rows;
    private final int cols;
    private final int mines;
    private final Cell[][] grid;
    private final BoardModel model;

    private JLabel mineLabel;
    private JLabel timerLabel;
    private JButton resetButton;
    private Timer timer;
    private int seconds;

    public MinesweeperGUI(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.grid = new Cell[rows][cols];
        this.model = new BoardModel(rows, cols, mines, grid);
        setLayout(new BorderLayout());
        buildUI();
        setupTimer();
    }

    private void buildUI() {
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));
        mineLabel = new JLabel("Minen: " + mines);
        mineLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        timerLabel = new JLabel("Zeit: 0");
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        resetButton = new JButton("Neu");
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(e -> resetGame());
        topBar.add(mineLabel);
        topBar.add(resetButton);
        topBar.add(timerLabel);

        JPanel boardPanel = new JPanel(new GridLayout(rows, cols, 2, 2));
        boardPanel.setBackground(new Color(0xDADCE0));
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = new Cell();
                final int rr = r, cc = c;
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            handleReveal(rr, cc);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            handleFlag(rr, cc);
                        }
                    }
                });
                grid[r][c] = cell;
                boardPanel.add(cell);
            }
        }

        add(topBar, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(cols * 36 + 40, rows * 36 + 80));
    }

    private void setupTimer() {
        seconds = 0;
        timer = new Timer(1000, e -> {
            seconds++;
            timerLabel.setText("Zeit: " + seconds);
        });
    }

    private void startTimerIfNeeded() {
        if (!timer.isRunning()) timer.start();
    }

    private void stopTimer() {
        if (timer.isRunning()) timer.stop();
    }

    private void handleReveal(int r, int c) {
        if (model.isExploded() || model.isWin()) return;
        startTimerIfNeeded();
        model.reveal(r, c);
        mineLabel.setText("Minen: " + (mines - model.getFlagsPlaced()));
        if (model.isExploded()) {
            stopTimer();
            JOptionPane.showMessageDialog(this, "Boom! Du hast eine Mine getroffen.", "Verloren", JOptionPane.WARNING_MESSAGE);
        } else if (model.isWin()) {
            stopTimer();
            JOptionPane.showMessageDialog(this, "Gewonnen! Alle sicheren Felder aufgedeckt.", "Gewonnen", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleFlag(int r, int c) {
        if (model.isExploded() || model.isWin()) return;
        model.toggleFlag(r, c);
        mineLabel.setText("Minen: " + (mines - model.getFlagsPlaced()));
    }

    private void resetGame() {
        stopTimer();
        seconds = 0;
        timerLabel.setText("Zeit: 0");
        mineLabel.setText("Minen: " + mines);
        model.reset();
        revalidate();
        repaint();
    }
}

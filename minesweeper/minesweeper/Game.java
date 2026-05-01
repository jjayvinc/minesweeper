import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] levels = {"Einfach (9x9, 10)", "Mittel (16x16, 40)", "Schwer (16x30, 99)"};
            int choice = JOptionPane.showOptionDialog(null, "Schwierigkeitsgrad wählen",
                    "Minesweeper", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, levels, levels[0]);
            int rows = 9, cols = 9, mines = 10;
            if (choice == 1) { rows = 16; cols = 16; mines = 40; }
            else if (choice == 2) { rows = 16; cols = 30; mines = 99; }

            JFrame f = new JFrame("Minesweeper");
            MinesweeperGUI gui = new MinesweeperGUI(rows, cols, mines);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(gui);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}

import javax.swing.*;
import java.awt.*;

public class Cell extends JButton {
    private boolean mine;
    private boolean revealed;
    private boolean flagged;
    private int adjacent;

    private static final Color COVERED_BG = new Color(0xC0, 0xCF, 0xD8);
    private static final Color REVEALED_BG = new Color(0xE8, 0xF0, 0xFE);
    private static final Color BORDER = new Color(0x9AA0A6);

    public Cell() {
        setMargin(new Insets(0,0,0,0));
        setFocusPainted(false);
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(BORDER));
        reset();
    }

    public boolean isMine() { return mine; }
    public void setMine(boolean m) { mine = m; }

    public boolean isRevealed() { return revealed; }
    public void setRevealed(boolean r) {
        revealed = r;
        updateAppearance(false);
    }

    public boolean isFlagged() { return flagged; }
    public void setFlagged(boolean f) {
        if (!revealed) {
            flagged = f;
            updateAppearance(false);
        }
    }

    public int getAdjacent() { return adjacent; }
    public void setAdjacent(int n) { adjacent = n; }

    public void reveal(boolean showMine) {
        revealed = true;
        updateAppearance(showMine);
    }

    public void reset() {
        mine = false;
        revealed = false;
        flagged = false;
        adjacent = 0;
        updateAppearance(false);
    }

    public void updateAppearance(boolean showMine) {
        if (flagged && !revealed) {
            setText("⚑");
            setForeground(new Color(220, 0, 0));
            setBackground(COVERED_BG);
        } else if (!revealed) {
            setText("");
            setForeground(Color.BLACK);
            setBackground(COVERED_BG);
        } else {
            setBackground(REVEALED_BG);
            if (mine) {
                setText(showMine ? "💣" : "");
                setForeground(Color.BLACK);
            } else {
                if (adjacent == 0) {
                    setText("");
                } else {
                    setText(String.valueOf(adjacent));
                    setForeground(numberColor(adjacent));
                }
            }
        }
    }

    private Color numberColor(int n) {
        switch (n) {
            case 1: return new Color(25, 118, 210);
            case 2: return new Color(46, 125, 50);
            case 3: return new Color(211, 47, 47);
            case 4: return new Color(123, 31, 162);
            case 5: return new Color(255, 143, 0);
            case 6: return new Color(0, 151, 167);
            case 7: return new Color(84, 110, 122);
            case 8: return new Color(0, 0, 0);
            default: return Color.BLACK;
        }
    }
}

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.*;

public class Main {
    private static JFrame frame;
    private static JLabel createCuisineLabel;

    public static void main(String[] args) {

        frame = new JFrame("청주대 근처 음식점, 놀이 찾기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(0, 1));

        String[] cuisines = {"한식", "중식", "양식", "일식", "카페", "편의점", "당구장", "노래방", "PC방"};
        for (String cuisine : cuisines) {
            JLabel label = createCuisineLabel(cuisine);
            frame.add(label);
        }
        frame.setVisible(true);

    }

    private static JLabel createCuisinelLabel(String cuisine) {
        JLabel label = new JLabel(cuisine, SwingConstants.CENTER);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.setFont(new Font("Serif", Font.BOLD, 18));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 50));

        label.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                showFoodList(cuisine);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.BLACK);
            }
        });

        return label;
    }

    }

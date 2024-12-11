import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

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

    private static JLabel createCuisineLabel(String cuisine) {
        return null;
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

    private static void showFoodList(String cuisine) {
        JFrame foodFrame = new JFrame();
        foodFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        foodFrame.setSize(300, 300);
        foodFrame.setLayout(new GridLayout(0, 1));

        for (String food : getFoodList(cuisine)) {
            JLabel foodLabel = createFoodLabel(food);
            foodFrame.add(foodLabel);
        }
        foodFrame.setVisible(true);
    }

    private static String[] getFoodList(String cuisine) {
        return new String[0];
    }

    private static JLabel createFoodLabel(String food) {
        JLabel label = new JLabel(food, SwingConstants.CENTER);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.setFont(new Font("Serif", Font.PLAIN, 16));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 40));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        return label; // 이 줄을 추가하여 label을 반환합니다.
    }

    private static void openMapForFood(String food) {
        try {
            String bounds = "36.6152,127.4420|36.6603,127.4850";
            String query = URLEncoder.encode(food + " 음식점", "UTF-8");
            String url =  "https://www.google.com/maps/search/?api=1&query=" + query + "&bounds=" + bounds;

            if (Desktop.isDesktopSupported()) {
                Desktop getDesktop().browse(new URI(url));
            }
            else {
                System.err.println("Desktop is not supported");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}



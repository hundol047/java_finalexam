import javax.swing.*;
import java.awt.*;
import java.net.*;

public class Main {
    private static JFrame frame;

    public static void main(String[] args) {

        frame = new JFrame("청주대 근처 음식점, 놀이 찾기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.setLayout(new GridLayout(0,1));

        String[] cuisines = {"한식","중식","양식","일식","카페","편의점","당구장", "노래방", "PC방"};
        for (String cuisine : cuisines) {
            JLabel label = new JLabel(cuisine);
            frame.add(label);
        }
        frame.setVisible(true);

        }
    }

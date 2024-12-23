import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Main {

    private static JFrame frame;
    private static JPanel foodPanel; // 음식 목록 패널
    private static boolean isDarkMode = false; // 다크 모드 상태
    private static JButton toggleButton; // 다크 모드 전환 버튼

    public static void main(String[] args) {
        // 창 생성
        frame = new JFrame("청주대 근처 음식점, 놀이 찾기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 300); // 창 크기 조정
        frame.setLayout(new BorderLayout()); // 전체 레이아웃을 BorderLayout으로 설정

        // 음식 종류 레이블 생성
        JPanel cuisinePanel = new JPanel();
        cuisinePanel.setLayout(new GridLayout(0, 1)); // 세로로 나열
        cuisinePanel.setPreferredSize(new Dimension(100, 0)); // 가로 길이 줄이기

        String[] cuisines = {"한식", "중식", "양식", "일식", "카페", "편의점", "당구장", "노래방", "PC방"};
        for (String cuisine : cuisines) {
            JLabel label = createCuisineLabel(cuisine);
            cuisinePanel.add(label);
        }

        // 다크 모드 전환 버튼 생성 (이모지 사용)
        toggleButton = new JButton("🌙"); // 초기 아이콘은 달 이모지
        toggleButton.setPreferredSize(new Dimension(60, 60)); // 버튼 크기 조정
        toggleButton.setBorderPainted(false); // 버튼 테두리 없애기
        toggleButton.setBackground(Color.DARK_GRAY); // 버튼 배경색 초기화
        toggleButton.setForeground(Color.WHITE);
        toggleButton.addActionListener(e -> toggleDarkMode()); // 버튼 클릭 시 다크 모드 전환

        // 상단 패널 생성 (버튼 및 구분선 포함)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 버튼 왼쪽 정렬
        topPanel.add(toggleButton); // 버튼 추가
        JSeparator topSeparator = new JSeparator();
        topSeparator.setForeground(Color.BLACK); // 구분선 초기 색상 설정
        topPanel.add(topSeparator); // 구분선 추가

        // 음식 목록 패널 초기화
        foodPanel = new JPanel();
        foodPanel.setLayout(new GridLayout(0, 1)); // 세로로 나열
        foodPanel.setPreferredSize(new Dimension(300, 300)); // 음식 목록 패널 크기 설정

        // 스크롤 가능하도록 설정
        JScrollPane foodScrollPane = new JScrollPane(foodPanel);
        foodScrollPane.setPreferredSize(new Dimension(300, 300)); // 음식 목록 스크롤 패널 크기 설정

        // 패널을 프레임에 추가
        frame.add(cuisinePanel, BorderLayout.WEST); // 음식 종류 패널을 왼쪽에 추가
        frame.add(topPanel, BorderLayout.NORTH); // 상단 패널 추가
        frame.add(foodScrollPane, BorderLayout.CENTER); // 음식 목록 패널을 중앙에 추가

        frame.setVisible(true);
    }

    // 다크 모드 전환 메서드
    private static void toggleDarkMode() {
        isDarkMode = !isDarkMode; // 다크 모드 상태 전환
        Color backgroundColor = isDarkMode ? Color.DARK_GRAY : Color.WHITE;
        Color foregroundColor = isDarkMode ? Color.WHITE : Color.BLACK;

        // 프레임 전체 배경색 변경
        frame.getContentPane().setBackground(backgroundColor);

        // 버튼 색상 변경
        toggleButton.setText(isDarkMode ? "☀️" : "🌙"); // 다크 모드일 때 해, 아닐 때 달 이모지로 변경
        toggleButton.setBackground(backgroundColor);
        toggleButton.setForeground(foregroundColor);

        // 음식 종류 패널 색상 변경
        JPanel cuisinePanel = (JPanel) frame.getContentPane().getComponent(0); // 음식 종류 패널
        cuisinePanel.setBackground(backgroundColor);
        for (Component comp : cuisinePanel.getComponents()) {
            comp.setBackground(backgroundColor); // 배경색 변경
            comp.setForeground(foregroundColor); // 글자색 변경
        }

        // 음식 목록 패널 색상 변경
        foodPanel.setBackground(backgroundColor);
        for (Component comp : foodPanel.getComponents()) {
            comp.setBackground(backgroundColor); // 배경색 변경
            comp.setForeground(foregroundColor); // 글자색 변경
        }

        // 상단 패널 내 구분선 색상 변경
        JPanel topPanel = (JPanel) frame.getContentPane().getComponent(1); // 상단 패널
        topPanel.setBackground(backgroundColor);
        JSeparator topSeparator = (JSeparator) topPanel.getComponent(1); // 구분선
        topSeparator.setForeground(isDarkMode ? Color.LIGHT_GRAY : Color.BLACK);

        updateFoodLabels();
        frame.revalidate(); // UI 재배치
        frame.repaint(); // UI 다시 그리기
    }

    // 음식 레이블 색상 갱신 메서드
    private static void updateFoodLabels() {
        Color backgroundColor = isDarkMode ? Color.DARK_GRAY : Color.WHITE;
        Color foregroundColor = isDarkMode ? Color.WHITE : Color.BLACK;

        for (Component comp : foodPanel.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                label.setBackground(backgroundColor);
                label.setForeground(foregroundColor);
            }
        }
    }

    // 음식 종류 레이블 생성
    private static JLabel createCuisineLabel(String cuisine) {
        JLabel label = new JLabel(cuisine, SwingConstants.CENTER);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 커서 변경
        label.setFont(new Font("Serif", Font.BOLD, 16)); // 글씨 크기 및 스타일 변경
        label.setOpaque(true); // 배경색 적용을 위해 불투명으로 설정
        label.setBackground(Color.WHITE); // 배경색 흰색으로 설정
        label.setForeground(Color.BLACK); // 글자색 검정색으로 설정
        label.setPreferredSize(new Dimension(80, 50)); // 가로 길이 줄이기

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showFoodList(cuisine); // 음식 목록 표시
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.BLUE); // 마우스 오버 시 색상 변경
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(isDarkMode ? Color.WHITE : Color.BLACK); // 다크 모드일 때 색상 변경
            }
        });

        return label;
    }

    // 음식 목록을 보여주는 메서드
    private static void showFoodList(String cuisine) {
        foodPanel.removeAll(); // 기존 음식 목록 지우기

        // 음식 목록을 가져와서 레이블 추가
        for (Object food : getFoodList(cuisine)) {
            JLabel foodLabel = createFoodLabel((String) food);
            foodPanel.add(foodLabel);
        }

        foodPanel.revalidate(); // 패널 재배치
        foodPanel.repaint(); // 패널 다시 그리기
    }

    // 음식 목록 레이블 생성
    private static JLabel createFoodLabel(String food) {
        JLabel label = new JLabel(food, SwingConstants.CENTER);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 커서 변경
        label.setFont(new Font("Serif", Font.PLAIN, 16)); // 글씨 크기 및 스타일 변경
        label.setOpaque(true); // 배경색 적용을 위해 불투명으로 설정
        label.setBackground(isDarkMode ? Color.DARK_GRAY : Color.WHITE); // 배경색 설정
        label.setForeground(isDarkMode ? Color.WHITE : Color.BLACK); // 글자색 설정
        label.setPreferredSize(new Dimension(300, 40)); // 레이블 크기 설정

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openMapForFood(food); // 클릭 시 지도 열기
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.BLUE); // 마우스 오버 시 색상 변경
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(isDarkMode ? Color.WHITE : Color.BLACK); // 다크 모드일 때 색상 변경
            }
        });

        return label;
    }

    // 음식 이름 클릭 시 지도에서 위치 열기
    private static void openMapForFood(String food) {
        try {
            String query = URLEncoder.encode(food + " 음식점", "UTF-8");
            String url = "https://www.google.com/maps/search/?q=" + query;

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.err.println("Desktop API가 지원되지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 음식 목록 반환 (ArrayList 사용)
    private static ArrayList<Object> getFoodList(String cuisine) {
        ArrayList<Object> foodList = new ArrayList<>();
        switch (cuisine) {
            case "한식":
                foodList.add("오로지");
                foodList.add("덕불감자탕");
                foodList.add("국가대표");
                foodList.add("부활");
                foodList.add("성진식당");
                foodList.add("청주대 불고기");
                break;
            case "중식":
                foodList.add("짬뽕의 맛");
                foodList.add("가연각");
                foodList.add("진풍루");
                foodList.add("황궁쟁반짜장");
                foodList.add("마라퀸");
                foodList.add("대박 마라팅");
                foodList.add("천미 마라탕");
                break;
            case "양식":
                foodList.add("믹스레스토랑");
                foodList.add("버거운버거");
                break;
            case "일식":
                foodList.add("봉득식당");
                foodList.add("면식당");
                foodList.add("자연을 담은 돈까스");
                foodList.add("다누끼");
                foodList.add("아지트");
                break;
            case "카페":
                foodList.add("아르떼");
                foodList.add("CHAI");
                foodList.add("쿼드커피");
                foodList.add("씨스테이션");
                foodList.add("Dessert 39");
                foodList.add("A TWOSOME PLACE");
                break;
            case "편의점":
                foodList.add("GS25");
                foodList.add("7 Eleven");
                foodList.add("CU");
                break;
            case "당구장":
                foodList.add("중문당구장");
                foodList.add("럭키당구장");
                foodList.add("브론슨당구장");
                foodList.add("갤러리당구장");
                foodList.add("윙크당구장");
                foodList.add("잘굴러당구장");
                break;
            case "노래방":
                foodList.add("코인고 동전노래방");
                foodList.add("럭셔리 노래방");
                foodList.add("질러코인 노래방");
                foodList.add("아이코인 노래방");
                break;
            case "PC방":
                foodList.add("긱스타");
                foodList.add("OXPC방");
                foodList.add("LOLPC");
                foodList.add("PC TAG");
                foodList.add("락PC스테이션");
                foodList.add("흑&백PC방");
                break;
            default:
                foodList.add("알 수 없는 음식");
                break;
        }
        return foodList;
    }
}













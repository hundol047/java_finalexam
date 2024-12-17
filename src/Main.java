import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Main {

    private static JFrame frame;

    public static void main(String[] args) {
        // 창 생성
        frame = new JFrame("청주대 근처 음식점, 놀이 찾기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(0, 1)); // 세로로 나열
        frame.getContentPane().setBackground(Color.WHITE); // 배경색 흰색으로 설정

        // 음식 종류 레이블 생성
        String[] cuisines = {"한식", "중식", "양식", "일식", "카페", "편의점", "당구장", "노래방", "PC방"};
        for (String cuisine : cuisines) {
            JLabel label = createCuisineLabel(cuisine);
            frame.add(label);
        }

        frame.setVisible(true);
    }

    // 음식 종류 레이블 생성
    private static JLabel createCuisineLabel(String cuisine) {
        JLabel label = new JLabel(cuisine, SwingConstants.CENTER);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 커서 변경
        label.setFont(new Font("Serif", Font.BOLD, 18)); // 글씨 크기 및 스타일 변경
        label.setOpaque(true); // 배경색 적용을 위해 불투명으로 설정
        label.setBackground(Color.WHITE); // 배경색 흰색으로 설정
        label.setForeground(Color.BLACK); // 글자색 검정색으로 설정
        label.setPreferredSize(new Dimension(300, 50)); // 레이블 크기 설정

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showFoodList(cuisine);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.BLUE); // 마우스 오버 시 색상 변경
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.BLACK); // 마우스 나갈 시 색상 원래대로
            }
        });

        return label;
    }

    // 음식 목록을 보여주는 메서드
    private static void showFoodList(String cuisine) {
        JFrame foodFrame = new JFrame(cuisine + " 음식 목록");
        foodFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        foodFrame.setSize(300, 300);
        foodFrame.setLayout(new BorderLayout()); // 레이아웃을 BorderLayout으로 설정
        foodFrame.getContentPane().setBackground(Color.WHITE); // 배경색 흰색으로 설정

        JPanel foodPanel = new JPanel();
        foodPanel.setLayout(new GridLayout(0, 1)); // 세로로 나열
        foodPanel.setBackground(Color.WHITE); // 패널 배경색 흰색으로 설정

        for (Object food : getFoodList(cuisine)) {
            JLabel foodLabel = createFoodLabel((String) food);
            foodPanel.add(foodLabel);
        }

        JButton backButton = new JButton("뒤로 돌아가기");
        backButton.setPreferredSize(new Dimension(300, 40)); // 버튼 크기 설정
        backButton.setBackground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setFocusPainted(false); // 버튼의 포커스 효과 제거

        // 마우스 리스너 추가
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setBackground(Color.BLUE); // 마우스 오버 시 배경색 변경
                backButton.setForeground(Color.WHITE); // 글자색 변경
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setBackground(Color.WHITE); // 마우스 나갈 시 배경색 원래대로
                backButton.setForeground(Color.BLACK); // 글자색 원래대로
            }
        });

        backButton.addActionListener(e -> foodFrame.dispose()); // 버튼 클릭 시 음식 목록 창 닫기

        foodFrame.add(foodPanel, BorderLayout.CENTER);
        foodFrame.add(backButton, BorderLayout.SOUTH); // 버튼을 아래쪽에 추가

        foodFrame.setVisible(true);
    }

    // 음식 목록 레이블 생성
    private static JLabel createFoodLabel(String food) {
        JLabel label = new JLabel(food, SwingConstants.CENTER);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 커서 변경
        label.setFont(new Font("Serif", Font.PLAIN, 16)); // 글씨 크기 및 스타일 변경
        label.setOpaque(true); // 배경색 적용을 위해 불투명으로 설정
        label.setBackground(Color.WHITE); // 배경색 흰색으로 설정
        label.setForeground(Color.BLACK); // 글자색 검정색으로 설정
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
                label.setForeground(Color.BLACK); // 마우스 나갈 시 색상 원래대로
            }
        });

        return label;
    }

    // 음식 이름 클릭 시 지도에서 위치 열기
    private static void openMapForFood(String food) {
        try {
            // 음식점 검색 쿼리 인코딩
            String query = URLEncoder.encode(food + " 음식점", "UTF-8");
            // 구글 맵 URL 생성
            String url = "https://www.google.com/maps/search/?q=" + query;

            // Desktop API가 지원되는지 확인
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url)); // 웹 브라우저로 URL 열기
            } else {
                System.err.println("Desktop API가 지원되지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // 오류 발생 시 스택 트레이스 출력
        }
    }

    // 음식 목록 반환 (ArrayList 사용)
    private static ArrayList<Object> getFoodList(String cuisine) {
        ArrayList<Object> foodList = new ArrayList<>(); // ArrayList 생성
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
                foodList.add("럭셔시수노래방");
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
        return foodList; // ArrayList 반환
    }
}






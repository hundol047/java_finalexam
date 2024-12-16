import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URLEncoder;

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
        foodFrame.setLayout(new GridLayout(0, 1)); // 세로로 나열
        foodFrame.getContentPane().setBackground(Color.WHITE); // 배경색 흰색으로 설정

        for (String food : getFoodList(cuisine)) {
            JLabel foodLabel = createFoodLabel(food);
            foodFrame.add(foodLabel);
        }

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

    // 음식 목록 반환
    private static String[] getFoodList(String cuisine) {
        switch (cuisine) {
            case "한식":
                return new String[]{"오로지", "덕불감자탕", "국가대표", "부활", "성진식당", "청주대 불고기"};
            case "중식":
                return new String[]{"짬뽕의 맛", "가연각", "진풍루", "황궁쟁반짜장", "마라퀸", "대박 마라팅", "천미 마라탕"};
            case "양식":
                return new String[]{"믹스레스토랑", "버거운버거"};
            case "일식":
                return new String[]{"봉득식당", "면식당", "자연을 담은 돈까스", "다누끼", "아지트"};
            case "카페":
                return new String[]{"아르떼", "CHAI", "쿼드커피", "씨스테이션", "Dessert 39", "A TWOSOME PLACE"};
            case "편의점":
                return new String[]{"GS25", "7 Eleven", "CU"};
            case "당구장":
                return new String[]{"중문당구장", "럭키당구장", "브론슨당구장", "갤러리당구장", "윙크당구장", "잘굴러당구장"};
            case "노래방":
                return new String[]{"코인고 동전노래방", "럭셔시수노래방", "질러코인 노래방", "아이코인 노래방"};
            case "PC방":
                return new String[]{"긱스타", "OXPC방", "LOLPC", "PC TAG", "락PC스테이션", "흑&백PC방"};
            default:
                return new String[]{"알 수 없는 음식"};
        }
    }
}






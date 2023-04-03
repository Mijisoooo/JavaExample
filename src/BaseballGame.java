import java.util.*;

public class BaseballGame {
    public static void main(String[] args) {

        boolean wantsGameAgain = true;

        while (wantsGameAgain) {
            List<Integer> sysNumList = generateSystemNumber();  // 컴퓨터 랜덤 숫자 생성

            boolean isFinish = false;
            while (!isFinish) {
                List<Integer> userNum = getUserNum();   // 사용자 숫자 받기

                // sysNum 과 userNum 비교해서
                // 같은 숫자 같은 자리 -> 스트라이크
                // 같은 숫자 다른 자리 -> 볼
                // 하나도 없으면 -> 낫싱

                int ballNum = 0;
                int strikeNum = 0;
                for (int i = 0; i < sysNumList.size(); i++) {
                    int val = sysNumList.get(i);
                    if (userNum.contains(val)) {    // 볼 개수 계산
                        ballNum++;
                        if (userNum.get(i) == sysNumList.get(i)) {   // 스트라이크 개수 계산
                            strikeNum++;
                            ballNum--;
                        }
                    } else {
                        continue;
                    }
                }

                String resultMsg = generateResultMsg(ballNum, strikeNum); // resultMsg 생성
                System.out.println(resultMsg);

                if (strikeNum == sysNumList.size()) {  // 모두 맞췄다면 새게임시작(1) 게임종료(2) 여부 묻기
                    System.out.println(String.format("%d개의 숫자를 모두 맞히셨습니다. 게임을 종료합니다.", strikeNum));
                    isFinish = true;
                }
            }

            wantsGameAgain = getWantsGameAgain();

        }
    }

    public static List<Integer> generateSystemNumber() {
        Random random = new Random();
        Set<Integer> sysNum = new LinkedHashSet<>();
        while (sysNum.size() < 3) sysNum.add(random.nextInt(9) + 1);
        return new ArrayList<>(sysNum);
    }

    public static ArrayList<Integer> getUserNum() {   // Set 이 아니라 ArrayList나 LinkedList 로 리턴 (둘 중 뭐가 더 좋을까?)
        // 사용자에게 숫자 받기 (inputNum) + 예외처리

        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> inputNumList = new ArrayList<>();

        boolean validInputNum = false;

        do {
            System.out.println("숫자를 입력하세요: ");
            String inputNum = scanner.nextLine();

            // validation
            if (inputNum.length() != 3) {
                System.out.println("잘못된 입력입니다. 세자리 숫자를 입력해주세요.");
                continue;
            }
            inputNumList.clear();
            boolean hasZero = false;
            boolean hasDuplicate = false;
            for (int i = 0; i < inputNum.length(); i++) {
                char c = inputNum.charAt(i);
                if (!Character.isDigit(c)) {
                    System.out.println("잘못된 입력입니다. 숫자만 입력해주세요.");
                    hasZero = false;
                    hasDuplicate = false;
                    break;
                } else if (c == '0') {
                    hasZero = true;
                } else if (inputNumList.contains(Character.getNumericValue(c))) {
                    System.out.println("잘못된 입력입니다. 중복된 숫자를 입력하셨습니다.");
                    hasZero = false;
                    hasDuplicate = true;
                    break;
                } else {
                    inputNumList.add(Character.getNumericValue(c));
                }
            }
            if (!hasZero && !hasDuplicate && inputNumList.size() == 3) {
                validInputNum = true;
            } else {
                System.out.println("잘못된 입력입니다. 1부터 9까지의 서로다른 세자리 숫자를 입력해주세요.");
            }
        } while (!validInputNum);

        return inputNumList;
    }

    public static String generateResultMsg(int ballNum, int strikeNum) {
        String resultMsg = "";
        if (ballNum == 0) {
            if (strikeNum == 0) resultMsg = "낫싱";
            else resultMsg = String.format("%d스트라이크", strikeNum);
        }
        else {
            if (strikeNum == 0) resultMsg = String.format("%d볼", ballNum);
            else resultMsg = String.format("%d볼 %d스트라이크", ballNum, strikeNum);
        }
        return resultMsg;
    }

    private static boolean getWantsGameAgain() {
        boolean isValidInput = false;
        boolean wantsGameAgain = false;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("새게임시작(1) 게임종료(2)");

            String wants = scanner.nextLine();

            switch (wants.trim()) {
                case "1":
                    wantsGameAgain = true;
                    isValidInput = true;
                    break;
                case "2":
                    wantsGameAgain = false;
                    isValidInput = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    isValidInput = false;
                    break;
            }
        } while (!isValidInput);
        return wantsGameAgain;
    }
}

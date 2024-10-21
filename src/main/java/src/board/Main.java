package src.board;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, String> parameters = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.print("guest > ");
            command = scanner.nextLine();

            try {
                processURL(command);
            } catch (InvalidURLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // URL 처리 및 분석
    private static void processURL(String url) throws InvalidURLException {
        if (!url.startsWith("/")) {
            throw new InvalidURLException("잘못된 URL 형식입니다.");
        }

        String[] parts = url.split("\\?");
        String path = parts[0];  // /구분/기능 부분
        String[] pathParts = path.split("/");

        if (pathParts.length < 3) {
            throw new InvalidURLException("URL 경로가 너무 짧습니다.");
        }

        String category = pathParts[1];  // 구분
        String action = pathParts[2];    // 기능

        if (parts.length > 1) {
            // 파라미터 처리
            parseParameters(parts[1]);
        }

        // URL 경로에 따라 동작 처리
        switch (action) {
            case "write":
                writePost();
                break;
            case "view":
                viewPost();
                break;
            case "delete":
                deletePost();
                break;
            case "modify":
                modifyPost();
                break;
            default:
                throw new InvalidURLException("Unknown Features: " + action);
        }
    }

    // 파라미터 분석
    private static void parseParameters(String paramString) {
        parameters.clear();  // 파라미터를 초기화
        String[] paramPairs = paramString.split("&");

        for (String pair : paramPairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                parameters.put(keyValue[0], keyValue[1]);  // 마지막 파라미터 값으로 덮어쓰기
            }
        }
    }

    // 게시글 작성 예시
    private static void writePost() {
        String title = parameters.get("title");
        String content = parameters.get("content");

        if (title == null || content == null) {
            System.out.println("Insufficient parameters, check the 'title' and 'content' parameters.");
        } else {
            System.out.println("Posted - title: " + title + ", content: " + content);
        }
    }

    // 게시글 조회 예시
    private static void viewPost() {
        String id = parameters.get("id");

        if (id == null) {
            System.out.println("parameter 'id' is required.");
        } else {
            System.out.println(id + "post check");
        }
    }

    // 게시글 삭제 예시
    private static void deletePost() {
        String id = parameters.get("id");

        if (id == null) {
            System.out.println("parameter 'id' is required.");
        } else {
            System.out.println(id + "post check");
        }
    }

    // 게시글 수정 예시
    private static void modifyPost() {
        String id = parameters.get("id");
        String newTitle = parameters.get("title");
        String newContent = parameters.get("content");

        if (id == null || newTitle == null || newContent == null) {
            System.out.println("Insufficient parameters, check 'id', 'title', and 'content' parameters.");
        } else {
            System.out.println(id + "post modified - new title: " + newTitle + ", new content: " + newContent);
        }
    }
}

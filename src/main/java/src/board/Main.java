package src.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, String> parameters = new HashMap<>();
    private static List<Board> boards = new ArrayList<>();
    private static List<Post> posts = new ArrayList<>(); // 게시물 리스트

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.print("손님 > ");
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

        String category = pathParts[1];  // posts, boards 구분
        String action = pathParts[2];    // 기능

        if (parts.length > 1) {
            // 파라미터 처리
            parseParameters(parts[1]);
        }

        // URL 경로에 따라 동작 처리
        switch (category) {
            case "게시글":
                handlePostAction(action);
                break;
            case "boards":
                handleBoardAction(action);
                break;
            default:
                throw new InvalidURLException("알 수 없는 구분: " + category);
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

    // 게시판 관련 URL 처리
    private static void handleBoardAction(String action) throws InvalidURLException {
        switch (action) {
            case "add":
                addBoard();
                break;
            case "edit":
                editBoard();
                break;
            case "remove":
                removeBoard();
                break;
            case "view":
                viewBoard();
                break;
            default:
                throw new InvalidURLException("알 수 없는 게시판 기능: " + action);
        }
    }

    // 게시판 추가
    private static void addBoard() {
        String boardName = parameters.get("name");
        if (boardName == null) {
            System.out.println("게시판 이름을 입력하세요.");
        } else {
            boards.add(new Board(boardName));
            System.out.println("게시판 '" + boardName + "'이(가) 추가되었습니다.");
        }
    }

    // 게시판 수정
    private static void editBoard() {
        String boardIdStr = parameters.get("boardId");
        if (boardIdStr == null) {
            System.out.println("boardId가 필요합니다.");
            return;
        }

        int boardId = Integer.parseInt(boardIdStr);
        String newName = parameters.get("name");

        Board board = findBoardById(boardId);
        if (board == null) {
            System.out.println(boardId + "번 게시판을 찾을 수 없습니다.");
        } else {
            board.setName(newName);
            System.out.println(boardId + "번 게시판이 '" + newName + "'으로 수정되었습니다.");
        }
    }

    // 게시판 삭제
    private static void removeBoard() {
        String boardIdStr = parameters.get("boardId");
        if (boardIdStr == null) {
            System.out.println("boardId가 필요합니다.");
            return;
        }

        int boardId = Integer.parseInt(boardIdStr);
        Board board = findBoardById(boardId);

        if (board == null) {
            System.out.println(boardId + "번 게시판을 찾을 수 없습니다.");
        } else {
            boards.remove(board);
            System.out.println(boardId + "번 게시판이 삭제되었습니다.");
        }
    }

    // 게시판 조회 (게시글 목록 출력)
    private static void viewBoard() {
        String boardName = parameters.get("boardName");
        if (boardName == null) {
            System.out.println("게시판 이름을 입력하세요.");
            return;
        }

        Board board = findBoardByName(boardName);
        if (board == null) {
            System.out.println("게시판 '" + boardName + "'을(를) 찾을 수 없습니다.");
        } else {
            System.out.println("게시글 목록:");
            for (Post post : board.getPosts()) {
                System.out.println(post.getId() + " / " + post.getTitle() + " / " + post.getContent());
            }
        }
    }

    // 게시판 찾기 by ID
    private static Board findBoardById(int id) {
        for (Board board : boards) {
            if (board.getId() == id) {
                return board;
            }
        }
        return null;
    }

    // 게시판 찾기 by 이름
    private static Board findBoardByName(String name) {
        for (Board board : boards) {
            if (board.getName().equals(name)) {
                return board;
            }
        }
        return null;
    }

    // 게시글 관련 URL 처리 (기존 코드 유지)
    private static void handlePostAction(String action) throws InvalidURLException {
        switch (action) {
            case "add":
                addPost();
                break;
            case "edit":
                editPost();
                break;
            case "remove":
                removePost();
                break;
            case "view":
                viewPost();
                break;
            default:
                throw new InvalidURLException("알 수 없는 게시물 기능: " + action);
        }
    }

    // 게시글 작성
    private static void addPost() {
        String boardName = parameters.get("boardName");
        String title = parameters.get("title");
        String content = parameters.get("content");

        if (boardName == null || title == null || content == null) {
            System.out.println("파라미터 부족: 게시판 이름, 제목, 내용을 입력하세요.");
            return;
        }

        Board board = findBoardByName(boardName);
        if (board == null) {
            System.out.println("게시판 '" + boardName + "'을(를) 찾을 수 없습니다.");
            return;
        }

        Post post = new Post(title, content, board);
        posts.add(post);
        board.addPost(post);
        System.out.println("게시물 '" + title + "'이(가) 게시판 '" + boardName + "'에 추가되었습니다.");
    }

    // 게시물 수정
    private static void editPost() {
        String postIdStr = parameters.get("postId");
        String newTitle = parameters.get("title");
        String newContent = parameters.get("content");

        if (postIdStr == null || newTitle == null || newContent == null) {
            System.out.println("파라미터 부족: postId, 제목, 내용을 입력하세요.");
            return;
        }

        int postId = Integer.parseInt(postIdStr);
        Post post = findPostById(postId);

        if (post == null) {
            System.out.println(postId + "번 게시물을 찾을 수 없습니다.");
            return;
        }

        post.setTitle(newTitle);
        post.setContent(newContent);
        System.out.println(postId + "번 게시물이 수정되었습니다.");
    }


    // 게시물 삭제
    private static void removePost() {
        String postIdStr = parameters.get("postId");

        if (postIdStr == null) {
            System.out.println("postId가 필요합니다.");
            return;
        }

        int postId = Integer.parseInt(postIdStr);
        Post post = findPostById(postId);

        if (post == null) {
            System.out.println(postId + "번 게시물을 찾을 수 없습니다.");
            return;
        }

        posts.remove(post);
        post.getBoard().removePost(post);
        System.out.println(postId + "번 게시물이 삭제되었습니다.");
    }


    // 게시물 조회
    private static void viewPost() {
        String postIdStr = parameters.get("postId");

        if (postIdStr == null) {
            System.out.println("postId가 필요합니다.");
            return;
        }

        int postId = Integer.parseInt(postIdStr);
        Post post = findPostById(postId);

        if (post == null) {
            System.out.println(postId + "번 게시물을 찾을 수 없습니다.");
        } else {
            System.out.println(post);
        }
    }

    // 게시물 찾기 by ID
    private static Post findPostById(int id) {
        for (Post post : posts) {
            if (post.getId() == id) {
                return post;
            }
        }
        return null;
    }
}

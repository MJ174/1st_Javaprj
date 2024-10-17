import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Post> posts = new ArrayList<>(); // 게시글 목록을 저장할 리스트
    private static int postIdCounter = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        // 명령어 프롬프트 출력
        while (true) {
            System.out.print("console > ");
            command = scanner.nextLine(); // 입력 받기

            switch (command) {
                case "write":
                    writePost(scanner);
                    break;
                case "search":
                    viewPost(scanner);
                    break;
                case "delete":
                    deletePost(scanner);
                    break;
                case "modify":
                    modifyPost(scanner);
                    break;
                case "end":
                    System.out.println("The program will be terminated.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Command that does not exist.");
                    break;
            }
        }
    }
    // 게시글 작성
    private static void writePost(Scanner scanner) {
        System.out.print("Please enter the title: ");
        String title = scanner.nextLine();
        System.out.print("Please enter the contents: ");
        String content = scanner.nextLine();

        Post newPost = new Post(postIdCounter++, title, content);
        posts.add(newPost);
        System.out.println("The post has been created.");
    }

    // 게시글 조회
    private static void viewPost(Scanner scanner) {
        if (posts.isEmpty()) {
            System.out.println("No posts have been created.");
            return;
        }

        System.out.print("Enter the post number to view:");
        int postId = scanner.nextInt();
        scanner.nextLine();

        Post post = findPostById(postId);
        if (post != null) {
            System.out.println("title: " + post.getTitle());
            System.out.println("content: " + post.getContent());
        } else {
            System.out.println("The post for that number does not exist.");
        }
    }

    // 게시글 삭제
    private static void deletePost(Scanner scanner) {
        if (posts.isEmpty()) {
            System.out.println("no posts to delete.");
            return;
        }

        System.out.print("Enter the post number you want to delete:");
        int postId = scanner.nextInt();
        scanner.nextLine();

        Post post = findPostById(postId);
        if (post != null) {
            posts.remove(post);
            System.out.println("post has been deleted.");
        } else {
            System.out.println("The post for that number does not exist.");
        }
    }

    // 게시글 수정
    private static void modifyPost(Scanner scanner) {
        if (posts.isEmpty()) {
            System.out.println("no posts to modify.");
            return;
        }

        System.out.print("enter the post number you want to modify: ");
        int postId = scanner.nextInt();
        scanner.nextLine();

        Post post = findPostById(postId);
        if (post != null) {
            System.out.print("enter a new title: ");
            String newTitle = scanner.nextLine();
            System.out.print("enter new content: ");
            String newContent = scanner.nextLine();

            post.setTitle(newTitle);
            post.setContent(newContent);
            System.out.println("The post has been modified.");
        } else {
            System.out.println("The post for that number does not exist.");
        }
    }
    // 게시글 ID로 게시글 찾기
    private static Post findPostById(int postId) {
        for (Post post : posts) {
            if (post.getId() == postId) {
                return post;
            }
        }
        return null;
    }
    }

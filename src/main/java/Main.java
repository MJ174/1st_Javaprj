import java.util.Scanner;

public class Main {
    private static Post lastPost = null; // 마지막으로 작성된 게시글을 저장할 변수

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
                    viewPost();
                    break;
                case "delete":
                    deletePost();
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

        lastPost = new Post(title, content);
        System.out.println("The post has been created.");
    }

    // 게시글 조회
    private static void viewPost() {
        if (lastPost == null) {
            System.out.println("No posts have been created.");
        } else {
            System.out.println("Title: " + lastPost.getTitle());
            System.out.println("Contents: " + lastPost.getContent());
        }
    }

    // 게시글 삭제
    private static void deletePost() {
        if (lastPost == null) {
            System.out.println("no posts to delete.");
        } else {
            lastPost = null;
            System.out.println("Your post has been deleted.");
        }
    }

    // 게시글 수정
    private static void modifyPost(Scanner scanner) {
        if (lastPost == null) {
            System.out.println("no posts to modify.");
        } else {
            System.out.print("enter a new title: ");
            String newTitle = scanner.nextLine();
            System.out.print("enter a new content: ");
            String newContent = scanner.nextLine();

            lastPost.setTitle(newTitle);
            lastPost.setContent(newContent);
            System.out.println("post has been modified.");
        }
    }
    }

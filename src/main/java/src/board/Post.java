package src.board;

import java.time.LocalDateTime;

public class Post {
    private static int nextId = 1;
    private int id;
    private String title;
    private String content;
    private Board board;// 게시물이 속한 게시판
    private String author; // 작성자 (회원명 또는 비회원)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Post(String title, String content, Board board, String author) {
        this.id = nextId++;
        this.title = title;
        this.content = content;
        this.board = board;
        this.author = author;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null; // 최초 작성 시에는 수정일이 없음
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public Board getBoard() {
        return board;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "게시물 번호: " + id + "\n" +
                "\n제목: " + title + "\n" +
                "\n내용: " + content + "\n" +
                "\n게시판: " + board.getName() + "\n" +
                "작성자: " + author + "\n" +
                "\n작성일: " + createdAt + (updatedAt != null ? "\n수정일: " + updatedAt : "");
    }
}

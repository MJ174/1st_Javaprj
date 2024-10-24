package src.board;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private static int nextId = 1;
    private int id;
    private String name;
    private String author;
    private List<Post> posts;

    public Board(String name, String author) {
        this.id = nextId++;
        this.name = name;
        this.author = author;
        this.posts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void removePost(Post post) {
        posts.remove(post);
    }

    @Override
    public String toString() {
        return "게시판 번호: " + id + "\n" +
                "게시판 이름: " + name + "\n" +
                "작성자: " + author + "\n게시물 수: " + posts.size();
    }

    public void setName(String newName) {
        this.name = newName;
    }
}
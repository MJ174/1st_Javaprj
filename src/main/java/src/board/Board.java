package src.board;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private static int nextId = 1;
    private int id;
    private String name;
    private List<Post> posts;

    public Board(String name) {
        this.id = nextId++;
        this.name = name;
        this.posts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void removePost(Post post) {

    }

    @Override
    public String toString() {
        return id + "번 게시판: " + name;
    }

    public void setName(String newName) {
    }
}
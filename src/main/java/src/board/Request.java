package src.board;

import java.util.Map;

public class Request {
    private String url;
    private Map<String, String> parameters;
    private boolean isLoggedIn;

    // 생성자
    public Request(String url, Map<String, String> parameters, boolean isLoggedIn) {
        this.url = url;
        this.parameters = parameters;
        this.isLoggedIn = isLoggedIn;
    }

    // URL을 반환
    public String getUrl() {
        return url;
    }

    // 파라미터 반환
    public Map<String, String> getParameters() {
        return parameters;
    }

    // 로그인 여부 반환
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}


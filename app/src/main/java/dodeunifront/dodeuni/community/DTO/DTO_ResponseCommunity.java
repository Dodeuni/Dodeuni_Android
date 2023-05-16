package dodeunifront.dodeuni.community.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class DTO_ResponseCommunity {
    private Long id;

    private String createdDateTime;

    private Long userId;

    private String email;

    private String nickname;

    private String main;

    private String sub;

    private String title;

    private String content;

    private List<Long> photoId;

    private List<String> photoUrl;

    public DTO_ResponseCommunity(String createdDateTime, String nickname, String title, String content) {
        this.createdDateTime = createdDateTime;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Long> getPhotoId() {
        return photoId;
    }

    public void setPhotoId(List<Long> photoId) {
        this.photoId = photoId;
    }

    public List<String> getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(List<String> photoUrl) {
        this.photoUrl = photoUrl;
    }
}

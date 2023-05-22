package dodeunifront.dodeuni.community.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostCommunityDTO {
    @SerializedName("userId")
    private Long userId;

    @SerializedName("main")
    private String main;

    @SerializedName("sub")
    private String sub;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("photo")
    private List<PhotoDTO> photo;

    public PostCommunityDTO(Long userId, String main, String sub, String title, String content, List<PhotoDTO> photo) {
        this.userId = userId;
        this.main = main;
        this.sub = sub;
        this.title = title;
        this.content = content;
        this.photo = photo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public List<PhotoDTO> getPhotoList() {
        return photo;
    }

    public void setPhotoList(List<PhotoDTO> photoList) {
        this.photo = photoList;
    }
}

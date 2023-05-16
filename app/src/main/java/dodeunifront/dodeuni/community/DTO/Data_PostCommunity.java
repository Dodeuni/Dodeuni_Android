package dodeunifront.dodeuni.community.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import dodeunifront.dodeuni.community.DTO.Data_photo;

public class Data_PostCommunity {
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
    private List<Data_photo> photo;

    public Data_PostCommunity(Long userId, String main, String sub, String title, String content, List<Data_photo> photo) {
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

    public List<Data_photo> getPhotoList() {
        return photo;
    }

    public void setPhotoList(List<Data_photo> photoList) {
        this.photo = photoList;
    }
}

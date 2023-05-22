package dodeunifront.dodeuni.community.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import okhttp3.MultipartBody;

public class CommunityUpdateRequestDTO {
    @SerializedName("id")
    private Long id;

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

    @SerializedName("addPhoto")
    private List<MultipartBody.Part> addPhoto;

    @SerializedName("deletePhotoId")
    private List<Long> deletePhotoId;

    public CommunityUpdateRequestDTO(Long id, Long userId, String main, String sub, String title, String content, List<MultipartBody.Part> addPhoto, List<Long> deletePhotoId) {
        this.id = id;
        this.userId = userId;
        this.main = main;
        this.sub = sub;
        this.title = title;
        this.content = content;
        this.addPhoto = addPhoto;
        this.deletePhotoId = deletePhotoId;
    }
}

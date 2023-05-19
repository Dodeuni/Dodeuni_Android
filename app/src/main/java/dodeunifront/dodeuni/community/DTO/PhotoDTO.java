package dodeunifront.dodeuni.community.DTO;

import com.google.gson.annotations.SerializedName;

public class PhotoDTO {
    @SerializedName("id")
    private Long id;

    @SerializedName("communityId")
    private PostCommunityDTO communityId;

    @SerializedName("origPhotoName")
    private String origPhotoName;

    @SerializedName("photoName")
    private String photoName;

    @SerializedName("photoUrl")
    private String photoUrl;
}


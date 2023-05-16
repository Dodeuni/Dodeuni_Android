package dodeunifront.dodeuni.community.DTO;

import com.google.gson.annotations.SerializedName;

public class Data_photo {
    @SerializedName("id")
    private Long id;

    @SerializedName("communityId")
    private Data_PostCommunity communityId;

    @SerializedName("origPhotoName")
    private String origPhotoName;

    @SerializedName("photoName")
    private String photoName;

    @SerializedName("photoUrl")
    private String photoUrl;
}


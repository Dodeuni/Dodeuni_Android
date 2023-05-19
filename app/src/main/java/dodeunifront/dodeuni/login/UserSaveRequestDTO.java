package dodeunifront.dodeuni.login;

import com.google.gson.annotations.SerializedName;

public class UserSaveRequestDTO {
    @SerializedName("email")
    private String email;

    @SerializedName("nickname")
    private String nickname;

    public UserSaveRequestDTO(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}

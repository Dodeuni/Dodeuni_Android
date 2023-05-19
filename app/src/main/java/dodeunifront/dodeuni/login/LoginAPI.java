package dodeunifront.dodeuni.login;

import java.util.ArrayList;
import java.util.Map;

import dodeunifront.dodeuni.community.DTO.DTO_ResponseCommunity;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface LoginAPI {
    String URL = "http://192.168.0.18:8080/";

    @POST("auth/login")
    Call<TokenDTO> postDatalogin(
            @Body UserSaveRequestDTO userSaveRequestDto
    );

    @GET("api/user")
    Call<UserResponseDTO> getDatalogin(
            @Query("id") Long id
    );
}

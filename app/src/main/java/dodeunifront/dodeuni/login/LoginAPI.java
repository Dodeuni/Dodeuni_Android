package dodeunifront.dodeuni.login;

import dodeunifront.dodeuni.R;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface LoginAPI {
    String URL = "http://34.64.117.48:8080";

    @POST("auth/login")
    Call<TokenDTO> postDatalogin(
            @Body UserSaveRequestDTO userSaveRequestDto
    );

    @GET("api/user")
    Call<UserResponseDTO> getDatalogin(
            @Query("id") Long id
    );
    @PUT("api/user")
    Call<UserResponseDTO> updateNickname(@Query("id") Long id, @Query("newNickname") String newNickname);
}

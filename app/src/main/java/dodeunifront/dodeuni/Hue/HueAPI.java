package dodeunifront.dodeuni.Hue;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HueAPI {
    String URL = "http://192.168.0.18:8080/";

    @POST("/api/hyus")
    Call<List<HyuResponseDTO>> postDataActive(
            @Body HuePostDTO huePostDTO
    );

    @GET("api/hyus/list")
    Call<List<HyuResponseDTO>> getDataHue(
    );


}

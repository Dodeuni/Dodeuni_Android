package dodeunifront.dodeuni.hue;

import java.util.List;

import dodeunifront.dodeuni.R;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface HueAPI {
    String URL = "http://34.64.117.48:8080";

    @POST("/api/hyus")
    Call<List<HyuResponseDTO>> postDataActive(
            @Body HuePostDTO huePostDTO
    );

    @GET("api/hyus/list")
    Call<List<HyuResponseDTO>> getDataHue(
    );


}

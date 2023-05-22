package dodeunifront.dodeuni.map.api;

import java.util.List;

import dodeunifront.dodeuni.map.dto.request.RequestEnrollLocationDTO;
import dodeunifront.dodeuni.map.dto.request.RequestRecommendLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseDetailLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseEnrollLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseRecommendLocationDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LocationAPI {
    String URL = "http://192.168.0.18:8080/";

    @POST("api/places")
    Call<ResponseEnrollLocationDTO> postLocation(
            @Body RequestEnrollLocationDTO requestEnrollLocationDTO
    );

    @POST("api/places/list")
    Call<List<ResponseRecommendLocationDTO>> postRecommendList(
            @Body RequestRecommendLocationDTO requestRecommendLocationDTO
    );

    @GET("api/places/{id}")
    Call<ResponseDetailLocationDTO> getLocationDetail(
            @Path("id") long id
    );

}

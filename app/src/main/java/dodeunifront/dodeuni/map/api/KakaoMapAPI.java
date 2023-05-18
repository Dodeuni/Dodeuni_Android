package dodeunifront.dodeuni.map.api;

import dodeunifront.dodeuni.map.dto.response.ResponseLocationListDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface KakaoMapAPI {
    String URL = "https://dapi.kakao.com/";

    @Headers({"Authorization: KakaoAK 982406bc0a8d7e13dea460aad667ef33"})
    @GET("v2/local/search/keyword.json")
    Call<ResponseLocationListDTO> getFindDataList(
            @Query("query") String query,
            @Query("x") String x,
            @Query("y") String y,
            @Query("size") int size,
            @Query("sort") String sort
    );

}

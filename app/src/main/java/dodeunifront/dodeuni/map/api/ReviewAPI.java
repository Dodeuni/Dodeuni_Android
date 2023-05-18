package dodeunifront.dodeuni.map.api;

import dodeunifront.dodeuni.map.dto.request.RequestEnrollLocationDTO;
import dodeunifront.dodeuni.map.dto.request.RequestEnrollReviewDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseEnrollLocationDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseEnrollReviewDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReviewAPI {
    String URL = "http://172.30.1.6:8080/";

    @POST("api/places/reviews")
    Call<ResponseEnrollReviewDTO> postReview(
            @Body RequestEnrollReviewDTO requestEnrollReviewDTO
    );
}

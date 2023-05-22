package dodeunifront.dodeuni.map.api;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.map.dto.request.RequestEnrollReviewDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseReviewDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewAPI {
    String URL = "http://34.64.117.48:8080";

    @POST("/api/places/reviews")
    Call<ResponseReviewDTO> postReview(
            @Body RequestEnrollReviewDTO requestEnrollReviewDTO
    );

    @GET("/api/places/reviews/{id}")
    Call<ResponseReviewDTO> getReviewDetail(
            @Path("id") long id
    );
}

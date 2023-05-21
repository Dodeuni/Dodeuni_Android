package dodeunifront.dodeuni.Hue;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HuefeelAPI {
    String URL = "http://192.168.0.18:5000";

    @GET("/get_sentence_predict/{sentence}") // 서버에 GET 요청을 할 주소 입력
    Call<String> getfeeling(
            @Path("sentence")String sentence);
}

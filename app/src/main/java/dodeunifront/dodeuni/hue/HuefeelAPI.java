package dodeunifront.dodeuni.hue;

import dodeunifront.dodeuni.R;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HuefeelAPI {
    String URL = "http://172.20.20.144:5000";

    @GET("/get_sentence_predict/{sentence}") // 서버에 GET 요청을 할 주소 입력
    Call<String> getfeeling(
            @Path("sentence")String sentence);
}

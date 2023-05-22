package dodeunifront.dodeuni.map.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    public static KakaoMapAPI kakaoMapAPI;
    private Retrofit retrofit = null;
    String KakaoURL = "https://dapi.kakao.com/";

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public void RetrofitBuilder() {
        if(retrofit == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(KakaoURL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        //return retrofit;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}

package dodeunifront.dodeuni.map.retroifit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dodeunifront.dodeuni.map.api.KakaoMapAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KakaoRetrofitBuilder {
    static final String URL = "https://dapi.kakao.com/";
    public static KakaoMapAPI kakaoMapAPI;

    public KakaoRetrofitBuilder(){
    }

    public static KakaoMapAPI getKakaoMapAPI(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        kakaoMapAPI = retrofit.create(KakaoMapAPI.class);

        return kakaoMapAPI;
    }
}

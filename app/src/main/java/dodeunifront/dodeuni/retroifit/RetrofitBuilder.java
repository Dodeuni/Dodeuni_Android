package dodeunifront.dodeuni.retroifit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dodeunifront.dodeuni.alert.AlertAPI;
import dodeunifront.dodeuni.map.api.LocationAPI;
import dodeunifront.dodeuni.map.api.ReviewAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    static final String URL = "http://34.64.117.48:8080";
    public static LocationAPI locationAPI;
    public static ReviewAPI reviewAPI;
    public static AlertAPI alertAPI;
    public static Retrofit retrofit;

    public RetrofitBuilder(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static LocationAPI getLocationAPI(){
        locationAPI = retrofit.create(LocationAPI.class);
        return locationAPI;
    }

    public static ReviewAPI getReviewAPI(){
        reviewAPI = retrofit.create(ReviewAPI.class);
        return reviewAPI;
    }

    public static AlertAPI getAlertAPI(){
        alertAPI = retrofit.create(AlertAPI.class);
        return alertAPI;
    }
}

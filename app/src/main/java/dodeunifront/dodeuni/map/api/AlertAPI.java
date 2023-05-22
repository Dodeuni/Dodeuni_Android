package dodeunifront.dodeuni.map.api;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.map.dto.AlertDTO;
import dodeunifront.dodeuni.map.dto.response.ResponseAlertListDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AlertAPI {
    //String URL = "http://192.168.0.18:8080/";
    String URL = "http://34.64.117.48:8080";

    @GET("/api/alarm")
    Call<ResponseAlertListDTO> getAlert(
            @Query("userId") long userId
    );

    @POST("/api/alarm")
    Call<String> postToken(
            @Query("userId") long userId,
            @Query("fcmToken") String fcmToken
    );
}

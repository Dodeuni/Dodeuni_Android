package dodeunifront.dodeuni.community;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.community.DTO.CommentResponseDTO;
import dodeunifront.dodeuni.community.DTO.CommentSaveRequestDTO;
import dodeunifront.dodeuni.community.DTO.CommunityDeatilDTO;
import dodeunifront.dodeuni.community.DTO.CommunityListResponseDTO;
import dodeunifront.dodeuni.community.DTO.ResponseCommunityDTO;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostcommunityAPI {
    String URL = "http://34.64.117.48:8080";

    @Multipart
    @POST("/api/community")
    Call <ResponseCommunityDTO> postData(
            @Part("userId") Long userId,
           // @Part("title") String title,
           // @Part("content") String content,
           // @Part("main") String main,
            //@Part("sub") String sub,
            @PartMap Map<String, RequestBody> map,
            @Part ArrayList<MultipartBody.Part> photo
    );

    @POST("/api/comments")
    Call <List<CommentResponseDTO>> postcomment(
            @Body CommentSaveRequestDTO commentSaveRequestDTO
    );
    @Headers({"Accept: application/json"})
    @GET("api/community/list")
    Call<List<CommunityListResponseDTO>> getDatalist(
            @Query("main") String main,
            @Query("sub") String sub
    );

    @GET("api/community/detail")
    Call<CommunityDeatilDTO> getDatadetail(
            @Query("id") Long id);

    @GET("api/comments/list/{cid}")
    Call <List<CommentResponseDTO>> getDataComment(
            @Path("cid") Long cid
    );

    @Multipart
    @PUT("api/community")
    Call<CommentResponseDTO> putDataCommunity(
            @Part("id") Long id,
            @Part("userId") Long userId,
            // @Part("title") String title,
            // @Part("content") String content,
            // @Part("main") String main,
            //@Part("sub") String sub,
            @PartMap Map<String, RequestBody> map,
            @Part ArrayList<MultipartBody.Part> addPhoto,
            @Part ("deletePhotoId")List<Long> deletePhotoId
    );

    @DELETE("/api/community")
    Call<Object> deletecommunity(
            @Query("userId") Long userId,
            @Query("id") Long id
    );

    @DELETE("/api/comments/{id}/{cid}")
    Call<List<CommentResponseDTO>> deletecomment(
            @Path("id") Long id,
            @Path("cid") Long cid
    );
}

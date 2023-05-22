package dodeunifront.dodeuni;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dodeunifront.dodeuni.alert.AlertActivity;
import dodeunifront.dodeuni.map.api.AlertAPI;
import dodeunifront.dodeuni.map.api.ReviewAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DodeuniFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    static String token = null;

    public DodeuniFirebaseMessagingService(){
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        this.token = token;
        Log.d("FIREBASE", "FCM token created: " + token);
        sendTokenToServer();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        String icon = remoteMessage.getNotification().getIcon();
        int requestId = Integer.parseInt(remoteMessage.getData().get("communityId"));

        Log.d("FIREBASE", "message received: " + remoteMessage);
        Log.d("FIREBASE", "from: " + from + "title: " + title + "body: " + body + "data: " + requestId + "icon " + icon);
        sendNotification(title, body);
    }

    private void sendNotification(String title, String body){
        Intent intent = new Intent(this, AlertActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
        String channelId = getString(R.string.default_notification_channel_id);
        String channelName = getString(R.string.default_notification_channel_id);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        notificationBuilder.setAutoCancel(true)
                .setSound(defaultSound)
                .setContentText(body)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        notificationManager.notify(0, notificationBuilder.build());
    }

    public static void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    Log.d("FIREBASE", "Fetching FCM registratino token failed", task.getException());
                }
                token = task.getResult();
                Log.d("FIREBASE", "FCM token: " + token);
                sendTokenToServer();
            }
        });
    }

    public static void sendTokenToServer(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AlertAPI.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        AlertAPI alertAPI = retrofit.create(AlertAPI.class);
        Log.d("FIREBASE", "FCM token: " + token);
        alertAPI.postToken(2, token).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("TOKEN", "토큰 전송됨");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TOKEN", "통신 실패: " + t.getMessage());
            }
        });
    }
}

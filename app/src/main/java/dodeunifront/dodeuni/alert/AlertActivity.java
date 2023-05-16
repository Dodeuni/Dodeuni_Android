package dodeunifront.dodeuni.alert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;

public class AlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        TopView topView = findViewById(R.id.topview_alert);
        topView.setOnButtonClickListener(() -> finish());
    }
}
package dodeunifront.dodeuni.map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;

public class PostLocationMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_location_map);

        TopView topView = findViewById(R.id.topview_post_location);
        topView.setOnButtonClickListener(() -> finish());
    }
}
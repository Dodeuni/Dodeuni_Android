package dodeunifront.dodeuni.map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;

public class ReviewMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_map);

        TopView topView = findViewById(R.id.topview_review);

        topView.setOnButtonClickListener(() -> finish());
    }
}
package dodeunifront.dodeuni.map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dodeunifront.dodeuni.R;
import dodeunifront.dodeuni.TopView;

public class ReviewDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_review);

        TopView topView = findViewById(R.id.topview_review);
        topView.setOnButtonClickListener(() -> finish());
    }
}
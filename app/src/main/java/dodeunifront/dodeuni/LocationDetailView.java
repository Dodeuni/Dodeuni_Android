package dodeunifront.dodeuni;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class LocationDetailView extends FrameLayout {
    TextView tvName;
    TextView tvCategory;
    TextView tvAddress;
    TextView tvContact;
    String name, category, address, contact;

    public LocationDetailView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_detail_location, this);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LocationDetailView,
                0, 0);

        tvName = findViewById(R.id.tv_location_detail_name);
        tvCategory = findViewById(R.id.tv_location_detail_category);
        tvAddress = findViewById(R.id.tv_location_detail_address);
        tvContact = findViewById(R.id.tv_location_detail_contact);

        try {
            name = a.getString(R.styleable.TopView_title);
            category = a.getString(R.styleable.TopView_title);
            address = a.getString(R.styleable.TopView_title);
            contact = a.getString(R.styleable.TopView_title);
        } finally {
            a.recycle();
        }
    }

    public void setName(String name) {
        tvName.setText(name);
    }
    public void setCategory(String category) {
        tvCategory.setText(category);
    }
    public void setAddress(String address) {
        tvAddress.setText(address);
    }
    public void setContact(String contact) {
        tvContact.setText(contact);
    }
}

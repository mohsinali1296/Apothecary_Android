package com.example.apothecary.views.initials;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apothecary.R;
import com.example.apothecary.views.view.login.LoginScreen;

public class OfflineActivity extends AppCompatActivity {

    @BindView(R.id.tryAgain_btn)
    Button tryAgain_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        ButterKnife.bind(this);
        TryAgain();
    }

    void TryAgain(){
        tryAgain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfflineActivity.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

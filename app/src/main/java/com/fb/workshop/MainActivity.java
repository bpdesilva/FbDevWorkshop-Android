package com.fb.workshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnEmail, btnMobile;
    String username, picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEmail = findViewById(R.id.btnEmail);
        btnMobile = findViewById(R.id.btnPhone);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    //Begin add interest activity
    private void loginRedirect() {
        Intent i = new Intent(MainActivity.this, AddInterests.class);

        if(username != null){
            i.putExtra("username",username);
        }

        if(picture != null){
            i.putExtra("picture",picture);
        }

        startActivity(i);
        finish();
    }

}

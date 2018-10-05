package com.fb.workshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fb.workshop.newsfeed.NewsActivity;
import com.robertlevonyan.views.chip.Chip;
import com.squareup.picasso.Picasso;

public class AddInterests extends AppCompatActivity {

    Chip tech, business, politics, enter, bit, ai, music, holly, bolly, block;
    TextView username;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interests);

        username = findViewById(R.id.tvGreeting);
        picture = findViewById(R.id.ivImage);

        tech = findViewById(R.id.btnTech);
        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passInterest("technology");

            }
        });

        business = findViewById(R.id.btnBusiness);
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passInterest("business");
            }
        });

        politics = findViewById(R.id.btnPolitics);
        politics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passInterest("politics");
            }
        });

        enter = findViewById(R.id.btnEntertainement);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passInterest("entertainment");
            }
        });

        bit = findViewById(R.id.btnBitcoin);
        bit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passInterest("bitcoin");
            }
        });

        ai = findViewById(R.id.btnAI);
        ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passInterest("artificial intelligence");
            }
        });

        music = findViewById(R.id.btnMusic);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passInterest("music");
            }
        });

        holly = findViewById(R.id.btnHollywood);
        holly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passInterest("hollywood");
            }
        });

        bolly = findViewById(R.id.btnBollywood);
        bolly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passInterest("bollywood");
            }
        });

        block = findViewById(R.id.btnBlockchain);
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passInterest("blockchain");
            }
        });

        Intent intent = getIntent();
        String d_username = intent.getStringExtra("username");
        String d_picture = intent.getStringExtra("picture");

        if (d_username != null) {
            username.setText("Welcome, " + d_username);
        }

        if (d_picture != null) {
            Picasso.with(AddInterests.this).load(d_picture).placeholder(R.drawable.userdummy).into(picture);
        }

    }

    private void passInterest(String interest) {

        //Log to FB Analytics here
        //Facebook Analytics

        //Start the news activity
        Intent i = new Intent(AddInterests.this, NewsActivity.class);
        i.putExtra("interests", interest);
        startActivity(i);
        finish();

    }


}

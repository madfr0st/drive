package main.drive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button signUpButton;
    private Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        signUpButton = (Button) findViewById(R.id.signUp);
        logInButton = (Button) findViewById(R.id.lonIn);

        final Intent signUpIntent = new Intent(this,SignupActivity.class);
        final Intent logInIntent = new Intent(this,loginActivity.class);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signUpIntent);
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(logInIntent);
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
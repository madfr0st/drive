package main.drive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;


    private TextView name;
    private TextView email;
    private TextView pass;
    private TextView pass1;
    private Button signUp;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        pass = (TextView) findViewById(R.id.password1);
        pass1 = (TextView) findViewById(R.id.password2);
        signUp = (Button) findViewById(R.id.signUp2);
        progressDialog = new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String Pass = pass.getText().toString();
                String Pass1 = pass1.getText().toString();

                if(!Name.isEmpty() && !Email.isEmpty() && !Pass.isEmpty() && !Pass1.isEmpty()){

                    if(Pass.equals(Pass1)){
                        Register(Name,Email,Pass);
                    }
                    else{
                        Toast.makeText(SignupActivity.this,"password not same",Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(SignupActivity.this,"Fields are Empty", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    private void Register(final String name, final String email, final String pass){

        progressDialog.setTitle("Registering user");
        progressDialog.setMessage("wait while we create");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();



        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    mUser = mAuth.getCurrentUser();
                    DatabaseReference ref = mDatabase.getReference().child("users").child(mUser.getUid());

                    HashMap<String,String> map = new HashMap<>();
                    map.put("name",name);
                    map.put("email",email);
                    map.put("pass",pass);

                    ref.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(SignupActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                                progressDialog.hide();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(SignupActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                    progressDialog.hide();
                }
            }
        });

    }


}
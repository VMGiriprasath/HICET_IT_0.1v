package com.renotech.app.hicetit.ForgetPassword;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.renotech.app.hicetit.LoginActivity;
import com.renotech.app.hicetit.R;
public class ForgetPassword extends AppCompatActivity {
    TextInputEditText emailx;
    MaterialButton btnforget;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_forget_password);
        //id find
        emailx=findViewById(R.id.forgetpassword_email);
        btnforget=findViewById(R.id.forgetpassword_btn);
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        //firebase
        firebaseAuth=FirebaseAuth.getInstance();
        btnforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                btnforget.setVisibility(View.INVISIBLE);
                String email=emailx.getText().toString();
                if (email.isEmpty())
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    btnforget.setVisibility(View.VISIBLE);
                    Toast.makeText(ForgetPassword.this, "Enter a Valid E-mail", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ForgetPassword.this, "Check you Inbox,\n reset Password link sent Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(ForgetPassword.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else if (task.getException()!=null)
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                 btnforget.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
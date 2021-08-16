package com.renotech.app.hicetit;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.renotech.app.hicetit.ForgetPassword.ForgetPassword;

public class LoginActivity extends AppCompatActivity {
   TextView link ;
   TextView forgetpassword;
   TextInputEditText email , password;
   MaterialButton loginbtn;
   FirebaseAuth firebaseAuth;
   ProgressBar progressBasr;
   CheckBox remember;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Firebase Instance
          firebaseAuth = FirebaseAuth.getInstance();
        //find viewby id
        link=findViewById(R.id.loginpage_signuplink);
        email=findViewById(R.id.email_login);
        password=findViewById(R.id.password_login);
        progressBasr=findViewById(R.id.procgress_circular);
        forgetpassword=findViewById(R.id.forgetpassword_link);
        loginbtn=findViewById(R.id.login_btn);
        remember=findViewById(R.id.checkbox);
      SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
      String checkbox=preferences.getString("remember","");
      if (checkbox.equals("true"))
      {
          Intent intent=new Intent(LoginActivity.this,Mainactivity.class);
          startActivity(intent);
          finish();

      }
      else if (checkbox.equals("false"))
      {
      }

     //
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                String pass=password.getText().toString();
                progressBasr.setVisibility(View.VISIBLE);
                loginbtn.setVisibility(View.INVISIBLE);
                if (mail.isEmpty())
                {
                    progressBasr.setVisibility(View.INVISIBLE);
                    loginbtn.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Enter E-mail id", Toast.LENGTH_SHORT).show();
                }
                else if (pass.isEmpty())
                {
                    progressBasr.setVisibility(View.INVISIBLE);
                    loginbtn.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Enter a Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Auth(mail,pass);

                }
            }

            private void Auth(String mail,String pass) {
                firebaseAuth.signInWithEmailAndPassword(mail,pass) .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent=new Intent(LoginActivity.this,Mainactivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(task.getException()!=null)
                        {
                            progressBasr.setVisibility(View.INVISIBLE);
                            loginbtn.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
     remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if (buttonView.isChecked())
             {
                 SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                 SharedPreferences.Editor editor=preferences.edit();
                 final SharedPreferences.Editor editor1 = editor.putString("remember", String.valueOf(true));
                 editor.apply();

             }
             else if (!buttonView.isChecked())
             {
                 SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                 SharedPreferences.Editor editor=preferences.edit();
                 SharedPreferences.Editor editor1 = editor.putString("remember", String.valueOf(false));
                 editor.apply();


             }
         }

     });
        //Forget password
     forgetpassword.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(LoginActivity.this, ForgetPassword.class);
             startActivity(intent);
         }
     });

    }

    public void link_methods(View view) {
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }

}
package com.renotech.app.hicetit;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.renotech.app.hicetit.Model_Firebase.Student;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText firstname;
    TextInputEditText lastname;
    TextInputEditText email;
    TextInputEditText passwords;
    ProgressBar progressBars;
    MaterialButton createaccountbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //find viewby id
        firstname = findViewById(R.id.Firstname_reg);
        lastname = findViewById(R.id.Lastname_reg);
        progressBars=findViewById(R.id.progressbar);
        email = findViewById(R.id.email_reg);
        createaccountbutton = findViewById(R.id.reg_btn);
        passwords = findViewById(R.id.password_reg);
      //Oreientation

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //check internet on activity start
         ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo==null||!networkInfo.isConnected()||!networkInfo.isAvailable())
        {
            Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.alart_dialogue);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().getAttributes().windowAnimations= android.R.style.Animation_Dialog;
            MaterialButton materialButton;
            materialButton = dialog.findViewById(R.id.notinternet_connection_btn);
            materialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });
            dialog.show();
        }

        //Firebase auth
        FirebaseAuth fbase = FirebaseAuth.getInstance();
        //Firebase Database
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        //
        DatabaseReference finalMDatabase = mDatabase;
        DatabaseReference finalMDatabase1 = mDatabase;
        DatabaseReference finalMDatabase2 = mDatabase;
        DatabaseReference finalMDatabase3 = mDatabase;
        progressBars.setVisibility(View.INVISIBLE);
        createaccountbutton.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       String Firstname = firstname.getText().toString();
                                                       String lstname = lastname.getText().toString();
                                                       String mail = email.getText().toString();
                                                       String pass = passwords.getText().toString();
                                                         progressBars.setVisibility(View.VISIBLE);
                                                         createaccountbutton.setVisibility(View.INVISIBLE);
                                                       if (Firstname.isEmpty())
                                                       {
                                                           progressBars.setVisibility(View.INVISIBLE);
                                                           createaccountbutton.setVisibility(View.VISIBLE);
                                                           Toast.makeText(RegisterActivity.this, "Enter Firstname", Toast.LENGTH_LONG).show();
                                                       }
                                                       else if (lstname.equals(""))
                                                       {
                                                           progressBars.setVisibility(View.INVISIBLE);
                                                           createaccountbutton.setVisibility(View.VISIBLE);
                                                           Toast.makeText(RegisterActivity.this, "Enter Lastname", Toast.LENGTH_LONG).show();
                                                       }
                                                       else if (mail.equals(""))
                                                       {
                                                           progressBars.setVisibility(View.INVISIBLE);
                                                           createaccountbutton.setVisibility(View.VISIBLE);
                                                           Toast.makeText(RegisterActivity.this, "Enter Valid Mobilenumber", Toast.LENGTH_LONG).show();
                                                       }
                                                       else if (pass.equals("") || pass.length() < 6)
                                                       {
                                                           progressBars.setVisibility(View.INVISIBLE);
                                                           createaccountbutton.setVisibility(View.VISIBLE);
                                                           Toast.makeText(RegisterActivity.this, "Enter a valid password !" +
                                                                   " \n Password must contains atleast 6 Character", Toast.LENGTH_LONG).show();
                                                       }

                                                       else
                                                           {
                                                           registereation(Firstname,lstname,mail,pass);
                                                           }

                                                   }
                                                   // Userdefined method
                                                   private void registereation(String Firstname, String lstname, String mail, String pass) {
                                                       fbase.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                           @Override
                                                           public void onComplete(@NonNull Task<AuthResult> task) {
                                                               if (task.isSuccessful()) {
                                                                   FirebaseUser currentUser = fbase.getCurrentUser();
                                                                   final String uid = currentUser.getUid();
                                                                   Student userInfo = new Student(firstname.getText().toString(), lastname.getText().toString(), email.getText().toString(), passwords.getText().toString());
                                                                   finalMDatabase2.child(uid).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                       @Override
                                                                       public void onComplete(@NonNull Task<Void> task) {
                                                                           if (task.isSuccessful())
                                                                           {
                                                                               progressBars.setVisibility(View.VISIBLE);
                                                                               createaccountbutton.setVisibility(View.INVISIBLE);
                                                                               Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
                                                                               startActivity(intent);
                                                                               finish();

                                                                           }
                                                                           else if (task.getException() != null)
                                                                           {
                                                                               progressBars.setVisibility(View.INVISIBLE);
                                                                               createaccountbutton.setVisibility(View.VISIBLE);
                                                                               Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                                           }
                                                                       }
                                                                   });
                                                               }
                                                               else if (task.getException() != null)
                                                               {
                                                                   progressBars.setVisibility(View.INVISIBLE);
                                                                   createaccountbutton.setVisibility(View.VISIBLE);
                                                                   Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                               }
                                                           }

                                                       });


                                                   }
        }
        );

    }

    public void link_method(View view) {
          Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
          startActivity( intent);
          finish();
     }
}

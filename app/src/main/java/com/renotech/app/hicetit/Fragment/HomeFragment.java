package com.renotech.app.hicetit.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.renotech.app.hicetit.Model_Firebase.Student;
import com.renotech.app.hicetit.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment
{
    TextView greet;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    TextView name;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    CircleImageView profile_home;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home, container,false);
       //Find view by id

        greet=(TextView) v.findViewById(R.id.greetings);
        name=(TextView)v.findViewById(R.id.loginname);
        profile_home=(CircleImageView)v.findViewById(R.id.profile_image_home);

        // greetings code
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if(timeOfDay >= 0 && timeOfDay < 12)
        {
            greet.setText("Good Morning, ");
        }
        else if(timeOfDay >= 12 && timeOfDay < 16)
        {
            greet.setText("Good Afternoon, ");
        }
        else if(timeOfDay >= 16 && timeOfDay < 21)
        {
            greet.setText("Good Evening, ");
        }else if(timeOfDay >= 21 && timeOfDay < 24)
        {
            greet.setText("Good Night, ");
        }
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        String Userid=firebaseUser.getUid();
        databaseReference.child(Userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student loginname=snapshot.getValue(Student.class);
                if (loginname !=null)
                {
                    String firstname=loginname.getFirstname();
                       name.setText(firstname);
                   }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference=FirebaseDatabase.getInstance().getReference().child("users");
        storageReference=firebaseStorage.getInstance().getReference().child("Profilepics");

        databaseReference.child(Userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()&& snapshot.getChildrenCount()>0)
                {
                    if (snapshot.hasChild("image"))
                    {
                        String image=snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profile_home);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;

    }
}
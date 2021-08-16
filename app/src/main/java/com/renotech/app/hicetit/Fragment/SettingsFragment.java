package com.renotech.app.hicetit.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.renotech.app.hicetit.LoginActivity;
import com.renotech.app.hicetit.Model_Firebase.Student;
import com.renotech.app.hicetit.R;
import static android.content.Context.MODE_PRIVATE;
//main class
public class SettingsFragment extends Fragment {
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    TextView name;
    MaterialButton logout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_settingspage, container,false);
      //find view by id
        name=(TextView)v.findViewById(R.id.logname);
        logout=(MaterialButton) v.findViewById(R.id.Logoutsettings) ;
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences preferences=getActivity().getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                SharedPreferences.Editor editor1 = editor.putString("remember", String.valueOf(false));
                editor.apply();
                getActivity().finish();
                 Intent intent=new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                 startActivity(intent);
            }
        });
       //firebase db
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        String Userid=firebaseUser.getUid();
        final TextView names=(TextView)v.findViewById(R.id.logname);
        databaseReference.child(Userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student loginname=snapshot.getValue(Student.class);
                if (loginname !=null)
                {
                    String firstname=loginname.getFirstname();
                    names.setText(firstname);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext().getApplicationContext(), "Check you Internet connectivity", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
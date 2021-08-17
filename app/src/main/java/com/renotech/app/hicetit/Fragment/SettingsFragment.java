package com.renotech.app.hicetit.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
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
import com.google.firebase.storage.StorageTask;
import com.renotech.app.hicetit.LoginActivity;
import com.renotech.app.hicetit.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import java.util.HashMap;
import de.hdodenhof.circleimageview.CircleImageView;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
//main class
public class SettingsFragment extends Fragment {
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    MaterialButton saveorupdate;
    CircleImageView imageview_profile_settings;
    MaterialButton logout;
   FirebaseAuth firebaseAuth;
   TextView tv;
   Uri imageuri;
   String myuri="";
   FirebaseStorage firebaseStorage;
    StorageTask uploadtask;
    StorageReference storageReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_settingspage, container,false);
      //find view by id
        imageview_profile_settings=(CircleImageView)v.findViewById(R.id.profile_image_settings);
        logout=(MaterialButton) v.findViewById(R.id.Logoutsettings) ;
        tv=(TextView)v.findViewById(R.id.tvupdate);
        saveorupdate=(MaterialButton)v.findViewById(R.id.update_save) ;
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
        //Firebase storage
        firebaseStorage=FirebaseStorage.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("users");
        storageReference=firebaseStorage.getInstance().getReference().child("Profilepics");

      //firebase db
        saveorupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadprofileimage();
            }
        });

        imageview_profile_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(getContext(),SettingsFragment.this);

            }

        });

        getuserinfo();
        return v;

    }
    private void uploadprofileimage()
    {
        final ProgressDialog progressDialog=new ProgressDialog(getView().getContext());
        progressDialog.setTitle("Set your profile picture");
        progressDialog.setMessage("Updating...");
        progressDialog.show();
        if (imageuri!=null)
        {
            final StorageReference fileref=storageReference.child(firebaseAuth.getCurrentUser().getUid()+".jpg");
            uploadtask=fileref.putFile(imageuri);

            uploadtask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }


                    return fileref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful())
                    {
                        Uri downloaduri=(Uri) task.getResult();
                        myuri=downloaduri.toString();
                        HashMap<String , Object >usermap=new HashMap<>();
                        usermap.put("image",myuri);
                        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).updateChildren(usermap);
                        progressDialog.dismiss();
                    }
                }
            });
        }
        else {
            progressDialog.dismiss();
            Toast.makeText(getView().getContext(), "Please select an Image to Update", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE&&resultCode==RESULT_OK&&data!=null)
        {
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageuri=result.getUri();
            imageview_profile_settings.setImageURI(imageuri);
        }
        else
        {
            Toast.makeText(getView().getContext(), "Error , Try again later", Toast.LENGTH_SHORT).show();
        }
    }

    private void getuserinfo() {
        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()&&snapshot.getChildrenCount()>0)
                {
                    if (snapshot.hasChild("image"))
                    {
                        String image=snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(imageview_profile_settings);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}
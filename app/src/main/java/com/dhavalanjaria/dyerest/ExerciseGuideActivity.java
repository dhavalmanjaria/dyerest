package com.dhavalanjaria.dyerest;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dhavalanjaria.dyerest.helpers.UriPathUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ExerciseGuideActivity extends BaseActivity {

    private static final String TAG = "ExerciseGuideActivity";
    private static final String EXTRA_EXERCISE_REF_URL = TAG + "ExerciseRefUrl";
    private Button mEditImageButton;
    private ImageView mImageView;
    private static final int REQUEST_PICK_IMAGE = 1000;
    private Uri mImageUri;
    public DatabaseReference mExerciseRef;

    public static Intent newIntent(Context context, String exerciseRefUrl) {
        Intent intent = new Intent(context, ExerciseGuideActivity.class);
        intent.putExtra(EXTRA_EXERCISE_REF_URL, exerciseRefUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_guide);

        mEditImageButton = findViewById(R.id.edit_exercise_guide_button);
        mImageView = findViewById(R.id.exercise_guide_image);

        String exerciseRefUrl = (String) getIntent().getSerializableExtra(EXTRA_EXERCISE_REF_URL);
        mExerciseRef = FirebaseDatabase.getInstance().getReferenceFromUrl(exerciseRefUrl);

        mExerciseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uriString = dataSnapshot.child("uri").getValue(String.class);
                if (uriString!= null) {
                    mImageUri = Uri.parse(uriString);

                    String path = UriPathUtil.getPathFromUri(ExerciseGuideActivity.this, mImageUri);


                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    mImageView.setImageBitmap(bitmap);

//                    if (ActivityCompat.checkSelfPermission(ExerciseGuideActivity.this,
//                            Manifest.permission.MANAGE_DOCUMENTS) != PackageManager.PERMISSION_GRANTED) {
//                        // Request missing manage documents permission
//                        ActivityCompat.requestPermissions(ExerciseGuideActivity.this,
//                                new String[] {Manifest.permission.MANAGE_DOCUMENTS},
//                                REQUEST_PICK_IMAGE);
//
//                    } else {
//                        mImageView.setImageURI(mImageUri);
//                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
                Log.e(TAG, databaseError.getDetails());
            }
        });

        mEditImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

                startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_PICK_IMAGE);
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageUri != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    Log.d(TAG, "Path from uri: " + mImageUri.getPath());

                    intent.setDataAndType(mImageUri, "image/*");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PICK_IMAGE) {
            if (grantResults.length == 1) {
                if ( grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    InputStream inputStream = null;
                    try {
                        inputStream = getContentResolver().openInputStream(mImageUri);
                    } catch (FileNotFoundException e) {
                        Log.e(TAG, e.getStackTrace().toString());
                        Toast.makeText(ExerciseGuideActivity.this, "Error loading file!",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    mImageView.setImageBitmap(bitmap);
                }
                else  {
                    Toast.makeText(ExerciseGuideActivity.this, "Permission to manage documents denied!",
                            Toast.LENGTH_SHORT)
                    .show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();


            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                Log.e(TAG, e.getStackTrace().toString());
            }

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            String insertImageReturn = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
            "exercise-guide.jpg", "JPG image");

            mImageView.setImageURI(uri);
            mExerciseRef.child("uri").setValue(uri.toString());
        }
    }



    @Override
    public Query getQuery() {
        return null;
    }
}

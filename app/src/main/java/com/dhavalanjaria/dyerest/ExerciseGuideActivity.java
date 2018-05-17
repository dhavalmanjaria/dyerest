package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dhavalanjaria.dyerest.fragments.EditDialogFragment;
import com.dhavalanjaria.dyerest.helpers.OnDialogCompletedListener;
import com.dhavalanjaria.dyerest.helpers.UriPathUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.content.Intent.ACTION_VIEW;

public class ExerciseGuideActivity extends BaseActivity {

    private static final String TAG = "ExerciseGuideActivity";
    private static final String EXTRA_EXERCISE_REF_URL = TAG + "ExerciseRefUrl";
    private Button mEditImageButton;
    private ImageView mImageView;
    private Button mAddLinkButton;
    private Button mViewLinkButton;
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

                // This is what I believe allows the URI to be stored somewhere so that the same URI
                // returns the same image.
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

                startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_PICK_IMAGE);
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageUri != null) {

                    // Since the intent flags do not work, I am doing this.
                    Uri fileUri = Uri.parse("file:///" + UriPathUtil.getPathFromUri(ExerciseGuideActivity.this, mImageUri));
                    Intent intent = new Intent(ACTION_VIEW, fileUri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.setDataAndType(fileUri, "image/*");
                    startActivityForResult(intent, 999);
                }
            }
        });

        mAddLinkButton = findViewById(R.id.add_link_button);
        mAddLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialogFragment.newInstance("Enter external URL/link", new OnDialogCompletedListener() {
                    @Override
                    public void onDialogComplete(String text) {
                        mExerciseRef.child("link").setValue(text);
                    }
                })
                .show(getSupportFragmentManager(), TAG);
            }
        });

        mViewLinkButton = findViewById(R.id.view_link_button);
        mViewLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExerciseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String link = (String) dataSnapshot.child("link").getValue();

                        Uri linkUri = Uri.parse(link);

                        Intent intent = new Intent(ACTION_VIEW, linkUri);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.getMessage());
                        Log.e(TAG, databaseError.getDetails());
                    }
                });
            }
        });
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


            // Here we create a copy of the image and upload the URI of that image. Whether or not
            // that is the best way to do this is debatable.
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            String insertImageReturn = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
            "exercise-guide-"+mExerciseRef.getKey()+".jpg", "JPG image");

            mImageView.setImageURI(Uri.parse(insertImageReturn));
            mExerciseRef.child("uri").setValue(uri.toString());
        }
    }



    @Override
    public Query getQuery() {
        return null;
    }
}

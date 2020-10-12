package in.ibridge.aizawl.loneitu;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


public class CameraReport extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private Button btnSelectImage,btnUpload;
    ProgressDialog progressDialog;
    private ImageView imageView;
    private TextView txtUploaded,txtSelect;
    private EditText txtLocation,txtReportDetails;
    private String downloadURL,userPhoneNumber;
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 234;
    private FirebaseAuth agricultureAuthentication;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    private CoordinatorLayout mylayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camerareportmenu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        progressDialog= new ProgressDialog(this);
        agricultureAuthentication=FirebaseAuth.getInstance();
        user=agricultureAuthentication.getCurrentUser();
        userPhoneNumber=user.getPhoneNumber().toString();
        txtSelect=findViewById(R.id.txtselect);
        txtSelect.setVisibility(View.VISIBLE);

        btnSelectImage=findViewById(R.id.btnSelectImage);
        btnSelectImage.setVisibility(View.VISIBLE);
        btnUpload=findViewById(R.id.btnUpload);
        imageView=findViewById(R.id.imageView);


        txtLocation=findViewById(R.id.txtLocation);
        txtReportDetails=findViewById(R.id.txtReport);
        txtUploaded=findViewById(R.id.txtUploaded);


        firebaseFirestore=FirebaseFirestore.getInstance();
        btnSelectImage.setOnClickListener(this);
        btnUpload.setOnClickListener(this);

        btnUpload.setVisibility(View.INVISIBLE);
        txtReportDetails.setVisibility(View.INVISIBLE);
        txtLocation.setVisibility(View.INVISIBLE);
        txtUploaded.setVisibility(View.INVISIBLE);

        mylayout=(CoordinatorLayout) findViewById(R.id.mylayout);
       /* DocumentReference dr = firebaseFirestore.collection("Alert").document("1");
        dr.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {

                if(documentSnapshot !=null && documentSnapshot.exists())
                {
                    //  Toast.makeText(getApplicationContext(),documentSnapshot.get("Message").toString(),Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar.make(mylayout,documentSnapshot.get("Message").toString(),Snackbar.LENGTH_INDEFINITE);
                    View snackbarView = snackbar.getView();
                    TextView txtView =(TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    txtView.setMaxLines(10);
                    txtView.setTextColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }

            }
        });
   */
       Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Snackbar.make(view, "Testing SnackBar Pop-up Message", Snackbar.LENGTH_LONG)
                //  .setAction("Action tur ", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View view) {
        final Vibrator vibrator = (Vibrator) CameraReport.this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);

        if(view==btnSelectImage)
        {
            txtUploaded.setVisibility(View.INVISIBLE);
            txtLocation.setText("");
            txtReportDetails.setText("");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        }
        else if(view==btnUpload)
        {

            if (ConnectivityCheck.isConnectedToNetwork(this)) {

                uploadImage();
            }
            else
            {

                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setTitle("No Network Detected");
                progressDialog.setMessage("Please try after activating Data Connection..");
                progressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(CameraReport.this,MainActivity.class));
                        finish();

                    }
                });

                progressDialog.show();

            }




        }
        else
        {

        }

    }

     public void uploadImage(){

         if(TextUtils.isEmpty(txtLocation.getText())==true)
         {
             txtLocation.setError("Cannot be Empty !...");
             return;
         }
         if( TextUtils.isEmpty(txtReportDetails.getText())==true)
         {
             txtReportDetails.setError("Cannot be Empty !...");
             return;
         }
       // thlalak firebase Storage-ah kan upload ang
         if (filePath != null) {
             //displaying a progress dialog while upload is going on
             final ProgressDialog progressDialog = new ProgressDialog(this);
             progressDialog.setTitle("Uploading");
             progressDialog.show();

             final StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("IncidentReport" + userPhoneNumber + "-" + System.currentTimeMillis() + "." + GetFileExtension(filePath));
             riversRef.putFile(filePath)
                     .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             //if the upload is successfull
                             //hiding the progress dialog
                             progressDialog.dismiss();

                             Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                             task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                 @Override
                                 public void onSuccess(Uri uri) {
                                     CameraModel cameraModel= new CameraModel(txtLocation.getText().toString(),txtReportDetails.getText().toString(),uri.toString(),"No","",userPhoneNumber);

                                     firebaseFirestore.collection("IncidentReport").add(cameraModel);

                                 }
                             });

                             //and displaying a success toast
                             btnUpload.setVisibility(View.INVISIBLE);
                             txtReportDetails.setVisibility(View.INVISIBLE);
                             txtLocation.setVisibility(View.INVISIBLE);
                             txtUploaded.setVisibility(View.VISIBLE);
                             Toast.makeText(getApplicationContext(), " File Uploaded Successfully !", Toast.LENGTH_LONG).show();

                         }
                     })
                     .addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception exception) {
                             //if the upload is not successfull
                             //hiding the progress dialog
                             progressDialog.dismiss();

                             //and displaying error message
                             Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     })
                     .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                             //calculating progress percentage
                             double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                             //displaying percentage in progress dialog
                             progressDialog.setMessage("Almost Complete .....   ");
                         }
                     });
         }
         //if there is not any file
         else {
             //you can display an error toast
         }
     }
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                btnUpload.setVisibility(View.VISIBLE);
                txtReportDetails.setVisibility(View.VISIBLE);
                txtLocation.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();

       /* DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            agricultureAuthentication.signOut();
            startActivity(new Intent(this, NewRegistration.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_cropcultivation) {

            startActivity(new Intent(CameraReport.this, CropCultivation.class));
             finish();

            // Handle the camera action
        } else if (id == R.id.nav_cropprotection) {
            startActivity(new Intent(CameraReport.this, CameraReport.class));
            finish();

        } else if (id == R.id.nav_dealers) {
            startActivity(new Intent(CameraReport.this, Dealers.class));
            finish();

        } else if (id == R.id.nav_news) {
            startActivity(new Intent(CameraReport.this, News.class));
            finish();

        } else if (id == R.id.nav_success_stories) {
            startActivity(new Intent(CameraReport.this, SuccessStories.class));
            finish();

        }
        else if (id == R.id.nav_rainfall) {
            startActivity(new Intent(CameraReport.this, Rainfallnew.class));
            finish();

        }

        else if (id == R.id.nav_areaproduction) {
            startActivity(new Intent(CameraReport.this, AreaProductionTable.class));
            finish();

        }
        else if (id == R.id.nav_call_official) {
            startActivity(new Intent(CameraReport.this, OfficialDirectory.class));
            finish();

        }
        /*else if (id == R.id.nav_cropcalendar) {
            startActivity(new Intent(CameraReport.this, CropCalendars.class));
            finish();

        }*/
       /* else if (id == R.id.nav_marketing) {
            startActivity(new Intent(CameraReport.this, Marketing.class));
            finish();

        }*/
        else if (id == R.id.nav_logout) {
            agricultureAuthentication.signOut();
            startActivity(new Intent(this,NewRegistration.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


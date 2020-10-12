package in.ibridge.aizawl.loneitu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SuccessStories extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    WebView webViewDetails;
    ProgressDialog progressDialog,progressDialog1;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth agricultureAuthentication;
    CoordinatorLayout mylayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.successstoriesmenu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        agricultureAuthentication=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        mylayout= (CoordinatorLayout) findViewById(R.id.mylayout);
      /*  DocumentReference dr = firebaseFirestore.collection("Alert").document("1");
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

        // Menus
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
        webViewDetails=findViewById(R.id.webView);
        progressDialog= new ProgressDialog(this);
        progressDialog1= new ProgressDialog(this);

        if(ConnectivityCheck.isConnectedToNetwork(this))
        {

        }

        else
        {
            progressDialog.dismiss();
            webViewDetails.setVisibility(View.INVISIBLE);
            progressDialog1.setCancelable(false);
            progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog1.setTitle("No Network Detected");
            progressDialog1.setMessage("Please try after activating Data Connection..");
            progressDialog1.setIcon(R.drawable.ibridge);
            progressDialog1.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    startActivity(new Intent(SuccessStories.this,MainActivity.class));
                    finish();

                }
            });

            progressDialog1.show();
        }
        webViewDetails.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.setTitle("Loading Loneitu ");
                progressDialog.setMessage("Please Wait....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressDialog.dismiss();
            }
        });


        webViewDetails.getSettings().setJavaScriptEnabled(true);
        webViewDetails.getSettings().setSupportZoom(true);
        webViewDetails.getSettings().setBuiltInZoomControls(true);
        webViewDetails.getSettings().setDisplayZoomControls(true);
        webViewDetails.loadUrl("https://youtube.com/channel/UCNDWB1BMi1cmxE4wTKEniBA");
        //webViewDetails.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
        /*
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
*/
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
            startActivity(new Intent(this,NewRegistration.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cropcultivation) {

            startActivity(new Intent(SuccessStories.this, CropCultivation.class));
            finish();

            // Handle the camera action
        } else if (id == R.id.nav_cropprotection) {
            startActivity(new Intent(SuccessStories.this, CameraReport.class));
            finish();

        } else if (id == R.id.nav_dealers) {
            startActivity(new Intent(SuccessStories.this, Dealers.class));
            finish();

        } else if (id == R.id.nav_news) {
            startActivity(new Intent(SuccessStories.this, News.class));
            finish();

        } else if (id == R.id.nav_success_stories) {
            startActivity(new Intent(SuccessStories.this, SuccessStories.class));
            finish();

        }
        else if (id == R.id.nav_rainfall) {
            startActivity(new Intent(SuccessStories.this, Rainfallnew.class));
            finish();

        }

        else if (id == R.id.nav_areaproduction) {
            startActivity(new Intent(SuccessStories.this, AreaProductionTable.class));
            finish();

        }
        else if (id == R.id.nav_call_official) {
            startActivity(new Intent(SuccessStories.this, OfficialDirectory.class));
            finish();

        }
        /*else if (id == R.id.nav_cropcalendar) {
            startActivity(new Intent(SuccessStories.this, CropCalendars.class));
            finish();

        }*/
       /* else if (id == R.id.nav_marketing) {
            startActivity(new Intent(this, Marketing.class));
            finish();

        }*/
        else if (id == R.id.nav_logout) {
            agricultureAuthentication.signOut();
            startActivity(new Intent(SuccessStories.this,NewRegistration.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

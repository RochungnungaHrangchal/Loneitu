package in.ibridge.aizawl.loneitu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class AreaProductionTable extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    WebView webViewDetails;
    ProgressDialog progressDialog;
    FirebaseAuth agricultureAuthentication;
    FirebaseFirestore firebaseFirestore;
    CoordinatorLayout mylayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areaproductiontablemenu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        progressDialog= new ProgressDialog(this);
        webViewDetails=findViewById(R.id.webViewDetails);
        webViewDetails.getSettings().setJavaScriptEnabled(true);
        webViewDetails.getSettings().setSupportZoom(true);
        webViewDetails.getSettings().setBuiltInZoomControls(true);
        webViewDetails.getSettings().setDisplayZoomControls(true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //  FloatingActionButton fab = findViewById(R.id.fab);

        mylayout=findViewById(R.id.mylayout);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        webViewDetails.loadUrl("file:///android_asset/AreaProductionTable.html");
      /*
        if (ConnectivityCheck.isConnectedToNetwork(this)) {
            //Show the connected screen
            webViewDetails.loadUrl("http://loneitu.nic.in/assets/AreaProductionTable.html");
        } else {
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("No Network Detected");
            progressDialog.setMessage("Please try after activating Data Connection..");
            progressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    startActivity(new Intent(AreaProductionTable.this,MainActivity.class));
                    finish();

                }
            });

            progressDialog.show();

        } */



       // webViewDetails.loadUrl("file:///android_asset/Error.html");
        webViewDetails.setWebViewClient(new WebViewClient(){


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.setTitle("Loading Area and Production ");
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


            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
             super.onReceivedError(view,request,error);

            }

            @Override
            public  void onReceivedError(WebView view,int errcode,String description,String failingURL)
            {
               super.onReceivedError(view,errcode,description,failingURL);
            }
            @Override
            public void onReceivedHttpError(WebView viewss, WebResourceRequest resourceRequest, WebResourceResponse resourceResponse)
            {
               // webViewDetails.loadUrl("file:///android_asset/Error.html");
                super.onReceivedHttpError(viewss,resourceRequest,resourceResponse);
            }

        });




    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,MainActivity.class));
        finish();

      super.onBackPressed();
      //finish();


    }


    // Menusss
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

            startActivity(new Intent(this, CropCultivation.class));
            finish();

            // Handle the camera action
        } else if (id == R.id.nav_cropprotection) {
            startActivity(new Intent(this, CameraReport.class));
            finish();

        } else if (id == R.id.nav_dealers) {
            startActivity(new Intent(this, Dealers.class));
            finish();

        } else if (id == R.id.nav_news) {
            startActivity(new Intent(this, News.class));
            finish();

        } else if (id == R.id.nav_success_stories) {
            startActivity(new Intent(this, SuccessStories.class));
            finish();

        }
        else if (id == R.id.nav_rainfall) {
            startActivity(new Intent(this, Rainfallnew.class));
            finish();

        }

        else if (id == R.id.nav_areaproduction) {
            startActivity(new Intent(this, AreaProductionTable.class));
            finish();

        }
        else if (id == R.id.nav_call_official) {
            startActivity(new Intent(this, OfficialDirectory.class));
            finish();

        }
        /*else if (id == R.id.nav_cropcalendar) {
            startActivity(new Intent(this, CropCalendars.class));
            finish();

        }*/
       /* else if (id == R.id.nav_marketing) {
            startActivity(new Intent(this, Marketing.class));
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

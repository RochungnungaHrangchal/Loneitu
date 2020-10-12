package in.ibridge.aizawl.loneitu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class Dealers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    WebView webViewDetails;
    ProgressDialog progressDialog1,progressDialog;
    String categoryType;
    Spinner spinnerDealer;
    FirebaseAuth agricultureAuthentication;
    CoordinatorLayout mylayout;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dealersmenu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        webViewDetails = findViewById(R.id.webViewDealer);
        progressDialog1 = new ProgressDialog(this);
        progressDialog = new ProgressDialog(this);
        spinnerDealer=findViewById(R.id.spinnerDealer);
        mylayout=(CoordinatorLayout) findViewById(R.id.mylayout);
        firebaseFirestore=FirebaseFirestore.getInstance();

        if(ConnectivityCheck.isConnectedToNetwork(this))
        {

        }
        else
        {
            progressDialog1.dismiss();
            progressDialog1.setCancelable(false);
            progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog1.setTitle("No Network Detected");
            progressDialog1.setMessage("Please try after activating Data Connection..");
            progressDialog1.setIcon(R.drawable.ibridge);
            progressDialog1.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    startActivity(new Intent(Dealers.this,MainActivity.class));
                    finish();

                }
            });

            progressDialog1.show();
        }
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
      /*  FloatingActionButton fab = findViewById(R.id.fab);
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
        spinnerDealer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Select Dealer Category"))
                {

                }
                else
                {
                    categoryType = adapterView.getItemAtPosition(i).toString();
                    webPageView();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }



   // Privaet Function hetah hian
    private void webPageView()
    {
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
                progressDialog.setIcon(R.drawable.ibridge);
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
               // view.loadUrl("about:blank");
               // Toast.makeText(getApplicationContext(),"Cannot Load the File",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        webViewDetails.getSettings().setJavaScriptEnabled(true);
        webViewDetails.getSettings().setSupportZoom(true);
        webViewDetails.getSettings().setBuiltInZoomControls(true);
        webViewDetails.getSettings().setDisplayZoomControls(true);

        if(categoryType.equals("Pesticides"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/Pesticides.html");
        }
        else if (categoryType.equals("Insecticides"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/Insecticides.html");
        }
        else if (categoryType.equals("Fertilizers"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/Fertilizer.html");
        }

        else if (categoryType.equals("Seeds"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/Seeds.html");
        }
        else if (categoryType.equals("Machinery Rotovator"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryRotovator.html");
        }
        else if (categoryType.equals("Machinery Power Tillers"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryPowerTillers.html");
        }
        else if (categoryType.equals("Machinery Paddy Weeder"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryPaddyWeeder.html");
        }
        else if (categoryType.equals("Machinery Paddy Transplanter"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryPaddyTransplanter.html");
        }
        else if (categoryType.equals("Machinery Self-Propelled Paddy"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachinerySelfPropelledPaddy.html");
        }
        else if (categoryType.equals("Machinery Terracer Blade Leveller"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryTerracerBladeLeveller.html");
        }
        else if (categoryType.equals("Machinery Tractor"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryTractor.html");
        }
        else if (categoryType.equals("Machinery Trailer"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryTrailer.html");
        }
        else if (categoryType.equals("Machinery Disc Plough"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryDiscPlough.html");
        }
        else if (categoryType.equals("Machinery Knapsack Sprayer"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryKnapsackSprayer.html");
        }
        else if (categoryType.equals("Machinery Gur Boiling Plant"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryGurBoilingPlant.html");
        }
        else if (categoryType.equals("Machinery Offset Disc Harrow"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryOffsetDiscHarrow.html");
        }
        else if (categoryType.equals("Machinery Mini Power Tiller(below 8 HP)"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryMiniPowerTillerBelow8HP.html");
        }
        else if (categoryType.equals("Machinery Brush Cutter"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryBrushCutter.html");
        }
        else if (categoryType.equals("Machinery Cultivator"))
        {
            webViewDetails.loadUrl("http://www.loneitu.nic.in/assets/MachineryCultivator.html");
        }

        else{}

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();

      /*  DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
            startActivity(new Intent(this, NewRegistration.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cropcultivation) {

            startActivity(new Intent(Dealers.this, CropCultivation.class));
           finish();

            // Handle the camera action
        } else if (id == R.id.nav_cropprotection) {
            startActivity(new Intent(Dealers.this, CameraReport.class));
            finish();

        } else if (id == R.id.nav_dealers) {
            startActivity(new Intent(Dealers.this, Dealers.class));
            finish();

        } else if (id == R.id.nav_news) {
            startActivity(new Intent(Dealers.this, News.class));
            finish();

        } else if (id == R.id.nav_success_stories) {
            startActivity(new Intent(Dealers.this, SuccessStories.class));
            finish();

        }
        else if (id == R.id.nav_rainfall) {
            startActivity(new Intent(Dealers.this, Rainfallnew.class));
            finish();

        }

        else if (id == R.id.nav_areaproduction) {
            startActivity(new Intent(Dealers.this, AreaProductionTable.class));
            finish();

        }
        else if (id == R.id.nav_call_official) {
            startActivity(new Intent(Dealers.this, OfficialDirectory.class));
            finish();

        }
        /*else if (id == R.id.nav_cropcalendar) {
            startActivity(new Intent(Dealers.this, CropCalendars.class));
            finish();

        }*/

        /*else if (id == R.id.nav_marketing) {
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

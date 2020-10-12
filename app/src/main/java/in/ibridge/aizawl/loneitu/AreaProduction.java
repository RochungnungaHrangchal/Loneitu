package in.ibridge.aizawl.loneitu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import static com.github.mikephil.charting.utils.ColorTemplate.VORDIPLOM_COLORS;

public class AreaProduction extends AppCompatActivity implements View.OnClickListener , NavigationView.OnNavigationItemSelectedListener{
    PieChart pieChartArea;
    Button btnTableForm;
    PieChart pieChartProduction;
    List<PieEntry> entriesArea= new ArrayList<>();
    List<PieEntry> entriesProduction= new ArrayList<>();
    PieDataSet pieDataSetProduction;
    PieData pieDataProduction;
    PieDataSet pieDataSetArea;
    PieData pieDataArea;
    Button btndetails;
   ProgressDialog progressDialog;
   WebView webViewDetails;
    Spinner spinnerCategory;
    String categoryType;
    String urlString;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth agricultureAuthentication;

    private CoordinatorLayout mylayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areaproductiontablemenu);
        btnTableForm=findViewById(R.id.btnTableForm);
        btnTableForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AreaProduction.this,AreaProductionTable.class));
               // finish();
            }
        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        pieChartArea=(PieChart) findViewById(R.id.chart1);
        pieChartProduction=(PieChart) findViewById(R.id.chart2);
      //  mylayout=(CoordinatorLayout) findViewById(R.id.mylayout);
       // btndetails =(Button) findViewById(R.id.btnDetails);
        webViewDetails=(WebView) findViewById(R.id.webViewCropCalendar);

        spinnerCategory= findViewById(R.id.spinnerCategory);
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Select the Category"))
                {

                }
                else {
                    categoryType = adapterView.getItemAtPosition(i).toString();
                    webPageView();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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


        checkInternetCOnnection(); // Checking for Availablility of Internet


    }
    // Fucntions Starts From Here
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
       // webViewDetails.loadUrl("http://loneitu.nic.in/assets/AreaProductionTable.html");

        if(categoryType.equals("By Crop Name"))
        {
           // webViewDetails.loadUrl("http://loneitu.nic.in/assets/AreaProductionTable.html");
           webViewDetails.loadUrl("http://loneitu.nic.in/assets/AreaProductionReal1.html");
        }
        else if (categoryType.equals("By District Wise"))
        {
            webViewDetails.loadUrl("http://loneitu.nic.in/assets/AreaProductionRealDistrictWise.html");
        }
        else if (categoryType.equals("State Consolidated"))
        {
            webViewDetails.loadUrl("http://loneitu.nic.in/assets/AreaProductionRealMizoram.html");
        }

        else{}


    }


   /* private void snackBarDisplay()
    {
        DocumentReference dr = firebaseFirestore.collection("Alert").document("1");
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

    }*/

    private  void checkInternetCOnnection()
    {
        if (ConnectivityCheck.isConnectedToNetwork(this)) {
            //Show the connected screen
        } else {
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("No Network Detected");
            progressDialog.setMessage("Please try after activating Data Connection..");
            progressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                   // finish();

                }
            });

            progressDialog.show();

        }
    }

   private  void  constructPieChartProduction()
   {
       entriesProduction.add(new PieEntry(12.01f, "Rice"));
       entriesProduction.add(new PieEntry(14.22f, "Maize"));
       entriesProduction.add(new PieEntry(13.33f, "Topioca"));
       entriesProduction.add(new PieEntry(15.09f, "Onion"));
       entriesProduction.add(new PieEntry(11.21f, "Pulses"));
       entriesProduction.add(new PieEntry(11.45f, "OilSeed"));
       entriesProduction.add(new PieEntry(45.07f, "Cotton"));
       entriesProduction.add(new PieEntry(11.21f, "Tobacco"));
       entriesProduction.add(new PieEntry(11.45f, "Sugarcane"));
       entriesProduction.add(new PieEntry(45.07f, "Potato"));

       // PieChart for Production
       pieDataSetProduction= new PieDataSet(entriesProduction,"");
       pieDataProduction= new PieData(pieDataSetProduction);
       pieDataSetProduction.setValueTextColor(R.color.button_pressed);
       pieDataSetProduction.setValueTextSize(16f);
       pieDataSetProduction.setSliceSpace(3);
       pieDataSetProduction.setColors(ColorTemplate.MATERIAL_COLORS);
       pieDataSetProduction.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

       pieChartProduction.setData(pieDataArea);
       pieChartProduction.setCenterTextSize(17);
       pieChartProduction.setCenterText(" Production \n(in M Tonnes)");


       pieChartProduction.setEntryLabelColor(R.color.colorPrimaryDark);
       pieChartProduction.setDescription(null);
       pieChartProduction.getLegend().setEnabled(false);
       pieChartProduction.setEntryLabelTextSize(16f);

       pieChartProduction.animateX(4000);

      // pieChartProduction
       pieChartProduction.invalidate();


   }
    private  void constructPieChartArea()
    {
        entriesArea.add(new PieEntry(12.01f, "Rice"));
        entriesArea.add(new PieEntry(14.22f, "Maize"));
        entriesArea.add(new PieEntry(13.33f, "Topioca"));
        entriesArea.add(new PieEntry(15.09f, "Onion"));
        entriesArea.add(new PieEntry(11.21f, "Pulses"));
        entriesArea.add(new PieEntry(11.45f, "OilSeed"));
        entriesArea.add(new PieEntry(45.07f, "Cotton"));
        entriesArea.add(new PieEntry(11.21f, "Tobacco"));
        entriesArea.add(new PieEntry(11.45f, "Sugarcane"));
        entriesArea.add(new PieEntry(45.07f, "Potato"));

        pieDataSetArea= new PieDataSet(entriesArea,"");
        pieDataArea= new PieData(pieDataSetArea);
        pieDataSetArea.setValueTextColor(R.color.button_pressed);
        pieDataSetArea.setValueTextSize(16f);
        pieDataSetArea.setSliceSpace(3);
        pieDataSetArea.setColors(VORDIPLOM_COLORS);
        pieDataSetArea.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        pieChartArea.setData(pieDataArea);
        pieChartArea.setCenterTextSize(17);
        pieChartArea.setCenterText(" Area \n( in ha)");


        pieChartArea.setEntryLabelColor(R.color.colorPrimaryDark);
        pieChartArea.setDescription(null);
        pieChartArea.getLegend().setEnabled(false);
        pieChartArea.setEntryLabelTextSize(16f);

        pieChartArea.animateX(4000);
        pieChartArea.invalidate();
    }



    @Override
    public void onClick(View view) {

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
        webViewDetails.loadUrl("http://loneitu.nic.in/assets/fertilizer.html");
        webViewDetails.setVisibility(View.VISIBLE);

    }


    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,MainActivity.class));
        finish();

     /*   DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
            startActivity(new Intent(this, AreaProduction.class));
            finish();

        }
        else if (id == R.id.nav_call_official) {
            startActivity(new Intent(this, OfficialDirectory.class));
            finish();

        }
       /* else if (id == R.id.nav_cropcalendar) {
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

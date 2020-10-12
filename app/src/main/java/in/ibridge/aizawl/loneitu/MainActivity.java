package in.ibridge.aizawl.loneitu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import com.google.android.material.drawable.*;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;

import android.view.Menu;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

      // Constructing PieChart
    PieChart pieChart;
    List<PieEntry> pieEntryList = new ArrayList<>();
    PieDataSet pieDataSet;
    PieData pieData;
    TextView txtUser;


    private FirebaseAuth agricultureAuthentication;
    FirebaseFirestore firebaseFirestore;
    private CoordinatorLayout mylayout;
    FirebaseUser currentUser;

    @Override
    public void onStart() {
        super.onStart();
       /* FirebaseUser currentUser = agricultureAuthentication.getCurrentUser();
        if (currentUser != null) {

          String usersname=currentUser.getPhoneNumber().toString();

            txtUser.setText((CharSequence) usersname);

            Toast.makeText(getApplicationContext(),"USer :" + currentUser.getPhoneNumber().toString(),Toast.LENGTH_LONG).show();


        }*/
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        agricultureAuthentication=FirebaseAuth.getInstance();
        final Vibrator vibrator = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        firebaseFirestore= FirebaseFirestore.getInstance();
        mylayout=(CoordinatorLayout) findViewById(R.id.mylayout);
        // txtUser=(TextView) findViewById(R.id.txtUser);

        pieChart=(PieChart) findViewById(R.id.chart1);
         DocumentReference dr = firebaseFirestore.collection("Alert").document("1");
         dr.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

               if(documentSnapshot !=null && documentSnapshot.exists())
               {
                 //  Toast.makeText(getApplicationContext(),documentSnapshot.get("Message").toString(),Toast.LENGTH_LONG).show();
                   Snackbar snackbar = Snackbar.make(mylayout,documentSnapshot.get("Message").toString(),Snackbar.LENGTH_INDEFINITE);
                   View snackbarView = snackbar.getView();
                   TextView txtView =(TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                  // TextView txtView =(TextView) snackbarView.findViewById(androidx.R.id.snackbar_text);
                  // snackbarView.findViewById(android.support.design.R.id.)
                   txtView.setMaxLines(10);
                   txtView.setTextColor(getResources().getColor(R.color.colorPrimary));
                   snackbar.show();
               }

            }
        });
         Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Populating PieChart

        pieEntryList.add(new PieEntry(12.5f,"Thil tul Report-na"));
        pieEntryList.add(new PieEntry(12.5f,"Thlai Chin Dan"));
        pieEntryList.add(new PieEntry(12.5f,"Dealers"));
        pieEntryList.add(new PieEntry(12.5f,"News"));
        pieEntryList.add(new PieEntry(12.5f,"Hlawhtlinna Chanchin"));
        pieEntryList.add(new PieEntry(12.5f,"Ruahtui tlak dan"));
        pieEntryList.add(new PieEntry(12.5f,"Official Directory"));
        pieEntryList.add(new PieEntry(12.5f,"Thlai Tharchhuah Zat"));
       pieEntryList.subList(1,2);
        // pieEntryList.add(new PieEntry(12.5f,"Crop Calendars"));
       // pieEntryList.add(new PieEntry(12.5f,"Marketing"));



        pieDataSet = new PieDataSet(pieEntryList,"");

        pieData = new PieData(pieDataSet);

        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieDataSet.setSliceSpace(5);
        pieData.setValueTextSize(1);
        pieDataSet.setValueTextColor(R.color.colorPrimaryDark);

       // pieDataSet.setValues(null);

        pieChart.setData(pieData);

        pieChart.setCenterText("Agriculture Dept. Govt. of Mizoram");
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(45);
        pieChart.setHoleColor(ColorTemplate.getHoloBlue());
        pieChart.setHoleRadius(35);
        pieChart.setCenterTextSize(14);

       // pieChart.clearValues();
        pieChart.setDescription(null);
        pieChart.setEntryLabelTextSize(10);

        pieChart.setEntryLabelColor(R.color.colorPrimaryDark);
        pieChart.getLegend().setEnabled(false);
        pieChart.animateX(400);
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                vibrator.vibrate(100);

                int position = (int) h.getX();

                if(position==0)
                {
                    startActivity(new Intent(MainActivity.this,CameraReport.class));
                    finish();
                }

                if(position==1)
                {
                    startActivity(new Intent(MainActivity.this,CropCultivation.class));
                    finish();
                }
                if(position==2)
                {
                    startActivity(new Intent(MainActivity.this,Dealers.class));
                    finish();
                }
                if(position==3)
                {
                    startActivity(new Intent(MainActivity.this,News.class));
                    finish();
                }
                if(position==4)
                {
                    startActivity(new Intent(MainActivity.this,SuccessStories.class));
                    finish();
                }
                if(position==5)
                {
                    startActivity(new Intent(MainActivity.this,Rainfallnew.class));
                    finish();
                }
                if(position==6)
                {
                    startActivity(new Intent(MainActivity.this,OfficialDirectory.class));
                    finish();
                }
                if(position==7)
                {
                    startActivity(new Intent(MainActivity.this,AreaProductionTable.class));
                    finish();
                }

                if(position==8)
                {
                    startActivity(new Intent(MainActivity.this,CropCalendars.class));
                    finish();
                }
                if(position==9)
                {
                    startActivity(new Intent(MainActivity.this,Marketing.class));
                    finish();
                }


            }

            @Override
            public void onNothingSelected() {

            }
        });
        /*FirebaseUser currentUser = agricultureAuthentication.getCurrentUser();
        if (currentUser != null) {

            String usersname=currentUser.getPhoneNumber().toString();

            txtUser.setText((CharSequence) usersname);

            Toast.makeText(getApplicationContext(),"USer :" + currentUser.getPhoneNumber().toString(),Toast.LENGTH_LONG).show();


        }*/

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Do You Want to Exit?")
                .setMessage("Visit our Website  :  http://loneitu.nic.in  ")



                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //
                            }
                        }
                )

                .create()
                .show();




           /*   Snackbar snackbarExit = Snackbar.make(mylayout,"(Swipe --> to CANCEL)                                  Do you want to Exit?",Snackbar.LENGTH_INDEFINITE);



              snackbarExit.setAction("YES", new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                      finish();
                     // Toast.makeText(getApplicationContext(),"Actioned",Toast.LENGTH_LONG);
                  }
              });
             // snackbarExit.
              snackbarExit.setActionTextColor(Color.GREEN);
              snackbarExit.show();/*

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cropcultivation) {

            startActivity(new Intent(MainActivity.this, CropCultivation.class));
           finish();

            // Handle the camera action
        } else if (id == R.id.nav_cropprotection) {
            startActivity(new Intent(MainActivity.this, CameraReport.class));
            finish();

        } else if (id == R.id.nav_dealers) {
            startActivity(new Intent(MainActivity.this, Dealers.class));
            finish();

        } else if (id == R.id.nav_news) {
            startActivity(new Intent(MainActivity.this, News.class));
            finish();

        } else if (id == R.id.nav_success_stories) {
            startActivity(new Intent(MainActivity.this, SuccessStories.class));
            finish();

        }
        else if (id == R.id.nav_rainfall) {
            startActivity(new Intent(MainActivity.this, Rainfallnew.class));
            finish();

        }

        else if (id == R.id.nav_areaproduction) {
            startActivity(new Intent(MainActivity.this, AreaProductionTable.class));
            finish();

        }
        else if (id == R.id.nav_call_official) {
            startActivity(new Intent(MainActivity.this, OfficialDirectory.class));
            finish();

        }
        /*else if (id == R.id.nav_cropcalendar) {
            startActivity(new Intent(MainActivity.this, CropCalendars.class));
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

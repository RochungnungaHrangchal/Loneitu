package in.ibridge.aizawl.loneitu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class OfficialDirectory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ListView listView;
    List<String> list = new ArrayList<>();
    String contactNos;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog,progressDialog1;
    FirebaseAuth agricultureAuthentication;
    String designation;
    CoordinatorLayout mylayout;
    // GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.officialdirectorymenu);
        progressDialog = new ProgressDialog(this);
        progressDialog1 = new ProgressDialog(this);

        listView = findViewById(R.id.listView);
        firebaseFirestore = FirebaseFirestore.getInstance();
        agricultureAuthentication = FirebaseAuth.getInstance();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text2, list);
        list.clear();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //   FloatingActionButton fab = findViewById(R.id.fab);

        mylayout = findViewById(R.id.mylayout);
        Snackbar snackbar = Snackbar.make(mylayout,"Tap/Press on the List to Call ",Snackbar.LENGTH_INDEFINITE);
        View snackbarView = snackbar.getView();
        TextView txtView =(TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        // snackbarView.findViewById(android.support.design.R.id.)
        txtView.setMaxLines(10);
        txtView.setTextColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();

        /*DocumentReference dr = firebaseFirestore.collection("Alert").document("1");
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
        });*/
     /*   fab.setOnClickListener(new View.OnClickListener() {
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

        progressDialog.setTitle("Loading Official Directory....");
        progressDialog.setMessage("Please wait...");
        progressDialog.setIcon(R.drawable.ibridge);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

if(ConnectivityCheck.isConnectedToNetwork(this))
        {
            CollectionReference cr = firebaseFirestore.collection("Directory");
            cr.orderBy("Priority", Query.Direction.ASCENDING).get()
                //cr.orderBy(FieldPath.documentId(), Query.Direction.ASCENDING);

                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot qs) {

                        if (!qs.isEmpty()) {
                            list.clear();
                            for (DocumentSnapshot ds : qs) {
                                // Toast.makeText(getApplicationContext(),"New Line",Toast.LENGTH_LONG).show();
                                // Toast.makeText(getApplicationContext(),"Priority :" +  ds.getString("Priority").toString(),
                                //  Toast.LENGTH_LONG).show();
                                list.add(ds.get("Designation").toString() + "    --    " + ds.get("Phonenos").toString());
                                listView.setAdapter(arrayAdapter);
                            }
                            progressDialog.dismiss();

                        }

                    }
                });

    }
else
   {
       progressDialog.dismiss();
       progressDialog1.setCancelable(false);
       progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
       progressDialog1.setTitle("No Network Detected");
       progressDialog1.setMessage("Please try after activating Data Connection..");
       progressDialog1.setIcon(R.drawable.ibridge);
       progressDialog1.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

               startActivity(new Intent(OfficialDirectory.this,MainActivity.class));
               finish();

           }
       });

       progressDialog1.show();
   }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

              /*  if(i %2==0)
                {
                    view.setBackgroundColor(Color.BLUE);
                }
                else
                {
                    view.setBackgroundColor(Color.CYAN);
                }*/

                designation=adapterView.getItemAtPosition(i).toString();
                int strlength=designation.length()-11;
                int strlenoffset=designation.length();


                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:+91"+designation.substring(strlength,strlenoffset).toString()  ));
                startActivity(call);


                // Toast.makeText(getApplicationContext(),"My Designation :" + designation.substring(strlength,strlenoffset),Toast.LENGTH_LONG).show();
            }
        });


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

            startActivity(new Intent(OfficialDirectory.this, CropCultivation.class));
            finish();

            // Handle the camera action
        } else if (id == R.id.nav_cropprotection) {
            startActivity(new Intent(OfficialDirectory.this, CameraReport.class));
            finish();

        } else if (id == R.id.nav_dealers) {
            startActivity(new Intent(OfficialDirectory.this, Dealers.class));
            finish();

        } else if (id == R.id.nav_news) {
            startActivity(new Intent(OfficialDirectory.this, News.class));
            finish();

        } else if (id == R.id.nav_success_stories) {
            startActivity(new Intent(OfficialDirectory.this, SuccessStories.class));
            finish();

        }
        else if (id == R.id.nav_rainfall) {
            startActivity(new Intent(OfficialDirectory.this, Rainfallnew.class));
            finish();

        }

        else if (id == R.id.nav_areaproduction) {
            startActivity(new Intent(OfficialDirectory.this, AreaProductionTable.class));
            finish();

        }
        else if (id == R.id.nav_call_official) {
            startActivity(new Intent(OfficialDirectory.this, OfficialDirectory.class));
            finish();

        }
       /* else if (id == R.id.nav_cropcalendar) {
            startActivity(new Intent(OfficialDirectory.this, CropCalendars.class));
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
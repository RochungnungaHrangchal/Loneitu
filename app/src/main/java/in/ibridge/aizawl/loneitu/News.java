package in.ibridge.aizawl.loneitu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class News extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    ListView listview;
    FirebaseFirestore firebaseFirestore;
    CoordinatorLayout mylayout;
    List<String> list = new ArrayList<>();
    ProgressDialog progressDialog;
    FirebaseAuth agricultureAuthentication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsmenu);
        progressDialog= new ProgressDialog(this);
        listview=(ListView) findViewById(R.id.listView);
        firebaseFirestore=FirebaseFirestore.getInstance();
        agricultureAuthentication=FirebaseAuth.getInstance();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mylayout= (CoordinatorLayout) findViewById(R.id.mylayout);

        // Menus
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Snackbar.make(view, "Testing SnackBar Pop-up Message", Snackbar.LENGTH_LONG)
                //  .setAction("Action tur ", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        progressDialog.setTitle("Updating News");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_2,android.R.id.text2,list);
        list.clear();
        CollectionReference cr = firebaseFirestore.collection("News");
        cr.orderBy("Count", Query.Direction.DESCENDING).get()

        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot qs) {


                if(!qs.isEmpty())
                {
                    progressDialog.dismiss();
                    list.clear();
                    for(DocumentSnapshot ds:qs)
                    {
                        list.add(ds.get("News").toString() );//+ " :" + qs.get("News").toString());
                        listview.setAdapter(arrayAdapter);
                    }
                }
            }
        });

       /* cr.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())

                {
                    progressDialog.dismiss();

                    for(QueryDocumentSnapshot qs : task.getResult())
                    {


                        list.add(qs.get("News").toString() );//+ " :" + qs.get("News").toString());

                        listview.setAdapter(arrayAdapter);


                    }

                }

            }



        });*/

        snackBarLoad();
    }


    //

    //


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

            startActivity(new Intent(News.this, CropCultivation.class));
            finish();

            // Handle the camera action
        } else if (id == R.id.nav_cropprotection) {
            startActivity(new Intent(News.this, CameraReport.class));
            finish();

        } else if (id == R.id.nav_dealers) {
            startActivity(new Intent(News.this, Dealers.class));
            finish();

        } else if (id == R.id.nav_news) {
            startActivity(new Intent(News.this, News.class));
            finish();

        } else if (id == R.id.nav_success_stories) {
            startActivity(new Intent(News.this, SuccessStories.class));
            finish();

        }
        else if (id == R.id.nav_rainfall) {
            startActivity(new Intent(News.this, Rainfallnew.class));
            finish();

        }

        else if (id == R.id.nav_areaproduction) {
            startActivity(new Intent(News.this, AreaProductionTable.class));
            finish();

        }
        else if (id == R.id.nav_call_official) {
            startActivity(new Intent(News.this, OfficialDirectory.class));
            finish();

        }
       /* else if (id == R.id.nav_cropcalendar) {
            startActivity(new Intent(News.this, CropCalendars.class));
            finish();

        }*/
       /* else if (id == R.id.nav_marketing) {
            startActivity(new Intent(this, Marketing.class));
            finish();

        }*/
        else if (id == R.id.nav_logout) {
            agricultureAuthentication.signOut();
            startActivity(new Intent(News.this,NewRegistration.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void snackBarLoad(){

        DocumentReference dr = firebaseFirestore.collection("Alert").document("1");

        try
        {
            dr.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                            if(documentSnapshot !=null && documentSnapshot.exists())
                            {
                                Snackbar snackbar = Snackbar.make(mylayout,documentSnapshot.get("Message").toString(),Snackbar.LENGTH_INDEFINITE);
                                View snackView = snackbar.getView();
                                TextView txtView = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                                txtView.setMaxLines(10);
                                txtView.setTextColor(getResources().getColor(R.color.colorPrimary));
                                snackbar.show();
                            }

                        }
                    }
            );
        } catch (NullPointerException ne){

            Toast.makeText(getApplicationContext(),"News Feed Not Available ",Toast.LENGTH_LONG).show();

        }
    }
}


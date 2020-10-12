package in.ibridge.aizawl.loneitu;

import android.content.Intent;
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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Marketing extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CoordinatorLayout mylayout;
    private FirebaseAuth agricultureAuthentication;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketing);
        firebaseFirestore= FirebaseFirestore.getInstance();
        agricultureAuthentication=FirebaseAuth.getInstance();
        mylayout=(CoordinatorLayout) findViewById(R.id.mylayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.fab);
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
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

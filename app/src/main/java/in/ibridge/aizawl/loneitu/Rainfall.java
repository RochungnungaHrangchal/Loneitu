/* package in.ibridge.aizawl.loneitu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.VORDIPLOM_COLORS;

public class Rainfall<pieColors> extends AppCompatActivity  implements View.OnClickListener {
// Aiawl District Chhung a khua te
    float aizawl, sairang, neihbawi, sialsuk, khawruhlian, average, darlawn;
    float lunglei,hnahthial,haulawng,tlabung,svanlaiphai;
    float mamit,kawrtethawveng,zawlnuam;
    float serchhip,nvanlaiphai;
    float lawngtlai;
    float siaha;
    float kolasib,bukpui,bilkhawthlir,thingdawl;
    float champhai,vaphai,ngopa,khawzawl;

    EditText txtDistrict,txtYear;


    Spinner spinnerDistrict,spinnerYear,spinnerMonth;
    Button btnRainfallData;
    String districtName,yearName,monthName;
    PieChart pieChart;
    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    //ArrayList<Entry> entries= new ArrayList<>();
    ArrayList<String> chartlabels= new ArrayList<>();

    List<String> districtList = new ArrayList<>();
    List<String> yearList = new ArrayList<>();
    //ArrayList<String> districtList = new ArrayList<>();

    List<PieEntry> entries= new ArrayList<>();
    PieDataSet pieDataSet;
    PieData pieData;
    List<String> documentIDList = new ArrayList<>();
    private String TAG="Assigned";

    private CoordinatorLayout mylayout;

    private  void loadYear()
    {
        try {
            CollectionReference yr = firebaseFirestore.collection("Year");


            yr.orderBy("Year", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, yearList);
                        for (QueryDocumentSnapshot qs : task.getResult()) {
                            yearList.add(qs.get("Year").toString());
                            spinnerYear.setAdapter(arrayAdapter);
                        }

                        yearName = spinnerYear.getSelectedItem().toString();

                    }

                }
            });
        }catch (NullPointerException e)
        {
            Toast.makeText(getApplicationContext(),"Error while Collecting information ! Please try Again" ,Toast.LENGTH_LONG).show();



            if (ConnectivityCheck.isConnectedToNetwork(this)) {
                //Show the connected screen
            } else {

                progressDialog = new ProgressDialog(this);
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle("No Network Detected");
                progressDialog.setMessage("Please try after activating Data Connection..");
                progressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();

                    }
                });

                progressDialog.show();

            }


        }

    }

    private void checkConnectionInternet()
    {
        if (ConnectivityCheck.isConnectedToNetwork(this)) {

            loadDistrict();
            // Year Populate
            loadYear();
            snackBarLoad();
            //Show the connected screen
        } else {

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("No Network Detected");
            progressDialog.setMessage("Please try after activating Data Connection..");
            progressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();

                }
            });

            progressDialog.show();

        }
    }
  private void loadDistrict()
  {
      try{

          CollectionReference cr = firebaseFirestore.collection("District");

          cr.orderBy("District").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {

                  if (task.isSuccessful()) {
                      ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, districtList);
                      for (QueryDocumentSnapshot qs : task.getResult()) {
                          districtList.add(qs.get("District").toString());
                          spinnerDistrict.setAdapter(arrayAdapter);
                      }

                      districtName = spinnerDistrict.getSelectedItem().toString();
                  }

              }
          });
      }      catch (NullPointerException e)
      {


          Toast.makeText(getApplicationContext(),"Error while Collecting information ! Please try Again",Toast.LENGTH_LONG).show();
          if (ConnectivityCheck.isConnectedToNetwork(this)) {
              //Show the connected screen
          } else {

              progressDialog = new ProgressDialog(this);
              progressDialog.setCancelable(false);
              progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
              progressDialog.setTitle("No Network Detected");
              progressDialog.setMessage("Please try after activating Data Connection..");
              progressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                      finish();

                  }
              });

              progressDialog.show();

          }
      }
  }

   private void snackBarLoad()
   {
       DocumentReference dr = firebaseFirestore.collection("Alert").document("1");

       try{  dr.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<DocumentSnapshot>() {
           @Override
           public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {

               if (documentSnapshot != null && documentSnapshot.exists()) {
                   //  Toast.makeText(getApplicationContext(),documentSnapshot.get("Message").toString(),Toast.LENGTH_LONG).show();
                   Snackbar snackbar = (Snackbar) Snackbar.make(mylayout, documentSnapshot.get("Message").toString(), Snackbar.LENGTH_INDEFINITE);

                   View snackbarView = snackbar.getView();

                   TextView txtView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                   txtView.setMaxLines(10);
                   txtView.setTextColor(getResources().getColor(R.color.colorPrimary));


                   snackbar.show();
               }

           }
       });}
       catch (NullPointerException e)
       {


           if (ConnectivityCheck.isConnectedToNetwork(this)) {
               //Show the connected screen
           } else {

               progressDialog = new ProgressDialog(this);
               progressDialog.setCancelable(false);
               progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
               progressDialog.setTitle("No Network Detected");
               progressDialog.setMessage("Please try after activating Data Connection..");
               progressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       finish();

                   }
               });

               progressDialog.show();

           }
       }
   }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.rainfall);



        // Firebase and Firestore Bul Tanna
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);

        btnRainfallData = (Button) findViewById(R.id.btnRainfallData);
        pieChart = (PieChart) findViewById(R.id.chart1);
        mylayout = (CoordinatorLayout) findViewById(R.id.mylayout);


         checkConnectionInternet();
        // District Populate



        // District Selection
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                                  districtName=adapterView.getItemAtPosition(i).toString();

                   // Toast.makeText(getApplicationContext(),"Month :" + monthName.toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    yearName=adapterView.getItemAtPosition(i).toString();

                   // Toast.makeText(getApplicationContext(),"Month :" + monthName.toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(adapterView.getItemAtPosition(i).equals("Choose the Month"))
                {

                }
                else
                {
                    monthName=adapterView.getItemAtPosition(i).toString();
                    //Toast.makeText(getApplicationContext(),"Month :" + monthName.toString(),Toast.LENGTH_LONG).show();
                    entries.clear();


                    try {
                        progressDialog = new ProgressDialog(getApplicationContext());
                        //progressDialog.setCancelable(false);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setTitle("Collecting Rainfall Data");
                        progressDialog.setMessage("Please wait....");
                        progressDialog.show();


                        DocumentReference dr = firebaseFirestore.collection("Rainfall").document(districtName).collection(yearName).document(monthName);

                        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                try{
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot ds = task.getResult();


                                        if (districtName.equals("Aizawl")) {
                                            aizawl = Float.parseFloat(ds.get("Aizawl").toString());


                                         //   Toast.makeText(getApplicationContext(), "Aizawl Actual Value b: " + aizawl, Toast.LENGTH_LONG).show();

                                            darlawn = Float.valueOf(ds.get("Darlawn").toString());
                                            sialsuk = Float.valueOf(ds.get("Sialsuk").toString());
                                            sairang = Float.valueOf(ds.get("Sairang").toString());
                                            khawruhlian = Float.valueOf(ds.get("Khawruhlian").toString());
                                            neihbawi = Float.valueOf(ds.get("Neihbawi").toString());
                                            average = Float.valueOf(ds.get("Average").toString());
                                            // Entering the Actual Value to PIECHART


                                            entries.add(new PieEntry(aizawl, "Aizawl"));
                                            entries.add(new PieEntry(darlawn, "Darlawn"));
                                            entries.add(new PieEntry(sialsuk, "Sialsuk"));
                                            entries.add(new PieEntry(khawruhlian, "Khawruhlian"));
                                            entries.add(new PieEntry(neihbawi, "Neihbawi"));
                                            entries.add(new PieEntry(average, "Average"));
                                            entries.add(new PieEntry(sairang, "Sairang"));
                                        }

                                        if (districtName.equals("Lunglei")) {
                                            lunglei = Float.valueOf((String) ds.get("Lunglei").toString());

                                            hnahthial = Float.valueOf(ds.get("Hnahthial").toString());
                                            tlabung = Float.valueOf(ds.get("Tlabung").toString());
                                            svanlaiphai = Float.valueOf(ds.get("SVanlaiphai").toString());
                                            haulawng = Float.valueOf(ds.get("Haulawng").toString());

                                            average = Float.valueOf(ds.get("Average").toString());
                                            // Entering the Actual Value to PIECHART

                                            entries.add(new PieEntry((float) lunglei, "Lunglei"));
                                            entries.add(new PieEntry((float) hnahthial, "Hnahthial"));
                                            entries.add(new PieEntry((float) tlabung, "Tlabung"));
                                            entries.add(new PieEntry((float) svanlaiphai, "S Vanlaiphai"));
                                            entries.add(new PieEntry((float) haulawng, "Haulawng"));
                                            entries.add(new PieEntry((float) average, "Average"));
                                            //entries.add(new PieEntry((float) sairang, "Sairang"));
                                        }
                                        if (districtName.equals("Siaha")) {
                                            siaha = Float.valueOf((String) ds.get("Siaha").toString());

                                            entries.add(new PieEntry((float) siaha, "Siaha"));
                                            //entries.add(new PieEntry((float) sairang, "Sairang"));
                                        }


                                        if (districtName.equals("Champhai")) {
                                            champhai = Float.valueOf((String) ds.get("Champhai").toString());
                                            vaphai = Float.valueOf(ds.get("Vaphai").toString());
                                            khawzawl = Float.valueOf(ds.get("Khawzawl").toString());
                                            ngopa = Float.valueOf(ds.get("Ngopa").toString());
                                            average = Float.valueOf(ds.get("Average").toString());
                                            // Entering the Actual Value to PIECHART

                                            entries.add(new PieEntry((float) champhai, "Champhai"));
                                            entries.add(new PieEntry((float) vaphai, "Vaphai"));
                                            entries.add(new PieEntry((float) khawzawl, "Khawzawl"));
                                            entries.add(new PieEntry((float) ngopa, "Ngopa"));
                                            entries.add(new PieEntry((float) average, "Average"));
                                            //entries.add(new PieEntry((float) sairang, "Sairang"));
                                        }

                                        if (districtName.equals("Kolasib")) {
                                            kolasib = Float.valueOf((String) ds.get("Kolasib").toString());

                                            bilkhawthlir = Float.valueOf(ds.get("Bilkhawthlir").toString());
                                            bukpui = Float.valueOf(ds.get("Bukpui").toString());
                                            thingdawl = Float.valueOf(ds.get("Thingdawl").toString());
                                            average = Float.valueOf(ds.get("Average").toString());
                                            // Entering the Actual Value to PIECHART

                                            entries.add(new PieEntry((float) kolasib, "Kolasib"));
                                            entries.add(new PieEntry((float) bilkhawthlir, "Bilkhawthlir"));
                                            entries.add(new PieEntry((float) bukpui, "Bukpui"));
                                            entries.add(new PieEntry((float) thingdawl, "Thingdawl"));
                                            entries.add(new PieEntry((float) average, "Average"));
                                            //entries.add(new PieEntry((float) sairang, "Sairang"));
                                        }

                                        if (districtName.equals("Serchhip")) {
                                            serchhip = Float.valueOf((String) ds.get("Serchhip").toString());
                                            nvanlaiphai = Float.valueOf(ds.get("NVanlaiphai").toString());

                                            average = Float.valueOf(ds.get("Average").toString());
                                            // Entering the Actual Value to PIECHART

                                            entries.add(new PieEntry((float) serchhip, "Serchhip"));
                                            entries.add(new PieEntry((float) nvanlaiphai, "N Vanlaiphai"));
                                            entries.add(new PieEntry((float) average, "Average"));
                                            //entries.add(new PieEntry((float) sairang, "Sairang"));
                                        }

                                        if (districtName.equals("Lawngtlai")) {
                                            lawngtlai = Float.valueOf((String) ds.get("Lawngtlai").toString());


                                            // Entering the Actual Value to PIECHART


                                            entries.add(new PieEntry((float) lawngtlai, "Lawngtlai"));
                                            //entries.add(new PieEntry((float) sairang, "Sairang"));
                                        }

                                        if (districtName.equals("Mamit")) {
                                            mamit = Float.valueOf((String) ds.get("Mamit").toString());

                                            kawrtethawveng = Float.valueOf(ds.get("SKawrtethawveng").toString());
                                            zawlnuam = Float.valueOf(ds.get("Zawlnuam").toString());
                                            average = Float.valueOf(ds.get("Average").toString());
                                            // Entering the Actual Value to PIECHART
                                            entries.add(new PieEntry((float) mamit, "Mamit"));
                                            entries.add(new PieEntry((float) kawrtethawveng, "S Kawrtethawveng"));
                                            entries.add(new PieEntry((float) zawlnuam, "Zawlnuam"));
                                            entries.add(new PieEntry((float) average, "Average"));
                                            //entries.add(new PieEntry((float) sairang, "Sairang"));
                                        }


                                        // Piechart Dataset siamna
                                        final PieDataSet set;
                                        set = new PieDataSet(entries, "");

                                        PieData data = new PieData(set);

                                        // a Lan dan thlakna tur
                                        set.setValueTextColor(R.color.button_pressed);
                                        set.setValueTextSize(16f);
                                        set.setSliceSpace(3);
                                        set.setColors(VORDIPLOM_COLORS);
                                        // set.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                                        set.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);


                                        progressDialog.dismiss();
                                        pieChart.setData(data);
                                        pieChart.setCenterTextSize(20);
                                        pieChart.setCenterText("RainFall \n( in mm)");

                                        //pieChart.
                                        pieChart.setEntryLabelColor(R.color.colorPrimaryDark);
                                        pieChart.setDescription(null);
                                        pieChart.getLegend().setEnabled(false);

                                        pieChart.animateX(4000);
                                        pieChart.invalidate();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Cannot Find Data ..", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        return;
                                    }

                                }catch (NullPointerException e)
                                {
                                    Toast.makeText(getApplicationContext(),"Cannot Find Data on Selected Items, Sorry ",Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }




                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(getApplicationContext(), "Cannot Find the Specified Data !", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();

                                    }
                                });

                    } catch (Exception e) {

                       // Toast.makeText(getApplicationContext(),"Sorry, Please Check your Selected Items Again !",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        return;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        btnRainfallData.setOnClickListener(this);
       // Toast.makeText(getApplicationContext(),spinnerMonth.getSelectedItem().toString(),Toast.LENGTH_LONG).show();

       // // Will get Data from Firestore




    }

    @Override
    public void onBackPressed() {
        //Toast.makeText(getApplicationContext(),"Backed Key Pressed",Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onClick(View view) {
        entries.clear();
        final Vibrator vibrator = (Vibrator) Rainfall.this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);

        try {
            progressDialog = new ProgressDialog(this);
            //progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("Collecting Rainfall Data");
            progressDialog.setMessage("Please wait....");
            progressDialog.show();


            DocumentReference dr = firebaseFirestore.collection("Rainfall").document(districtName).collection(yearName).document(monthName);

            dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                  try{
                      if (task.isSuccessful()) {
                          DocumentSnapshot ds = task.getResult();


                          if (districtName.equals("Aizawl")) {
                              aizawl = Float.parseFloat(ds.get("Aizawl").toString());


                              Toast.makeText(getApplicationContext(), "Aizawl Actual Value b: " + aizawl, Toast.LENGTH_LONG).show();

                              darlawn = Float.valueOf(ds.get("Darlawn").toString());
                              sialsuk = Float.valueOf(ds.get("Sialsuk").toString());
                              sairang = Float.valueOf(ds.get("Sairang").toString());
                              khawruhlian = Float.valueOf(ds.get("Khawruhlian").toString());
                              neihbawi = Float.valueOf(ds.get("Neihbawi").toString());
                              average = Float.valueOf(ds.get("Average").toString());
                              // Entering the Actual Value to PIECHART


                              entries.add(new PieEntry(aizawl, "Aizawl"));
                              entries.add(new PieEntry(darlawn, "Darlawn"));
                              entries.add(new PieEntry(sialsuk, "Sialsuk"));
                              entries.add(new PieEntry(khawruhlian, "Khawruhlian"));
                              entries.add(new PieEntry(neihbawi, "Neihbawi"));
                              entries.add(new PieEntry(average, "Average"));
                              entries.add(new PieEntry(sairang, "Sairang"));
                          }

                          if (districtName.equals("Lunglei")) {
                              lunglei = Float.valueOf((String) ds.get("Lunglei").toString());

                              hnahthial = Float.valueOf(ds.get("Hnahthial").toString());
                              tlabung = Float.valueOf(ds.get("Tlabung").toString());
                              svanlaiphai = Float.valueOf(ds.get("SVanlaiphai").toString());
                              haulawng = Float.valueOf(ds.get("Haulawng").toString());

                              average = Float.valueOf(ds.get("Average").toString());
                              // Entering the Actual Value to PIECHART

                              entries.add(new PieEntry((float) lunglei, "Lunglei"));
                              entries.add(new PieEntry((float) hnahthial, "Hnahthial"));
                              entries.add(new PieEntry((float) tlabung, "Tlabung"));
                              entries.add(new PieEntry((float) svanlaiphai, "S Vanlaiphai"));
                              entries.add(new PieEntry((float) haulawng, "Haulawng"));
                              entries.add(new PieEntry((float) average, "Average"));
                              //entries.add(new PieEntry((float) sairang, "Sairang"));
                          }
                          if (districtName.equals("Siaha")) {
                              siaha = Float.valueOf((String) ds.get("Siaha").toString());

                              entries.add(new PieEntry((float) siaha, "Siaha"));
                              //entries.add(new PieEntry((float) sairang, "Sairang"));
                          }


                          if (districtName.equals("Champhai")) {
                              champhai = Float.valueOf((String) ds.get("Champhai").toString());
                              vaphai = Float.valueOf(ds.get("Vaphai").toString());
                              khawzawl = Float.valueOf(ds.get("Khawzawl").toString());
                              ngopa = Float.valueOf(ds.get("Ngopa").toString());
                              average = Float.valueOf(ds.get("Average").toString());
                              // Entering the Actual Value to PIECHART

                              entries.add(new PieEntry((float) champhai, "Champhai"));
                              entries.add(new PieEntry((float) vaphai, "Vaphai"));
                              entries.add(new PieEntry((float) khawzawl, "Khawzawl"));
                              entries.add(new PieEntry((float) ngopa, "Ngopa"));
                              entries.add(new PieEntry((float) average, "Average"));
                              //entries.add(new PieEntry((float) sairang, "Sairang"));
                          }

                          if (districtName.equals("Kolasib")) {
                              kolasib = Float.valueOf((String) ds.get("Kolasib").toString());

                              bilkhawthlir = Float.valueOf(ds.get("Bilkhawthlir").toString());
                              bukpui = Float.valueOf(ds.get("Bukpui").toString());
                              thingdawl = Float.valueOf(ds.get("Thingdawl").toString());
                              average = Float.valueOf(ds.get("Average").toString());
                              // Entering the Actual Value to PIECHART

                              entries.add(new PieEntry((float) kolasib, "Kolasib"));
                              entries.add(new PieEntry((float) bilkhawthlir, "Bilkhawthlir"));
                              entries.add(new PieEntry((float) bukpui, "Bukpui"));
                              entries.add(new PieEntry((float) thingdawl, "Thingdawl"));
                              entries.add(new PieEntry((float) average, "Average"));
                              //entries.add(new PieEntry((float) sairang, "Sairang"));
                          }

                          if (districtName.equals("Serchhip")) {
                              serchhip = Float.valueOf((String) ds.get("Serchhip").toString());
                              nvanlaiphai = Float.valueOf(ds.get("NVanlaiphai").toString());

                              average = Float.valueOf(ds.get("Average").toString());
                              // Entering the Actual Value to PIECHART

                              entries.add(new PieEntry((float) serchhip, "Serchhip"));
                              entries.add(new PieEntry((float) nvanlaiphai, "N Vanlaiphai"));
                              entries.add(new PieEntry((float) average, "Average"));
                              //entries.add(new PieEntry((float) sairang, "Sairang"));
                          }

                          if (districtName.equals("Lawngtlai")) {
                              lawngtlai = Float.valueOf((String) ds.get("Lawngtlai").toString());


                              // Entering the Actual Value to PIECHART


                              entries.add(new PieEntry((float) lawngtlai, "Lawngtlai"));
                              //entries.add(new PieEntry((float) sairang, "Sairang"));
                          }

                          if (districtName.equals("Mamit")) {
                              mamit = Float.valueOf((String) ds.get("Mamit").toString());

                              kawrtethawveng = Float.valueOf(ds.get("SKawrtethawveng").toString());
                              zawlnuam = Float.valueOf(ds.get("Zawlnuam").toString());
                              average = Float.valueOf(ds.get("Average").toString());
                              // Entering the Actual Value to PIECHART
                              entries.add(new PieEntry((float) mamit, "Mamit"));
                              entries.add(new PieEntry((float) kawrtethawveng, "S Kawrtethawveng"));
                              entries.add(new PieEntry((float) zawlnuam, "Zawlnuam"));
                              entries.add(new PieEntry((float) average, "Average"));
                              //entries.add(new PieEntry((float) sairang, "Sairang"));
                          }


                          // Piechart Dataset siamna
                          final PieDataSet set;
                          set = new PieDataSet(entries, "");

                          PieData data = new PieData(set);

                          // a Lan dan thlakna tur
                          set.setValueTextColor(R.color.button_pressed);
                          set.setValueTextSize(16f);
                          set.setSliceSpace(3);
                          set.setColors(VORDIPLOM_COLORS);
                          // set.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                          set.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);


                          progressDialog.dismiss();
                          pieChart.setData(data);
                          pieChart.setCenterTextSize(20);
                          pieChart.setCenterText("RainFall \n( in mm)");

                          //pieChart.
                          pieChart.setEntryLabelColor(R.color.colorPrimaryDark);
                          pieChart.setDescription(null);
                          pieChart.getLegend().setEnabled(false);

                          pieChart.animateX(4000);
                          pieChart.invalidate();

                      } else {
                          Toast.makeText(getApplicationContext(), "Cannot Find Data ..", Toast.LENGTH_LONG).show();
                          progressDialog.dismiss();
                          return;
                      }

                  }catch (NullPointerException e)
                  {
                     Toast.makeText(getApplicationContext(),"Cannot Find Data on Selected Items, Sorry ",Toast.LENGTH_LONG).show();
                     progressDialog.dismiss();
                  }




                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), "Cannot Find the Specified Data !", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                        }
                    });

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(),"Sorry, Please Check your Selected Items Again !",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }

    }


}


 */
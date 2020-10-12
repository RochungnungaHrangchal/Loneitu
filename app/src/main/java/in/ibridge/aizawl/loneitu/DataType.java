package in.ibridge.aizawl.loneitu;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.github.mikephil.charting.utils.ColorTemplate.VORDIPLOM_COLORS;

public class DataType extends AppCompatActivity implements View.OnClickListener {


    PieChart pieChart;
    Button btnView;
    TextView txtView;
    FirebaseFirestore firebaseFirestore;
    float aizawldata;

    List<PieEntry> entries= new ArrayList<>();
    PieDataSet pieDataSet;
    PieData pieData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datatype);


        firebaseFirestore=FirebaseFirestore.getInstance();

        DocumentReference dr = firebaseFirestore.collection("Rainfall").document("Aizawl").collection("2017").document("February");

        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful())
                {
                    DocumentSnapshot ds = task.getResult();


                   aizawldata= Float.valueOf (ds.get("Aizawl").toString());

                    txtView.setText(String.valueOf(aizawldata));

                    Toast.makeText(getApplicationContext(),"Aizawl Float Value :"+ txtView.getText().toString(),Toast.LENGTH_LONG).show();


                }

            }


        });

        txtView=(TextView) findViewById(R.id.txtView);
        btnView=(Button) findViewById(R.id.btnView);
        btnView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {



    }
}

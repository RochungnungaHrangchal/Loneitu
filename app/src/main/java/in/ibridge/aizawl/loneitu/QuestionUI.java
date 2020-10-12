/*
package in.ibridge.aizawl.loneitu;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;

public class QuestionUI extends AppCompatActivity {
    private RadioGroup radioGroup;
    FirebaseFirestore firebaseFirestore;
    CoordinatorLayout mylayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionui);
        radioGroup=findViewById(R.id.radioGroup);
        mylayout=findViewById(R.id.mylayout);
        firebaseFirestore=FirebaseFirestore.getInstance();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.myquestion)
                {
                    Toast.makeText(getApplicationContext(),"My Question List",Toast.LENGTH_LONG).show();
                }
                else if(i==R.id.allquestion)
                {
                    Toast.makeText(getApplicationContext(),"All Question List",Toast.LENGTH_LONG).show();
                }
                else if(i==R.id.questionpost)
                {
                    Toast.makeText(getApplicationContext(),"Post Question",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Reply to Question",Toast.LENGTH_LONG).show();
                }

            }
        });

        // Loading the Notification SnackBar

        snackBarLoad();

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
                                TextView txtView = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
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
*/
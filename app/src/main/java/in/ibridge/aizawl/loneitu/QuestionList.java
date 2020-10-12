/*package in.ibridge.aizawl.loneitu;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuestionList extends AppCompatActivity implements View.OnClickListener {

    private ListView questionList,answerlistView;
    List<String> list = new ArrayList<>();
    List<String> anslist = new ArrayList<>();
    FirebaseFirestore firebaseFirestore;
    FirebaseUser currentUser;
    FirebaseAuth firebaseAuth;
    Button btnClose;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.replyquestion);
        questionList=(ListView) findViewById(R.id.questionList);
        answerlistView= findViewById(R.id.answerList);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ...");
        progressDialog.setTitle("Loading your posted Questions");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        currentUser=firebaseAuth.getCurrentUser();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        final ArrayAdapter<String> ansarrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,anslist);
       // questionList.setAdapter(arrayAdapter);
        anslist.clear();
        answerlistView.setVisibility(View.INVISIBLE);
        list.clear();
        btnClose=findViewById(R.id.btnclose);
        btnClose.setVisibility(View.INVISIBLE);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answerlistView.setVisibility(View.INVISIBLE);
                questionList.setVisibility(View.VISIBLE);
                btnClose.setVisibility(View.INVISIBLE);
            }
        });

        CollectionReference ds = firebaseFirestore.collection("Users").document("+918794501007").collection("Questions");
       // DocumentReference ds = firebaseFirestore.collection("Users").document(currentUser.getPhoneNumber().toString()).collection("Questions").document("Answers");

       // ds.whereEqualTo("question_category","Pesticides");
        ds.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    for (QueryDocumentSnapshot newds : task.getResult())
                    {

                         list.add(newds.get("question").toString());
                         questionList.setAdapter(arrayAdapter);


                    }
                }

            }
        });


       questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           @Override
           public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {

               progressDialog.setMessage("Please Wait ...");
               progressDialog.setTitle("Loading Answers for your Selected Question");
               progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
               progressDialog.show();


               String ssss=adapterView.getItemAtPosition(i).toString();

          //   DocumentReference dr = firebaseFirestore.collection("Users").document("+918794501007").collection("Questions").document();


                CollectionReference ds = firebaseFirestore.collection("Users").document("+918794501007").collection("Questions");
               // DocumentReference ds = firebaseFirestore.collection("Users").document(currentUser.getPhoneNumber().toString()).collection("Questions").document("Answers");

              // ds.whereEqualTo("questions")
               ds.whereEqualTo("question",adapterView.getItemAtPosition(i).toString())

              //  Toast.makeText(getApplicationContext(),"Key :" + ds.getId(),Toast.LENGTH_LONG).show();

               .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {

                       // task to list all answer


                       if (task.isSuccessful()) {



                           questionList.setVisibility(View.INVISIBLE);
                           btnClose.setVisibility(View.VISIBLE);
                           for (DocumentSnapshot dsss : task.getResult())
                           {
                              // Toast.makeText(getApplicationContext(), "Key :" + dsss.getId() + "Questions :" + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();

                           CollectionReference dr = firebaseFirestore.collection("Users").document("+918794501007").collection("Questions").document(dsss.getId()).collection("CHHANNA");

                           dr.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                               @Override
                               public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                  answerlistView.setVisibility(View.VISIBLE);
                                   anslist.clear();



                                   if(task.isSuccessful() )
                                   {
                                      for(DocumentSnapshot ansdoc: task.getResult())
                                      {

                                          anslist.add("\n" + ansdoc.get("answer").toString()+ "\n\n                                   ~ By: "+ "+918794501007");
                                          answerlistView.setAdapter(ansarrayAdapter);
                                      }

                                   }
                                   progressDialog.dismiss();

                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(getApplicationContext(),"No Answers posted for this Question !",Toast.LENGTH_LONG).show();
                               }
                           });


                       }

                   }
               }

               });


                           // Map<String, Object> docData = new HashMap<>();



                          //  CollectionReference dr = firebaseFirestore.collection("Users").document("+918794501007").collection("Questions").document(ds.getId()).collection("CHHANNA");
                            // dr.collection(currentUser.getPhoneNumber().toString());

                          //  docData.put("answer","Chhana");

                        //    dr.add(docData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            //    @Override
                              //  public void onSuccess(DocumentReference documentReference) {



                             //   }
                        //    });
                      //  }




           }
       });

    }

    @Override
    public void onClick(View view) {



    }
}
*/
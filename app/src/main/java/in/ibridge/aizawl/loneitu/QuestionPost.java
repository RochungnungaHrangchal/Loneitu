/*package in.ibridge.aizawl.loneitu;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class QuestionPost extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmitQuestion;
    private EditText txtQuestionInput;
    private Spinner spinnerQuestionCategory;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.postquestions);
        btnSubmitQuestion=(Button) findViewById(R.id.btnSubmitQuestion);
        txtQuestionInput=(EditText) findViewById(R.id.txtQuestionInput);
        spinnerQuestionCategory=(Spinner) findViewById(R.id.spinnerQuestionCategory);

        btnSubmitQuestion.setOnClickListener(this);

        firebaseFirestore = FirebaseFirestore.getInstance();

       // currentUser=firebaseAuth.getInstance().getCurrentUser();

       // Toast.makeText(getApplicationContext(),"jj:" + currentUser.getPhoneNumber().toString(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {

        Map<String, Object> docData = new HashMap<>();



        //CollectionReference dr = firebaseFirestore.collection("Users").document(currentUser.getPhoneNumber().toString()).collection("Questions");
        CollectionReference dr = firebaseFirestore.collection("Users").document("+918794501007").collection("Questions");

        CollectionReference crr = firebaseFirestore.collection("Questions");
        // dr.collection(currentUser.getPhoneNumber().toString());

        docData.put("question",txtQuestionInput.getText().toString());
        docData.put("question_category",spinnerQuestionCategory.getSelectedItem().toString());
        docData.put("users","+918794501007");

        crr.add(docData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        });


          dr.add(docData) .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
               @Override
               public void onSuccess(DocumentReference documentReference) {

                   Toast.makeText(getApplicationContext(),"Question Posted Successfully",Toast.LENGTH_LONG).show();

               }
           })
             .addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Toast.makeText(getApplicationContext(),"Sorry Failed to post your Question",Toast.LENGTH_LONG).show();

                 }
             });






    }
}
*/
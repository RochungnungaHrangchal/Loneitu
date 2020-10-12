package in.ibridge.aizawl.loneitu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewRegistration extends AppCompatActivity implements View.OnClickListener {

   // Private Views i.e. Register Button and Input Box for Mobile Number and ProgressBar
        public FirebaseFirestore firebaseFirestore;

        EditText mPhoneNumberField, mVerificationField;
        Button mStartButton, mVerifyButton, mResendButton;
        ProgressDialog progressDialog,progressDialog1,progressDialog2,progressDialog4;

        private FirebaseAuth mAuth;
        private PhoneAuthProvider.ForceResendingToken mResendToken;
        private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
        String mVerificationId;

        private static final String TAG = "PhoneAuthActivity";

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setContentView(R.layout.newregistration);
            progressDialog = new ProgressDialog(this);
            progressDialog1 = new ProgressDialog(this);
            progressDialog2 = new ProgressDialog(this);
            progressDialog4 = new ProgressDialog(this);
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

                        finish();

                    }
                });

                progressDialog.show();

            }


            mPhoneNumberField = (EditText) findViewById(R.id.field_phone_number);
            mVerificationField = (EditText) findViewById(R.id.field_verification_code);

            mStartButton = (Button) findViewById(R.id.button_start_verification);
            mVerifyButton = (Button) findViewById(R.id.button_verify_phone);
            mResendButton = (Button) findViewById(R.id.button_resend);

            mStartButton.setOnClickListener(this);
            mVerifyButton.setOnClickListener(this);
            mResendButton.setOnClickListener(this);

            mVerifyButton.setVisibility(View.INVISIBLE);
            mResendButton.setVisibility(View.INVISIBLE);
            mVerificationField.setVisibility(View.INVISIBLE);

            mAuth = FirebaseAuth.getInstance();
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {
                    Log.d(TAG, "onVerificationCompleted:" + credential);
                    signInWithPhoneAuthCredential(credential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Log.w(TAG, "onVerificationFailed", e);
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        mPhoneNumberField.setError("Invalid phone number.");
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                                Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCodeSent(String verificationId,
                                       PhoneAuthProvider.ForceResendingToken token) {
                    Log.d(TAG, "onCodeSent:" + verificationId);
                    mVerificationId = verificationId;
                    mResendToken = token;
                }
                @Override
                public void onCodeAutoRetrievalTimeOut(String verificationId)
                {
                    progressDialog1.setCancelable(false);
                    progressDialog1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog1.setTitle("Session Time-out");
                    progressDialog1.setMessage("Please try after sometime...");
                    progressDialog1.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    progressDialog1.show();
                    Toast.makeText(getApplicationContext(),"Session Timeout while trying to Retrive your Code ..",Toast.LENGTH_LONG).show();
                }
            };
        }

        private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = task.getResult().getUser();
                                startActivity(new Intent(NewRegistration.this, MainActivity.class));
                                finish();
                            } else {
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    mVerificationField.setError("Invalid code.");
                                }
                            }
                        }
                    });
        }


        private void startPhoneNumberVerification(String phoneNumber) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks
        }

        private void verifyPhoneNumberWithCode(String verificationId, String code) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithPhoneAuthCredential(credential);
        }

        private void resendVerificationCode(String phoneNumber,
                                            PhoneAuthProvider.ForceResendingToken token) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks,         // OnVerificationStateChangedCallbacks
                    token);             // ForceResendingToken from callbacks
        }

        private boolean validatePhoneNumber() {
            String phoneNumber = "+91" + mPhoneNumberField.getText().toString();
            if (TextUtils.isEmpty(phoneNumber)) {
                mPhoneNumberField.setError("Invalid phone number.");
                return false;
            }
            return true;
        }
        @Override
        public void onStart() {
            super.onStart();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                startActivity(new Intent(NewRegistration.this, MainActivity.class));
                finish();
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_start_verification:



                    if (TextUtils.isEmpty(mPhoneNumberField.getText().toString()))
                    {
                        mPhoneNumberField.setError("Phone Number cannot be BLANK !");
                        return;
                    }

                    if (mPhoneNumberField.length() !=10)
                    {
                        mPhoneNumberField.setError("Phone Number should be 10 DIGITS !");
                        return;

                    }

                    if (!validatePhoneNumber()) {
                        return;
                    }
                    progressDialog2.setCancelable(true);
                    progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog2.setTitle("Verifying your Mobile Number");
                    progressDialog2.setMessage("Please wait...");
                    progressDialog2.show();

                    startPhoneNumberVerification("+91"+ mPhoneNumberField.getText().toString());
                    break;
                case R.id.button_verify_phone:
                    String code = mVerificationField.getText().toString();
                    if (TextUtils.isEmpty(code)) {
                        mVerificationField.setError("Cannot be empty.");
                        return;
                    }

                    verifyPhoneNumberWithCode(mVerificationId, code);
                    break;
                case R.id.button_resend:

                    resendVerificationCode("+91" + mPhoneNumberField.getText().toString(), mResendToken);
                    break;
            }

        }

    }



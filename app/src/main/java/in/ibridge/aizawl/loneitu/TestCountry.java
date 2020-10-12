package in.ibridge.aizawl.loneitu;

import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestCountry extends AppCompatActivity implements View.OnClickListener {

    Button testbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testcountry);
        testbtn=findViewById(R.id.testbtn);

    }

    @Override
    public void onClick(View view) {
        TelephonyManager manager = (TelephonyManager) getApplicationContext().getSystemService(this.TELEPHONY_SERVICE);
        String testcodeiso = manager.getNetworkCountryIso();
        Toast.makeText(getApplicationContext(),"Code is:"+testcodeiso,Toast.LENGTH_LONG).show();
    }
}

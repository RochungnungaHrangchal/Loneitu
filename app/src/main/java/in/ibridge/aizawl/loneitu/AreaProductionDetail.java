package in.ibridge.aizawl.loneitu;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.VORDIPLOM_COLORS;

public class AreaProductionDetail extends AppCompatActivity {
    PieChart pieChartArea;
    PieChart pieChartProduction;
    List<PieEntry> entriesArea= new ArrayList<>();
    List<PieEntry> entriesProduction= new ArrayList<>();
    PieDataSet pieDataSetProduction;
    PieData pieDataProduction;
    PieDataSet pieDataSetArea;
    PieData pieDataArea;
    ProgressDialog progressDialog;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areaproductiondetail);

        pieChartArea=(PieChart) findViewById(R.id.chart1);
        pieChartProduction=(PieChart) findViewById(R.id.chart2);
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        constructPieChartArea();// Calling PieChart for Area
        constructPieChartProduction();// Calling PieChart for Production


    }

    private  void  constructPieChartProduction()
    {
        entriesProduction.add(new PieEntry(12.01f, "Rice"));
        entriesProduction.add(new PieEntry(14.22f, "Maize"));
        entriesProduction.add(new PieEntry(13.33f, "Topioca"));
        entriesProduction.add(new PieEntry(15.09f, "Onion"));
        entriesProduction.add(new PieEntry(11.21f, "Pulses"));
        entriesProduction.add(new PieEntry(11.45f, "OilSeed"));
        entriesProduction.add(new PieEntry(45.07f, "Cotton"));
        entriesProduction.add(new PieEntry(11.21f, "Tobacco"));
        entriesProduction.add(new PieEntry(11.45f, "Sugarcane"));
        entriesProduction.add(new PieEntry(45.07f, "Potato"));

        // PieChart for Production
        pieDataSetProduction= new PieDataSet(entriesProduction,"");
        pieDataProduction= new PieData(pieDataSetProduction);
        pieDataSetProduction.setValueTextColor(R.color.button_pressed);
        pieDataSetProduction.setValueTextSize(16f);
        pieDataSetProduction.setSliceSpace(3);
        pieDataSetProduction.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSetProduction.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        pieChartProduction.setData(pieDataArea);
        pieChartProduction.setCenterTextSize(17);
        pieChartProduction.setCenterText(" Production \n(in M Tonnes)");


        pieChartProduction.setEntryLabelColor(R.color.colorPrimaryDark);
        pieChartProduction.setDescription(null);
        pieChartProduction.getLegend().setEnabled(false);
        pieChartProduction.setEntryLabelTextSize(16f);

        pieChartProduction.animateX(4000);

        // pieChartProduction
        pieChartProduction.invalidate();


    }
    private  void constructPieChartArea()
    {
        entriesArea.add(new PieEntry(12.01f, "Rice"));
        entriesArea.add(new PieEntry(14.22f, "Maize"));
        entriesArea.add(new PieEntry(13.33f, "Topioca"));
        entriesArea.add(new PieEntry(15.09f, "Onion"));
        entriesArea.add(new PieEntry(11.21f, "Pulses"));
        entriesArea.add(new PieEntry(11.45f, "OilSeed"));
        entriesArea.add(new PieEntry(45.07f, "Cotton"));
        entriesArea.add(new PieEntry(11.21f, "Tobacco"));
        entriesArea.add(new PieEntry(11.45f, "Sugarcane"));
        entriesArea.add(new PieEntry(45.07f, "Potato"));

        pieDataSetArea= new PieDataSet(entriesArea,"");
        pieDataArea= new PieData(pieDataSetArea);
        pieDataSetArea.setValueTextColor(R.color.button_pressed);
        pieDataSetArea.setValueTextSize(16f);
        pieDataSetArea.setSliceSpace(3);
        pieDataSetArea.setColors(VORDIPLOM_COLORS);
        pieDataSetArea.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        pieChartArea.setData(pieDataArea);
        pieChartArea.setCenterTextSize(17);
        pieChartArea.setCenterText(" Area \n( in ha)");


        pieChartArea.setEntryLabelColor(R.color.colorPrimaryDark);
        pieChartArea.setDescription(null);
        pieChartArea.getLegend().setEnabled(false);
        pieChartArea.setEntryLabelTextSize(16f);

        pieChartArea.animateX(4000);
        pieChartArea.invalidate();
    }
}

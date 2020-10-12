package in.ibridge.aizawl.loneitu;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class AnimationTest extends AppCompatActivity implements View.OnClickListener {

    ImageButton imageButton,imageButton1,imageButton2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animationtest);
        imageButton=findViewById(R.id.imageButton);
        imageButton1=findViewById(R.id.imageButton1);
        imageButton2=findViewById(R.id.imageButton2);
        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.bounce);

        AnimationInterpolator animinter=new AnimationInterpolator(.2,20);
        animation.setInterpolator(animinter);
        imageButton.startAnimation(animation);
        imageButton.setOnClickListener(this);
        imageButton1.startAnimation(animation);
        imageButton1.setOnClickListener(this);
        imageButton2.startAnimation(animation);
        imageButton2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.bounce);

        AnimationInterpolator animinter=new AnimationInterpolator(.2,20);
        animation.setInterpolator(animinter);
        view.startAnimation(animation);
    }
}

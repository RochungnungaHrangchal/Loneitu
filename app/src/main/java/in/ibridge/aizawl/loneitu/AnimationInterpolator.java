package in.ibridge.aizawl.loneitu;

import android.view.animation.Interpolator;

public class AnimationInterpolator implements Interpolator {
    private double mAmplitude=1;
    private double mFrequency=10;

    AnimationInterpolator(double Amp,double Fre)
    {
        mAmplitude=Amp;
        mFrequency=Fre;
    }
    @Override
    public float getInterpolation(float time) {

        return (float)(1 * Math.pow(Math.E,-time/mAmplitude) * Math.cos(mFrequency*time)+ 1);
    }
}

package de.timfreiheit.mathjax.android;

import android.content.res.TypedArray;
import android.os.Build;
import android.webkit.JavascriptInterface;

/**
 * http://docs.mathjax.org/en/latest/options/
 * for more information
 *
 * Created by timfreiheit on 06.06.15.
 */
public class MathJaxConfig {

    public enum Output{
        SVG("output/SVG"),
        HTML_CSS("output/HTML-CSS"),
        CommonHTML("output/CommonHTML"),
        NativeMML("output/NativeMML")
        ;

        String value;

        Output(String s) {
            value = s;
        }
    }

    public enum Input{
        TeX("input/TeX"),
        MathML("input/MathML"),
        AsciiMath("input/AsciiMath")
        ;

        String value;

        Input(String s) {
            value = s;
        }
    }

    private String input = Input.TeX.value;
    private String output = Output.SVG.value;
    private int outputScale = 100;
    private int minScaleAdjust = 100;
    private boolean automaticLinebreaks = false;
    private int blacker = 1;

    public MathJaxConfig(){
        if (Build.VERSION.SDK_INT >= 14) {
            output = Output.SVG.value;
        }else {
            output = Output.HTML_CSS.value;
        }
    }

    public MathJaxConfig(TypedArray attrs){
        this();
        int inputIndex = attrs.getInteger(R.styleable.MathJaxView_input,-1);
        if (inputIndex > 0) {
            setInput(Input.values()[inputIndex]);
        }
        int outputIndex = attrs.getInteger(R.styleable.MathJaxView_output,-1);
        if (outputIndex > 0) {
            setOutput(Output.values()[outputIndex]);
        }
        setAutomaticLinebreaks(attrs.getBoolean(R.styleable.MathJaxView_automaticLinebreaks,automaticLinebreaks));
        setMinScaleAdjust(attrs.getInteger(R.styleable.MathJaxView_minScaleAdjust, minScaleAdjust));
        setOutputScale(attrs.getInteger(R.styleable.MathJaxView_outputScale,outputScale));
        setBlacker(attrs.getInteger(R.styleable.MathJaxView_blacker,blacker));
    }


    public void setInput(Input input){
        this.input = input.value;
    }

    @JavascriptInterface
    public String getInput(){
        return input;
    }

    public void setOutput(Output output){
        this.output = output.value;
    }

    @JavascriptInterface
    public String getOutput(){
        return output;
    }

    public void setOutputScale(int outputScale) {
        this.outputScale = outputScale;
    }

    @JavascriptInterface
    public int getOutputScale() {
        return outputScale;
    }

    public void setMinScaleAdjust(int scale){
        this.minScaleAdjust = scale;
    }

    @JavascriptInterface
    public int getMinScaleAdjust(){
        return minScaleAdjust;
    }

    @JavascriptInterface
    public boolean getAutomaticLinebreaks(){
        return automaticLinebreaks;
    }

    public void setAutomaticLinebreaks(boolean b){
        this.automaticLinebreaks = b;
    }

    public void setBlacker(int blacker){
        this.blacker = blacker;
    }

    @JavascriptInterface
    public int getBlacker(){
        return blacker;
    }

}

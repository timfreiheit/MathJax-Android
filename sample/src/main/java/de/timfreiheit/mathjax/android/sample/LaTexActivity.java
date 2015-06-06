package de.timfreiheit.mathjax.android.sample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.timfreiheit.mathjax.android.MathJaxView;

/**
 *
 * Created by timfreiheit on 30.05.15.
 */
public class LaTexActivity extends Activity
        implements View.OnClickListener
{
    MathJaxView mMathJaxView;

    private int exampleIndex = 0;

    private String getExample(int index) {
        return getResources().getStringArray(R.array.tex_examples)[index];
    }

    public void onClick(@NonNull View v) {
        int id = v.getId();

        EditText e = (EditText) findViewById(R.id.edit);
        switch (id){
            case R.id.showBtn: {
                mMathJaxView.setInputText(e.getText().toString());
                break;
            }
            case R.id.clearBtn: {
                e.setText("");
                mMathJaxView.setInputText("");
                break;
            }
            case R.id.exampleBtn: {
                e.setText(getExample(exampleIndex++));
                if (exampleIndex > getResources().getStringArray(R.array.tex_examples).length-1)
                    exampleIndex=0;
                mMathJaxView.setInputText(e.getText().toString());
                break;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mMathJaxView = (MathJaxView) findViewById(R.id.laTexView);

        EditText e = (EditText) findViewById(R.id.edit);
        e.setBackgroundColor(Color.LTGRAY);
        e.setTextColor(Color.BLACK);
        e.setText("");
        findViewById(R.id.showBtn).setOnClickListener(this);
        findViewById(R.id.clearBtn).setOnClickListener(this);

        View exampleBtn = findViewById(R.id.exampleBtn);
        exampleBtn.setOnClickListener(this);
        //initial load the first example
        onClick(exampleBtn);

        TextView t = (TextView) findViewById(R.id.textview3);
        t.setMovementMethod(LinkMovementMethod.getInstance());
        t.setText(Html.fromHtml(t.getText().toString()));


    }
}


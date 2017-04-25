package de.timfreiheit.mathjax.android.sample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.timfreiheit.mathjax.android.MathJaxView;

/**
 * Created by timfreiheit on 30.05.15.
 */
public class LaTexActivity extends Activity
        implements View.OnClickListener {
    MathJaxView mMathJaxView;

    private int exampleIndex = 0;

    private String getExample(int index) {
        return getResources().getStringArray(R.array.tex_examples)[index];
    }

    public void onClick(@NonNull View v) {
        int id = v.getId();

        EditText e = (EditText) findViewById(R.id.edit);
        switch (id) {
            case R.id.showBtn: {
                showMathJax(e.getText().toString());
                break;
            }
            case R.id.clearBtn: {
                e.setText("");
                showMathJax("");
                break;
            }
            case R.id.exampleBtn: {
                e.setText(getExample(exampleIndex++));
                if (exampleIndex > getResources().getStringArray(R.array.tex_examples).length - 1)
                    exampleIndex = 0;
                showMathJax(e.getText().toString());
                break;
            }
        }
    }

    private void showMathJax(String value) {
        mMathJaxView.setInputText(value);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mMathJaxView = (MathJaxView) findViewById(R.id.laTexView);
        setupMathJaxListener();
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

    private void setupMathJaxListener() {
        mMathJaxView.setRenderListener(new MathJaxView.OnMathJaxRenderListener() {
            @Override
            public void onRendered() {
                showToast();
            }
        });
    }

    private void showToast() {
        Toast.makeText(this, "Render complete", Toast.LENGTH_SHORT).show();
    }
}


# MathJaxAndroid
[MathJax](https://github.com/mathjax/MathJax) Wrapper for Android to render LaTex in an WebView.


# Usage

## Include in Layout

```

    <de.timfreiheit.mathjax.android.MathJaxView
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/laTexView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_gravity="center"
            app:input="TeX"
            app:automaticLinebreaks="true"
            app:output="SVG"/>

```


## Render Example LaTex

```Java

    mMathJaxView.setInputText("\\(x=\\frac{-b\\pm\\sqrt{b^2-4ac}}{2a}\\)");

```


## Adding Listeners

```Java

    mMathJaxView.setRenderListener(new MathJaxView.OnMathJaxRenderListener() {
            @Override
            public void onRendered() {
                showToast();
            }
        });

```


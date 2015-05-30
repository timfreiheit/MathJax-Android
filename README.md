# MathJaxAndroid
[MathJax](https://github.com/mathjax/MathJax) Wrapper for Android to render LaTex in an WebView.


# Usage

## Include in Layout

```

    <de.timfreiheit.mathjax.android.MathJaxView
    			android:layout_width="match_parent"
    			android:layout_height="match_parent"/>

```


## Render Example LaTex

```Java

    mMathJaxView.setLaTex("x=\\frac{-b\\pm\\sqrt{b^2-4ac}}{2a}");

```




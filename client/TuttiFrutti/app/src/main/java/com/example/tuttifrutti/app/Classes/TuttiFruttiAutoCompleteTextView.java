package com.example.tuttifrutti.app.Classes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.TuttiFruttiCore.Category;
import com.example.tuttifrutti.app.R;
import com.tokenautocomplete.TokenCompleteTextView;

/**
 * Created by Sebastian on 14/09/2014.
 */
public class TuttiFruttiAutoCompleteTextView extends TokenCompleteTextView {

    public TuttiFruttiAutoCompleteTextView(Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getViewForObject(Object o) {
        Category c = (Category)o;

        LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout)l.inflate(R.layout.category_autocomplete, (ViewGroup)TuttiFruttiAutoCompleteTextView.this.getParent(), false);
        ((TextView)view.findViewById(R.id.name)).setText(c.getName());
        return view;
    }

    @Override
    protected Object defaultObject(String s) {
       return new Category(s);
    }
}

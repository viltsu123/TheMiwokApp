package com.example.viltsu.transitionsbetweenviews;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ville-pekkapalmgren on 22/02/18.
 *
 * My first custom adapter!!!!
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int colorRes;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorResId){
        super(context,0, words);
        colorRes = colorResId;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listITem = convertView;

        if(listITem == null){
            listITem = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_2, parent, false
            );
        }

        Word currentWord = getItem(position);

        TextView miwok = (TextView) listITem.findViewById(R.id.item1);
        TextView english = (TextView) listITem.findViewById(R.id.item2);

        miwok.setText(currentWord.getMiwok());
        english.setText(currentWord.getEnglish());

        if(currentWord.getPicture() == -1){
            ImageView pic = (ImageView) listITem.findViewById(R.id.list_pic);
            pic.setVisibility(View.GONE);
        }else{
            ImageView pic = (ImageView) listITem.findViewById(R.id.list_pic);
            pic.setImageResource(currentWord.getPicture());
            pic.setVisibility(View.VISIBLE);
        }
        //setting background color for tekstitTasuta
        View tekstitTasuta = listITem.findViewById(R.id.tekstit_lista);

        int color = ContextCompat.getColor(getContext(), colorRes);

        tekstitTasuta.setBackgroundColor(color);

        return listITem;
        //return super.getView(position, convertView, parent);
    }
}

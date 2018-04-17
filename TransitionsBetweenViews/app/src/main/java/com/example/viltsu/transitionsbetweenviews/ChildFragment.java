package com.example.viltsu.transitionsbetweenviews;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildFragment extends Fragment {

    private MediaPlayer mp;
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp){
            releseMediaPlayer();
        }
    };
    private AudioManager myAudioManager;
    private AudioManager.OnAudioFocusChangeListener myAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //audio focus lähti hetkeksi, tulee takas. Mitä teet? Pause ja alkuun, soitto jatkuu focuksen tultua takasin.
                mp.pause();
                mp.seekTo(0);
            }else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                //focus tulee takas niin soitto alkaa.
                mp.start();
            }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                //vapauttaa resurssit taas.
                releseMediaPlayer();
            }
        }
    };


    public ChildFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_number, container, false);

        myAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> wordZ = new ArrayList<Word>();

        wordZ.add(new Word("lutti", "gray", R.raw.family_grandfather));
        wordZ.add(new Word("otiiko","lisa", R.raw.family_grandfather));
        wordZ.add(new Word("tolookosu","bear", R.raw.family_grandfather));
        wordZ.add(new Word( "oyyisa","poo", R.raw.family_grandfather));
        wordZ.add(new Word("massokka","whatever", R.raw.family_grandfather));
        wordZ.add(new Word("temmokka","tryingtofinishthis", R.raw.family_grandfather));
        wordZ.add(new Word("kenekaku","timeisoftheessence", R.raw.family_grandfather));
        wordZ.add(new Word("kawinta","letsgoletsgo", R.raw.family_grandfather));
        wordZ.add(new Word("wo'e","hahaha", R.raw.family_grandfather));
        wordZ.add(new Word("na'aacha","anddone", R.raw.family_grandfather));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(),wordZ,R.color.children);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int tulos = myAudioManager.requestAudioFocus(myAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                if(tulos == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mp = MediaPlayer.create(getActivity(), wordZ.get(i).getAudio());
                    mp.start();
                    mp.setOnCompletionListener(completionListener);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releseMediaPlayer();
    }

    public void releseMediaPlayer(){
        if(mp != null) {
            mp.release();
            mp = null;
            myAudioManager.abandonAudioFocus(myAudioFocusChangeListener);
        }
    }

}

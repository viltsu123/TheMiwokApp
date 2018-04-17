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
public class ParentFragment extends Fragment {

    private MediaPlayer mp;
    private MediaPlayer.OnCompletionListener releaseMediaPlayer = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            freeMedia();
        }
    };
    private AudioManager myAudioManager;
    private AudioManager.OnAudioFocusChangeListener myAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mp.pause();
                mp.seekTo(0);
            }else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mp.start();
            }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                freeMedia();
            }
        }
    };

    public ParentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_number, container, false);

        myAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> wordZ = new ArrayList<Word>();

        wordZ.add(new Word("lutti", "dad", R.drawable.ruori, R.raw.family_father));
        wordZ.add(new Word("otiiko","mum", R.drawable.ruori, R.raw.family_daughter));
        wordZ.add(new Word("tolookosu","sister", R.drawable.ruori, R.raw.family_grandfather));
        wordZ.add(new Word( "oyyisa","brother", R.drawable.ruori, R.raw.family_mother));
        wordZ.add(new Word("massokka","uncle", R.drawable.ruori, R.raw.family_grandfather));
        wordZ.add(new Word("temmokka","aunt", R.drawable.ruori, R.raw.family_grandfather));
        wordZ.add(new Word("kenekaku","cousin", R.drawable.ruori, R.raw.family_grandfather));
        wordZ.add(new Word("kawinta","grandma", R.drawable.ruori, R.raw.family_grandfather));
        wordZ.add(new Word("wo'e","pappap", R.drawable.ruori, R.raw.family_grandfather));
        wordZ.add(new Word("na'aacha","stepuncle", R.drawable.ruori, R.raw.family_grandfather));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(),wordZ,R.color.parents);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int tulos = myAudioManager.requestAudioFocus(myAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                if(tulos == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mp = MediaPlayer.create(getActivity(), wordZ.get(i).getAudio());
                    mp.start();
                    mp.setOnCompletionListener(releaseMediaPlayer);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        freeMedia();
    }

    public void freeMedia(){
        if(mp != null){
            mp.release();
            mp = null;
            myAudioManager.abandonAudioFocus(myAudioFocusChangeListener);
        }

    }

}

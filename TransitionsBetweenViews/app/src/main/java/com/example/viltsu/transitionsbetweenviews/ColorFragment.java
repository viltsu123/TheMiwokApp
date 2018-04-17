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


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {

    private MediaPlayer mp;
    private MediaPlayer.OnCompletionListener completionListener= new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp){
            releaseMediaPlayer();
        }
    };
    private AudioManager mAudioManager;
    //uusi metodi statejen käsittelyyn. Takaa estottoman audio toiston.
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
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
                releaseMediaPlayer();
            }
        }
    };

    public ColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_number, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> wordZ = new ArrayList<Word>();

        wordZ.add(new Word("lutti", "one", R.drawable.indina, R.raw.family_grandfather));
        wordZ.add(new Word("otiiko","two", R.drawable.indina, R.raw.family_grandfather));
        wordZ.add(new Word("tolookosu","three", R.drawable.indina, R.raw.family_grandfather));
        wordZ.add(new Word( "oyyisa","four",R.drawable.indina, R.raw.family_grandfather));
        wordZ.add(new Word("massokka","five", R.drawable.indina, R.raw.family_grandfather));
        wordZ.add(new Word("temmokka","six", R.drawable.indina, R.raw.family_grandfather));
        wordZ.add(new Word("kenekaku","seven", R.drawable.indina, R.raw.family_grandfather));
        wordZ.add(new Word("kawinta","eight", R.drawable.indina, R.raw.family_grandfather));
        wordZ.add(new Word("wo'e","nine", R.drawable.indina, R.raw.family_grandfather));
        wordZ.add(new Word("na'aacha","ten", R.drawable.indina, R.raw.family_grandfather));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(),wordZ,R.color.colors);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //uusi lisäys: audiomanagerin state muutoksiin. Tarkistaa mikä state on menossa ja alottaa jos voi
                int tulos = mAudioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
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
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer(){
        if(mp != null){
            mp.release();
            mp = null;
            mAudioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }

}

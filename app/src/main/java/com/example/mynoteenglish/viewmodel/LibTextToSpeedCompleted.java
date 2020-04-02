package com.example.mynoteenglish.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import com.example.mynoteenglish.R;
import com.example.mynoteenglish.view.MainActivity;

import java.util.HashMap;
import java.util.Locale;

public class LibTextToSpeedCompleted implements TextToSpeech.OnInitListener
{

    boolean repeat =false;
    Context mcontext;
    Activity mainActivity;
    private boolean initialized;
    private String queuedText;
    private String TAG = "TTS";
    private TextToSpeech tts;
    private String  textTospeak ="";
    public LibTextToSpeedCompleted(Activity activity) {
        this.mcontext= activity;
        mainActivity= activity;
        tts = new TextToSpeech(activity, this /* listener */);
        tts.setOnUtteranceProgressListener(mProgressListener);
        Log.d(TAG,"TTS create");
    }
//https://stackoverflow.com/questions/19312536/android-tts-fails-to-speak-large-amount-of-text
//    int length = toSpeech.getMaxSpeechInputLength() - 1;
//    Iterable<String> chunks = Splitter.fixedLength(length).split(largeText);
//Lists.newArrayList(chunks);
//private void speech(String charSequence) {
//
//    int position ;
//
//
//    int sizeOfChar= charSequence.length();
//    String testStri= charSequence.substring(position,sizeOfChar);
//
//
//    int next = 20;
//    int pos =0;
//    while(true) {
//        String temp="";
//        Log.e("in loop", "" + pos);
//
//        try {
//
//            temp = testStri.substring(pos, next);
//            HashMap<String, String> params = new HashMap<String, String>();
//            params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, temp);
//            engine.speak(temp, TextToSpeech.QUEUE_ADD, params);
//
//            pos = pos + 20;
//            next = next + 20;
//
//        } catch (Exception e) {
//            temp = testStri.substring(pos, testStri.length());
//            engine.speak(temp, TextToSpeech.QUEUE_ADD, null);
//            break;
//
//        }
//
//    }
//
//}
    public void speak(String text) {

        if (!initialized) {
            queuedText = text;
            return;
        }
        queuedText = null;

        setTtsListener(); // no longer creates a new UtteranceProgressListener each time
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        tts.speak(text, TextToSpeech.QUEUE_ADD, map);
        textTospeak= text;
        Log.d(TAG,tts.getMaxSpeechInputLength()+"");
    }
    public  void SetLanguage(Locale locale)
    {
        tts.setLanguage(locale);
    }
    public  void SetSpeed(Float value)
    {
        tts.setSpeechRate(value);
    }
    public void SetStop()
    {
        tts.stop();
    }
    public void SetRepeat(boolean status)
    {
       repeat=status;
    }
    private void setTtsListener() {

    }

    @Override
    public void onInit(int status) {
        Log.d(TAG,"TTS Init");
        if (status == TextToSpeech.SUCCESS) {
            initialized = true;
            tts.setLanguage(Locale.ENGLISH);

            if (queuedText != null) {
                speak(queuedText);
            }
        }
    }
    private abstract class runnable implements Runnable {
    }




    private UtteranceProgressListener mProgressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {
        } // Do nothing

        @Override
        public void onError(String utteranceId) {
        } // Do nothing.

        @Override
        public void onDone(String utteranceId) {
                Log.d(TAG,"TTS Done");
            new Thread()
            {
                public void run()
                {
                    mainActivity.runOnUiThread(new runnable()
                    {
                        public void run()
                        {

                            if (repeat)
                            {
                                speak(textTospeak);

                            }

                        }
                    });
                }
            }.start();

        }
    };
}

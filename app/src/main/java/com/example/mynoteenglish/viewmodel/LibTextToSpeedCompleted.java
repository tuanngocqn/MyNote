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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class LibTextToSpeedCompleted implements TextToSpeech.OnInitListener
{

    boolean repeat =false,checkstatus=false;
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
    public void speak(String text) {
        checkstatus=false;
        if (!initialized) {
            queuedText = text;
            return;
        }
        String afterSubstring=text;
        ArrayList<String> arrayList= new ArrayList<>();
        int maxlength=tts.getMaxSpeechInputLength()-1;
//        int position=0;
//        int next =maxlength;
        HashMap<String, String> map;// su dung cai nay de cho co the dung detect completed!@
        queuedText = null;
       while (true) {
           try {
               String stringTospeak= afterSubstring.substring(0, maxlength);
               afterSubstring= afterSubstring.substring(maxlength);
               map= new HashMap<String, String>();
               map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
               tts.speak(stringTospeak, TextToSpeech.QUEUE_ADD,map);
           }
           catch (Exception e)
           {
               checkstatus=true;
               map= new HashMap<String, String>();
               map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
               tts.speak(afterSubstring, TextToSpeech.QUEUE_ADD,map);
               break;
           }

       }
        textTospeak= text;
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

                            if (repeat && checkstatus)
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

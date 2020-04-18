package com.example.mynoteenglish.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import com.example.mynoteenglish.R;
import com.example.mynoteenglish.model.OnlistenerVocabulary;
import com.example.mynoteenglish.model.Onlistennertexttospeed;
import com.example.mynoteenglish.view.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Logger;

public class LibTextToSpeedCompleted implements TextToSpeech.OnInitListener
{


    boolean repeat =false,checkstatus=false;
    Context mcontext;
    Activity mainActivity;
    private boolean initialized;
    private String queuedText;
    Boolean Ready=false;
    private String TAG = "TTS";
    private TextToSpeech tts;
    private String  textTospeak ="";
    Onlistennertexttospeed onlistennertexttospeed;
    public LibTextToSpeedCompleted(Activity activity) {
        this.mcontext= activity;
        mainActivity= activity;
        tts = new TextToSpeech(activity, this /* listener */);
        tts.setOnUtteranceProgressListener(mProgressListener);
        Log.d("bbb","Creat this program");
    }
    public void speak(String text) {
        checkstatus=false;
        if (!Ready)
        {
            Toast.makeText(mainActivity,"Text to speech not ready",Toast.LENGTH_SHORT).show();
            return;
        }

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
        int result =tts.setLanguage(locale);
        if (result==TextToSpeech.LANG_MISSING_DATA)
        {
            this.Ready=false;
            Toast.makeText(mainActivity,"LANG_MISSING_DATA",Toast.LENGTH_SHORT).show();
            return;
        }
        else  if(result==TextToSpeech.LANG_NOT_SUPPORTED)
        {
            this.Ready=false;
            Toast.makeText(mainActivity,"LANG_NOT_SUPPORTED",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            this.Ready=true;
            Locale currentLanguage= tts.getVoice().getLocale();
           // Toast.makeText(mainActivity,"Language "+ currentLanguage,Toast.LENGTH_SHORT).show();
        }
    }
    public  void SetSpeed(Float value)
    {
        tts.setSpeechRate(value);
    }
    public void SetStop()
    {
        tts.stop();

    }
    public boolean isRepeat() {
        return repeat;
    }

    public void SetRepeat(boolean repeat) {
        this.repeat = repeat;
    }
    private void setTtsListener() {

    }

    @Override
    public void onInit(int status) {
        Log.d(TAG,"TTS Init");
        if (status == TextToSpeech.SUCCESS) {
            initialized = true;
            SetLanguage(Locale.ENGLISH);

            if (queuedText != null) {
                speak(queuedText);
            }
        }
    }

    public void Shutdown() {
        tts.shutdown();
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
                               // Toast.makeText(mainActivity, textTospeak,Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }.start();

            onlistennertexttospeed.Onlistensucess(mainActivity);
        }
    };
    public  void SetOnEventOnSucess(Onlistennertexttospeed onlistennertexttospeed)
    {
        this.onlistennertexttospeed= onlistennertexttospeed;
    }
}

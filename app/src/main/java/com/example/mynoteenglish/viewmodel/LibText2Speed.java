package com.example.mynoteenglish.viewmodel;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class LibText2Speed {
    TextToSpeech textToSpeech;
    Context context;
    Boolean Ready=false;
    boolean repeat=false;
    private String envPath = Environment.getDataDirectory().getAbsolutePath() + "/Text2Speech";
    private Uri fileUri;
    public LibText2Speed(Context contextInput,Activity activity)
    {
        context= contextInput;
        textToSpeech = new TextToSpeech(contextInput, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Set_Language(Locale.ENGLISH);
            }
        });
        textToSpeech.setSpeechRate(1);
    }
//    public void fileCreate(String text,String namefolder) {
//        if (isCheckWriteExternal()&& isCheckpermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
//        {
//            Log.d("nnn",namefolder);
//            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/5Note" + "/"+namefolder+"/");
//            dir.mkdir();
//            if (dir.exists())
//            {
//                File file = new File(dir, "Text2Speech" + new Random().nextDouble() +".mp3");
//                int test = textToSpeech.synthesizeToFile((CharSequence) text, null, file, "tts");
//                Toast.makeText(context,"fileCreate "+ " Successfully !",Toast.LENGTH_LONG).show();
//            }
//            else
//            {
//                Toast.makeText(context,"fileCreate "+ "Not Exist",Toast.LENGTH_LONG).show();
//            }
//        }
//        else
//        {
//            Toast.makeText(context,"fileCreate "+ "Not Permission",Toast.LENGTH_LONG).show();
//        }
//
//
//    }
//
    public  void stop()
    {
        textToSpeech.stop();
    }
    public void SpeakOut(String inputtext) {
        if(!Ready)
        {
            Toast.makeText(context,"Text to speech not ready",Toast.LENGTH_LONG).show();
            return;
        }
        textToSpeech.speak(inputtext,TextToSpeech.QUEUE_FLUSH,null, UUID.randomUUID().toString());
    }

    public  void SetspeechRate( float speechRate)
    {
        textToSpeech.setSpeechRate(speechRate);
    }
    public void Set_Language(Locale input)
    {

        int result = textToSpeech.setLanguage(input);
        if (result==TextToSpeech.LANG_MISSING_DATA)
        {
            this.Ready=false;
            Toast.makeText(context,"LANG_MISSING_DATA",Toast.LENGTH_LONG).show();
            return;
        }
        else  if(result==TextToSpeech.LANG_NOT_SUPPORTED)
        {
            this.Ready=false;
            Toast.makeText(context,"LANG_NOT_SUPPORTED",Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            this.Ready=true;
            Locale currentLanguage= textToSpeech.getVoice().getLocale();
            Toast.makeText(context,"Language "+ currentLanguage,Toast.LENGTH_LONG).show();
        }

    }
}

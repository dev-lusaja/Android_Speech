package com.example.osanchez.speechvoice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import java.util.Locale;
import android.speech.tts.TextToSpeech;

public class MainActivity extends AppCompatActivity {

    /* variables */
    Button btn_numbers;
    TextToSpeech text_to_speech, speech_object;
    EditText txt_value;
    Locale loc;
    String[] ar;
    ImageButton btn_pause, btn_speak;



    public TextToSpeech speech_object(){
	    /* Speech Object */
        speech_object = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                }
            }
        });
        return speech_object;
    }

    public TextToSpeech set_voice(TextToSpeech object){
        Spinner spinner_voice=(Spinner) findViewById(R.id.options_language);
        String voice_value = spinner_voice.getSelectedItem().toString();
        if (voice_value.equals("Español")){
            loc = new Locale("es", "MEX");
            object.setLanguage(loc);
            ar = getResources().getStringArray(R.array.spanish_numbers);
        } else if (voice_value.equals("Frances")){
            object.setLanguage(Locale.FRANCE);
            ar = getResources().getStringArray(R.array.france_numbers);
        } else if (voice_value.equals("Portugues")){
            loc = new Locale("pt", "BR");
            object.setLanguage(loc);
            ar = getResources().getStringArray(R.array.portuges_numbers);
        } else if (voice_value.equals("Portugues")){
            loc = new Locale("pt", "BR");
            object.setLanguage(loc);
            ar = getResources().getStringArray(R.array.portuges_numbers);
        } else if (voice_value.equals("Aleman")){
            object.setLanguage(Locale.GERMANY);
            ar = getResources().getStringArray(R.array.germany_numbers);
        }
        Toast.makeText(getApplicationContext(), "Language: " + voice_value,Toast.LENGTH_SHORT).show();
        return object;
    }

    public void speak(TextToSpeech object, String val){
        object.speak(val, TextToSpeech.QUEUE_ADD, null);
    }

    public void onPause(){
        if(text_to_speech !=null){
            text_to_speech.stop();
            text_to_speech.shutdown();
        }
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        txt_value = (EditText)findViewById(R.id.txt_value);
        btn_numbers = (Button)findViewById(R.id.btn_numbers);
        btn_speak = (ImageButton)findViewById(R.id.btn_speak_2);
        btn_pause = (ImageButton)findViewById(R.id.btn_pause);

        /* Create speech object */
        text_to_speech = speech_object();

        /* Button Listener */
        btn_numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String toSpeak = txt_value.getText().toString();
                text_to_speech = set_voice(text_to_speech);
                int len = ar.length;
                for (int i = 0; i < len; i++){
                    speak(text_to_speech,ar[i]);
                }
            }
        });

        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_to_speech = set_voice(text_to_speech);
                speak(text_to_speech,txt_value.getText().toString());
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPause();
                Toast.makeText(getApplicationContext(), "Voice paused",Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

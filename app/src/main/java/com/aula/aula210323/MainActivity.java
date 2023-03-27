package com.aula.aula210323;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView texto;
    ImageView microfone;
    String ditado;
    ImageButton ouvir;
    private final int ID_TEXTO_PARA_VOZ = 100;
    TextToSpeech textToSpeech;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto = (TextView) findViewById(R.id.texto);
        microfone = (ImageView) findViewById(R.id.microfone);
        ouvir = (ImageButton) findViewById(R.id.ouvir);
        microfone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iVoz = new
                        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                iVoz.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt_BR");
                iVoz.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale o Título");

                try {
                    startActivityForResult(iVoz, ID_TEXTO_PARA_VOZ);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(), "Seu aparelho não tem suporte para o idioma",Toast.LENGTH_LONG).show();
                }
            }
        });
        textToSpeech = new TextToSpeech(getApplicationContext(), new
                TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i != TextToSpeech.ERROR) {
                            Locale loc = new Locale("pt", "BR");

                            textToSpeech.setLanguage(loc);
                        }
                    }
                });
        ouvir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ouvir.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ouvir.setVisibility(View.VISIBLE);
                    }
                }, 200);
                String toSpeak = texto.getText().toString();
                textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    protected void onActivityResult(int id, int resultCodeID, Intent dados) {
        super.onActivityResult(id, resultCodeID, dados);
        switch (id) {
            case ID_TEXTO_PARA_VOZ:
                if (resultCodeID == RESULT_OK && null != dados) {
                    ArrayList<String> result =
                            dados.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    ditado = result.get(0);
                    comparador();
                }
                break;
        }
    }

    public void comparador() {
        texto.setText(ditado);
// Depois comentem a linha a cima e descometem a linha de baixo
//aqui quando vc falar “unifacear” o metodo vai comparar as string e vai mostar um aviso usando o – Toast -
 /*texto.setText("Unifacear");
 if(texto.getText().toString().toLowerCase().equals(ditado)){

Toast.makeText(getApplicationContext(),"Acertou!!!",Toast.LENGTH_LONG).sho
w();
 }
 else{

Toast.makeText(getApplicationContext(),"Errou!!!",Toast.LENGTH_LONG).show(
);
 }*/
    }
}
// sempre usem o toLowerCase para que as frases fiquem todas com as letras



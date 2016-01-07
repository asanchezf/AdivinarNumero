package com.antonioejemplos.adivinarnumero;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mp;
    private EditText txtEntrada;
    private TextView lblSalida;
    private Button btnAceptar,btnReiniciar;
    private int apuesta;
    private int adivinar;

    //Preferencias de sonido
    private boolean ACTIVAR_SONIDO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Incicializamos componentes
        txtEntrada=(EditText)findViewById(R.id.txtEntrada);
        lblSalida=(TextView)findViewById(R.id.lblSalida);
        btnAceptar=(Button)findViewById(R.id.btnAceptar);
        btnReiniciar=(Button)findViewById(R.id.btnReiniciar);
        generarNumero();





//        Random r = new Random();//Objeto Random
//        final int adivinar = r.nextInt(101); // genera un número entre 0 y 100
//        apuesta=0;



        SharedPreferences preferencias= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ACTIVAR_SONIDO=preferencias.getBoolean("swipe_refresh",false);

    if(ACTIVAR_SONIDO){

        mp= MediaPlayer.create(this, R.raw.sonido);
        mp.setLooping(true);

        }

        btnAceptar.setOnClickListener(new View.OnClickListener() {

            private int i=0;//Contador de intentos

            @Override
            public void onClick(View v) {
                //int apuesta = 0;

//                generarNumero();
                if (!txtEntrada.getText().toString().isEmpty()) {
                    //Recogemos los valores que se introduzcan en el edittext.
                    String valor = txtEntrada.getText().toString();
                    apuesta = Integer.parseInt(valor);


                    while (adivinar != apuesta) {
                        i++;
                        if (adivinar > apuesta) {
                            //String ususario=preferencias.getString("username",null);
                            //String urlUsuario=String.format("Hola  '%s' adios",ususario);
                            String mensajeMayorque=String.format("El número a adivinar es más grande que %d .Inténtalo otra vez.",apuesta);
                            //lblSalida.setText("El número a adivinar es más grande que "+ apuesta +". Inténtalo otra vez.");
                            lblSalida.setText(mensajeMayorque);
                            Toast.makeText(btnAceptar.getContext(), "El número a adivinar es más grande. Vuelve a intentarlo.", Toast.LENGTH_LONG).show();
                            break;


                        } else {
                            String mensajeMenorque=String.format("El número a adivinar es más pequeño que %d .Inténtalo otra vez.",apuesta);
                            //lblSalida.setText("El número a adivinar es más pequeño que "+ apuesta +". Inténtalo otra vez.");
                            lblSalida.setText(mensajeMenorque);
                            Toast.makeText(btnAceptar.getContext(), "El número a adivinar es más pequeño. Vuelve a intentarlo.", Toast.LENGTH_LONG).show();
                            break;
                        }


                    }

                    if (adivinar != apuesta) {
                        txtEntrada.setText("");
                    } else {
                        //lblSalida.setTextColor(android.R.color.holo_green_light);
                        btnAceptar.setVisibility(View.INVISIBLE);

                        Toast.makeText(
                                btnAceptar.getContext(),
                                "ENHORABUENA!!!", Toast.LENGTH_LONG).show();


                        if (i == 1) {
                            lblSalida.setText("Felicidades has acertado el número y has necesitado " + i + " intento.");
                            mp.pause();
                            btnReiniciar.setVisibility(View.VISIBLE);
                        } else {
                            lblSalida.setText("Felicidades has acertado el número y has necesitado " + i + " intentos.");
                            mp.pause();
                            btnReiniciar.setVisibility(View.VISIBLE);
                        }

                        //lblSalida.setTextColor(android.R.color.holo_purple);
                    }

                }

            }//Fin if control entrada

        });


        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciar();
            }
        });

    }

    private void generarNumero() {
        adivinar=0;
        apuesta=0;

        Random r = new Random();//Objeto Random
        //adivinar = r.nextInt(100); // genera un número entre 0 y 99
        adivinar = r.nextInt((100)+1); // genera un número entre 0 y 100

        //adivinar=(int)(r.nextDouble() * 101 + 1);



    }

    public void reiniciar(){
//        mp= MediaPlayer.create(this, R.raw.sonido);
//
//        mp.setLooping(true);
        mp.start();
        apuesta=0;
        txtEntrada.getText().clear();
        lblSalida.setText("");
        btnReiniciar.setVisibility(View.INVISIBLE);
        btnAceptar.setVisibility(View.VISIBLE);
        generarNumero();

    }




    @Override protected void onResume() {
		/*
		Se llama cuando la actividad
		 *  va a comenzar a interactuar con el usuario. Es un buen lugar para lanzar
		 *  las animaciones y la música.
		 *
		 * */

        super.onResume();
        if(ACTIVAR_SONIDO) {
            mp.start();
        }
        //Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }


    @Override protected void onPause() {
       // Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        super.onPause();
        if(ACTIVAR_SONIDO) {
            mp.stop();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            Intent intent=new Intent(this,Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

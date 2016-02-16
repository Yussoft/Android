package gymkana.brujulavoz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.speech.RecognizerIntent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends Activity implements SensorEventListener {
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;

    // Objeto para la imagen de la brujula
    private ImageView imagen; //, flecha;
    private float dest= 0.0f;
    private String direccion;
    private int tol = 0;
    private boolean en_direccion = false;
    private TextView tvDir;

    // Variable para almacenar el angulo de la brujula
    private float angulo = 0.0f;
    // Manejador del sensor
    private SensorManager sManager;
    // Objeto para manejar el boton
    private Button botonHablar;
    private static int ASR_CODE = 123;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.botonHablar = (Button)this.findViewById(R.id.button);
        this.botonHablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga la dirección");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,  new Locale("spa","ESP"));
                startActivityForResult(intent, ASR_CODE);
            }
        });

        imagen = (ImageView)findViewById(R.id.rosadelosvientos);
        //flecha = (ImageView)findViewById(R.id.flecha_d);

        // Texto que nos muestra el angulo actual
        tvDir = (TextView) findViewById(R.id.tvHeading);

        // Inicializamos el sensor
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @SuppressLint("InlinedApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ASR_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    ArrayList<String> nBestList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String[] palabras = nBestList.get(0).split(" ");
                    if(palabras.length == 2 && formatoCorrecto(palabras[0]) && numerico(palabras[1])) {
                        tvDir.setText("Tu dirección: ".concat(nBestList.get(0)));
                        String tolerancia = palabras[1];
                        direccion = palabras[0];
                        tol = Integer.parseInt(tolerancia);
                        switch (direccion.toLowerCase()) {
                            case "norte":
                                dest = 0f;
                                en_direccion = true;
                                break;
                            case "sur":
                                dest = 180f;
                                en_direccion = true;
                                break;
                            case "este":
                                dest = 90f;
                                en_direccion = true;
                                break;
                            case "oeste":
                                dest = 270f;
                                en_direccion = true;
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), getString(R.string.error_orden), Toast.LENGTH_LONG).show();
                                break;
                        }
                    }else Toast.makeText(getApplicationContext(), getString(R.string.error_orden), Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    private boolean numerico(String tole) {
        try
        {
            int d = Integer.parseInt(tole);
        }
        catch(NumberFormatException nfe)
        {
            Toast.makeText(getApplicationContext(), getString(R.string.error_orden), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean formatoCorrecto(String dir) {
        switch(dir.toLowerCase()) {
            case "norte":
                return true;
            case "sur":
                return true;
            case "este":
                return true;
            case "oeste":
                return true;
            default:
                Toast.makeText(getApplicationContext(), getString(R.string.error_orden), Toast.LENGTH_LONG).show();
                return false;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        sManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // Obtiene el angulo
        float degree = Math.round(event.values[0]);


        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra1 = new RotateAnimation(
                angulo,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra1.setDuration(210);
        // set the animation after the end of the reservation status
        ra1.setFillAfter(true);
        // Start the animation
        imagen.startAnimation(ra1);
        angulo = -degree;

        if(en_direccion == true) {
            if (degree < dest + (360 * tol / 2) / 100 && degree > dest - (360 * tol / 2) / 100) {
                imagen.setImageResource(R.drawable.rosadelosvientos);
                tvDir.setText(R.string.dir_correcta);
            }else {
                imagen.setImageResource(R.drawable.rosadelosvientos2);
                tvDir.setText("Tu dirección: ".concat(direccion).concat(" ").concat(Integer.toString(tol)));
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }


}

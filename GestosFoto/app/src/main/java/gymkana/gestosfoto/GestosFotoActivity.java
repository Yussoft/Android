package gymkana.gestosfoto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import static java.lang.Math.abs;

public class GestosFotoActivity extends FragmentActivity{

    ImageView img;
    Intent i;
    Bitmap bmp;

    int rank;
    int coor11[] = new int[2], coor12[] = new int[2], coor13[] = new int[2];
    int coor21[] = new int[2], coor22[] = new int[2], coor23[] = new int[2];
    int coor31[] = new int[2], coor32[] = new int[2], coor33[] = new int[2];
    ImageView b11,b12,b13,b21,b22,b23,b31,b32,b33;
    ImageView photoV;
    boolean tocandob11 = false, tocandob12 = false,tocandob13 = false,tocandob21 = false,tocandob22 = false;
    boolean tocandob23 = false,tocandob31 = false,tocandob32 = false,tocandob33 = false;

    private static int CAM_CODE = 123;

    boolean isImageFitToScreen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestos_foto);
        b11 = (ImageView) findViewById(R.id.punto_11);
        b12 = (ImageView) findViewById(R.id.punto_12);
        b13 = (ImageView) findViewById(R.id.punto_13);
        b21 = (ImageView) findViewById(R.id.punto_21);
        b22 = (ImageView) findViewById(R.id.punto_22);
        b23 = (ImageView) findViewById(R.id.punto_23);
        b31 = (ImageView) findViewById(R.id.punto_31);
        b32 = (ImageView) findViewById(R.id.punto_32);
        b33 = (ImageView) findViewById(R.id.punto_33);

        photoV = (ImageView)findViewById(R.id.photoView);

        rank = b11.getWidth();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        /*******************************************************/
        b11.getLocationInWindow(coor11);
        b12.getLocationInWindow(coor12);
        b13.getLocationInWindow(coor13);
        b21.getLocationInWindow(coor21);
        b22.getLocationInWindow(coor22);
        b23.getLocationInWindow(coor23);
        b31.getLocationInWindow(coor31);
        b32.getLocationInWindow(coor32);
        b33.getLocationInWindow(coor33);
        /********************************************************/

        if (event.getAction()==MotionEvent.ACTION_DOWN) {
            int px = (int) event.getRawX();
            int py = (int) event.getRawY();

            if((px-coor31[0])<240 && (px-coor31[0])>0 && (py-coor31[1])<240 && (py-coor31[1])>0){
                tocandob31 = true;
                b31.setImageResource(R.drawable.puntoverde);

            }else{
                tocandob31 = false;
                b31.setImageResource(R.drawable.puntogris);
            }
        }else if (event.getAction()==MotionEvent.ACTION_MOVE && tocandob31){
            int px = (int) event.getRawX();
            int py = (int) event.getRawY();
            if((px-coor21[0])<240 && (px-coor21[0])>0 && (py-coor21[1])<240 && (py-coor21[1])>0){
                tocandob21 = true;
                b21.setImageResource(R.drawable.puntoverde);
            }
            if((px-coor12[0])<240 && (px-coor12[0])>0 && (py-coor12[1])<240 && (py-coor12[1])>0){
                if(tocandob21) {
                    tocandob12 = true;
                    b12.setImageResource(R.drawable.puntoverde);
                }
            }
            if((px-coor23[0])<240 && (px-coor23[0])>0 && (py-coor23[1])<240 && (py-coor23[1])>0){
                if(tocandob21 && tocandob12) {
                    tocandob23 = true;
                    b23.setImageResource(R.drawable.puntoverde);
                }
            }
            if((px-coor33[0])<240 && (px-coor33[0])>0 && (py-coor33[1])<240 && (py-coor33[1])>0){
                if(tocandob21 && tocandob12 && tocandob23) {
                    tocandob33 = true;
                    b33.setImageResource(R.drawable.puntoverde);
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, CAM_CODE);
                    }
                }
                /*****************/
                b31.setImageResource(R.drawable.puntogris);
                b21.setImageResource(R.drawable.puntogris);
                b12.setImageResource(R.drawable.puntogris);
                b23.setImageResource(R.drawable.puntogris);
                b33.setImageResource(R.drawable.puntogris);
                tocandob11 = tocandob12 = tocandob13 = tocandob21 = tocandob22 = tocandob23 =
                        tocandob31 = tocandob32 = tocandob33 = false;
            }
            if((px-coor11[0])<240 && (px-coor11[0])>0 && (py-coor11[1])<240 && (py-coor11[1])>0){
                b31.setImageResource(R.drawable.puntorojo);
                tocandob31 = false;
                if(tocandob21) b21.setImageResource(R.drawable.puntorojo);
                if(tocandob12) b12.setImageResource(R.drawable.puntorojo);
                if(tocandob23) b23.setImageResource(R.drawable.puntorojo);

            }
            if((px-coor13[0])<240 && (px-coor13[0])>0 && (py-coor13[1])<240 && (py-coor13[1])>0){
                b31.setImageResource(R.drawable.puntorojo);
                tocandob31 = false;
                if(tocandob21) b21.setImageResource(R.drawable.puntorojo);
                if(tocandob12) b12.setImageResource(R.drawable.puntorojo);
                if(tocandob23) b23.setImageResource(R.drawable.puntorojo);
            }
            if((px-coor22[0])<240 && (px-coor22[0])>0 && (py-coor22[1])<240 && (py-coor22[1])>0){
                b31.setImageResource(R.drawable.puntorojo);
                tocandob31 = false;
                if(tocandob21) b21.setImageResource(R.drawable.puntorojo);
                if(tocandob12) b12.setImageResource(R.drawable.puntorojo);
                if(tocandob23) b23.setImageResource(R.drawable.puntorojo);
            }
            if((px-coor32[0])<240 && (px-coor32[0])>0 && (py-coor32[1])<240 && (py-coor32[1])>0){
                b31.setImageResource(R.drawable.puntorojo);
                tocandob31 = false;
                if(tocandob21) b21.setImageResource(R.drawable.puntorojo);
                if(tocandob12) b12.setImageResource(R.drawable.puntorojo);
                if(tocandob23) b23.setImageResource(R.drawable.puntorojo);
            }
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            b31.setImageResource(R.drawable.puntogris);
            b21.setImageResource(R.drawable.puntogris);
            b12.setImageResource(R.drawable.puntogris);
            b23.setImageResource(R.drawable.puntogris);
            b33.setImageResource(R.drawable.puntogris);
            tocandob11 = tocandob12 = tocandob13 = tocandob21 = tocandob22 = tocandob23 =
                    tocandob31 = tocandob32 = tocandob33 = false;

        }


        return true;



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == CAM_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photoV.setImageBitmap(imageBitmap);
        }
    }
}

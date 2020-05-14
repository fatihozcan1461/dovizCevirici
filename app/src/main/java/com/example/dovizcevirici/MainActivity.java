package com.example.dovizcevirici;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView trytx;
    TextView dolartext;
    TextView jpytext;
    TextView CanadaDtext;
    TextView cryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        trytx = findViewById( R.id.tlText );
        dolartext = findViewById( R.id.usdText );
        jpytext = findViewById( R.id.jpyText );
        CanadaDtext = findViewById( R.id.cadText );
        cryText = findViewById( R.id.chfText );
    }

    public void guncelle(View view){
        IndirilenVeri indirilenVeri = new IndirilenVeri();

        try{

            String url = "http://data.fixer.io/api/latest?access_key=53f772f4cc9932459d6cccbc013b8ee1&format=1";
            indirilenVeri.execute(url);

        }catch (Exception e){

        }

    }


    private class IndirilenVeri extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            String result ="";
            URL url;
            HttpURLConnection httpURLConnection;
            try {

                url = new URL( strings[0] );
                httpURLConnection =  (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader( inputStream );

                int data = inputStreamReader.read();
                while(data > 0){
                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();
                }

            }catch (Exception e){
                return null;
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute( s );
            //System.out.println("Alınan Veri"+s);
            try {
                JSONObject  jsonObject = new JSONObject( s );
                String base = jsonObject.getString( "base" );
               // System.out.println(base);

                String rates = jsonObject.getString( "rates" );
                //System.out.println(rate);
                JSONObject jsonObject1 = new JSONObject(rates);

                String turkishlira = jsonObject1.getString( "TRY" );
                trytx.setText("TL : " + turkishlira );

                String dolar = jsonObject1.getString( "USD" );
                dolartext.setText( "DOLAR : "+dolar );

                String kanadadolari = jsonObject1.getString( "CAD" );
                CanadaDtext.setText( "KANADA DOLARI : "+kanadadolari );

                String cfy = jsonObject1.getString( "CHF" );
                cryText.setText( "CFY : "+cfy );

                String jpy = jsonObject1.getString( "JPY" );
                jpytext.setText( "Japon Yuanı :  "+jpy );


            }catch (Exception e){

            }
        }
    }
}

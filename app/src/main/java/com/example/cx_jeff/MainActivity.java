package com.example.cx_jeff;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

//import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final int[] idArray={R.id.Canada,R.id.Japan,R.id.UK,R.id.USA,R.id.France,R.id.Germany,R.id.Italy};

    private final ImageButton[] imgbtn = new ImageButton[idArray.length];

    String api_key = "8ce66b2a404d9cd85954dcd9";

    ProgressDialog progressDialog;

    //private ProgressBar progressBar;
    //private TextView progressText;
    int i = 0;

    //AnimationDrawable anime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClick();
    }

    private void setContentofTextView(int id, String newContents)
    {
        View view = findViewById(id);
        TextView textView = (TextView) view;
        textView.setText(newContents);
    }

    public void btnClick()
    {
        for(int i = 0;i<idArray.length;i++)
        {
            imgbtn[i] = (ImageButton) findViewById(idArray[i]);
            imgbtn[i].setOnClickListener(new View.OnClickListener()
             {
                 public void onClick(View view)
                 {
                     switch(view.getId())
                     {
                         case R.id.Canada:
                             currencyRatesRequest("CAD");
                             break;
                         case R.id.Japan:
                             currencyRatesRequest("JPY");
                             break;
                         case R.id.UK:
                             currencyRatesRequest("GBP");
                             break;
                         case R.id.USA:
                             currencyRatesRequest("USD");
                             break;
                         case R.id.France:
                             currencyRatesRequest("EUR");
                             break;
                         case R.id.Germany:
                             currencyRatesRequest("EUR");
                             break;
                         case R.id.Italy:
                             currencyRatesRequest("EUR");
                             break;
                     }
                     startLoading();
                 }
             }
            );
        }
    }

    public void startLoading()
    {
        //ImageView v = (ImageView) findViewById(R.id.loader);
        //v.setVisibility(View.VISIBLE);
        //v.setBackgroundResource(R.drawable.loader);
        //anime = (AnimationDrawable) v.getBackground();
        //anime.start();

        //Glide.with(this).load(R.drawable.loader).into(v);
        //Glide.with(this).onStart();
        //loading.

        progressDialog = new ProgressDialog(this);

        progressDialog.show();

        progressDialog.setContentView(R.xml.progress_dialog);

        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

    }

    public void stopLoading()
    {
        progressDialog.dismiss();
        //ImageView v = (ImageView) findViewById(R.id.loader);
        //v.setVisibility(View.INVISIBLE);
        //Glide.with(this).onStop();
        //if(anime.isRunning())
        //{
        //    anime.stop();
        //}
    }

    public void currencyRatesRequest(String base)
    {


        setContentofTextView(R.id.theResult, "");
        String url = "https://v6.exchangerate-api.com/v6/" + api_key + "/latest/" + base;

        RequestQueue rQ = Volley.newRequestQueue(this);
        JsonObjectRequest jOR=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jObj = response.getJSONObject("conversion_rates");

                    String s = "";
                    s += "CAD:" + jObj.getDouble("CAD") + "\n";
                    s += "JPY:" + jObj.getDouble("JPY")+"\n";
                    s += "GBP:" + jObj.getDouble("GBP")+"\n";
                    s += "USD:" + jObj.getDouble("USD")+"\n";
                    s += "EUR:" + jObj.getDouble("EUR")+"\n";
                    stopLoading();
                    setContentofTextView(R.id.theResult, s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        rQ.add(jOR);
    }

}
package com.meraprojects.weatherapplication;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
        private RelativeLayout homerl;
        private ProgressBar loadpb;
        private ImageView bgimage,search,imageviewicon;
        private TextView citynametv,temperaturetv,conditiontv;
        private RecyclerView weatherRV;
        private EditText citynameet;
        private ArrayList<WeatherRVModel> obj1;
        private RecyclerViewAdapter adapter;
        private LocationManager locationManager;
        private int PERMISSION_CODE=1;
        private String cityname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for displaying in full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        imageviewicon = findViewById(R.id.imageviewicon);
        homerl =findViewById(R.id.home);
        loadpb =findViewById(R.id.loading);
        bgimage =findViewById(R.id.bgimage);
        citynametv =findViewById(R.id.cityname);
        temperaturetv =findViewById(R.id.temperature);
        conditiontv =findViewById(R.id.condition);
        weatherRV =findViewById(R.id.recview);
        citynameet =findViewById(R.id.edittext1);
        search=findViewById(R.id.search);
        obj1 = new ArrayList<>();
        adapter = new RecyclerViewAdapter(this, obj1);
        weatherRV.setAdapter(adapter);
        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= getPackageManager().PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_CODE);
        }
        Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        cityname=getCityname(location.getLongitude(),location.getLatitude());
        getWeatherInfo(cityname);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=citynameet.getText().toString();
                if (city.isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter city name",Toast.LENGTH_SHORT).show();
                }
                else {
                    citynametv.setText(cityname);
                    getWeatherInfo(city);
            }}
        });

        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "please provide permission to access location", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    //separate method for getting weather information

    private void getWeatherInfo(String cityname){
        String url="http://api.weatherapi.com/v1/forecast.json?key=f4059f187fc94ea1802163909242511&q="+cityname+"&days=1&aqi=no&alerts=no";
        citynametv.setText(cityname);
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadpb.setVisibility(View.GONE);
                homerl.setVisibility(View.VISIBLE);
                obj1.clear();
                try {
                    String temperauree = response.getJSONObject("current").getString("temp_c");
                    temperaturetv.setText(temperauree + "c");
                    int isDay = response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getString("text");
                    String conditionicon = response.getJSONObject("current").getString("icon");
                    Picasso.get().load("http:".concat(conditionicon)).into(imageviewicon);
                    conditiontv.setText(condition);
                    if (isDay == 1) {
                        Picasso.get().load("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.space.com%2F31851-what-is-morning-star-evening-star.html&psig=AOvVaw1STb3Nqaso8hJfC3Iiv5D_&ust=1732679069329000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIig49WK-YkDFQAAAAAdAAAAABAE").into(bgimage);
                    } else {
                        Picasso.get().load("https://www.google.com/url?sa=i&url=https%3A%2F%2Fstock.adobe.com%2Fsearch%3Fk%3Dstarry%2Bsky&psig=AOvVaw0ZwTji5EU-3zvuGlF9Sf8F&ust=1732678498067000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCJil6sWI-YkDFQAAAAAdAAAAABAE").into(bgimage);
                    }
                    JSONObject forcastobj=response.getJSONObject("forecast");
                    JSONObject forcastday=forcastobj.getJSONArray("forecastday").getJSONObject(0);
                    JSONObject hourarray=forcastday.getJSONObject("hour");
                    for (int i=0;i<hourarray.length();i++){
                        JSONObject obj=hourarray.getJSONObject(String.valueOf(i));
                        String time=obj.getString("time");
                        String temperature=obj.getString("temp_c");
                        String condition1=obj.getJSONObject("condition").getString("text");
                        String windspeed=obj.getString("wind_kph");
                        String icon=obj.getJSONObject("condition").getString("icon");
                        obj1.add(new WeatherRVModel(time,temperature,condition1,windspeed,icon));


                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "valid city name", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
    private String getCityname(double longitude, double lattitude){
        String cityName="notfound";
        Geocoder geocoder=new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses=geocoder.getFromLocation(lattitude,longitude,1);
            for (Address addr:addresses){
                if (addr!=null){
                    String city=addr.getLocality();
                    if (city!=null&&!city.equals("")){
                        cityName=city;
                    }else {
                        Log.d("TAG","CITY NOT FOUND");
                        Toast.makeText(this,"CITY NOT FOUND",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return cityName;
    }
        }
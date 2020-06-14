package com.example.pemetaanpuradibali;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] nama, foto, keterangan, id;
    int jumData;
    private Double[] latitude, longtitude;
    Boolean MarkerD[];
    LatLng latLng[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getLokasi(){
        String url = "https://gispuradibali.000webhostapp.com/lokasipura.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                jumData = response.length();
                Log.d("DEBUG", "PARSE JSON");
                latLng = new LatLng[jumData];
                MarkerD = new Boolean[jumData];
                nama = new String[jumData];
                keterangan = new String[jumData];
                foto = new String[jumData];
                id = new String[jumData];
                latitude = new Double[jumData];
                longtitude = new Double[jumData];

                for (int i = 0; i < jumData; i++) {
                    try {
                        JSONObject data = response.getJSONObject(i);
                        id[i] = data.getString("id");
                        latLng[i] = new LatLng(data.getDouble("latitude"), data.getDouble("longtitude"));
                        nama[i] = data.getString("name");
                        keterangan[i] = data.getString("informasi");
                        foto[i] = data.getString("foto");
                        latitude[i] = data.getDouble("latitude");
                        longtitude[i] = data.getDouble("longtitude");
                        MarkerD[i] = false;
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng[i])
                                .snippet(keterangan[i])
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
                                .title(nama[i]));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng[i], 15.5f));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("Error");
                builder.setMessage("Connection Filed");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("reload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getLokasi();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        Volley.newRequestQueue(this).add(request);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLokasi();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

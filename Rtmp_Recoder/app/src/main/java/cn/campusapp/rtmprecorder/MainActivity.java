package cn.campusapp.rtmprecorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    private int address;
    RequestQueue mQueue;
    Intent intent = new Intent();
    Bundle bundle = new Bundle();
    private String Address = "8";

    @OnClick(R.id.record_btn)
    void onRecordClick() {
        //The address of the server to get the RTMP server address
        String geturl2 = "http://52.196.227.76/flive/get_address";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(geturl2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").contains("OK")){
                                JSONArray array = response.getJSONArray("data");
                                JSONObject lan = array.getJSONObject(0);
                                // using jason to get the RTMP server address
                                address = lan.getInt("address");
                                Address = String.valueOf(address);
                                // using intent to send the address to the next activity
                                bundle.putString("StreamUrl", Address);
                                intent.putExtras(bundle);
                                intent.setClass(MainActivity.this,
                                        BlankActivity.class);
                                startActivity(intent);
                            }
                            else
                                Log.d("TAG", "Server is busy now");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse");
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(jsonObjectRequest);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent.setClass(MainActivity.this,
                    BlankActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mQueue = Volley.newRequestQueue(this);
    }


    protected String getUrl(){
        return "hello";
    }
}

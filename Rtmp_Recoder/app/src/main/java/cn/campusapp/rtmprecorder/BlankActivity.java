package cn.campusapp.rtmprecorder;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlankActivity extends AppCompatActivity implements OnClickListener {

    private ImageButton send = null;
    RequestQueue mQueue;
    String rooms;
    String savemsg;
    String savetime;
    String savename;
    private Handler handler;
    private String chat_room;
    private String user_name;
    private String tpc;
    private ListView lv;
    private String LiveURL;
    private EditText msg = null;
    private EditText msg2 = null;
    private EditText msg3 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        Button mBtnStop = (Button)findViewById(R.id.submit);
        mBtnStop.setOnClickListener(this);
        Intent intent = getIntent();
        LiveURL = intent.getStringExtra("StreamUrl");
        mQueue = Volley.newRequestQueue(this);
    }

        public void onClick(View v){

            if(v.getId() == R.id.submit){
                msg = (EditText) findViewById(R.id.username);
                user_name = msg.getText().toString().trim();
                msg2 = (EditText) findViewById(R.id.topic);
                tpc = msg2.getText().toString().trim();
                msg3 = (EditText) findViewById(R.id.chatroom);
                chat_room = msg3.getText().toString().trim();
                // post the user profile
                if(!chat_room.equals("")&&!tpc.equals("")&&!user_name.equals("")){
                    // the address of the server to receive the user information
                    String posturl ="http://52.196.227.76/flive/create_chatroom";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, posturl,new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.indexOf("OK")!=-1){
                                Log.d("TAG", "Submited");
                            }
                            else if (response.indexOf("ERROR")!=-1){
                                Log.d("TAG", "Errol Error");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("TAG", "error");
                            System.out.println("Error:" + error.toString());
                        }})
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            // write down the information
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("chatroom_name", chat_room);
                            map.put("topic", tpc);
                            map.put("user_name", user_name);
                            map.put("address",LiveURL);
                            return map;
                        }
                    };
                    mQueue.add(stringRequest);
                    // directed to the activity of recording.
                    startActivity(RecordActivity.makeIntent(LiveURL, 0));
                }
                else{
                    Toast.makeText(getApplicationContext(), "No empty or plain.", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }

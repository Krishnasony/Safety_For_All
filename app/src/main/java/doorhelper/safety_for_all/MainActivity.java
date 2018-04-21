package doorhelper.safety_for_all;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    ImageButton imageButton;
    String num1,num2,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String sendTo = "7992295457";
//        String myMesaage ="hello ...";
//        smsManager.sendTextMessage(sendTo,null,myMesaage,null,null);
        imageButton = findViewById(R.id.imagebutton);

        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                username = dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseDatabase.getInstance().getReference().child("relative").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid().toString()).child("firstrelative").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        num1 = dataSnapshot.child("mobile").getValue().toString();
                        FirebaseDatabase.getInstance().getReference().child("relative").child(FirebaseAuth.getInstance()
                                .getCurrentUser().getUid().toString()).child("secondrelative").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                num2 = dataSnapshot.child("mobile").getValue().toString();
                                sendSmsMessage(num1,num2);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });

        mDrawerlayout=findViewById(R.id.drawer);
        mToggle =new ActionBarDrawerToggle(this,mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navbar);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void sendSmsMessage( String number1 , String number2) {

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        String url ="http://happystore.16mb.com/sihapi/SmsApi.php";

        RequestQueue requestQueue = new RequestQueue(cache,network);

        requestQueue.start();

        StringRequest stringRequest  = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("mobileno",num1);
                param.put("message","Your Friend "+username+" is in Trouble");
                return  param;
            }
        };


        StringRequest stringRequest1  = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("mobileno",num2);
                param.put("message","Your Friend "+username+" is in Trouble");
                return  param;
            }
        };


        requestQueue.add(stringRequest);
        requestQueue.add(stringRequest1);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        switch (id){
            case R.id.help:
                startActivity(new Intent(MainActivity.this,HelpActivity.class));
                Toast.makeText(this, "help is Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.support:
                startActivity(new Intent(MainActivity.this,SafetyActivity.class));
                Toast.makeText(this, "support is Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.l_out:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Successfully Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,SignIn.class));
                break;
            case R.id.login:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "You Are In Login Activity ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,SignIn.class));
                break;


        }
        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

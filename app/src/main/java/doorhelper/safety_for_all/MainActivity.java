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

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String sendTo = "7992295457";
//        String myMesaage ="hello ...";
//        smsManager.sendTextMessage(sendTo,null,myMesaage,null,null);
        imageButton = findViewById(R.id.imagebutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSmsMessage();
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

    private void sendSmsMessage() {
        String phoneNo="7992295457";
        String message = "hhedhshas";
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo,null,message,null ,null);
            Toast.makeText(this, "Sms sent!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, "Sms Failed, Please Try Again!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }

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

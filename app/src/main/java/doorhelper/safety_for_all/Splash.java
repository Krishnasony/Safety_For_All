package doorhelper.safety_for_all;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 10000;
    SharedPreferences sharedPreferences;
    Boolean firstTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        firstTime = sharedPreferences.getBoolean("firstTime",true);
        if(firstTime) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    firstTime = false;
                    editor.putBoolean("firstTime", firstTime);
                    editor.apply();
                    Intent mainIntent = new Intent(Splash.this, SignIn.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
        else {
            Intent mainIntent = new Intent(Splash.this, MainActivity.class);
            Splash.this.startActivity(mainIntent);
            finish();
        }
    }
}

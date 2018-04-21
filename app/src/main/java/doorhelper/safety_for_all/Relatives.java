package doorhelper.safety_for_all;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Relatives extends AppCompatActivity {

    Button skip,save;
    EditText editTextname,editTextemail,editTextmob,editText_name,editText_email,editText_mob;
    FirebaseDatabase mdatabase;
    DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatives);
        editTextname = findViewById(R.id.r1_name);
        editTextemail = findViewById(R.id.r1_email);
        editTextmob = findViewById(R.id.r1_mob);

        editText_name = findViewById(R.id.r2_name);
        editText_email = findViewById(R.id.r2_email);
        editText_mob = findViewById(R.id.r2_mob);

        mdatabase = FirebaseDatabase.getInstance();
        mref = mdatabase.getReference("relative").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        skip = findViewById(R.id.skipbutton);
        save = findViewById(R.id.saverelative);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Relatives.this,MainActivity.class));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = editTextname.getText().toString().trim();
                String femail = editTextemail.getText().toString().trim();
                String fmob = editTextmob.getText().toString().trim();

                String sname = editText_name.getText().toString().trim();
                String semail = editText_email.getText().toString().trim();
                String smob = editText_mob.getText().toString().trim();
               DatabaseReference myref = mref.child("firstrelative");
                myref.child("name").setValue(fname);
                myref.child("email").setValue(femail);
                myref.child("mobile").setValue(fmob);

                DatabaseReference mysref = mref.child("secondrelative");
                mysref.child("name").setValue(sname);
                mysref.child("email").setValue(semail);
                mysref.child("mobile").setValue(smob);
                startActivity(new Intent(Relatives.this,MainActivity.class));

            }
        });

    }
}

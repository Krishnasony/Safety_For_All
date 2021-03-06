package doorhelper.safety_for_all;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText editTextname,editTextemail,editTextpassword,editTextconfpass;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mref;
    Button verify;
    RadioGroup radioGroup;
   // String[] city= {"Bhopal", "Indore", "Gwalior", "jabalpur"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        verify = findViewById(R.id.verify);
        editTextname = findViewById(R.id.name);
        editTextemail = findViewById(R.id.email);
        editTextpassword= findViewById(R.id.password);
        radioGroup=findViewById(R.id.rdg);
       // m=findViewById(R.id.sdf);
        mDatabase = FirebaseDatabase.getInstance();
        mref = mDatabase.getReference("users");
       /* Spinner spinner= findViewById(R.id.city);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa= ArrayAdapter<this;
        android.R.doorhelper.safety_for_all.R.id.action_bar_subtitle,city>;
*/
        mAuth = FirebaseAuth.getInstance();
        editTextconfpass =findViewById(R.id.confirmpassword);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = editTextemail.getText().toString().trim();
                final String password = editTextpassword.getText().toString().trim();
                final String name = editTextname.getText().toString().trim();
                final String confpasswd = editTextconfpass.getText().toString().trim();
                String gender=radioGroup.toString();
               // Toast.makeText(Register.this, , Toast.LENGTH_SHORT).show();
                if (!password.equals(confpasswd)){
                    Toast.makeText(Register.this, "Password Mismatch?  Try again!", Toast.LENGTH_SHORT).show();

                }
                else {
                    if (email.isEmpty() & password.isEmpty()) {
                        Toast.makeText(Register.this, "Please Enter Email and Password..? and try again", Toast.LENGTH_SHORT).show();


                    } else if (email.isEmpty()) {
                        Toast.makeText(Register.this, "Please Enter Email and Password..? and try again", Toast.LENGTH_SHORT).show();
                    } else if (password.isEmpty()) {
                        Toast.makeText(Register.this, "Please Enter Email and Password..? and try again", Toast.LENGTH_SHORT).show();

                    } else {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mref = mDatabase.getReference("users").child(task.getResult().getUser().getUid());

                                    mref.child("name").setValue(name);
                                    mref.child("email").setValue(email);
                                    mref.child("password").setValue(password);
                                    mref.child("gender").setValue(radioGroup);
                                    Toast.makeText(Register.this, "Register Successfully!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register.this, SignIn.class));
                                } else {

                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(Register.this,""+task,Toast.LENGTH_LONG).show();

                                        Toast.makeText(getApplicationContext(), "You Are Already Registered!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }


                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Register.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });
    }
}

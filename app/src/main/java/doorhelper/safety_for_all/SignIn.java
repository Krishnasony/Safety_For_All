package doorhelper.safety_for_all;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    EditText editTextemail,editTextpassword;
    Button submit;
    FirebaseAuth mAuth;
    TextView click;
    TextView forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editTextemail = findViewById(R.id.editText1);
        editTextpassword = findViewById(R.id.editText2);
        mAuth = FirebaseAuth.getInstance();
        submit = findViewById(R.id.submit);
        click = findViewById(R.id.clickhere);
        forgot = findViewById(R.id.forgotpass);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextemail.getText().toString().trim();
                String password = editTextpassword.getText().toString().trim();
                if (email.isEmpty()&&password.isEmpty()){
                    Toast.makeText(SignIn.this, "Please Enter Email and Password and Try Again!", Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty()){
                    Toast.makeText(SignIn.this, "Please Enter Password and Try Again!", Toast.LENGTH_SHORT).show();

                }
                else if (email.isEmpty()){
                    Toast.makeText(SignIn.this, "Please Enter Email and Try Again!", Toast.LENGTH_SHORT).show();

                }
                else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignIn.this, "User SignedIn Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignIn.this, Relatives.class));
                                finish();
                            }

                        }
                    });
                }


            }
        });
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this,Register.class));
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this,ForgotPass.class));
            }
        });

    }
}

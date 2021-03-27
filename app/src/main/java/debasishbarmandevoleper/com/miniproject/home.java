package debasishbarmandevoleper.com.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class home extends AppCompatActivity {



    private Button loginBtn, regBtn;
    private EditText username, password;
    private TextView register,forgotPassword;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore=FirebaseFirestore.getInstance();
    DocumentReference dRefs;
    ProgressDialog progressDialog;
    private Date date= Calendar.getInstance().getTime();
    private int flag;// 0 for admin 1 for user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        forgotPassword=findViewById(R.id.textView55);
        loginBtn = findViewById(R.id.login);
        username = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        register = findViewById(R.id.textView54);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        //Login button Click
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginBtn.setEnabled(false);//after clicked on login button
                Login();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home.this,SignUp.class));
                finish();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(home.this, "Clicked", Toast.LENGTH_SHORT).show();
                EditText txt=new EditText(home.this);
                txt.setBackgroundResource(R.drawable.custom_input);
                txt.setBackgroundColor(Color.parseColor("#6e7c7c"));
                txt.setTextColor(Color.parseColor("#FFFFFFFF"));
                AlertDialog.Builder dialog=new AlertDialog.Builder(home.this);
                dialog.setTitle("Reset Password");
                dialog.setMessage("Enter your email");
                dialog.setView(txt);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(home.this, "Yes clicked", Toast.LENGTH_SHORT).show();
                        String mail=txt.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(home.this, "Password reset link sent to your mail", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(home.this, "Failed to sent reset mail", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(home.this, "No Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.create();
                dialog.show();
            }
        });


    }

    private void Login() {
        String u, p;
        u = username.getText().toString().trim();
        p = password.getText().toString().trim();

        //check if field is empty
        if ((TextUtils.isEmpty(u) || TextUtils.isEmpty(p))) {
            username.setError("Email is empty");
            password.setError("Password is empty");
            return;
        }else{
            try{
                progressDialog.setMessage("Login");
                progressDialog.setCancelable(false);
                progressDialog.show();
                fAuth.signInWithEmailAndPassword(u,p).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        DocumentReference dref=fStore.collection("Users").document(authResult.getUser().getUid());
                        dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                               // String x=documentSnapshot.getString("isUser");
                                if(documentSnapshot.getString("isUser")!=null){

                                    //startActivity(new Intent(home.this,User_Profile.class));
                                    startActivity(new Intent(home.this,allCategory.class));
                                    finish();

                                }
                                if(documentSnapshot.getString("isAdmin")!=null){
                                    startActivity(new Intent(home.this,AdminProfile.class));
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(home.this, "Data not Found" +e.getMessage(), Toast.LENGTH_SHORT).show();
                                loginBtn.setEnabled(true);
                            }
                        });
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(home.this, "Error login" +e.getMessage(), Toast.LENGTH_SHORT).show();
                        loginBtn.setEnabled(true);
                    }
                });
            }catch (Exception e){
                Toast.makeText(this, "Error Handled", Toast.LENGTH_SHORT).show();
                loginBtn.setEnabled(true);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(fAuth.getCurrentUser()!=null){
            progressDialog.setMessage("Checking..");
            progressDialog.show();
            DocumentReference dRef=fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
            dRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if(documentSnapshot.getString("isUser")!=null){
                        progressDialog.dismiss();
                        startActivity(new Intent(home.this,allCategory.class));
                       // startActivity(new Intent(home.this,User_Profile.class));
                        Toast.makeText(home.this, "Welcome "+fAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    if(documentSnapshot.getString("isAdmin")!=null){
                        progressDialog.dismiss();
                        startActivity(new Intent(home.this,AdminProfile.class));
                        Toast.makeText(home.this, "Welcome "+fAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(home.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
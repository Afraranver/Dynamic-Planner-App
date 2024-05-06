package com.example.dynamicplanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamicplanner.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        int SPLASH_DISPLAY_LENGTH = 2000;
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Log.e("currentUser", String.valueOf(currentUser));
            /* Create an Intent that will start the MainActivity. */
            if(currentUser != null){
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

        binding.btnLogin.setOnClickListener(v->{
            if(TextUtils.isEmpty(binding.edtEmail.getText().toString())){
                Toast.makeText(this, "Please input your Email Id.",
                        Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(binding.edtPassword.getText().toString())){
                Toast.makeText(this, "Please input a password.",
                        Toast.LENGTH_SHORT).show();
            }else{
                mAuth.signInWithEmailAndPassword(binding.edtEmail.getText().toString(), binding.edtPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginActivity.this, "You have successfully Logged In.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Login Failed, Please try again later.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        binding.imgPassEye2.setOnClickListener(v-> ShowHidePass(binding.imgPassEye2));
        binding.btnGotoSignUp.setOnClickListener(v-> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
//        binding.txtForgotPassword.setOnClickListener(v-> startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class)));

    }

    public void ShowHidePass(View view){

        if(view.getId()==R.id.imgPassEye2){

            if(binding.edtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.hidden);
                //Show Password
                binding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.eye);
                //Hide Password
                binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
}
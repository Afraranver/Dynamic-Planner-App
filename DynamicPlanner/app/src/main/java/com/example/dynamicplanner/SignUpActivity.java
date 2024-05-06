package com.example.dynamicplanner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamicplanner.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        binding.imgBack.setOnClickListener(v-> finish());

        binding.btnSignUp.setOnClickListener(v->{
            if(TextUtils.isEmpty(binding.edtEmail.getText().toString())){
                Toast.makeText(SignUpActivity.this, "Please input your Email Id.",
                        Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(binding.edtPassword.getText().toString())){
                Toast.makeText(SignUpActivity.this, "Please input a password.",
                        Toast.LENGTH_SHORT).show();
            }else{
                mAuth.createUserWithEmailAndPassword(binding.edtEmail.getText().toString(), binding.edtPassword.getText().toString())
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(SignUpActivity.this, "Sign Up new user Success!!",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(this, LoginActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        binding.imgPassEye2.setOnClickListener(v-> ShowHidePass(binding.imgPassEye2));

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
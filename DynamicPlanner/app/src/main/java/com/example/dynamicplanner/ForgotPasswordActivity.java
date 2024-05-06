package com.example.dynamicplanner;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamicplanner.databinding.ActivityForgotPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        binding.btnResetPassword.setOnClickListener(v->{
            if(TextUtils.isEmpty(binding.edtForgotPassword.getText().toString())){
                Toast.makeText(ForgotPasswordActivity.this, "Please input your password.",
                        Toast.LENGTH_SHORT).show();
            }else{
                assert user != null;
                user.updatePassword(binding.edtForgotPassword.getText().toString())
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "Passwords Reset Successfully",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                try{
                                    Toast.makeText(ForgotPasswordActivity.this, task1.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(ForgotPasswordActivity.this, "Unknown Error, please try again later!",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        binding.imgPassEye.setOnClickListener(v-> ShowHidePass(binding.imgPassEye));
        binding.imgBack3.setOnClickListener(v->finish());

    }

    public void ShowHidePass(View view){

        if(view.getId()==R.id.imgPassEye){

            if(binding.edtForgotPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.hidden);
                //Show Password
                binding.edtForgotPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.eye);
                //Hide Password
                binding.edtForgotPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
}
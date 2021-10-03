package com.example.money_tracking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText emailInput, passwordInput;
    TextView emailError, passwordError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(onLoginButtonClick());

        emailInput = findViewById(R.id.loginEmailEditText);
        emailError = findViewById(R.id.loginEmailError);
        passwordInput = findViewById(R.id.loginPasswordEditText);
        passwordError = findViewById(R.id.loginPasswordError);

        ClearErrorMessage(emailInput, emailError);
        ClearErrorMessage(passwordInput, passwordError);

    }



    private View.OnClickListener onLoginButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperFunctions.hideSoftKeyboard(LoginActivity.this, v);
                LoginValidator loginValidator = new LoginValidator().invoke();
                String email = loginValidator.getEmail();
                String password = loginValidator.getPassword();
                if (loginValidator.isValid()) {

                    if (!email.equals("test@test.com") || !password.equals("123456")) {
                        Toast.makeText(LoginActivity.this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }
            }
        };
    }


    private void setErrorMessage(TextView errorField, String errorMessage) {
        errorField.setText(errorMessage);
    }

    private void ClearErrorMessage(EditText input, final TextView errorField) {
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()) setErrorMessage(errorField,"");

            }
        });
    }

    private class LoginValidator {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public LoginValidator invoke() {
            email = emailInput.getText().toString();
            password = passwordInput.getText().toString();
            if (email.isEmpty()) setErrorMessage(emailError, "Email is required");
            if (password.isEmpty()) setErrorMessage(passwordError, "Password is required");
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                setErrorMessage(emailError, "Must be an email");

            return this;
        }

        private boolean isValid() {
            return !password.isEmpty() && !email.isEmpty();
        }
    }
}

package com.example.iclu.bilbioapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iclu.biblioapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener {
    //region declaration
    private static final String TAG = "LoginActivity";
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private EditText etMail;
    private EditText etPassword;
    private FirebaseAuth firebaseAuth;
//endregion

    //region lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        etMail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());

        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.btnRegister).setOnClickListener(this);
        findViewById(R.id.btnTest).setOnClickListener(this);
    }
//endregion

    //region onclick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                startRegister();
                break;
            case R.id.btnLogin:
                startLogin();
                break;
            case R.id.btnTest:
                startTest();
                break;
        }
    }
//endregion

    //region private methods
    private void startRegister() {
        if (getMail().equals("") || getPassword().equals("")) {
            displayToast(R.string.login_error_empty_spaces);
        } else if (!getMail().matches(EMAIL_REGEX)) {
            displayToast(R.string.login_error_regex);
        } else {
            registerUser();
        }
    }

    private void startLogin() {
        if (getMail().equals("") || getPassword().equals("")) {
            displayToast(R.string.login_error_empty_spaces);
        } else {
            logUser();
        }
    }

    private void startTest() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private String getMail() {
        return etMail.getText().toString().trim();
    }

    private String getPassword() {
        return etPassword.getText().toString().trim();
    }

    private void displayToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    private void registerUser() {
        firebaseAuth.createUserWithEmailAndPassword(getMail(), getPassword())
                .addOnCompleteListener(this, getCompleteListener());
    }

    private void logUser() {
        firebaseAuth.signInWithEmailAndPassword(getMail(), getPassword())
                .addOnCompleteListener(this, getCompleteListener());
    }

    private void proceedToMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private OnCompleteListener<AuthResult> getCompleteListener() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    proceedToMainMenu();
                } else {
                    treatExceptions(task.getException());
                }
            }
        };
    }
    //endregion

    //region treatExceptions
    private void treatExceptions(Exception exception) {
        try {
            throw exception;
        } catch (FirebaseAuthWeakPasswordException e) {
            displayToast(R.string.fb_weak_password);
        } catch (FirebaseAuthInvalidCredentialsException e) {
            displayToast(R.string.fb_invalid_credentials);
        } catch (FirebaseAuthUserCollisionException e) {
            displayToast(R.string.fb_collisions_exception);
        } catch (FirebaseAuthInvalidUserException e) {
            displayToast(R.string.fb_invalid_user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}

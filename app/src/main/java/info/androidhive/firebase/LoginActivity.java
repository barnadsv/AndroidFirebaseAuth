package info.androidhive.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class  LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private static final String TAG = "LoginActiviry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        // set the view now
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.signup_validation_message_empty_email), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.signup_validation_message_empty_password), Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
//                                    try {
//                                        throw task.getException();
//                                    } catch(FirebaseAuthWeakPasswordException e) {
//                                        inputPassword.setError(getString(R.string.minimum_password));
//                                        inputPassword.requestFocus();
//                                    } catch(FirebaseAuthInvalidCredentialsException e) {
//                                        Toast.makeText(LoginActivity.this, getString(R.string.error_invalid_email), Toast.LENGTH_LONG).show();
//                                    } catch(FirebaseAuthUserCollisionException e) { // Este aqui seria apenas para signup
//                                        Toast.makeText(LoginActivity.this, getString(R.string.error_user_exists), Toast.LENGTH_LONG).show();
//                                    } catch(Exception e) {
//                                        Log.e(TAG, e.getMessage());
//                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
//                                    }
                                }
                            }
                        })
                        .addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof FirebaseAuthException) {
                                    /* Abaixo a lista de erros que podem acontecer ao utilizar o método auth.signInWithEmailAndPassword                           */
                                    /* O erro pode ser capturado neste evento ou no Complete acima, se a tarefa(task) não teve sucesso.                           */
                                    /* No caso de login, é boa prática não esclarecer ao usuário se o erro ocorreu no email ou na senha, por medida de segurança. */

//                                    ("ERROR_INVALID_EMAIL", "The email address is badly formatted."));
//                                    ("ERROR_USER_DISABLED", "The user account has been disabled by an administrator."));
//                                    ("ERROR_USER_NOT_FOUND", "There is no user record corresponding to this identifier. The user may have been deleted."));
//                                    ("ERROR_WRONG_PASSWORD", "The password is invalid or the user does not have a password."));

                                    /* Os erros abaixo acontecem em outras situações, mas estou mantendo por enquanto para documentar    */

//                                    ("ERROR_INVALID_CUSTOM_TOKEN", "The custom token format is incorrect. Please check the documentation."));
//                                    ("ERROR_CUSTOM_TOKEN_MISMATCH", "The custom token corresponds to a different audience."));
//                                    ("ERROR_INVALID_CREDENTIAL", "The supplied auth credential is malformed or has expired."));
//                                    ("ERROR_USER_MISMATCH", "The supplied credentials do not correspond to the previously signed in user."));
//                                    ("ERROR_REQUIRES_RECENT_LOGIN", "This operation is sensitive and requires recent authentication. Log in again before retrying this request."));
//                                    ("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."));
//                                    ("ERROR_EMAIL_ALREADY_IN_USE", "The email address is already in use by another account."));
//                                    ("ERROR_CREDENTIAL_ALREADY_IN_USE", "This credential is already associated with a different user account."));
//                                    ("ERROR_USER_TOKEN_EXPIRED", "The user\'s credential is no longer valid. The user must sign in again."));
//                                    ("ERROR_INVALID_USER_TOKEN", "The user\'s credential is no longer valid. The user must sign in again."));
//                                    ("ERROR_OPERATION_NOT_ALLOWED", "This operation is not allowed. You must enable this service in the console."));
//                                    ("ERROR_WEAK_PASSWORD", "The given password is invalid."));

                                    String error = (((FirebaseAuthException) e).getErrorCode());
                                    switch (error) {
                                        case "ERROR_INVALID_EMAIL":
                                            Toast.makeText(LoginActivity.this, getString(R.string.error_invalid_email), Toast.LENGTH_LONG).show();
                                            break;
                                        case "ERROR_USER_NOT_FOUND":
                                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                            break;
                                        case "ERROR_WRONG_PASSWORD":
                                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                            break;
                                        default:
                                            Log.e(TAG, ((FirebaseAuthException) e).getMessage());
                                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
            }
        });
    }
}


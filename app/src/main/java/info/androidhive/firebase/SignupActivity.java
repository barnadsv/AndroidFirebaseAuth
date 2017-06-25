package info.androidhive.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private static final String TAG = "SignupActiviry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.signup_validation_message_empty_email), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.signup_validation_message_empty_password), Toast.LENGTH_SHORT).show();
                    return;
                }

                /*
                 * Melhor deixar o Firebase lidar com a autenticação mais detalhada.
                 * Isto evitará efeitos indesejados no futuro.
                 * Caso as regras de autenticação do Firebase mudem, não será necessário
                 * fazer ajustes no código da aplicação de acordo com a mudança realizada.
                 */
//                if (password.length() < 6) {
//                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (task.isSuccessful()) {
                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                finish();
                            } else {
//                                try {
//                                    throw task.getException();
//                                } catch(FirebaseAuthWeakPasswordException e) {
//                                    inputPassword.setError(getString(R.string.minimum_password));
//                                    inputPassword.requestFocus();
//                                } catch(FirebaseAuthInvalidCredentialsException e) {
//                                    Toast.makeText(LoginActivity.this, getString(R.string.error_invalid_email), Toast.LENGTH_LONG).show();
//                                } catch(FirebaseAuthUserCollisionException e) {
//                                    Toast.makeText(LoginActivity.this, getString(R.string.error_user_exists), Toast.LENGTH_LONG).show();
//                                } catch(Exception e) {
//                                    Log.e(TAG, e.getMessage());
//                                    Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
//                                }
                            }
                        }
                    })
                    .addOnFailureListener(SignupActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof FirebaseAuthException) {
                                 /* Abaixo a lista de erros que podem acontecer ao utilizar o método auth.createUserWithEmailAndPassword                           */
                                 /* O erro pode ser capturado neste evento ou no Complete acima, se a tarefa(task) não teve sucesso */

//                                    ("ERROR_EMAIL_ALREADY_IN_USE", "The email address is already in use by another account."));
//                                    ("ERROR_INVALID_EMAIL", "The email address is badly formatted."));
//                                    ("ERROR_OPERATION_NOT_ALLOWED", "This operation is not allowed. You must enable this service in the console."));
//                                    ("ERROR_WEAK_PASSWORD", "The given password is invalid."));

                                /* Os erros abaixo acontecem em outras situações, mas estou mantendo por enquanto para documentar    */

//                                    ("ERROR_INVALID_CUSTOM_TOKEN", "The custom token format is incorrect. Please check the documentation."));
//                                    ("ERROR_CUSTOM_TOKEN_MISMATCH", "The custom token corresponds to a different audience."));
//                                    ("ERROR_INVALID_CREDENTIAL", "The supplied auth credential is malformed or has expired."));
//                                    ("ERROR_WRONG_PASSWORD", "The password is invalid or the user does not have a password."));
//                                    ("ERROR_USER_MISMATCH", "The supplied credentials do not correspond to the previously signed in user."));
//                                    ("ERROR_REQUIRES_RECENT_LOGIN", "This operation is sensitive and requires recent authentication. Log in again before retrying this request."));
//                                    ("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."));
//                                    ("ERROR_CREDENTIAL_ALREADY_IN_USE", "This credential is already associated with a different user account."));
//                                    ("ERROR_USER_DISABLED", "The user account has been disabled by an administrator."));
//                                    ("ERROR_USER_TOKEN_EXPIRED", "The user\'s credential is no longer valid. The user must sign in again."));
//                                    ("ERROR_USER_NOT_FOUND", "There is no user record corresponding to this identifier. The user may have been deleted."));
//                                    ("ERROR_INVALID_USER_TOKEN", "The user\'s credential is no longer valid. The user must sign in again."));

                                String error = (((FirebaseAuthException) e).getErrorCode());
                                switch (error) {
                                    case "ERROR_INVALID_EMAIL":
                                        Toast.makeText(SignupActivity.this, getString(R.string.error_invalid_email), Toast.LENGTH_LONG).show();
                                        inputEmail.setError(getString(R.string.error_invalid_email));
                                        inputEmail.requestFocus();
                                        break;
                                    case "ERROR_EMAIL_ALREADY_IN_USE":
                                        Toast.makeText(SignupActivity.this, getString(R.string.error_user_exists), Toast.LENGTH_LONG).show();
                                        break;
                                    case "ERROR_WEAK_PASSWORD":
                                        inputPassword.setError(getString(R.string.minimum_password));
                                        inputPassword.requestFocus();
                                        break;
                                    default:
                                        Log.e(TAG, ((FirebaseAuthException) e).getMessage());
                                        Toast.makeText(SignupActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
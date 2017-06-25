package info.androidhive.firebase;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private static final String TAG = "SignupActiviry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inputEmail = (EditText) findViewById(R.id.email);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), getString(R.string.send_password_reset_email_message_empty_email), Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, getString(R.string.send_password_reset_email_success), Toast.LENGTH_SHORT).show();
                            } else {
//                                Toast.makeText(ResetPasswordActivity.this, getString(R.string.send_password_reset_email_error), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(ResetPasswordActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            /* Abaixo a lista de erros que podem acontecer ao utilizar o método auth.sendPasswordResetEmail    */
                            /* O erro pode ser capturado neste evento ou no Complete acima, se a tarefa(task) não teve sucesso */
                            /* Notar que este erro não é do tipo FirebaseAuthException, mas do tipo Exception                  */
                            /*                                                                                                 */
                            /*  An internal error has occured. [ INVALID_EMAIL ]                                               */
                            /*  An internal error has occured. [ USER_NOT_FOUND ]                                               */

                            String error = (((Exception) e).getMessage());
                            if (error.contains("INVALID_EMAIL")) {
                                Toast.makeText(ResetPasswordActivity.this, getString(R.string.error_invalid_email), Toast.LENGTH_LONG).show();
                                inputEmail.setError(getString(R.string.error_invalid_email));
                                inputEmail.requestFocus();
                            } else {
                                Log.e(TAG, error);
                                Toast.makeText(ResetPasswordActivity.this, getString(R.string.send_password_reset_email_error), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
    }

}

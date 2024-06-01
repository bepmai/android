package tlu.cse.android.ht63.coffee;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import tlu.cse.android.ht63.coffee.models.User;
import tlu.cse.android.ht63.coffee.repository.UserRepository;

public class SignupActivity extends AppCompatActivity {
    private EditText snName, snUsername, snPassword, snConfirmPassword;
    private Button btnSignup;

    private RadioGroup radioGroupRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        snName = findViewById(R.id.snName);
        snUsername = findViewById(R.id.snUsername);
        snPassword = findViewById(R.id.snPassword);
        snConfirmPassword = findViewById(R.id.snConfirmPassword);
        btnSignup = findViewById(R.id.snBtnSignup);
        radioGroupRole = findViewById(R.id.radioGroupRole);
        ;
        UserRepository userRepository = new UserRepository(this);




        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = snName.getText().toString().trim();
                String username = snUsername.getText().toString().trim();
                String password = snPassword.getText().toString().trim();
                String confirmPassword = snConfirmPassword.getText().toString().trim();
                String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault()));
                int selectedRoleId = radioGroupRole.getCheckedRadioButtonId();

                String role = "";
                if (selectedRoleId == R.id.radioEmployee) {
                    role = "Employee";
                } else if (selectedRoleId == R.id.radioUser) {
                    role = "User";
                }

                if (password.equals(confirmPassword)) {
                    userRepository.addUser(new User(name, username, password,role,"1", createdAt));
                    Toast.makeText(SignupActivity.this, "Signup successful" + createdAt, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
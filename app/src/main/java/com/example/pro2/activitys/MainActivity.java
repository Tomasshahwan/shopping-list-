package com.example.pro2.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pro2.R;
import com.example.pro2.models.ProductData;
import com.example.pro2.models.students;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class
MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(LoginCallback callback){
        EditText emailField = findViewById(R.id.emailin);
        EditText passwordField = findViewById(R.id.passin);

        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this , "login ok" , Toast.LENGTH_LONG).show();
                            callback.onLoginResult(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this , "login faild" , Toast.LENGTH_LONG).show();
                            callback.onLoginResult(false);

                        }
                    }
                });
    }
    public interface LoginCallback {
        void onLoginResult(boolean success);
    }
    public void reg(){
        EditText emailField = findViewById(R.id.theemail);
        EditText passwordField = findViewById(R.id.onepass);

        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this , "reg ok" , Toast.LENGTH_LONG).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this , "reg faild" , Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
    public void addDATA(){

        String phone = ((EditText)findViewById(R.id.editTextPhone)).getText().toString();
        String email = ((EditText)findViewById(R.id.theemail)).getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(phone);

        students s = new students(phone , email);
        myRef.setValue( s );
    }
    public void getStudent(String phone){
        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(phone);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                students value = dataSnapshot.getValue(students.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }


    public void saveShoppingListToDatabase(ArrayList<ProductData> productList, DatabaseReference databaseReference) {
        if (databaseReference != null) {
            databaseReference.setValue(productList).addOnCompleteListener(task -> {
                if (task.isSuccessful() && !productList.isEmpty()) {
                    Toast.makeText(this, "Shopping List saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Empty Shopping List", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "Database reference is null", Toast.LENGTH_SHORT).show();
        }
    }
}
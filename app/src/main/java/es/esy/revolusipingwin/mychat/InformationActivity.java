package es.esy.revolusipingwin.mychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InformationActivity extends AppCompatActivity {

    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void sentData(View view) {
        EditText inputContac = (EditText) findViewById(R.id.input_contac);
        EditText inputCriticismAndSuggestions = (EditText) findViewById(R.id.input_criticism_and_suggestions);
        String phoneNumber = inputContac.getText().toString();
        String criticismAndSuggestions = inputCriticismAndSuggestions.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("criticism and suggestions");

        String key = myRef.push().getKey();
        myRef.child(key).child("name").setValue(user.getDisplayName());
        myRef.child(key).child("email").setValue(user.getEmail());
        myRef.child(key).child("contac").setValue(phoneNumber);
        myRef.child(key).child("massage").setValue(criticismAndSuggestions);
        long unixTimeNow = System.currentTimeMillis() / 1000L;
        myRef.child(key).child("time").setValue(unixTimeNow);

        Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show();
    }
}

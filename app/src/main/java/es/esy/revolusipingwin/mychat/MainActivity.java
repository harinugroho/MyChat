package es.esy.revolusipingwin.mychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseUser user;
    private ChatAdapter chatAdapter;
    private ListView listChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

        user = FirebaseAuth.getInstance().getCurrentUser();

        final EditText inputMassage = (EditText) findViewById(R.id.input_massage);

        final ImageView buttonSend = (ImageView) findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String massage = inputMassage.getText().toString();
                inputMassage.setText("");
                sendMassage(massage);
            }
        });

        ArrayList<Chat> chats = readInvoiceData();

        listChat = (ListView) findViewById(R.id.list_chat);
        chatAdapter = new ChatAdapter(this, chats);
        listChat.setAdapter(chatAdapter);
        scrollMyListViewToBottom();

    }

    /**
     * untuk mengirimkan pesan
     * @param massage pesan yang akan di kirimkan
     */
    private void sendMassage(String massage){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("chats");

        long unixTimeNow = System.currentTimeMillis() / 1000L;
        Chat chat = new Chat(user.getDisplayName(), user.getUid(), massage, unixTimeNow);
        myRef.child("group_besar").push().setValue(chat); //TODO: change for personal chat

        scrollMyListViewToBottom();
    }

    /**
     * mengambil data chat yang sudah ada
     * @return list dati data chat
     */
    private ArrayList<Chat> readInvoiceData(){
        final ArrayList<Chat> invoiceData = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progres_bar);
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference myRef = database.getReference("chats");
        //TODO: Change to your user id
        myRef.child("group_besar").addValueEventListener(new ValueEventListener() { //TODO: change when using personal chat
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                invoiceData.clear();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    Chat invoice = userSnapshot.getValue(Chat.class);
                    invoiceData.add(invoice);
                }
                chatAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
        return invoiceData;
    }

    /**
     * melakukan scroll ke pesan paling bawah
     */
    private void scrollMyListViewToBottom() {
        listChat.post(new Runnable() {
            @Override
            public void run() {
                listChat.smoothScrollToPosition(chatAdapter.getCount());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menus = getMenuInflater();
        menus.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * melakukan logout dan pindah activity
     */
    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}


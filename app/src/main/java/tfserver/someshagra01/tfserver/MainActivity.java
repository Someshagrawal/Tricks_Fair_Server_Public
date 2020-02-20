package tfserver.someshagra01.tfserver;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.PhoneBuilder().build());
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static final int RC_SIGN_IN = 3105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(user != null){

            showActionLayout();
        }

        else{

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setLogo(R.drawable.logo_main_512px)
                            .build(),
                    RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    showActionLayout();
                }
                // ...
            } else {

                Toast.makeText(this,"Some error occured \n Try again later",Toast.LENGTH_LONG);
                finish();
            }
        }
    }

    private void showActionLayout(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.select_action_layout);
        layout.setVisibility(View.VISIBLE);

        Button addNotification = (Button) findViewById(R.id.add_noti_button);
        addNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAddNotification = new Intent(MainActivity.this, add_notification.class);
                startActivity(openAddNotification);
            }
        });

        Button deleteNotification = (Button) findViewById(R.id.delete_noti_button);
        deleteNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openDeleteNotification = new Intent(MainActivity.this, delete_notification.class);
                startActivity(openDeleteNotification);
            }
        });

        Button updateNotification = (Button) findViewById(R.id.update_noti_button);
        updateNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openUpdateNotification = new Intent(MainActivity.this, update_notification.class);
                startActivity(openUpdateNotification);
            }
        });
    }
}

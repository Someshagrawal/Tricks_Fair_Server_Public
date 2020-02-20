package tfserver.someshagra01.tfserver;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class delete_notification extends AppCompatActivity {
    TextView notificationShown;
    EditText numberToDelete;
    Button deleteButton;
    String notifications = "",newKeysString=null, notiType;
    Map<String, Object> newKeySet = new HashMap<>();
    String[] keysArray;
    List<String> keysList;
    int n;
    CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Notification");
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notification);

        notificationShown = (TextView) findViewById(R.id.notificationShown);
        numberToDelete = (EditText) findViewById(R.id.numberToDelete);
        deleteButton = (Button) findViewById(R.id.buttonDelete);

        final Spinner chooseNotiType = findViewById(R.id.chooseNotiTypeDel);
        String[] type = new String[]{"Deals", "Tricks", "Coupons", "Cashback"};
        ArrayAdapter<String> adapterNotiType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type);
        adapterNotiType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseNotiType.setAdapter(adapterNotiType);

        chooseNotiType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch((String) parent.getItemAtPosition(position)) {
                    case "Deals": documentReference = collectionReference.document("Deals");
                        notiType = "Deals";
                        break;
                    case "Tricks": documentReference = collectionReference.document("Tricks");
                        notiType = "Tricks";
                        break;
                    case "Coupons": documentReference = collectionReference.document("Coupons");
                        notiType = "Coupons";
                        break;
                    case "Cashback": documentReference = collectionReference.document("Cashback");
                        notiType = "Cashback";
                        break;

                }
                getNotifications();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                documentReference = collectionReference.document("Deals");
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberToDelete.getText().toString().isEmpty())
                    Toast.makeText(delete_notification.this,"Enter notification number to delete",Toast.LENGTH_LONG).show();
                else {
                    n = Integer.parseInt(numberToDelete.getText().toString());
                    if(n>keysArray.length || n<=0){
                        Toast.makeText(delete_notification.this,"Please Enter a valid number",Toast.LENGTH_LONG).show();
                    }
                    else if(keysList.size()==1){
                        Toast.makeText(delete_notification.this,"Can't delete the only notification",Toast.LENGTH_LONG).show();
                    }
                    else{
                        AlertDialog.Builder alert = new AlertDialog.Builder(delete_notification.this)
                                .setIcon(android.R.drawable.ic_delete)
                                .setCancelable(true)
                                .setTitle("Confirm Action")
                                .setMessage("Do you surely want to delete this Notification\n\n"+keysList.get(n-1))
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteButton.setEnabled(false);
                                        newKeySet.put(keysList.get(n-1), FieldValue.delete());
                                        keysList.remove(n-1);
                                        for(int i=0;i<keysList.size();i++){
                                            if(i!=0)
                                                newKeysString = newKeysString + "," + keysList.get(i);
                                            else
                                                newKeysString = keysList.get(i);
                                        }
                                        newKeySet.put("keys",newKeysString);
                                        documentReference.set(newKeySet, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(delete_notification.this,"Action Completed Successfully",Toast.LENGTH_LONG).show();
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(delete_notification.this,""+e,Toast.LENGTH_LONG).show();
                                                finish();
                                            }
                                        });
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        alert.show();

                    }
                }
            }
        });
    }

    private void setShowNotifications(){
        notificationShown.setText(notifications);
        numberToDelete.setEnabled(true);
        deleteButton.setEnabled(true);
    }

    private void getNotifications(){
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                notifications = "";
                String keys = document.getString("keys");
                keysArray = keys.split(",",-2);
                keysList = new LinkedList<String>(Arrays.asList(keysArray));
                for(int i=0; i<keysArray.length;i++){
                    notifications = notifications +"\n\n"+" "+(i+1)+". "+keysArray[i];
                }
                setShowNotifications();
            }
        });
    }
}

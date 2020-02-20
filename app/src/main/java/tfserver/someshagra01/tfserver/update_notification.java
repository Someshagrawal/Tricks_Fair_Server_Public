package tfserver.someshagra01.tfserver;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class update_notification extends AppCompatActivity {

    CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Notification");
    DocumentReference documentReference;
    String notiType, notifications="", sDateTime, imageUrl = "", sHeading, sSubHeading, sUrlToOpen;
    String[] keysArray;
    List<String> keysList;
    Map<String, Object> notification = new HashMap<>();
    TextView notificationShownUpdate, textViewImageUrlUpdate;
    EditText numberToUpdate, heading, subHeading, urlToOpen, mannualImageUrl;
    Button button_next, updateButton;
    int n;
    DocumentSnapshot document;
    Spinner chooseImageUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notification);

        notificationShownUpdate = (TextView) findViewById(R.id.notificationShownUpdate);
        numberToUpdate = (EditText) findViewById(R.id.numberToUpdate);
        button_next = (Button) findViewById(R.id.button_next);
        heading = (EditText) findViewById(R.id.headingUpdate);
        subHeading = (EditText) findViewById(R.id.subHeadingUpdate);
        urlToOpen = (EditText) findViewById(R.id.urlToOpenUpdate);
        mannualImageUrl = (EditText) findViewById(R.id.manualImageUrlUpdate);
        textViewImageUrlUpdate = (TextView) findViewById(R.id.textViewImageUrlUpdate);
        updateButton = (Button) findViewById(R.id.updateButton);


        final Spinner chooseNotiTypeUpdate = findViewById(R.id.chooseNotiTypeUpdate);
        String[] type = new String[]{"Deals", "Tricks", "Coupons", "Cashback"};
        ArrayAdapter<String> adapterNotiType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type);
        adapterNotiType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseNotiTypeUpdate.setAdapter(adapterNotiType);

        chooseNotiTypeUpdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                heading.setText(null);
                subHeading.setText(null);
                urlToOpen.setText(null);
                imageUrl = "";
                getNotifications();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                documentReference = collectionReference.document("Deals");
            }
        });


        chooseImageUpdate = findViewById(R.id.chooseImageUpdate);
        final String[] imagesUrl = new String[]
                {"*same as old*", "Tricks Fair", "Amazon", "Flipkart", "Paytm",
                        "TricksGang", "CuteTricks", "3gHackerz","Tricky Time", "A2Y","Earticleblog","BigTricks","CoolzTricks","Rec. Tricks","Tricks by STG",
                        "Zoutons","GrabOn","CouponRani","CouponDunia",
                        "CashKaro","Paisaget","Gopaisa","Paisawapas",
                        "OTHER"};
        ArrayAdapter<String> adapterImageUpdate = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, imagesUrl);
        adapterImageUpdate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseImageUpdate.setAdapter(adapterImageUpdate);

        chooseImageUpdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                textViewImageUrlUpdate.setVisibility(View.GONE);
                mannualImageUrl.setVisibility(View.GONE);

                switch((String) parent.getItemAtPosition(position)){
                    case "*same as old*":
                        break;
                    case "Tricks Fair": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/logo_main_180px.png?alt=media&token=c2c069cd-a100-4aa1-8196-5b058318bd47";
                        break;
                    case "Amazon": imageUrl = "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/amazon%20logo%20white.png?alt=media&token=1f0e4c9a-eb09-42c3-9428-8760cdd80272";
                        break;
                    case "Flipkart": imageUrl = "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/flipkart%20logo.png?alt=media&token=c7b03724-d5e2-4680-8a45-e5d8c6d93b57";
                        break;
                    case "Paytm": imageUrl = "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/paytm%20logo.png?alt=media&token=53a1f72c-2ea1-4f28-bd20-8e36afb7f8ff";
                        break;
                    case "TricksGang": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/TricksGang_black.png?alt=media&token=e35d7500-fb99-4d10-850f-3372d3a56e93";
                        break;
                    case "CuteTricks": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/cutetricks2.png?alt=media&token=dc7924a9-3505-4741-bc08-a9d37c0ce1f2";
                        break;
                    case "3gHackerz": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/3G%20hackerz-min.png?alt=media&token=6da0b4b8-ff95-4d33-b706-d1720ba0dd22";
                        break;
                    case "Tricky Time": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/Tricky%20time.png?alt=media&token=a301c602-def4-4369-b3b0-c2a52aeac784";
                        break;
                    case "A2Y": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/A2Y-min.png?alt=media&token=a2277a78-79aa-4373-b21c-d648c076ffbd";
                        break;
                    case "Earticleblog": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/earticleblog.png?alt=media&token=9f3ec9ab-842a-4c52-ac19-7355ecdefd94";
                        break;
                    case "BigTricks": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/Bigtricks.png?alt=media&token=f682be12-89de-45b2-b502-dafc7960fc12";
                        break;
                    case "CoolzTricks": imageUrl =  "https://www.coolztricks.com/wp-content/uploads/2016/08/imageedit_4_3812642078-300x132.png";
                        break;
                    case "Rec. Tricks": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/rechargeTricks.png?alt=media&token=f2fb465a-d855-4aa3-9033-2b48032be1ba";
                        break;
                    case "Tricks by STG": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/tbstgfinal.png?alt=media&token=8d15cbde-87ca-47de-aee9-424ca48877fe";
                        break;
                    case "Zoutons": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/Zoutons.png?alt=media&token=b5ab612b-3a96-4851-912c-0a6af1f6c16d";
                        break;
                    case "GrabOn": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/GrabOn-min.png?alt=media&token=6e0d77f8-5b67-423f-b6e1-738abbcb9333";
                        break;
                    case "CouponRani": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/crani.png?alt=media&token=76c2dc76-25ae-44db-8251-319f8159c1ef";
                        break;
                    case "CouponDunia": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/CouponDunia-min.png?alt=media&token=3599dcc6-9f6d-4466-9d5e-9bb85f6dc7f6";
                        break;
                    case "CashKaro": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/CashKaro-min.png?alt=media&token=fa3d1506-9eae-49f6-8f83-6cb1a1ea254d";
                        break;
                    case "Paisaget": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/Paisaget-min.png?alt=media&token=ad04d1ad-7424-435f-afe3-cf8b72e3104d";
                        break;
                    case "Gopaisa": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/Gopaisa-min.png?alt=media&token=b7ae7cd6-4b19-46bf-8aee-c63685848e28";
                        break;
                    case "Paisawapas": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/PaisaWapas-min.png?alt=media&token=8911d89e-d7b2-4347-b42e-4b4a1e2a4741";
                        break;
                    case "OTHER": textViewImageUrlUpdate.setVisibility(View.VISIBLE);
                        mannualImageUrl.setVisibility(View.VISIBLE);
                        imageUrl = "";
                        break;
                }
                Log.e("ImageUrl", imageUrl);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                imageUrl = "";
            }
        });


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberToUpdate.getText().toString().isEmpty())
                    Toast.makeText(update_notification.this,"Enter notification number to Update",Toast.LENGTH_LONG).show();
                else {
                    n = Integer.parseInt(numberToUpdate.getText().toString());
                    if(n>keysArray.length || n<=0){
                        Toast.makeText(update_notification.this,"Please Enter a valid number",Toast.LENGTH_LONG).show();
                    }
                    else{
                            final String[] tempData = document.getString(keysArray[n-1]).split(" --- ", -2);
                            heading.setText(keysArray[n-1]);
                            subHeading.setText(tempData[0]);
                            urlToOpen.setText(tempData[1]);
                            sDateTime = tempData[3];
                            keysList = new LinkedList<String>(Arrays.asList(keysArray));

                            chooseImageUpdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                textViewImageUrlUpdate.setVisibility(View.GONE);
                                mannualImageUrl.setVisibility(View.GONE);

                                switch((String) parent.getItemAtPosition(position)){
                                    case "*same as old*": imageUrl = tempData[2];
                                        break;
                                    case "Tricks Fair": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/logo_main_180px.png?alt=media&token=c2c069cd-a100-4aa1-8196-5b058318bd47";
                                        break;
                                    case "Amazon": imageUrl = "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/amazon%20logo%20white.png?alt=media&token=1f0e4c9a-eb09-42c3-9428-8760cdd80272";
                                        break;
                                    case "Flipkart": imageUrl = "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/flipkart%20logo.png?alt=media&token=c7b03724-d5e2-4680-8a45-e5d8c6d93b57";
                                        break;
                                    case "Paytm": imageUrl = "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/paytm%20logo.png?alt=media&token=53a1f72c-2ea1-4f28-bd20-8e36afb7f8ff";
                                        break;
                                    case "TricksGang": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/TricksGang_black.png?alt=media&token=e35d7500-fb99-4d10-850f-3372d3a56e93";
                                        break;
                                    case "CuteTricks": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/cutetricks2.png?alt=media&token=dc7924a9-3505-4741-bc08-a9d37c0ce1f2";
                                        break;
                                    case "3gHackerz": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/3G%20hackerz-min.png?alt=media&token=6da0b4b8-ff95-4d33-b706-d1720ba0dd22";
                                        break;
                                    case "Tricky Time": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/Tricky%20time.png?alt=media&token=a301c602-def4-4369-b3b0-c2a52aeac784";
                                        break;
                                    case "A2Y": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/A2Y-min.png?alt=media&token=a2277a78-79aa-4373-b21c-d648c076ffbd";
                                        break;
                                    case "Earticleblog": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/earticleblog.png?alt=media&token=9f3ec9ab-842a-4c52-ac19-7355ecdefd94";
                                        break;
                                    case "BigTricks": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/Bigtricks.png?alt=media&token=f682be12-89de-45b2-b502-dafc7960fc12";
                                        break;
                                    case "CoolzTricks": imageUrl =  "https://www.coolztricks.com/wp-content/uploads/2016/08/imageedit_4_3812642078-300x132.png";
                                        break;
                                    case "Rec. Tricks": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/rechargeTricks.png?alt=media&token=f2fb465a-d855-4aa3-9033-2b48032be1ba";
                                        break;
                                    case "Tricks by STG": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/tbstgfinal.png?alt=media&token=8d15cbde-87ca-47de-aee9-424ca48877fe";
                                        break;
                                    case "Zoutons": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/Zoutons.png?alt=media&token=b5ab612b-3a96-4851-912c-0a6af1f6c16d";
                                        break;
                                    case "GrabOn": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/GrabOn-min.png?alt=media&token=6e0d77f8-5b67-423f-b6e1-738abbcb9333";
                                        break;
                                    case "CouponRani": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/crani.png?alt=media&token=76c2dc76-25ae-44db-8251-319f8159c1ef";
                                        break;
                                    case "CouponDunia": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/CouponDunia-min.png?alt=media&token=3599dcc6-9f6d-4466-9d5e-9bb85f6dc7f6";
                                        break;
                                    case "CashKaro": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/CashKaro-min.png?alt=media&token=fa3d1506-9eae-49f6-8f83-6cb1a1ea254d";
                                        break;
                                    case "Paisaget": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/Paisaget-min.png?alt=media&token=ad04d1ad-7424-435f-afe3-cf8b72e3104d";
                                        break;
                                    case "Gopaisa": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/Gopaisa-min.png?alt=media&token=b7ae7cd6-4b19-46bf-8aee-c63685848e28";
                                        break;
                                    case "Paisawapas": imageUrl =  "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/PaisaWapas-min.png?alt=media&token=8911d89e-d7b2-4347-b42e-4b4a1e2a4741";
                                        break;
                                    case "OTHER": textViewImageUrlUpdate.setVisibility(View.VISIBLE);
                                        mannualImageUrl.setVisibility(View.VISIBLE);
                                        imageUrl = "";
                                        break;
                                }
                                Log.e("ImageUrl", imageUrl);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                imageUrl = "";
                            }
                        });
                            chooseImageUpdate.setSelection(0);
                            imageUrl = tempData[2];
                            updateButton.setEnabled(true);
                            setUpdateButton();
                    }
                }
            }
        });

    }

    private void getNotifications(){
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                document = task.getResult();
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

    private void setShowNotifications(){
        notificationShownUpdate.setText(notifications);
        numberToUpdate.setEnabled(true);
        button_next.setEnabled(true);
    }

    private void setUpdateButton(){

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sHeading = heading.getText().toString();
                sSubHeading = subHeading.getText().toString();
                sUrlToOpen = urlToOpen.getText().toString();

                if(chooseImageUpdate.getSelectedItem().toString().equalsIgnoreCase("OTHER"))
                    imageUrl = mannualImageUrl.getText().toString();

                if(sHeading.length()!= 0){
                    if (sSubHeading.length() != 0){
                        if (sUrlToOpen.length() != 0){
                            if (imageUrl.length() != 0) {

                                updateButton.setEnabled(false);
                                Toast.makeText(update_notification.this, "Updating notification please wait......", Toast.LENGTH_SHORT).show();
                                sHeading = Html.fromHtml(sHeading).toString();
                                sHeading = sHeading.replaceAll("[~.,*/']"," ");
                                sHeading = sHeading.replace("["," ");
                                sHeading = sHeading.replace("]"," ");
                                sHeading = sHeading.replace(" --- "," ");
                                sSubHeading = sSubHeading.replace(" --- "," ");
                                notification.clear();
                                notification.put(keysList.get(n-1), FieldValue.delete());
                                notification.put(sHeading, sSubHeading + " --- " + sUrlToOpen + " --- " + imageUrl + " --- " + sDateTime);
                                keysList.remove(n-1);
                                keysList.add(n-1, sHeading);
                                String keysListString = "";
                                for(int i=0; i<keysList.size(); i++){
                                    if(i!=0)
                                        keysListString = keysListString + "," + keysList.get(i);
                                    else
                                        keysListString = keysList.get(i);
                                }
                                notification.put("keys", keysListString);
                                Log.e("KeysListString", keysListString);
                                Log.e("notifications", notification.toString());
                                Log.e("ImageUrl", imageUrl);

                                documentReference.set(notification, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(update_notification.this, "Notification Updated Successfully", Toast.LENGTH_SHORT).show();
                                        update_notification.super.onBackPressed();
                                    }
                                });

                            }
                            else{ Toast.makeText(update_notification.this,"Image Url is empty",Toast.LENGTH_SHORT).show();
                                Log.e("error","ImageUrl is empty");}
                        }
                        else{ Toast.makeText(update_notification.this,"Url to Open is empty",Toast.LENGTH_SHORT).show();
                            Log.e("error","UrlToOpen is empty");}
                    }
                    else{ Toast.makeText(update_notification.this,"SubHeading is empty",Toast.LENGTH_SHORT).show();
                        Log.e("error","Sub Heading is empty");}
                }
                else{ Toast.makeText(update_notification.this,"Heading is empty",Toast.LENGTH_SHORT).show();
                    Log.e("error","Heading is empty");}

            }
        });
    }
}

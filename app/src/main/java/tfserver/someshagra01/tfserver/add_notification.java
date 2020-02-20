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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class add_notification extends AppCompatActivity {
    String imageUrl, sHeading, sSubHeading, sUrlToOpen, sDateTime, notiType, url = "https://fcm.googleapis.com/fcm/send";
    Map<String, Object> notification = new HashMap<>();
    RequestQueue queue;
    CheckBox sendPushNotification;
    DocumentReference documentReference;
    CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Notification");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);


        final TextView textViewManualUrl = (TextView) findViewById(R.id.textViewImageUrl);
        final EditText manualUrl = (EditText) findViewById(R.id.manualImageUrl);
        final EditText heading = (EditText) findViewById(R.id.heading);
        final EditText subHeading = (EditText) findViewById(R.id.subHeading);
        final EditText urlToOpen = (EditText) findViewById(R.id.urlToOpen);
        sendPushNotification = (CheckBox) findViewById(R.id.sendPushNotification);

        queue = Volley.newRequestQueue(this);

        final Spinner chooseNotiType = findViewById(R.id.chooseNotiType);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                documentReference = collectionReference.document("Deals");
            }
        });

        final Spinner chooseImage = findViewById(R.id.chooseImage);
        String[] items = new String[]
                {"Tricks Fair", "Amazon", "Flipkart", "Paytm",
                        "TricksGang", "CuteTricks", "3gHackerz","Tricky Time", "A2Y","Earticleblog","BigTricks","CoolzTricks","Rec. Tricks","Tricks by STG",
                "Zoutons","GrabOn","CouponRani","CouponDunia",
                "CashKaro","Paisaget","Gopaisa","Paisawapas",
                "OTHER"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseImage.setAdapter(adapter);


        chooseImage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                textViewManualUrl.setVisibility(View.GONE);
                manualUrl.setVisibility(View.GONE);

                switch((String) parent.getItemAtPosition(position)){
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
                    case "OTHER": textViewManualUrl.setVisibility(View.VISIBLE);
                                  manualUrl.setVisibility(View.VISIBLE);
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

        final Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sHeading = heading.getText().toString();
                sSubHeading = subHeading.getText().toString();
                sUrlToOpen = urlToOpen.getText().toString();
                sDateTime = getDateTime();
                if(chooseImage.getSelectedItem().toString().equalsIgnoreCase("OTHER"))
                    imageUrl = manualUrl.getText().toString();

                if(sHeading.length()!= 0){
                    if (sSubHeading.length() != 0){
                        if (sUrlToOpen.length() != 0){
                            if (imageUrl.length() != 0) {

                                submit.setEnabled(false);
                                sHeading = Html.fromHtml(sHeading).toString();
                                sHeading = sHeading.replaceAll("[~.,']"," ");
                                sHeading = sHeading.replace("["," ");
                                sHeading = sHeading.replace("]"," ");
                                sHeading = sHeading.replace(" --- "," ");
                                sSubHeading = sSubHeading.replace(" --- "," ");
                                notification.put(sHeading, sSubHeading + " --- " + sUrlToOpen + " --- " + imageUrl + " --- " + sDateTime);
                                Toast.makeText(add_notification.this,"Please Wait...\nUploading notification",Toast.LENGTH_SHORT).show();
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                        String keys = documentSnapshot.getString("keys");
                                        keys = sHeading + "," + keys;
                                        notification.put("keys",keys);
                                        addNotification();
                                    }
                                });
                            }
                            else{ Toast.makeText(add_notification.this,"Image Url is empty",Toast.LENGTH_SHORT).show();
                                Log.e("error","ImageUrl");}
                        }
                        else{ Toast.makeText(add_notification.this,"Url to Open is empty",Toast.LENGTH_SHORT).show();
                            Log.e("error","UrlToOpen");}
                    }
                    else{ Toast.makeText(add_notification.this,"SubHeading is empty",Toast.LENGTH_SHORT).show();
                        Log.e("error","Sub Heading");}
                }
                else{ Toast.makeText(add_notification.this,"Heading is empty",Toast.LENGTH_SHORT).show();
                    Log.e("error","Heading");}
            }
        });


    }

    private void addNotification(){

        documentReference
                .set(notification, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(add_notification.this,"Notification Uploaded Successfully",Toast.LENGTH_SHORT).show();

                if(sendPushNotification.isChecked()) {
                    Toast.makeText(add_notification.this,"Sending push notification\nPlease Wait...",Toast.LENGTH_SHORT).show();
                    JSONObject jsonBodyMain = new JSONObject();
                    JSONObject info = new JSONObject();
                    try {
                        info.put("title", sHeading);
                        info.put("message", sSubHeading);
                        info.put("type", notiType);
                        info.put("image-url", "https://firebasestorage.googleapis.com/v0/b/tricksfair-8612e.appspot.com/o/logo_main_180px.png?alt=media&token=c2c069cd-a100-4aa1-8196-5b058318bd47");
                        jsonBodyMain.put("to", "/topics/"+notiType);
                        jsonBodyMain.put("data", info);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    final String requestBody = jsonBodyMain.toString();
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                            url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject responseJson = new JSONObject(response.toString());
                                if(responseJson.has("message_id")){
                                    Toast.makeText(add_notification.this,"Push notification send successfully",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Log.e("Response", String.valueOf(response));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.e("Error: ", error.getMessage());
                            Toast.makeText(add_notification.this,""+error,Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json");
                            headers.put("authorization", "REMOVED_IN_PUBLIC_REPOSITORY");
                            return headers;
                        }

                        @Override
                        public byte[] getBody() {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }
                    };
                    queue.add(jsonObjectRequest);
                }
                else
                    finish();

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(add_notification.this,""+e,Toast.LENGTH_LONG).show();
                finish();

            }
        });
        Log.e("Full Data",sHeading+"\n"+sSubHeading+"\n"+sUrlToOpen+"\n"+sDateTime+"\n"+ imageUrl);

    }

    private String getDateTime(){

        Calendar c = Calendar.getInstance();
        int minutes = c.get(Calendar.MINUTE);
        String s_min;
        int hour = c.get(Calendar.HOUR_OF_DAY);
        String s_hour;
        String ampm;
        if (c.get(Calendar.AM_PM) == 0) {
            ampm = "AM";
        }
        else {
            if(hour>12)
                hour -= 12;
            ampm = "PM";
        }
        if(minutes<10)
            s_min = "0"+minutes;
        else
            s_min = ""+minutes;
        if(hour<10)
            s_hour = "0"+hour;
        else
            s_hour = ""+hour;

        String time = s_hour + ":" + s_min + " " +ampm;

        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        String smonth = "";

        switch(month){
            case 0: smonth="JAN";
                break;
            case 1: smonth="FEB";
                break;
            case 2: smonth="MAR";
                break;
            case 3: smonth="APR";
                break;
            case 4: smonth="MAY";
                break;
            case 5: smonth="JUN";
                break;
            case 6: smonth="JUL";
                break;
            case 7: smonth="AUG";
                break;
            case 8: smonth="SEP";
                break;
            case 9: smonth="OCT";
                break;
            case 10: smonth="NOV";
                break;
            case 11: smonth="DEC";
                break;
        }

        int year = c.get(Calendar.YEAR);
        String date = day+" "+smonth+" "+year;

        return date+"    "+time;
    }

}

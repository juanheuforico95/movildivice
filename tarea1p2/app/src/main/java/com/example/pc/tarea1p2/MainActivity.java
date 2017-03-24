package com.example.pc.tarea1p2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);


        lv.setOnItemClickListener(this);

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://dl.dropbox.com/s/k5qpeuq2py9xv0l/frinds.json?dl=0";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("studentinfo");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String name = c.getString("name");
                        String hobby = c.getString("hobby");
                        String age =  String.valueOf(c.getInt("age"));
                        String address = c.getString("address");
                        String phone = c.getString("phone");


                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("name", name);
                        contact.put("hobby", hobby);
                        contact.put("address", address);
                        contact.put("age",age);
                        contact.put("phone", phone);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }


    }
    public void request(View v){

        ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList,
                R.layout.list_item, new String[]{ "name","hobby"},
                new int[]{R.id.name, R.id.hobby});
        lv.setAdapter(adapter); }
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ArrayList<HashMap<String, String>> infoList;
        infoList = new ArrayList<>();
        infoList.add(contactList.get(i));
       /* String nombre;
        nombre= contactList.get(i).get("name");
        Toast.makeText(this, "mira " + " " + nombre, Toast.LENGTH_SHORT).show();*/

        ListAdapter adapter = new SimpleAdapter(MainActivity.this, infoList,
                R.layout.all_info, new String[]{ "name","hobby","age","phone","address"},
                new int[]{R.id.name, R.id.hobby, R.id.age, R.id.phone, R.id.address});
        lv.setAdapter(adapter); }

}
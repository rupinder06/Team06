package com.example.team6;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class FirstPage extends AppCompatActivity {

    ArrayList<Products> pro;
    Listadpt adpt;
    ListView lstv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        pro =new ArrayList<>();
        lstv=findViewById(R.id.lst_products);

        String link = getResources().getString(R.string.link);
       try{
           String myjson =new Syncdata().execute(link).get();

           System.out.println("Bhakti JSON:"+myjson);


       //    JSONObject mainobj = new JSONObject(myjson);

          JSONArray products = new JSONArray(myjson);

           for(int i =0 ;i<30;i++)
           {
               JSONObject childobj = products.getJSONObject(i);
               System.out.println("childObj"+i);
               String name =childobj.getString("name");
               String avatar_url=childobj.getJSONObject("owner").getString("avatar_url");
               String description=childobj.getString("description");


               pro.add(new Products(name,avatar_url,description));
           System.out.println("Size of Array"+pro.size());

           adpt =new Listadpt(getApplicationContext(),pro);

           lstv.setAdapter(adpt);
           lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                   Intent i = new Intent(FirstPage.this,DataActivity.class);

                   i.putExtra("data",pro.get(position));
                   startActivity(i);

               }
           });


       }
       catch (ExecutionException e)
       {
           Log.e("bhaktiException",e.getMessage());
       }
       catch (JSONException e)
       {
           Log.e("JsonProblem :",e.getMessage());
       }

       catch (InterruptedException e)
       {
           Log.e("ProblemINSyncData :",e.getMessage());
       }

    }
}

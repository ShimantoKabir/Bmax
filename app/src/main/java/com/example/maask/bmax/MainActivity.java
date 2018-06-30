package com.example.maask.bmax;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvForConversation;
    private EditText userQuery;
    private ImageView sendQuery;

    private ArrayList<String> conversationList;
    private ConversationAdapter adapter;

    private BmaxService bmaxService;

    private ProgressDialog progressDialog;

    private LinearLayout gratings;

    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvForConversation = findViewById(R.id.rv_for_conversation);
        userQuery = findViewById(R.id.user_query);
        sendQuery = findViewById(R.id.send_query);
        gratings = findViewById(R.id.gratings);

        toolbar = findViewById(R.id.custom_toolbar);
        toolbar.setTitle("Bmax");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);

        conversationList = new ArrayList<>();

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rvForConversation.setLayoutManager(lm);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dialogflow.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bmaxService = retrofit.create(BmaxService.class);

        sendQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gratings.setVisibility(View.GONE);
                rvForConversation.setVisibility(View.VISIBLE);

                String query = userQuery.getText().toString();
                conversationList.add(query);
                getResponse(query);
                progressDialog.show();

            }
        });

    }

    private void getResponse(final String query) {

        List<String> shop = new ArrayList<>();
        shop.add("shop");

        SendBody sendBody = new SendBody();
        sendBody.setContexts(shop);
        sendBody.setLang("en");
        sendBody.setQuery(query);
        sendBody.setSessionId("12345");
        sendBody.setTimezone("America/New_York");

        Call<Response> responseCall = bmaxService.getResponse(sendBody);

        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if (response.isSuccessful()){

                    userQuery.setText("");
                    progressDialog.dismiss();
                    String bmaxRasponse = response.body().getResult().getFulfillment().getSpeech();
                    conversationList.add(bmaxRasponse);
                    Collections.reverse(conversationList);
                    adapter = new ConversationAdapter(conversationList,MainActivity.this);
                    adapter.instantDataChang(conversationList);
                    rvForConversation.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("onFailure: ",t.getMessage() );
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.about_us:
                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}

package com.ntnu.gard.smsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {
    public static final String CONVERSATION_ID = "conversationid";
    public static final String CONTACT_NAME = "contactName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Gards SMSapp");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(i);


            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);


        int conversationSize = DomainSingleton.getSingleton(this).getData().size();
        if (conversationSize > 0) {



            final ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    DomainSingleton.getSingleton(this).getAllConversationNames() );


            listView.setAdapter(namesAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //får tak i navnet til kontakten man har valgt
                    String contactName = namesAdapter.getItem(position);

                    //får tak i conversationId til kontakten man har valgt fra listen
                    Message tempMessage = DomainSingleton.getSingleton(MainActivity.this).getFirstMessageInConversation(position);
                    int conversationId = tempMessage.conversationId;

                            // int conversationId = getConversationIDWithArrayIndex(position);

                    Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                    i.putExtra(CONTACT_NAME, contactName);
                    i.putExtra(CONVERSATION_ID,conversationId);
                    startActivity(i);

                }
            });

        }

    }


    private int getConversationIDWithArrayIndex(int index)
    {
        int conversationId;
        Message tempMessage = DomainSingleton.getSingleton(MainActivity.this).getFirstMessageInConversation(index);
        conversationId = tempMessage.getConversationId();
        return conversationId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

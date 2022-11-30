package com.example.strukov.Get;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.strukov.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlayersActivity extends AppCompatActivity {
    private List<Player> mPlayerList = new ArrayList<>();
    private Adapter mAdapter;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        Button button = findViewById(R.id.buttonGet);
        ListView listView = findViewById( R.id.listView );
        txt = findViewById( R.id.textView);

        mAdapter = new Adapter(PlayersActivity.this, mPlayerList);
        listView.setAdapter(mAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getData().execute();
            }
        });
    }

    public class getData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                return SyncManagerGET.GetInstance().getData("players");
            } 
            catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.isEmpty()) {

                try {
                    JSONArray tempArray = new JSONArray(s);
                    for (int i = 0; i < tempArray.length(); i++) {
                        JSONObject tempO = tempArray.getJSONObject(i);
                        Player tempPlayer = new Player(tempO.getString("Name"),
                                tempO.getInt("PositionId"),
                                tempO.getInt("PlayerId"));
                        mPlayerList.add(tempPlayer);
                        mAdapter.notifyDataSetChanged();
                    }


                } catch (Exception e) {
                    txt.setText("Ошибка");
                }
            }
        }
    }
}


package com.example.strukov.Add;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.strukov.Add.Adapters.CountryAdapter;
import com.example.strukov.Add.Adapters.PositionAdapter;
import com.example.strukov.Add.Entities.Country;
import com.example.strukov.Add.Entities.Position;
import com.example.strukov.Get.SyncManagerGET;
import com.example.strukov.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class AddPlayerActivity extends AppCompatActivity {

    private List<Position> mPositionList = new ArrayList<>();
    private List<Country> mCountryList = new ArrayList<>();

    private CountryAdapter mCountryAdapter;
    private Spinner mSpinnerPos;
    private Spinner mSpinnerCode;
    private PositionAdapter mPositionAdapter;
    private Integer mPlayerId;
    private EditText mETName;
    private EditText mETBirthday;
    private EditText mETJoinYear;
    private EditText mETWeight;
    private EditText mETHeight;
    private EditText mETCollege;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        mETName = findViewById(R.id.editTextName);
        mETBirthday = findViewById(R.id.editTextBirthday);
        mETJoinYear = findViewById(R.id.editTextJoinYear);
        mETWeight = findViewById(R.id.editTextWeight);
        mETHeight = findViewById(R.id.editTextHeight);
        mETCollege = findViewById(R.id.editTextCollege);

        mCountryAdapter = new CountryAdapter(mCountryList, AddPlayerActivity.this);
        mPositionAdapter = new PositionAdapter(mPositionList, AddPlayerActivity.this);

        mSpinnerPos = findViewById(R.id.spinnerPosition);
        mSpinnerCode = findViewById(R.id.spinnerCode);

        mSpinnerPos.setAdapter(mPositionAdapter);
        mSpinnerCode.setAdapter(mCountryAdapter);

        new GetEntity("Countries").execute();
        new GetEntity("Positions").execute();

        Button btn = findViewById(R.id.buttonAddPlayer);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject tempObj = new JSONObject();
                    tempObj.put("Name", mETName.getText() );
                    tempObj.put("PositionId",((Position)mSpinnerPos.getSelectedItem() ).getId());
                    tempObj.put("CountryCode",((Country)mSpinnerCode.getSelectedItem() ).getCode());
                    tempObj.put("JoinYear", mETJoinYear.getText() );
                    tempObj.put("College", mETCollege.getText() );
                    tempObj.put("DateOfBirth", mETBirthday.getText() );
                    tempObj.put("Height",Double.parseDouble(mETHeight.getText().toString()) );
                    tempObj.put("Weight",Double.parseDouble(mETWeight.getText().toString()) );
                    new AddPlayer(tempObj).execute();

                }
                catch (Exception ex)
                {

                }

            }
        });

        mPlayerId = getIntent().getIntExtra("PlayerId", -1);
        if(mPlayerId != -1)
        {
            btn.setText("Изменить");
            new GetPlayerInfo().execute();
        }
        else
        {
        }
    }
    private class GetPlayerInfo extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            return SyncManagerGET.GetInstance().getData("players/"+mPlayerId);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{
                JSONObject tempObject = new JSONObject(s);

                mETBirthday.setText(tempObject.getString("DateOfBirth"));
                mETJoinYear.setText(tempObject.getString("JoinYear"));

                mETName.setText(tempObject.getString("Name"));
                mETWeight.setText(tempObject.getString("Weight"));
                mETHeight.setText(tempObject.getString("Height"));

                String college = tempObject.getString("College");
                if(college != "null")
                    mETCollege.setText(college);

                else
                    mETCollege.setText("отсутствует");
            }
            catch (Exception ex){
                Log.e("Ошибка", ex.getMessage());
            }
        }
    }



    private class GetEntity extends AsyncTask<Void, Void, String>
    {
        private String mPath;

        private GetEntity(String mPath) {
            this.mPath = mPath;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return SyncManagerPOST.getInstance().getData(mPath);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray tempArr = new JSONArray(s);

                for (int i=0; i < tempArr.length();i++)
                {
                    JSONObject tempObj = tempArr.getJSONObject(i);
                    if(mPath.equals("Positions"))
                    {
                        Position tempPos = new Position(tempObj.getInt("PositionId"),
                                tempObj.getString("Name"));
                        mPositionList.add(tempPos);
                        mPositionAdapter.notifyDataSetChanged();

                    }
                    else
                    {
                        Country tempCountry = new Country(
                                tempObj.getInt("CountryCode"),
                                tempObj.getString("CountryName")
                        );
                        mCountryList.add(tempCountry);
                        mCountryAdapter.notifyDataSetChanged();
                    }

                }

            }
            catch (Exception ex)
            {

            }

        }
    }
    private class AddPlayer extends AsyncTask<Void, Void, String>
    {
        JSONObject mObject;

        private AddPlayer(JSONObject mObject) {
            this.mObject = mObject;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.isEmpty())
            {
                Toast.makeText(AddPlayerActivity.this, s, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try
            {
                return  SyncManagerPOST.getInstance().postData("players", mObject);
            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }
}


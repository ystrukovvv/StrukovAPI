package com.example.strukov.Get;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.strukov.Add.AddPlayerActivity;
import com.example.strukov.R;

import java.util.List;

public class Adapter extends BaseAdapter {
    private Context mContext;
    private List<Player> mPlayerList;
    private ImageButton mImageButEdit;
    private ImageButton mImageButDelete;

    public Adapter(Context mContext, List<Player> mPlayerList)
    {
        this.mContext = mContext;
        this.mPlayerList = mPlayerList;
    }

    @Override
    public int getCount() {
        return mPlayerList.size();
    }

    @Override
    public Object getItem(int i) {
        return mPlayerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mPlayerList.indexOf(i);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_user, null);


        TextView tv_name = v.findViewById(R.id.textViewName);
        TextView tv_positionid = v.findViewById(R.id.textViewId);
        TextView tv_playerid = v.findViewById(R.id.textViewPlayerId);

        mImageButEdit = v.findViewById(R.id.imageButtonEditingPlayers);
        mImageButDelete = v.findViewById(R.id.imageButtonDeletePlayers);

        final Player currentPlayer = mPlayerList.get(i);
        tv_name.setText(currentPlayer.getName());
        tv_positionid.setText(currentPlayer.getPositionId().toString());
        tv_playerid.setText(currentPlayer.getPlayerId().toString());

        mImageButEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit = new Intent(mContext, AddPlayerActivity.class);
                int playerId = currentPlayer.getPlayerId();
                intentEdit.putExtra("PlayerId", playerId);
                mContext.startActivity(intentEdit);
            }
        });

        mImageButDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeletePlayer(currentPlayer).execute();
            }
        });
        return v;
    }
    private class DeletePlayer extends AsyncTask<Void, Void, String>{
        private Player mCurrentPlayer;
        private DeletePlayer(Player mCurrentPlayer) {
            this.mCurrentPlayer = mCurrentPlayer;
        }




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mImageButDelete.setEnabled(false);
            mImageButEdit.setEnabled(false);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return SyncManagerGET.GetInstance().deleteData("Players", mCurrentPlayer.getPlayerId());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mImageButDelete.setEnabled(true);
            mImageButEdit.setEnabled(true);

            if (s == null)
                Toast.makeText(mContext, "Ошибка", Toast.LENGTH_SHORT).show();

            else
                mPlayerList.remove(mCurrentPlayer);
                notifyDataSetChanged();
        }
    }



}


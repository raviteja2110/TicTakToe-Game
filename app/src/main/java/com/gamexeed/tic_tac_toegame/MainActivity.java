package com.gamexeed.tic_tac_toegame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button[][]=new Button[3][3];
    boolean player1Turn=true;
    int player1Points,player2Points,round_count;
    TextView tv_p1,tv_p2;
    Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_p1=findViewById(R.id.tv_player1);
        tv_p2=findViewById(R.id.tv_player2);
        btn_reset=findViewById(R.id.btn_reset);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetGame();
            }
        });

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                String ButtonId="btn_"+i+j;
                int ResourceId=getResources().getIdentifier(ButtonId,"id",getPackageName());
                button[i][j]=findViewById(ResourceId);
                button[i][j].setOnClickListener(this);
            }
        }
    }

    private void ResetGame() {
        ResetBoard();
        player1Points=0;
        player2Points=0;
        UpdatePoints();
    }


    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals(""))
        {
            return;
        }
        if(player1Turn){
            ((Button) v).setText("X");
        }
        else
            ((Button) v).setText("O");
        round_count++;
        if(checkWin())
        {
            if (player1Turn)
            {
                player1Wins();
            }
            else
                player2Wins();
        }
        else if (round_count==9)
         draw();
        else
            player1Turn=!player1Turn;
    }
    public boolean checkWin()
    {
        String field[][]=new String[3][3];
        for(int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
               field[i][j]=button[i][j].getText().toString();
            }
        }
        for(int i=0;i<3;i++)
        {
            if(field[i][0].equals(field[i][1]) &&  field[i][0].equals(field[i][2])  &&  !field[i][0].equals(""))
                return true;
        }
        for(int i=0;i<3;i++)
        {
            if(field[0][i].equals(field[1][i]) &&  field[0][i].equals(field[2][i])  &&  !field[0][i].equals(""))
                return true;
        }
        if(field[0][0].equals(field[1][1]) &&  field[0][0].equals(field[2][2])  &&  !field[0][0].equals(""))
            return true;
        if(field[0][0].equals(field[1][1]) &&  field[0][0].equals(field[2][2])  &&  !field[0][0].equals(""))
            return true;
        if(field[0][2].equals(field[1][1]) &&  field[0][2].equals(field[2][0])  &&  !field[0][2].equals(""))
            return true;
        return false;
    }
    private void player1Wins()
    {
      player1Points++;
      Toast.makeText(this,"Player 1 Wins!",Toast.LENGTH_SHORT).show();
      UpdatePoints();
      ResetBoard();

    }

    private void ResetBoard() {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                button[i][j].setText("");
            }
        }
        round_count=0;
        player1Turn=true;
    }

    private void UpdatePoints() {
        tv_p1.setText("Player 1:"+player1Points);
        tv_p2.setText("Player 2:"+player2Points);
    }

    private void player2Wins()
    {
        player2Points++;
        Toast.makeText(this,"Player 2 Wins!",Toast.LENGTH_SHORT).show();
        UpdatePoints();
        ResetBoard();
    }
    private void draw()
    {
        Toast.makeText(this,"Game is Draw",Toast.LENGTH_SHORT).show();
        ResetBoard();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("roundCount",round_count);
        outState.putInt("Player1Points",player1Points);
        outState.putInt("Player1Points",player2Points);
        outState.putBoolean("Player1Turn",player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        round_count=savedInstanceState.getInt("roundCount");
        player1Points=savedInstanceState.getInt("Player1Points");
        player2Points=savedInstanceState.getInt("Player2Points");
        player1Turn=savedInstanceState.getBoolean("Player1Turn");
    }
}

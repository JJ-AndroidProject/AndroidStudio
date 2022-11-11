package org.techtown.sampletab;

import android.content.Context;

public class MoveAccount {
    public MoveAccount(Context context){

        try{
            DBcommand command = new DBcommand(context);
            command.selectCount();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

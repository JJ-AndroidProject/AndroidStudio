package org.techtown.sampletab;

import android.content.Context;

public class MoveAccount {
    public MoveAccount(Context context){
        DBcommand command = new DBcommand(context);
        command.selectCount();
        try{
            Thread.sleep(5000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

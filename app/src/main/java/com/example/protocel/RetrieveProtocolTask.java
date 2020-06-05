package com.example.protocel;

import android.os.AsyncTask;

import java.util.concurrent.Executor;

public class RetrieveProtocolTask implements Executor {

    @Override
    public void execute(Runnable r){
        r.run();
    }

}

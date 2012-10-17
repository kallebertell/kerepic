package kerebus.kerepic.ui;

import android.os.AsyncTask;

public abstract class KereAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    @Override
    public Result doInBackground(Params... params) {
        return doInBackground(params[0]);
    }

    public abstract Result doInBackground(Params param);
}

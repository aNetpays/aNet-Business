package in.anetpays.siddhant.anet_business.SplashScreen;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

public class DownLoadingActivity extends AsyncTask<String, Integer, Integer>{

    public interface LoadingTaskFinishedListener{
        void onTaskFinished();
    }

    private final LoadingTaskFinishedListener loadingTaskFinishedListener;
    private final ProgressBar progressBar;

    public DownLoadingActivity(ProgressBar progressBar, LoadingTaskFinishedListener loadingTaskFinishedListener)
    {
        this.loadingTaskFinishedListener = loadingTaskFinishedListener;
        this.progressBar = progressBar;
    }

    @Override
    protected Integer doInBackground(String... params){
        Log.i("download", "Or Start task with url: "+params[0]);
        if(resourcesDontAlreadyExist()){
            downloadResources();
        }
        // Perhaps you want to return something to your post execute
        return 1234;
    }
    private boolean resourcesDontAlreadyExist(){
        return true;
    }
    private void downloadResources(){
        int count = 10;
        for (int i = 0; i < count; i++) {

            // Update the progress bar after every step
            int progress = (int) ((i / (float) count) * 100);
            publishProgress(progress);

            // Do some long loading things
            try { Thread.sleep(250); } catch (InterruptedException ignore) {}
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        loadingTaskFinishedListener.onTaskFinished();
    }
}

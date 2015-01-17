package com.traxbuddy.trax;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.os.AsyncTask;
import android.view.View.OnClickListener;

public class TraxStore {
    public interface HttpResponseIf {
        void postExecute(HttpResponse r);
    }

    public static final String TAG = "MainActivity";
    public static final String register_url = "http://www.traxbuddy.com/register";
    public static final String confirm_url = "http://www.traxbuddy.com/confirm.json";
    public static final String coords_url = "http://www.traxbuddy.com/coords.json";
    public static final String set_email_url = "http://www.traxbuddy.com/set_email";

    public static void execHttp(final List<NameValuePair> l, final String url, final OnClickListener onClickListener) {
        class HttpAsyncTask extends AsyncTask<Void, Void, Void> {
            HttpResponse r;
            @Override
            protected Void doInBackground(Void... voids) {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                // Url Encoding the POST parameters
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(l));
                    this.r = httpClient.execute(httpPost);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override 
            protected void onPostExecute(Void v) {
                ((HttpResponseIf) onClickListener).postExecute(this.r);
                
            }
        };
        HttpAsyncTask task = new HttpAsyncTask();
        task.execute((Void) null);
    }
}


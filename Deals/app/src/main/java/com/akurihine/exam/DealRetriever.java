package com.akurihine.exam;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;

import com.akurihine.exam.model.Deals;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



public class DealRetriever {
    private ObjectMapper objectMapper = new ObjectMapper();
    private JsonFactory jsonFactory = new JsonFactory();
    private JsonParser jp = null;
    private Deals deals = null;

    public interface DataRetrieverListener {
        void onResponse(Deals deals);
        void onError();
    }

    public void getDeals(final DataRetrieverListener dataRetrieverListener) {
        new LongOperation(dataRetrieverListener).execute();
    }

    private class LongOperation extends AsyncTask<String, Void, Deals> {

        private DataRetrieverListener dataRetrieverListener;

        LongOperation (DataRetrieverListener dataRetrieverListener) {
            this.dataRetrieverListener = dataRetrieverListener;
        }

        @Override
        protected Deals doInBackground(String... urls) {
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpResponse response = httpClient.execute(new HttpGet("http://sheltered-bastion-2512.herokuapp.com/feed.json"));
                InputStream is = response.getEntity().getContent();

                jp = jsonFactory.createJsonParser(is);
                deals = objectMapper.readValue(jp, Deals.class);
            }
            catch (JsonParseException e) {
                e.printStackTrace();
                deals = null;
            }

            catch (IOException e) {
                e.printStackTrace();
                deals = null;
            }
            return deals;
        }

        @Override
        protected void onPostExecute(Deals result) {
            if (deals == null) {
                dataRetrieverListener.onError();
            } else {
                dataRetrieverListener.onResponse(deals);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
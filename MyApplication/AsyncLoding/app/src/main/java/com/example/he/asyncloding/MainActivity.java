package com.example.he.asyncloding;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<ItemBean> itemList;
    private myAdapter adapter;
    private static final String URL = "http://www.imooc.com/api/teacher?type=4&num=30";//JSON数据的URL
    private static final int OK = 1;
    private NewsAsyncTask task;
    private ProgressBar bar;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OK:
                    adapter.notifyDataSetChanged();//更新数据
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv);
        bar = (ProgressBar) findViewById(R.id.progress_bar);
        task = new NewsAsyncTask();
        task.execute(URL);
//        listView.setOnScrollListener(adapter);

    }

    class NewsAsyncTask extends AsyncTask<String, Void, List<ItemBean>> {
        @Override
        protected void onPreExecute() {
            listView.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected List<ItemBean> doInBackground(String... params) {
            return getData(params[0]);
        }

        //
        @Override
        protected void onPostExecute(List<ItemBean> list) {
            super.onPostExecute(list);
            listView.setVisibility(View.VISIBLE);
            bar.setVisibility(View.GONE);

            itemList = new ArrayList<ItemBean>();
            itemList.addAll(list);
            adapter = new myAdapter(itemList, MainActivity.this,listView);
            listView.setAdapter(adapter);
            handler.sendEmptyMessageDelayed(OK,1000);

        }
    }


    /**
     * 返回一个添加ItemBean的List
     *
     * @param url
     * @return
     */

    private List<ItemBean> getData(String url) {
        List<ItemBean> list = new ArrayList<ItemBean>();
        try {
            //URL.openStream 方法已将将网络连接部分封装好了
            //等同于new URL(url).openConnection().getInputStream();
            String json = readStream(new URL(URL).openStream());

            /**
             * 解析JSON
             */

            JSONObject object;
            ItemBean item;

            object = new JSONObject(json);
            JSONArray array = object.getJSONArray("data");
            //jsonArray中每个元素都是jasonObject
            for (int i = 0; i < array.length(); i++) {
                object = array.getJSONObject(i);
                String imageUrl = object.getString("picSmall");
                String title = object.getString("name");
                String content = object.getString("description");
                item = new ItemBean(imageUrl, title, content);
                list.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;

    }

    /**
     * 返回JSON格式的字符串
     *
     * @param inputStream
     * @return
     */

    private String readStream(InputStream inputStream) {
        String result = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = "";

            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}


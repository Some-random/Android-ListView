package com.example.bob_jiang.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);

        //This is the case for simple layout which responds when you click on the item
        /*listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getData()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //arg1 here is the row number
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Toast.makeText(getApplicationContext(), "You clicked " + arg2 + " row.", Toast.LENGTH_SHORT).show();
            }
        });*/


        //This is the function to move your listview to the row you specified
        /*listView.clearFocus();
        listView.post(new Runnable() {
            @Override
            public void run() {
                listView.setSelection(20);
            }
        });*/


        //These are layouts with choices, I still haven't figured out how can I proceed after I made
        // the choice
        /*listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, getData()));
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, getData()));
        listView.setAdapter(newArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, getData()));
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);*/


        //SimpleAdapter listview, combination of text and image
        /*ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemImage", R.drawable.icon);
            map.put("ItemTitle", "Row " + i);
            map.put("ItemText", "This is row " + i);
            listItem.add(map);
        }
        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, listItem, R.layout.item, new String[]
                {"ItemImage", "ItemTitle", "ItemText"}, new int[] {R.id.ItemImage, R.id.ItemTitle,
                R.id.ItemText}
        );
        listView.setAdapter(mSimpleAdapter);//为ListView绑定适配器 */


        //The final boss, use baseadapter to override getView and getCount, enables the button click
        // functionality and uses tag for caching
        MyAdapter mAdapter = new MyAdapter(this);//得到一个MyAdapter对象lv.setAdapter(mAdapter);//为ListView绑定Adapter /*为ListView添加点击事件*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
                Toast.makeText(getApplicationContext(), "You pressed ListView row" + arg2,
                        Toast.LENGTH_SHORT).show();//在LogCat中输出信息
            }
        });
        listView.setAdapter(mAdapter);
        setContentView(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<String> getData(){

        List<String> data = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            data.add("Test data " + i);
        }

        return data;
    }

    public final class ViewHolder{
        public TextView title;
        public TextView text;
        public Button   bt;
    }

    private ArrayList<HashMap<String, Object>> getDate() {

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", i + " row");
            map.put("ItemText", "This is " + i + " row");
            listItem.add(map);
        }
        return listItem;
    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局 /*构造函数*/

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return getDate().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        //convertView here is the potential useable old view
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ArrayList<HashMap<String, Object>> dateList = getDate();
            //观察convertView随ListView滚动情况

            Toast.makeText(getApplicationContext(), "getView " + position + " " + convertView,
                    Toast.LENGTH_SHORT).show();
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item2,
                        null);
                holder = new ViewHolder();
                    /*得到各个控件的对象*/

                holder.title = (TextView) convertView.findViewById(R.id.ItemTitle);
                holder.text = (TextView) convertView.findViewById(R.id.ItemText);
                holder.bt = (Button) convertView.findViewById(R.id.ItemButton);
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }

            //holder.title.setText(dateList.get(position).get("ItemTitle").toString());
            holder.text.setText(dateList.get(position).get("ItemText").toString());
            holder.bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "You pressed button " + position,
                            Toast.LENGTH_SHORT).show();                                //打印Button的点击信息
                }
            });

            return convertView;
        }

    }

}

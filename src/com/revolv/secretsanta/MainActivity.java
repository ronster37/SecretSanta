package com.revolv.secretsanta;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.revolv.secretsanta.listadapter.MainListAdapter;
import com.revolv.secretsanta.listadapter.MainListData;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener, OnItemLongClickListener {

	ListView lv_list;
	Button button;
	MainListAdapter adapter;
	LinkedList<MainListData> list_data;
	GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		lv_list = (ListView) this.findViewById(R.id.listView1);
		lv_list.setOnItemClickListener(this);
		lv_list.setOnItemLongClickListener(this);
		
//		gestureDetector = new GestureDetector(this, new MyGestureDetector());
//		gestureListener = new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                return gestureDetector.onTouchEvent(event);
//            }
//        };
//        
//		lv_list.setOnTouchListener(gestureListener);
		
		
		button = (Button) this.findViewById(R.id.button1);
		button.setOnClickListener(this);
		adapter = new MainListAdapter(this, null);
		
		handleListView();
	}
	
	private void handleListView() {
		new AsyncGetMainList() {
			
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				try {
					JSONArray json = new JSONArray(result);
					LinkedList<MainListData> data = new LinkedList<MainListData>();
					for(int i=0; i < json.length(); i++) {
						JSONObject jsonObject = json.getJSONObject(i);
						data.add(new MainListData(jsonObject.getString("title"), jsonObject.getString("info")));
					}
					
					list_data = data;
					adapter.setData(data);
					lv_list.setAdapter(adapter);
				} catch (JSONException e) {
					// TODO ERROR TO USER
					
				}
			}
			
		}.execute("https://whensbest.com/revolv/");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
			case R.id.button1:
				button.setText("Yes");
				break;
			
			default:
				button.setText("No");
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		button.setText("WORKS");
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		button.setText(Long.toString(arg3));
		return true;
	}
	
	
//	class MyGestureDetector extends SimpleOnGestureListener {
//		private static final int SWIPE_MIN_DISTANCE = 50;
//	    private static final int SWIPE_MAX_OFF_PATH = 250;
//	    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
//		
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            try {
//                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
//                    return false;
//                // right to left swipe
//                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {         
//                    ((Button) lv_list.getChildAt(lv_list.pointToPosition((int)e1.getX(), (int)e1.getY())).findViewById(R.id.b_delete)).setVisibility(View.VISIBLE);
//                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {                    
//                    ((Button) lv_list.getChildAt(lv_list.pointToPosition((int)e1.getX(), (int)e1.getY())).findViewById(R.id.b_delete)).setVisibility(View.GONE);
//                }
//            } catch (Exception e) {
//                // nothing
//            }
//            return false;
//        }
//
//    }

}
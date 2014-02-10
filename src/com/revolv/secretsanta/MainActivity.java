package com.revolv.secretsanta;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.revolv.secretsanta.functions.HelperFunctions;
import com.revolv.secretsanta.listadapters.MainListAdapter;
import com.revolv.secretsanta.listadapters.MainListData;
import com.revolv.secretsanta.models.ListModel;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener, Observer {

	ListModel<MainListData> model = new ListModel<MainListData>();
	ListView lv_list;
	Button button;
	GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		model.addObserver(this);

		lv_list = (ListView) this.findViewById(R.id.listView1);
		lv_list.setOnItemClickListener(this);

		button = (Button) this.findViewById(R.id.button1);
		button.setOnClickListener(this);
		button.setEnabled(false);

		handleListView();
	}

	//GET USER LIST
	private void handleListView() {
		new AsyncHttpRequest() {

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				try {
					JSONObject json = new JSONObject(result);
					if(json.getString("response").equals("Success")) {
						JSONArray json_array = json.getJSONArray("data");
						LinkedList<MainListData> data = new LinkedList<MainListData>();
						for (int i = 0; i < json_array.length(); i++) {
							JSONObject jsonObject = json_array.getJSONObject(i);
							data.add(new MainListData(jsonObject.getInt("id"), jsonObject.getString("name")));
						}
						
						model.setList(data);
					} else {
						HelperFunctions.makeToast(MainActivity.this, MainActivity.this.getString(R.string.ERROR_GET_LIST));
					}
					
				} catch (JSONException e) {
					HelperFunctions.makeToast(MainActivity.this, MainActivity.this.getString(R.string.ERROR_GET_LIST));
				}
				
				button.setEnabled(true);
			}

		}.execute(getString(R.string.BASE_URL) + "get-user-list");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.add_user:
				Intent intent = new Intent(this, AddUserActivity.class);
				this.startActivity(intent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button1:
				Intent intent = new Intent(this, SecretSantasActivity.class);
				this.startActivity(intent);
				break;

			default:
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this, EditUserActivity.class);
		intent.putExtra("id", ((Integer) arg1.getTag()).intValue());
		this.startActivity(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.handleListView();
	}

	@Override
	public void update(Observable observable, Object data) {
		if(model.getList() != null) lv_list.setAdapter(new MainListAdapter(this, model.getList()));
	}

}
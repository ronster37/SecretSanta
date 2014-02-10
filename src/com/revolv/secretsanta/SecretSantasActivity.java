package com.revolv.secretsanta;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.revolv.secretsanta.functions.HelperFunctions;
import com.revolv.secretsanta.listadapters.SecretSantaListAdapter;
import com.revolv.secretsanta.listadapters.SecretSantaListData;
import com.revolv.secretsanta.models.ListModel;

public class SecretSantasActivity extends Activity implements Observer {

	ListModel<SecretSantaListData> model = new ListModel<SecretSantaListData>();
	ListView lv_list;
	ProgressBar progress_bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secretsantas);
		
		model.addObserver(this);
		lv_list = (ListView) this.findViewById(R.id.lv_santa_pairs);
		progress_bar = (ProgressBar) this.findViewById(R.id.progress_bar);
		handleListView();
	}

	private void handleListView() {
		new AsyncHttpRequest() {

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				try {
					JSONObject json = new JSONObject(result);
					if(json.getString("response").equals("Success")) {
						JSONArray json_array = json.getJSONArray("data");
						LinkedList<SecretSantaListData> data = new LinkedList<SecretSantaListData>();
						for (int i = 0; i < json_array.length(); i++) {
							JSONObject jsonObject = json_array.getJSONObject(i);
							data.add(new SecretSantaListData(jsonObject.getString("santa"), jsonObject.getString("giftee")));
						}
						
						model.setList(data);
					} else {
						HelperFunctions.makeToast(SecretSantasActivity.this, SecretSantasActivity.this.getString(R.string.ERROR_GET_LIST));
						SecretSantasActivity.this.finish();
					}
					
				} catch (JSONException e) {
					HelperFunctions.makeToast(SecretSantasActivity.this, SecretSantasActivity.this.getString(R.string.ERROR_GET_LIST));
					SecretSantasActivity.this.finish();
				}
			}

		}.execute(getString(R.string.BASE_URL) + "select-santas");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.no_menu_items, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.handleListView();
	}

	@Override
	public void update(Observable observable, Object data) {
		if(model.getList() != null) {
			progress_bar.setVisibility(View.GONE);
			lv_list.setVisibility(View.VISIBLE);
			lv_list.setAdapter(new SecretSantaListAdapter(this, model.getList()));
			lv_list.invalidate();
		}
	}

}
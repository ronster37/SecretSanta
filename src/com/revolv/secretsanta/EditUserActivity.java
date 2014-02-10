package com.revolv.secretsanta;

import java.util.Observable;
import java.util.Observer;

import org.json.JSONException;
import org.json.JSONObject;

import com.revolv.secretsanta.functions.HelperFunctions;
import com.revolv.secretsanta.models.UserInfoModel;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EditUserActivity extends Activity implements OnClickListener, Observer {

	private int id;
	Button b_delete;
	TextView tv_name;
	TextView tv_family;
	UserInfoModel model = new UserInfoModel();;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edituser);
		
		id = this.getIntent().getIntExtra("id", -1);
		
		model.addObserver(this);
		
		//SET EDITTEXT VIEWS
		tv_name = (TextView) this.findViewById(R.id.tv_name);
		
		//SET BUTTON VIEWS
		b_delete = (Button) this.findViewById(R.id.b_delete);
		b_delete.setOnClickListener(this);
		b_delete.setEnabled(false);
		
		//make sure id value valid
		if(id != -1) {
			this.getUserInfo("get-user-info", id);
		} else {
			HelperFunctions.makeToast(this, this.getString(R.string.ERROR_USER_INFO));
			this.finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.no_menu_items, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b_delete:
			b_delete.setEnabled(false);
			deleteUser("delete-user", id);
			break;

		default:
			break;
		}
	}
	
	private void deleteUser(String request, int id) {
		new AsyncHttpRequest() {

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				
				try {
					JSONObject json = new JSONObject(result);
					
					if(json.getString("response").equals("Success")) {
						HelperFunctions.makeToast(EditUserActivity.this, EditUserActivity.this.getString(R.string.SUCCESS_DELETE));
						EditUserActivity.this.finish();
					} else {
						HelperFunctions.makeToast(EditUserActivity.this, EditUserActivity.this.getString(R.string.ERROR_DELETE));
					}
					
				} catch (JSONException e) {
					HelperFunctions.makeToast(EditUserActivity.this, EditUserActivity.this.getString(R.string.ERROR_DELETE));
				}
				b_delete.setEnabled(true);
			}

		}.execute(getString(R.string.BASE_URL) + request + "/" + Integer.toString(id));
	}
	
	private void getUserInfo(String request, int id) {
		new AsyncHttpRequest() {

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				
				try {
					JSONObject json = new JSONObject(result);
					
					if(json.getString("response").equals("Success")) {
						JSONObject data = json.getJSONObject("data");
						model.setName(data.getString("name"));
						//model.setFamily(data.getInt("relationship"));
					} else {
						HelperFunctions.makeToast(EditUserActivity.this, EditUserActivity.this.getString(R.string.ERROR_USER_INFO));
						EditUserActivity.this.finish();
					}
					
				} catch (JSONException e) {
					HelperFunctions.makeToast(EditUserActivity.this, EditUserActivity.this.getString(R.string.ERROR_USER_INFO));
					EditUserActivity.this.finish();
				}
				
				b_delete.setEnabled(true);
			}

		}.execute(getString(R.string.BASE_URL) + request + "/" + Integer.toString(id));
	}

	@Override
	public void update(Observable observable, Object data) {
		this.tv_name.setText(model.getName());
	}

}
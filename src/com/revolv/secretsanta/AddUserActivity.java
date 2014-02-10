package com.revolv.secretsanta;

import org.json.JSONException;
import org.json.JSONObject;

import com.revolv.secretsanta.functions.HelperFunctions;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class AddUserActivity extends Activity implements OnClickListener {

	Spinner spinner;
	Button button;
	EditText et_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adduser);
		
		spinner = (Spinner) this.findViewById(R.id.spin_relationship);
		
		et_name = (EditText) this.findViewById(R.id.et_name);
		
		button = (Button) this.findViewById(R.id.b_add);
		button.setOnClickListener(this);
	}

	private void addNewUser() {
		new AsyncAddUser(et_name.getText().toString(), 0) {

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				
				try {
					JSONObject json = new JSONObject(result);
					if(json.get("response").equals("Success")) {
						HelperFunctions.makeToast(AddUserActivity.this, AddUserActivity.this.getString(R.string.SUCCESS_ADD_USER));
						AddUserActivity.this.finish();
					} else {		
						HelperFunctions.makeToast(AddUserActivity.this, AddUserActivity.this.getString(R.string.ERROR_ADD_USER));
					}
				} catch (JSONException e) {
					HelperFunctions.makeToast(AddUserActivity.this, AddUserActivity.this.getString(R.string.ERROR_ADD_USER));
				}
				
				button.setEnabled(true);
			}

		}.execute(getString(R.string.BASE_URL) + "add-user");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.no_menu_items, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b_add:
			if(et_name.getText().toString().trim().equals("")) {
				HelperFunctions.makeToast(this, "Please fill out all fields");
			} else {
				button.setEnabled(false);
				addNewUser();
			}
			break;

		default:
			break;
		}
	}

}
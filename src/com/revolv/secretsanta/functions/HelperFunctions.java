package com.revolv.secretsanta.functions;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class HelperFunctions {

	//MAKE TOAST MESSAGE
	public static void makeToast(Context context, String toast_message) {
		Toast toast = Toast.makeText(context, toast_message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
	}

}

package org.dhanishta.violetflame;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.widget.TextView;

public class MenuActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meditations);

		String value = getIntent().getExtras().getString("menu");

		TextView meditationsTextView = (TextView) findViewById(R.id.meditationsText);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"Adobe Garamond Pro.ttf");
		meditationsTextView.setTypeface(font);

		int id = getResources()
				.getIdentifier(value, "string", getPackageName());

		meditationsTextView.setText(Html.fromHtml(getString(id)));
		
		meditationsTextView.setMovementMethod(new ScrollingMovementMethod());

	}

}

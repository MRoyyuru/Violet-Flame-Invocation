package org.dhanishta.violetflame;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MeditationsActivity extends Activity implements SwipeInterface {

	public static final String PREFS_NAME = "storage";
	public static boolean firsttime = true;
	private static int MAX = 108;
	private static int MIN = 1;
	SharedPreferences settings;
	ActivitySwipeDetector swipe;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meditations);

		MeditationsActivity.this.overridePendingTransition(
				android.R.anim.slide_in_left, android.R.anim.slide_out_right);

		swipe = new ActivitySwipeDetector(this);
		View swipe_layout = (LinearLayout) findViewById(R.id.mainLinearLayout);
		swipe_layout.setOnTouchListener(swipe);

		/*
		 * Button forwardButton = (Button)
		 * findViewById(R.id.forwardNavigationBtn);
		 * forwardButton.setOnClickListener(NextPage);
		 * 
		 * Button randomButton = (Button) findViewById(R.id.randomBtn);
		 * randomButton.setOnClickListener(randomPage);
		 * 
		 * Button backButton = (Button)
		 * findViewById(R.id.backwardNavigationBtn);
		 * backButton.setOnClickListener(PreviousPage);
		 * 
		 * Button bookmarkBtn = (Button) findViewById(R.id.bookmarkBtn);
		 * bookmarkBtn.setOnClickListener(Bookmark);
		 * 
		 * Button shareBtn = (Button) findViewById(R.id.shareBtn);
		 * shareBtn.setOnClickListener(Share);
		 */

		SeekBar pbar = (SeekBar) findViewById(R.id.seekBar1);
		pbar.setOnSeekBarChangeListener(seekBarListener);
		settings = getSharedPreferences(PREFS_NAME, 0);

		int bookmarkValue = settings.getInt("bookmarked", 0);
		if (bookmarkValue <= 0) {
			bookmarkValue = 1;

		}

		Editor editor = settings.edit();
		editor.putInt("page", bookmarkValue);
		editor.commit();
		SetPageText();

	}

	public void SetPageText() {
		settings = getSharedPreferences(PREFS_NAME, 0);
		TextView meditationsTextView = (TextView) findViewById(R.id.meditationsText);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"Adobe Garamond Pro.ttf");
		meditationsTextView.setTypeface(font);

		String stringValue = "page";

		int integerValue = settings.getInt("page", 0);
		if (integerValue > MAX) {
			integerValue = MIN;
			Editor editor = settings.edit();
			editor.putInt("page", integerValue);
			editor.commit();
		}

		stringValue = stringValue + Integer.toString(integerValue);

		int id = getResources().getIdentifier(stringValue, "string",
				getPackageName());

		meditationsTextView.setText(Html.fromHtml(getString(id)));

		TextView pageNumberTextView = (TextView) findViewById(R.id.PageNumber);
		pageNumberTextView.setTypeface(font);
		pageNumberTextView.setText("Meditation : " + integerValue + " / " + MAX
				+ "");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_meditations, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

		switch (item.getItemId()) {
		case R.id.menu_author:
			intent = new Intent(this, MenuActivity.class);
			intent.putExtra("menu", "composer");
			startActivity(intent);
			break;

		case R.id.menu_book:
			intent = new Intent(this, MenuActivity.class);
			intent.putExtra("menu", "intro");
			startActivity(intent);
			break;
		case R.id.random:
			RandomFlow();
			break;
		case R.id.go_to_page:
			SpecificPageFlow();
			break;

		case R.id.share:
			Share();
			break;

		}
		return false;
	}

	public SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			Editor editor = settings.edit();
			editor.putInt("page", progress);
			editor.commit();

			SetPageText();
		}
	};

	public View.OnClickListener randomPage = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			RandomFlow();
		}

	};

	public void RandomFlow() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		Random r = new Random();
		int value = r.nextInt(MAX - MIN + 1) + MIN;
		Editor editor = settings.edit();
		editor.putInt("page", value);
		editor.commit();

		SetPageText();
	}

	public void SpecificPageFlow() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Meditations");
		alert.setMessage("Which page do you want to go?");

		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				int value = Integer.parseInt(input.getText().toString());

				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

				Editor editor = settings.edit();
				editor.putInt("page", value);
				editor.commit();

				SetPageText();

			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = alert.create();

		alertDialog.show();

	}

	public View.OnClickListener NextPage = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			ForwardFlow();
		}

	};

	public void ForwardFlow() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		int value = settings.getInt("page", 0);
		value++;

		Editor editor = settings.edit();
		editor.putInt("page", value);
		editor.commit();

		SetPageText();
	}

	public View.OnClickListener PreviousPage = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			BackwardFlow();

		}

	};

	public void BackwardFlow() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		int value = settings.getInt("page", 0);

		int prev = value - 1;

		if (prev > 0) {
			value--;

			Editor editor = settings.edit();
			editor.putInt("page", value);
			editor.commit();

			SetPageText();
		}

	}

	public View.OnClickListener Bookmark = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			int pageValue = settings.getInt("page", 0);
			Editor editor = settings.edit();
			editor.putInt("bookmarked", pageValue);
			editor.commit();

		}
	};

	public View.OnClickListener Share = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Share();
		}
	};

	public void Share() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		String stringValue = "page";

		int integerValue = settings.getInt("page", 0);

		stringValue = stringValue + Integer.toString(integerValue);

		int id = getResources().getIdentifier(stringValue, "string",
				getPackageName());

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(getString(id)));
		intent.putExtra(Intent.EXTRA_SUBJECT, "Violet Flame Invocations");
		startActivity(Intent.createChooser(intent, "Share via"));
	}

	@Override
	public void left2right(View v) {
		BackwardFlow();
	}

	@Override
	public void right2left(View v) {
		ForwardFlow();
	}

	public boolean dispatchTouchEvent(MotionEvent ev) {

		super.dispatchTouchEvent(ev);

		return true;
		// return swipe.onTouch(getCurrentFocus(), ev);

	}

}

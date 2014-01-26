package br.com.achei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import br.com.achei.view.ProdutoListaActivity;

public class MainActivity extends Activity implements Runnable {

	public Activity getActivity() {
		return MainActivity.this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Handler handler = new Handler();
		handler.postDelayed(this, 3000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void run() {
		Intent intent = new Intent(getApplicationContext(),
				ProdutoListaActivity.class);
		startActivity(intent);
		finish();
	}

}

package br.com.achei.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import br.com.achei.R;

public class CategoriaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categoria);

		setTitle("Achei! - Categoria");

		SharedPreferences sP = getSharedPreferences("as", MODE_PRIVATE);
		SharedPreferences.Editor editor = sP.edit();
		editor.remove("posicao");
		editor.commit();

		ImageButton ibAlimentacao = (ImageButton) findViewById(R.id.ibAlimentacaoId);
		ibAlimentacao.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(CategoriaActivity.this,
						"Alimentação - Iniciando ...", Toast.LENGTH_LONG)
						.show();

				startActivity(new Intent(CategoriaActivity.this,
						ProdutoActivity.class));
			}
		});

		ImageButton ibConstrucao = (ImageButton) findViewById(R.id.ibImovelId);
		ibConstrucao.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(CategoriaActivity.this,
						"Imóveis - Iniciando ...", Toast.LENGTH_LONG).show();

				startActivity(new Intent(CategoriaActivity.this,
						ProdutoActivity.class));
			}
		});

		ImageButton ibLazer = (ImageButton) findViewById(R.id.ibLazerId);
		ibLazer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(CategoriaActivity.this,
						"Diversões - Iniciando ...", Toast.LENGTH_LONG).show();

				startActivity(new Intent(CategoriaActivity.this,
						ProdutoActivity.class));
			}
		});

		ImageButton ibVeiculo = (ImageButton) findViewById(R.id.ibVeiculoId);
		ibVeiculo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(CategoriaActivity.this,
						"Veículos - Iniciando ...", Toast.LENGTH_LONG).show();

				startActivity(new Intent(CategoriaActivity.this,
						ProdutoActivity.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		MenuItem listagem = menu.add(0, 0, 0, "Principal");
		listagem.setIcon(android.R.drawable.ic_menu_save); // android.R.drawable.btn_plus

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		/*
		 * SharedPreferences sP = getSharedPreferences("as", MODE_PRIVATE);
		 * SharedPreferences.Editor editor = sP.edit();
		 * editor.remove("posicao"); editor.commit();
		 */

		switch (item.getItemId()) {
		case 0:
			startActivity(new Intent(CategoriaActivity.this,
					ProdutoListaActivity.class));

			break;
		case 1:
			// startActivity(new Intent(CategoriaActivity.this,
			// AlimentacaoActivity.class));

			break;
		case 2:
			// startActivity(new Intent(CategoriaActivity.this,
			// ConstrucaoActivity.class));

			break;
		case 3:
			// startActivity(new Intent(CategoriaActivity.this,
			// LazerActivity.class));

			break;
		case 4:
			// startActivity(new Intent(CategoriaActivity.this,
			// VeiculoActivity.class));

			break;
		}

		return super.onOptionsItemSelected(item);
	}

}
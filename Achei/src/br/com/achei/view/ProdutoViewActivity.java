package br.com.achei.view;

import java.io.ByteArrayInputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.achei.R;
import br.com.achei.model.dao.ProdutoDAO;
import br.com.achei.model.entity.ProdutoEntity;

public class ProdutoViewActivity extends Activity {

	private ProdutoEntity ent = new ProdutoEntity();

	private ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_produto_view);

		setTitle("Achei!");

		SharedPreferences sP = getSharedPreferences("as", MODE_PRIVATE);
		final int posicao = sP.getInt("posicao", -1);

		if (posicao != -1) {
			ProdutoDAO dao = new ProdutoDAO(ProdutoViewActivity.this);
			this.ent = dao.getById(posicao + 1);
			dao.close();

			((TextView) findViewById(R.id.tvTituloId)).setText(this.ent
					.getTitulo());
			((TextView) findViewById(R.id.tvDescricaoId)).setText(this.ent
					.getDescricao());
			((TextView) findViewById(R.id.tvTelefoneId)).setText(this.ent
					.getTelefone());
			((TextView) findViewById(R.id.tvEmailId)).setText(this.ent
					.getEmail());
			((TextView) findViewById(R.id.tvPrecoId)).setText(this.ent
					.getPreco().toString());
			((TextView) findViewById(R.id.tvEnderecoId)).setText(this.ent
					.getEndereco());

			((TextView) findViewById(R.id.tvCategoriaId)).setText(this.ent
					.getTipo());

			// this.iv = (ImageView) findViewById(R.id.ivFotoId);

			((ImageView) findViewById(R.id.ivFotoId))
					.setImageBitmap(BitmapFactory
							.decodeStream(new ByteArrayInputStream(this.ent
									.getFoto())));
		}
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
		switch (item.getItemId()) {
		case 0:
			startActivity(new Intent(ProdutoViewActivity.this,
					ProdutoListaActivity.class));

			break;
		}

		return super.onOptionsItemSelected(item);
	}

}
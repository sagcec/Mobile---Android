package br.com.achei.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import br.com.achei.R;
import br.com.achei.model.dao.ProdutoDAO;
import br.com.achei.model.entity.ProdutoEntity;

public class ProdutoListaActivity extends Activity {

	private ListView lvLista;

	private List auxLista;

	private ProdutoDAO dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_produtolista);

		this.lvLista = (ListView) findViewById(R.id.lista);

		this.dao = new ProdutoDAO(this);

		this.lvLista.setClickable(true);
		this.lvLista.setLongClickable(true);
		this.lvLista.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// String s = (String) lvLista.getItemAtPosition(arg2);
				// String[] s1 = s.split("-");

				escrever(arg2);

				Intent intent = new Intent(ProdutoListaActivity.this,
						ProdutoActivity.class);

				startActivity(intent);

				return false;
			}
		});

		this.lvLista.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// String s = (String) lvLista.getItemAtPosition(arg2);
				// String[] s1 = s.split("-");

				escrever(arg2);

				Intent intent = new Intent(ProdutoListaActivity.this,
						ProdutoViewActivity.class);

				startActivity(intent);
			}
		});

		carregaLista();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		MenuItem categoria = menu.add(0, 0, 0, "Categoria");
		categoria.setIcon(android.R.drawable.ic_menu_add); // android.R.drawable.btn_plus

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 0:
			startActivity(new Intent(ProdutoListaActivity.this,
					CategoriaActivity.class));

			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void carregaLista() {
		this.auxLista = new ArrayList();
		this.auxLista = this.dao.getList();
		this.dao.close();

		ArrayList<HashMap<String, String>> alist = new ArrayList<HashMap<String, String>>();

		if (!this.auxLista.isEmpty() && this.auxLista != null) {
			for (Object obj : this.auxLista) {
				ProdutoEntity ent = (ProdutoEntity) obj;

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("ID", ent.getId().toString());
				map.put("Título", ent.getTitulo());
				map.put("Preço", "R$ " + ent.getPreco().toString());
				map.put("Categoria", ent.getTipo());
				alist.add(map);
			}
		}

		SimpleAdapter sd = new SimpleAdapter(this, alist, R.layout.layout_row,
				new String[] { "ID", "Foto", "Título", "Preço", "Categoria" },
				new int[] { R.id.t1, R.id.t2, R.id.t3, R.id.t4 });
		this.lvLista.setAdapter(sd);
	}

	@Override
	public void onResume() {
		super.onResume();
		remover();
		carregaLista();
	}

	private void escrever(int arg2) {
		SharedPreferences sP = getSharedPreferences("as", MODE_PRIVATE);
		SharedPreferences.Editor editor = sP.edit();
		editor.putInt("posicao", arg2);
		editor.commit();
	}

	private void remover() {
		SharedPreferences sP = getSharedPreferences("as", MODE_PRIVATE);
		SharedPreferences.Editor editor = sP.edit();
		editor.remove("posicao");
		editor.commit();
	}

}
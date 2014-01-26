package br.com.achei.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.achei.R;
import br.com.achei.control.util.AparelhoUtil;
import br.com.achei.control.validation.AppValidation;
import br.com.achei.model.dao.ProdutoDAO;
import br.com.achei.model.entity.ProdutoEntity;

public class ProdutoActivity extends Activity {

	private static final int TIRA_FOTO = 101;

	private ProdutoEntity ent = new ProdutoEntity();

	private String arquivo;

	private ImageButton ib;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_produto);

		setTitle("Achei!");

		spinner();

		SharedPreferences sP = getSharedPreferences("as", MODE_PRIVATE);
		final int posicao = sP.getInt("posicao", -1);

		if (posicao != -1) {
			ProdutoDAO dao = new ProdutoDAO(ProdutoActivity.this);
			this.ent = dao.getById(posicao + 1);
			dao.close();

			((EditText) findViewById(R.id.etTituloId)).setText(this.ent
					.getTitulo());
			((EditText) findViewById(R.id.etDescricaoId)).setText(this.ent
					.getDescricao());
			((EditText) findViewById(R.id.etTelefoneId)).setText(this.ent
					.getTelefone());
			((EditText) findViewById(R.id.etEmailId)).setText(this.ent
					.getEmail());
			((EditText) findViewById(R.id.etPrecoId)).setText(this.ent
					.getPreco().toString());
			((EditText) findViewById(R.id.etEnderecoId)).setText(this.ent
					.getEndereco());

			Spinner sp = (Spinner) findViewById(R.id.spinner);

			int index = 0;

			for (int i = 0; i < sp.getCount(); i++) {
				if (sp.getItemAtPosition(i).equals(this.ent.getTipo())) {
					index = i;
				}
			}

			((Spinner) findViewById(R.id.spinner)).setSelection(index);
		} else {
			this.ent = new ProdutoEntity();
		}

		ImageButton b1 = (ImageButton) findViewById(R.id.ibSalvarId);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!AppValidation.validar(ProdutoActivity.this)) {
					if (posicao != -1) {
						atualizar(posicao);
					} else {
						salvar();
					}

					Toast.makeText(ProdutoActivity.this, "Salvo!",
							Toast.LENGTH_LONG).show();

					if (posicao != -1) {
						startActivity(new Intent(ProdutoActivity.this,
								ProdutoListaActivity.class));
					} else {
						startActivity(new Intent(ProdutoActivity.this,
								CategoriaActivity.class));
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void spinner() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.categoria_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	private void salvar() {
		EditText titulo = (EditText) findViewById(R.id.etTituloId);
		EditText descricao = (EditText) findViewById(R.id.etDescricaoId);
		EditText telefone = (EditText) findViewById(R.id.etTelefoneId);
		EditText email = (EditText) findViewById(R.id.etEmailId);
		EditText preco = (EditText) findViewById(R.id.etPrecoId);
		EditText endereco = (EditText) findViewById(R.id.etEnderecoId);

		Spinner sp = (Spinner) findViewById(R.id.spinner);
		String categoria = null;
		categoria = sp.getSelectedItem().toString();

		this.ent = new ProdutoEntity();

		this.ent.setDescricao(descricao.getText().toString());
		this.ent.setEmail(email.getText().toString());
		this.ent.setEndereco(endereco.getText().toString());
		this.ent.setPreco(Double.parseDouble(preco.getText().toString()));
		this.ent.setTelefone(telefone.getText().toString());
		this.ent.setTipo(categoria);
		this.ent.setTitulo(titulo.getText().toString());
		this.ent.setCaminhoImagem("/teste/");

		String s = AparelhoUtil.getDeviceId(ProdutoActivity.this);

		this.ent.setDeviceId(s);
		this.ent.setUsuario(0);

		ProdutoDAO dao = new ProdutoDAO(ProdutoActivity.this);

		dao.onAdd(this.ent);
	}

	private void atualizar(int posicao) {
		try {
			this.ent.setId(posicao + 1);

			EditText titulo = (EditText) findViewById(R.id.etTituloId);
			EditText descricao = (EditText) findViewById(R.id.etDescricaoId);
			EditText telefone = (EditText) findViewById(R.id.etTelefoneId);
			EditText email = (EditText) findViewById(R.id.etEmailId);
			EditText preco = (EditText) findViewById(R.id.etPrecoId);
			EditText endereco = (EditText) findViewById(R.id.etEnderecoId);

			Spinner sp = (Spinner) findViewById(R.id.spinner);
			String categoria = sp.getSelectedItem().toString();

			this.ent.setDescricao(descricao.getText().toString());
			this.ent.setEmail(email.getText().toString());
			this.ent.setEndereco(endereco.getText().toString());
			this.ent.setPreco(Double.parseDouble(preco.getText().toString()));
			this.ent.setTelefone(telefone.getText().toString());
			this.ent.setTipo(categoria);
			this.ent.setTitulo(titulo.getText().toString());
			this.ent.setCaminhoImagem("/teste/");

			String s = AparelhoUtil.getDeviceId(ProdutoActivity.this);

			this.ent.setDeviceId(s);
			this.ent.setUsuario(0);

			ProdutoDAO dao = new ProdutoDAO(ProdutoActivity.this);

			dao.onEdit(this.ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
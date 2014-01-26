package br.com.achei.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.achei.R;
import br.com.achei.control.util.AparelhoUtil;
import br.com.achei.control.validation.AppValidation;
import br.com.achei.model.dao.ProdutoDAO;
import br.com.achei.model.entity.ProdutoEntity;

public class ProdutoActivity extends Activity {

	private static final int TIRA_FOTO = 123;

	private ProdutoEntity ent = new ProdutoEntity();

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

		ivCamera = (ImageView) findViewById(R.id.ivFotoId);
		ivCamera.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				File itemp = null;
				bTemp = null; // propriedade Bitmap que uso para guardar
								// o
				// retorno.
				String filename = "instant_photo.jpg"; // nome temporario.
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.TITLE, filename);
				values.put(MediaStore.Images.Media.DESCRIPTION,
						"Image captured by camera");
				itemp = new File(Environment.getExternalStorageDirectory()
						+ FOLDER, filename);
				imageUri = Uri.fromFile(itemp);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
				startActivityForResult(intent, ACTIVITY_FOTO); // ACTIVITY_FOTO
																// constante.
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	Uri imageUri;
	Bitmap bTemp;
	ImageView ivCamera;

	String FOLDER = "/";
	int ACTIVITY_FOTO = 101;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ACTIVITY_FOTO) {
			if (resultCode == RESULT_OK) {
				try {
					bTemp = MediaStore.Images.Media.getBitmap(
							getContentResolver(), imageUri);

					FileOutputStream outStream = null; // outstream de saida
					String nomeFoto = "minhafoto.jpeg"; // nome da foto final
					File iPhoto = new File(
							Environment.getExternalStorageDirectory(), nomeFoto); // cria
																					// o
																					// arquivo
					outStream = new FileOutputStream(iPhoto); // associa o
																// outstream com
																// o arquivo
																// criado
					bTemp.compress(Bitmap.CompressFormat.JPEG, 90, outStream); // passa
																				// o
																				// Bitmap
																				// para
																				// o
																				// outstream,
																				// ou
																				// seja,
																				// para
																				// o
																				// arquivo
					outStream.close();
					bTemp.recycle();

					this.ent.setCaminhoImagem(nomeFoto);

					// btCamera.setImageBitmap(bTemp);

					Bitmap b = BitmapFactory.decodeFile(iPhoto.getPath());
					Bitmap b1 = Bitmap.createScaledBitmap(b, 100, 100, true);

					Bitmap original = BitmapFactory
							.decodeFile(iPhoto.getPath());
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					original.compress(Bitmap.CompressFormat.PNG, 100, out);

					this.ent.setFoto(out.toByteArray());

					ivCamera.setImageBitmap(b1);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {

				}
			}
		}
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

		this.ent.setDescricao(descricao.getText().toString());
		this.ent.setEmail(email.getText().toString());
		this.ent.setEndereco(endereco.getText().toString());
		this.ent.setPreco(Double.parseDouble(preco.getText().toString()));
		this.ent.setTelefone(telefone.getText().toString());
		this.ent.setTipo(categoria);
		this.ent.setTitulo(titulo.getText().toString());
		// this.ent.setCaminhoImagem("/teste/");

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
			// this.ent.setCaminhoImagem("/teste/");

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
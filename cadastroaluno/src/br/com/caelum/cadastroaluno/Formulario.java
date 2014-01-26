package br.com.caelum.cadastroaluno;

import java.io.File;
import java.io.FileInputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;
import br.com.caelum.model.bean.AlunoBean;
import br.com.caelum.model.dao.AlunoDAO;

public class Formulario extends Activity {

	private AlunoBean ent;

	private static final int TIRA_FOTO = 101;
	private String arquivo;
	private ImageButton ib;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);

		SharedPreferences sP = getSharedPreferences("as", MODE_PRIVATE);
		final int posicao = sP.getInt("posicao", -1);

		if (posicao != -1) {
			AlunoDAO dao = new AlunoDAO(Formulario.this);
			ent = dao.getById(posicao + 1);
			dao.close();

			((EditText) findViewById(R.id.nome)).setText(ent.getNome());
			((EditText) findViewById(R.id.end)).setText(ent.getEnd());
			((EditText) findViewById(R.id.fone)).setText(ent.getFone());
			((EditText) findViewById(R.id.site)).setText(ent.getSite());
			((RatingBar) findViewById(R.id.nota)).setRating((float) ent
					.getNota());
			((Button) findViewById(R.id.inserir)).setText("Alterar");
		} else {
			ent = new AlunoBean();

			((Button) findViewById(R.id.inserir)).setText("Inserir");
		}

		Button botao = (Button) findViewById(R.id.inserir);
		botao.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(Formulario.this, "Formulario ...",
						Toast.LENGTH_LONG).show();

				EditText etNome = (EditText) findViewById(R.id.nome);
				EditText etFone = (EditText) findViewById(R.id.fone);
				EditText etSite = (EditText) findViewById(R.id.site);
				EditText etEnd = (EditText) findViewById(R.id.end);

				RatingBar nota = (RatingBar) findViewById(R.id.nota);

				// ent = new AlunoBean();
				ent.setNome(etNome.getEditableText().toString());
				ent.setEnd(etNome.getEditableText().toString());
				ent.setFone(etFone.getEditableText().toString());
				ent.setFoto(etFone.getEditableText().toString());
				ent.setNota(nota.getRating());
				ent.setSite(etSite.getEditableText().toString());

				AlunoDAO dao = new AlunoDAO(Formulario.this);

				if (posicao != -1) {
					ent.setId(posicao + 1);
					dao.onEdit(ent);
				} else {
					dao.onAdd(ent);
				}

				dao.close();

				startActivity(new Intent(Formulario.this, CadastroAluno.class));

				finish();
			}
		});

		ib = (ImageButton) findViewById(R.id.foto);
		ib.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				arquivo = Environment.getExternalStorageState() + "/"
						+ System.currentTimeMillis() + ".jpg";
				File f = new File(arquivo);
				Log.i("arquivo", f.getName());
				Uri outputFileUri = Uri.fromFile(f);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
				startActivityForResult(intent, TIRA_FOTO);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == TIRA_FOTO) {
			if (requestCode != RESULT_CANCELED) {
				ent.setFoto(arquivo);
				carregarImagem();
			}
		}
	}

	private void carregarImagem() {
		FileInputStream fis;
		Bitmap b = null;

		try {
			fis = new FileInputStream(ent.getFoto());
			b = BitmapFactory.decodeStream(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (b != null) {
			b = Bitmap.createScaledBitmap(b, 50, 50, true);
			ib.setImageBitmap(b);
		}
	}

}
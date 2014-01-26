package br.com.caelum.cadastroaluno;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import br.com.caelum.model.bean.AlunoBean;
import br.com.caelum.model.dao.AlunoDAO;

public class GaleriaAlunos extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.galeria);

		AlunoDAO dao = new AlunoDAO(this);
		final List lista = dao.getList();
		dao.close();

		((Gallery) findViewById(R.id.galeria)).setAdapter(new BaseAdapter() {
			public View getView(int position, View convertView, ViewGroup parent) {
				ImageView iv = new ImageView(GaleriaAlunos.this);
				iv.setImageResource(R.drawable.noimage);

				AlunoBean aluno = (AlunoBean) lista.get(position);

				if (aluno.getFoto() != null && !aluno.getFoto().equals("")) {
					Bitmap bm = BitmapFactory.decodeFile(aluno.getFoto());
					bm = Bitmap.createScaledBitmap(bm, 150, 150, true);
					iv.setImageBitmap(bm);
				}

				iv.setScaleType(ImageView.ScaleType.FIT_XY);
				iv.setLayoutParams(new Gallery.LayoutParams(150, 150));

				return iv;
			}

			public long getItemId(int position) {
				return position;
			}

			public Object getItem(int position) {
				return position;
			}

			public int getCount() {
				return lista.size();
			}
		});
	}

}
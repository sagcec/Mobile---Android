package br.com.achei.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.achei.model.entity.ProdutoEntity;
import br.com.achei.model.entity.UsuarioEntity;

public class UsuarioDAO extends SQLiteOpenHelper {

	private static int VERSION = 1;

	private static String TABELA = "tb_usuarios";
	private static String[] COLS = { "id", "login", "senha" };

	public UsuarioDAO(Context context) {
		super(context, TABELA, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// onUpgrade(arg0, 0, 0);

		String sql = "CREATE TABLE " + TABELA
				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " login TEXT UNIQUE NOT NULL, senha TEXT NOT NULL);";
		arg0.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		arg0.execSQL(sql);

		this.onCreate(arg0);
	}

	public void onAdd(ProdutoEntity ent) {
		ContentValues cV = new ContentValues();
		cV.put("login", ent.getDescricao());
		cV.put("senha", ent.getEmail());

		super.getWritableDatabase().insert(TABELA, null, cV);
	}

	public void onEdit(ProdutoEntity ent) {
		ContentValues cV = new ContentValues();
		cV.put("login", ent.getDescricao());
		cV.put("senha", ent.getEmail());

		super.getWritableDatabase().update(TABELA, cV, "id=?",
				new String[] { "" + ent.getId() });
	}

	public UsuarioEntity getById(int posicao) {
		Cursor c = super.getWritableDatabase().query(TABELA, COLS, "id=?",
				new String[] { "" + posicao }, null, null, null);

		c.moveToFirst();

		UsuarioEntity ent = new UsuarioEntity();
		ent.setId(c.getInt(0));
		ent.setLogin(c.getString(1));
		ent.setSenha(c.getString(2));

		c.close();

		return ent;
	}

}
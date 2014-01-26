package br.com.achei.model.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.achei.model.entity.ProdutoEntity;

public class ProdutoDAO extends SQLiteOpenHelper {

	private static int VERSION = 1;

	private static String TABELA = "tb_produto";
	private static String[] COLS = { "id", "descricao", "email", "endereco",
			"preco", "telefone", "tipo", "titulo", "foto", "caminho_imagem",
			"device_id", "usuario_fk" };

	public ProdutoDAO(Context context) {
		super(context, TABELA, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// onUpgrade(arg0, 0, 0);

		String sql = "CREATE TABLE "
				+ TABELA
				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " descricao TEXT, email TEXT, endereco TEXT,"
				+ " preco REAL, telefone TEXT, tipo TEXT, titulo TEXT UNIQUE NOT NULL,"
				+ " foto BLOB, caminho_imagem TEXT, device_id TEXT NOT NULL, usuario_fk INTEGER);";
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
		cV.put("descricao", ent.getDescricao());
		cV.put("endereco", ent.getEndereco());
		cV.put("email", ent.getEmail());
		cV.put("preco", ent.getPreco());
		cV.put("telefone", ent.getTelefone());
		cV.put("tipo", ent.getTipo());
		cV.put("titulo", ent.getTitulo());
		// cV.put("titulo", ent.getFoto());
		cV.put("caminho_imagem", ent.getCaminhoImagem());
		cV.put("device_id", ent.getDeviceId());
		cV.put("usuario_fk", ent.getUsuario());

		super.getWritableDatabase().insert(TABELA, null, cV);
	}

	public void onEdit(ProdutoEntity ent) {
		ContentValues cV = new ContentValues();
		cV.put("descricao", ent.getDescricao());
		cV.put("endereco", ent.getEndereco());
		cV.put("email", ent.getEmail());
		cV.put("preco", ent.getPreco());
		cV.put("telefone", ent.getTelefone());
		cV.put("tipo", ent.getTipo());
		cV.put("titulo", ent.getTitulo());
		// cV.put("titulo", ent.getFoto());
		cV.put("caminho_imagem", ent.getCaminhoImagem());
		cV.put("device_id", ent.getDeviceId());
		cV.put("usuario_fk", ent.getUsuario());

		super.getWritableDatabase().update(TABELA, cV, "id=?",
				new String[] { "" + ent.getId() });
	}

	public List getList() {
		Cursor c = super.getWritableDatabase().query(TABELA, COLS, null, null,
				null, null, null);

		List lista = new ArrayList();

		while (c.moveToNext()) {
			ProdutoEntity ent = new ProdutoEntity();
			ent.setId(c.getInt(0));
			ent.setDescricao(c.getString(1));
			ent.setEmail(c.getString(2));
			ent.setEndereco(c.getString(3));
			ent.setPreco(c.getDouble(4));
			ent.setTelefone(c.getString(5));
			ent.setTipo(c.getString(6));
			ent.setTitulo(c.getString(7));
			// ent.setFoto(foto);
			ent.setCaminhoImagem(c.getString(9));
			ent.setDeviceId(c.getString(10));
			ent.setUsuario(c.getInt(11));

			lista.add(ent);
		}

		c.close();

		return lista;
	}

	public List getList(String s) {
		Cursor c = super.getWritableDatabase().query(TABELA, COLS, null, null,
				null, null, null);

		List lista = new ArrayList();

		while (c.moveToNext()) {
			ProdutoEntity ent = new ProdutoEntity();
			ent.setId(c.getInt(0));
			ent.setDescricao(c.getString(1));
			ent.setEmail(c.getString(2));
			ent.setEndereco(c.getString(3));
			ent.setPreco(c.getDouble(4));
			ent.setTelefone(c.getString(5));
			ent.setTipo(c.getString(6));
			ent.setTitulo(c.getString(7));
			// ent.setFoto(foto);
			ent.setCaminhoImagem(c.getString(9));

			if (!s.equals("")) {
				if (ent.getTitulo().startsWith(s.toLowerCase())) {
					lista.add(ent);
				}
			} else {
				lista.add(ent);
			}
		}

		c.close();

		return lista;
	}

	public ProdutoEntity getById(int posicao) {
		Cursor c = super.getWritableDatabase().query(TABELA, COLS, "id=?",
				new String[] { "" + posicao }, null, null, null);

		c.moveToFirst();

		ProdutoEntity ent = new ProdutoEntity();
		ent.setId(c.getInt(0));
		ent.setDescricao(c.getString(1));
		ent.setEmail(c.getString(2));
		ent.setEndereco(c.getString(3));
		ent.setPreco(c.getDouble(4));
		ent.setTelefone(c.getString(5));
		ent.setTipo(c.getString(6));
		ent.setTitulo(c.getString(7));
		// ent.setFoto(foto);
		ent.setCaminhoImagem(c.getString(9));

		c.close();

		return ent;
	}

}
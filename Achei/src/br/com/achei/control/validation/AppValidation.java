package br.com.achei.control.validation;

import android.app.Activity;
import android.widget.EditText;
import br.com.achei.R;

public class AppValidation {

	public static boolean validar(Activity c) {
		EditText titulo = (EditText) c.findViewById(R.id.etTituloId);
		EditText descricao = (EditText) c.findViewById(R.id.etDescricaoId);
		EditText telefone = (EditText) c.findViewById(R.id.etTelefoneId);
		EditText email = (EditText) c.findViewById(R.id.etEmailId);
		EditText preco = (EditText) c.findViewById(R.id.etPrecoId);
		EditText endereco = (EditText) c.findViewById(R.id.etEnderecoId);

		boolean vazio = false;

		if (titulo.getText().toString().trim().equals("")) {
			// titulo.setHint("Por favor, entre com o título");
			titulo.setError("Por favor, entre com o título");

			vazio = true;
		}

		if (descricao.getText().toString().trim().equals("")) {
			// descricao.setHint("Por favor, entre com o descrição");
			descricao.setError("Por favor, entre com o descrição");

			vazio = true;
		}

		if (telefone.getText().toString().trim().equals("")) {
			// telefone.setHint("Por favor, entre com o telefone");
			telefone.setError("Por favor, entre com o telefone");

			vazio = true;
		}

		if (email.getText().toString().trim().equals("")) {
			// email.setHint("Por favor, entre com o e-mail");
			email.setError("Por favor, entre com o e-mail");

			vazio = true;
		}

		if (preco.getText().toString().trim().equals("")) {
			// preco.setHint("Por favor, entre com o preço");
			preco.setError("Por favor, entre com o preço");

			vazio = true;
		}

		if (endereco.getText().toString().trim().equals("")) {
			// endereco.setHint("Por favor, entre com o endereço");
			endereco.setError("Por favor, entre com o endereço");

			vazio = true;
		}

		return vazio;
	}

}
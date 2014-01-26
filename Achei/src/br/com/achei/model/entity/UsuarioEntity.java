package br.com.achei.model.entity;

import java.io.Serializable;

public class UsuarioEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String login;

	private String senha;

	public UsuarioEntity() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	/*
	 * HASHCODE E EQUALS
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.id != null ? this.id.hashCode() : 0);

		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof UsuarioEntity)) {
			return false;
		}

		UsuarioEntity ent = (UsuarioEntity) object;

		if ((this.id == null && ent.id != null)
				|| (this.id != null && !this.id.equals(ent.id))) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[id=" + this.id + "]";
	}

}
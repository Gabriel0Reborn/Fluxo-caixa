package model;

import java.util.HashMap;
import java.util.Map;

public class GerenciadorUsuarios {
    private Map<String, String> usuarios = new HashMap<>();
    private String usuarioLogado = null;

    public GerenciadorUsuarios() {
        // Usuário padrão
        usuarios.put("admin", "123");
    }

    public boolean registrar(String nome, String senha) {
        if (usuarios.containsKey(nome)) {
            return false; // já existe
        }
        usuarios.put(nome, senha);
        return true;
    }

    public boolean login(String nome, String senha) {
        if (usuarios.containsKey(nome) && usuarios.get(nome).equals(senha)) {
            usuarioLogado = nome;
            return true;
        }
        return false;
    }

    public void logout() {
        usuarioLogado = null;
    }

    public boolean estaLogado() {
        return usuarioLogado != null;
    }

    public String getUsuarioLogado() {
        return usuarioLogado;
    }
}

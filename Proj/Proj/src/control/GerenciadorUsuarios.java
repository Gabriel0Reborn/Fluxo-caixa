package model;

import java.util.HashMap;
import java.util.Map;

public class GerenciadorUsuarios {
    private Map<String, String> usuarios = new HashMap<>();
    private String usuarioLogado = null;

    public GerenciadorUsuarios() {
        // Exemplo de usuário padrão
        usuarios.put("admin", "123");
    }

    public boolean login(String nome, String senha) {
        if (usuarios.containsKey(nome) && usuarios.get(nome).equals(senha)) {
            usuarioLogado = nome;
            return true;
        }
        return false;
    }

    public boolean registrar(String nome, String senha) {
        if (usuarios.containsKey(nome)) {
            return false; // Já existe
        }
        usuarios.put(nome, senha);
        return true;
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

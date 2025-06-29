package model;

import java.util.HashMap;
import java.util.Map;

public class GerenciadorUsuarios {
    // Armazenando usuários com chave sendo o nome de usuário
    private Map<String, Usuario> usuarios;

    public GerenciadorUsuarios() {
        usuarios = new HashMap<>();
    }

    // Autenticação de login
    public boolean autenticar(String nome, String senha) {
        Usuario usuario = usuarios.get(nome);
        return usuario != null && usuario.getSenha().equals(senha);
    }

    // Registro de novo usuário
    public boolean registrarUsuario(String nome, String senha, String email) {
        if (usuarios.containsKey(nome)) {
            return false; // Já existe um usuário com esse nome
        }
        usuarios.put(nome, new Usuario(nome, senha, email));
        return true;
    }

    // Obter o e-mail do usuário pelo nome
    public String getEmailUsuario(String nome) {
        Usuario usuario = usuarios.get(nome);
        return usuario != null ? usuario.getEmail() : null;
    }

    // ✅ Método para atualizar a senha de um usuário
    public boolean atualizarSenha(String nome, String novaSenha) {
        Usuario usuario = usuarios.get(nome);
        if (usuario != null) {
            usuario.setSenha(novaSenha); // Atualiza a senha
            return true;
        }
        return false; // Retorna false se o usuário não existir
    }

    // Verifica se o nome e e-mail conferem
    public boolean validarUsuario(String nome, String email) {
        Usuario usuario = usuarios.get(nome);
        if (usuario != null && usuario.getEmail().equals(email)) {
            return true; // Retorna true se o nome e o e-mail corresponderem
        }
        return false; // Caso contrário, retorna false
    }
}

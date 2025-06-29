package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Usuario {
    private String nome;
    private String senhaHash;  // Alterado para senhaHash (hash da senha)

    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senhaHash = hashSenha(senha);  // Armazena a senha como hash
    }

    public String getNome() {
        return nome;
    }

    // Método para autenticar o usuário comparando os hashes
    public boolean autenticar(String senhaDigitada) {
        return this.senhaHash.equals(hashSenha(senhaDigitada)); // Compara o hash da senha digitada com o hash armazenado
    }

    // Método que gera um hash da senha utilizando SHA-256 (ou outro algoritmo)
    private String hashSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");  // Usando SHA-256 para gerar o hash
            byte[] hashBytes = digest.digest(senha.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString(); // Retorna a senha em formato hash
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para alterar a senha, gerando um novo hash
    public void setSenha(String novaSenha) {
        this.senhaHash = hashSenha(novaSenha); // Atualiza a senha com o novo hash
    }
}

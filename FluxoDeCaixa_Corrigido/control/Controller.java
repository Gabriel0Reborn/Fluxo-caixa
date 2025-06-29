package control;

import model.*;

public class Controller {
    private GerenciadorUsuarios gerenciadorUsuarios;
    private FluxoCaixa fluxoCaixa;
    private Historico historico;

    public Controller() {
        gerenciadorUsuarios = new GerenciadorUsuarios();
        fluxoCaixa = new FluxoCaixa();
        historico = new Historico();
    }

    public boolean login(String nome, String senha) {
        try {
            return gerenciadorUsuarios.autenticar(nome, senha);
        } catch (Exception e) {
            System.out.println("Erro ao tentar fazer login: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarUsuario(String nome, String senha, String email) {
        try {
            // Verifica se o nome já está registrado
            if (gerenciadorUsuarios.getEmailUsuario(nome) != null) {
                System.out.println("Usuário já registrado.");
                return false;
            }
            return gerenciadorUsuarios.registrarUsuario(nome, senha, email);
        } catch (Exception e) {
            System.out.println("Erro ao registrar o usuário: " + e.getMessage());
            return false;
        }
    }

    // Recuperação de senha com nome e e-mail
    public boolean recuperarSenha(String nome, String email) {
        if (nome == null || nome.isEmpty() || email == null || email.isEmpty()) {
            return false;
        }

        boolean usuarioExiste = gerenciadorUsuarios.validarUsuario(nome, email);
        if (!usuarioExiste) {
            System.out.println("Nome ou e-mail não encontrados.");
            return false;
        }

        String novaSenha = gerarNovaSenha();
        boolean atualizado = gerenciadorUsuarios.atualizarSenha(nome, novaSenha);

        if (atualizado) {
            System.out.println("Senha recuperada com sucesso! Nova senha: " + novaSenha);
            return true;
        } else {
            System.out.println("Erro ao recuperar a senha.");
            return false;
        }
    }

    private String gerarNovaSenha() {
        return "senhaTemporaria123"; // Pode ser alterado para geração dinâmica
    }

    public void adicionarTransacao(Transacao t) {
        fluxoCaixa.adicionarTransacao(t);
        historico.adicionarTransacao(t);
    }

    public void removerTransacao(Transacao t) {
        fluxoCaixa.removerTransacao(t);
        historico.removerTransacao(t);
    }

    public double getSaldoAtual() {
        return fluxoCaixa.getSaldoAtual();
    }

    public java.util.List<Transacao> getTransacoes() {
        return fluxoCaixa.listarTransacoes();
    }

    public java.util.List<Transacao> getHistoricoTransacoes() {
        return historico.getTransacoes();
    }

    public void editarTransacao(Transacao antiga, Transacao nova) {
        removerTransacao(antiga);
        adicionarTransacao(nova);
    }

    public java.util.List<Transacao> clonarTransacoes() {
        return new java.util.ArrayList<>(fluxoCaixa.listarTransacoes());
    }

    public void setTransacoes(java.util.List<Transacao> transacoes) {
        fluxoCaixa.setTransacoes(transacoes);
    }

    // ✅ Método novo para redefinir senha manualmente
    public boolean atualizarSenha(String nome, String novaSenha) {
        return gerenciadorUsuarios.atualizarSenha(nome, novaSenha);
    }
}

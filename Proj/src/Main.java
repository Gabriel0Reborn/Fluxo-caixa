import control.Controller;
import model.FluxoCaixa;
import model.GerenciadorUsuarios;
import model.Historico;
import view.Login;

public class Main {
    public static void main(String[] args) {
        // Cria os modelos e o controller
        FluxoCaixa caixa = new FluxoCaixa();
        Historico historico = new Historico();
        GerenciadorUsuarios usuarios = new GerenciadorUsuarios();
        Controller controller = new Controller(caixa, historico, usuarios);

        // Inicia pela tela de login (que também permite registrar)
        new Login(controller);
    }
}

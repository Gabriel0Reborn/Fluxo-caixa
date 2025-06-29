package view;

import control.Controller;

public class Main {
    public static void main(String[] args) {
        // Criação do controller
        Controller controller = new Controller();

        // Inicia a tela de login e passa o controller para o Login
        new Login(controller);
    }
}

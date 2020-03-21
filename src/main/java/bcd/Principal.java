package bcd;

import exemplo01.ExemploMuitoSimples;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    private final String[] EXEMPLOS = {
            "\n..:: Pequenos exemplos com Java, SQLite e MySQL ::..\n",
            "1 - Exemplo 01",
            "6 - Sair do programa"
    };
    private final String[] MENU_EX1 = {"\n...:: Exemplo com SQLite ::...\n",
            "1 - Cadastrar pessoa",
            "2 - Alterar dados de uma pessoa",
            "3 - Excluir uma pessoa",
            "4 - Listar dados de uma pessoa",
            "5 - Listar todas pessoas",
            "6 - Sair do programa"};
    private Scanner teclado;

    public Principal() {
        this.teclado = new Scanner(System.in);
    }

    /**
     * Método principal
     *
     * @param args
     * @throws Exception Exceções não serão tratadas, apenas encaminhadas para o sistema operacional
     */
    public static void main(String[] args) throws Exception {
        Principal p = new Principal();
        int opcao = -1;
        do {
            opcao = p.menu(p.EXEMPLOS);
            switch (opcao) {
                case 1:
                    p.exemplo01();
                    break;
            }
        } while (opcao != 6);
    }

    /**
     * Método auxilar para gerar o menu com as opções para o usuário
     *
     * @param menuComOpcoes vetor de String com o menu a ser desenhado
     * @return int com a opção fornecida pelo usuário
     */
    private int menu(String[] menuComOpcoes) {
        int opcao = -1;
        if (menuComOpcoes != null) {
            for (String linha : menuComOpcoes) {
                System.out.println(linha);
            }
            System.out.print("Entre com uma opção: ");
            try {
                opcao = teclado.nextInt();
            } catch (Exception e) {
                System.err.println("Erro. Informe um número inteiro.");
                opcao = -1;
                teclado.nextLine();// consumindo caracter extra NL/CR
            }

        }
        return opcao;
    }

    /**
     * Executará a classe do pacote exemplo01
     *
     * @throws Exception
     */
    public void exemplo01() throws SQLException {
        int opcao;
        ExemploMuitoSimples app = new ExemploMuitoSimples();
        try {
            do {
                opcao = this.menu(this.MENU_EX1);

                switch (opcao) {
                    case 1:
                        try {
                            teclado.nextLine();// consumindo caracter extra NL/CR
                            System.out.print("Entre com o nome: ");
                            String nome = teclado.nextLine();
                            System.out.print("Entre com o email: ");
                            String email = teclado.nextLine();
                            System.out.print("Entre com o peso: ");
                            double peso = teclado.nextDouble();
                            System.out.print("Entre com a altura: ");
                            int altura = teclado.nextInt();
                            int resultado = app.cadastrarPessoa(nome, peso, altura, email);
                            if (resultado > 0) {
                                System.out.println("\nPessoa cadastrada com sucesso.\n");
                            } else {
                                System.out.println("\nHouve algum problema e não foi possível cadastrar");
                            }
                        } catch (Exception e) {
                            System.err.println("\nErro com os dados fornecidos. Tente novamente.\n");
                        }
                        break;
                    case 2:
                        System.out.println(app.listarRegistros());
                        System.out.print("Informe o ID da pessoa que irá alterar os dados: ");
                        int idPessoa = teclado.nextInt();
                        teclado.nextLine();// consumindo caracter extra NL/CR
                        System.out.print("Entre com o nome: ");
                        String nome = teclado.nextLine();
                        System.out.print("Entre com o email: ");
                        String email = teclado.nextLine();
                        System.out.print("Entre com o peso: ");
                        double peso = teclado.nextDouble();
                        System.out.print("Entre com a altura: ");
                        int altura = teclado.nextInt();
                        int resultado = app.alterarDadosPessoa(idPessoa, nome, peso, altura, email);
                        if (resultado > 0) {
                            System.out.println("\nDados alterados com sucesso.\n");
                        } else {
                            System.out.println("\nHouve algum problema e não foi possível alterar");
                        }
                        break;
                    case 3:
                        System.out.println(app.listarRegistros());
                        System.out.print("Informe o ID da pessoa que deseja excluir: ");
                        idPessoa = teclado.nextInt();
                        resultado = app.excluirPessoa(idPessoa);
                        if (resultado > 0) {
                            System.out.println("\nPessoa excluída com sucesso\n");
                        } else {
                            System.out.println("\nHouve algum problema e não foi possível excluir");
                        }
                        break;
                    case 4:
                        System.out.print("Entre com o email da pessoa que deseja procurar: ");
                        String e = this.teclado.next();
                        System.out.println(app.listarDadosPessoa(e));
                        break;
                    case 5:
                        System.out.println(app.listarRegistros());
                        break;
                }
            } while (opcao != 6);
        } catch (InputMismatchException e) {
            System.err.println("ERRO: Dados fornecidos estão em um formato diferente do esperado.");
        }
    }
}

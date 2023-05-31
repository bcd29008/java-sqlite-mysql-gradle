package bcd;

import exemplo01.ExemploMuitoSimples;
import exemplo02.PadroesDeProjeto;
import exemplo03.UsandoPreparedStmt;
import exemplo04.UsandoDAO;
import exemplo04.entities.Pessoa;
import exemplo05mysql.ExemploMySQL;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    private final String[] EXEMPLOS = {
            "\n..:: Pequenos exemplos com Java, SQLite e MySQL ::..\n",
            "1 - Exemplo 01",
            "2 - Exemplo 02 - uso de padrões de projeto",
            "3 - Exemplo 03 - uso de PreparedStatement",
            "4 - Exemplo 04 - uso do Data Access Object (DAO)",
            "5 - Exemplo 05 - MySQL",
            "6 - Sair do programa"
    };
    private final String[] MENU_EX1 = { "\n...:: Exemplo com SQLite ::...\n",
            "1 - Cadastrar pessoa",
            "2 - Alterar dados de uma pessoa",
            "3 - Excluir uma pessoa",
            "4 - Listar dados de uma pessoa",
            "5 - Listar todas pessoas",
            "6 - Voltar ao menu anterior"
    };
    private final String[] MENU_EX3 = { "\n...:: Exemplo com PreparedStatement ::...\n",
            "1 - Listar todas pessoas",
            "2 - Listar dados de uma pessoa",
            "3 - Atualizar email de uma pessoa",
            "4 - Voltar ao menu anterior"
    };
    private final String[] MENU_EX4 = { "\n...:: Exemplo com Data Access Object (DAO) ::...\n",
            "1 - Cadastrar pessoa",
            "2 - Listar todas pessoas",
            "3 - Voltar ao menu anterior"
    };

    private Scanner teclado;

    public Principal() {
        this.teclado = new Scanner(System.in);
    }

    /**
     * Método principal
     *
     * @param args
     * @throws Exception Exceções não serão tratadas, apenas encaminhadas para o
     *                   sistema operacional
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
                case 2:
                    p.exemplo02();
                    break;
                case 3:
                    p.exemplo03();
                    break;
                case 4:
                    p.exemplo04();
                    break;
                case 5:
                    p.exemplo05();
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

            try {
                System.out.print("Entre com uma opção: ");
                opcao = teclado.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Erro. Informe um número inteiro.");
                opcao = -1;
                teclado.nextLine();// consumindo caracter extra NL/CR
            }

        }
        return opcao;
    }

    /**
     * Executará métodos da classe no pacote exemplo01
     * 
     * @throws Exception
     */
    private void exemplo01() throws Exception {
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

    /**
     * Executará métodos da classe no pacote exemplo02
     * 
     * @throws SQLException
     */
    private void exemplo02() throws SQLException {
        PadroesDeProjeto app = new PadroesDeProjeto();
        System.out.println(app.listarPessoas());
    }

    /**
     * Executará métodos da classe no pacote exemplo03
     * 
     * @throws SQLException
     */
    private void exemplo03() throws SQLException {
        int opcao;
        UsandoPreparedStmt app = new UsandoPreparedStmt();
        try {
            do {
                opcao = this.menu(this.MENU_EX3);

                switch (opcao) {
                    case 1:
                        System.out.println(app.listarPessoas());
                        break;
                    case 2:
                        System.out.print("Informe o ID da pessoa: ");
                        int idPessoa = teclado.nextInt();
                        System.out.println(app.listarDadosDeUmaPessoa(idPessoa));
                        break;
                    case 3:
                        System.out.println(app.listarPessoas());
                        System.out.print("Informe o ID da pessoa que irá alterar o email: ");
                        idPessoa = teclado.nextInt();
                        System.out.print("Entre com o email: ");
                        String email = this.teclado.next();
                        if (app.atualizaEmail(idPessoa, email) > 0) {
                            System.out.println("Email atualizado com sucesso");
                        } else {
                            System.out.println("Não foi possível atualizar o email.");
                        }
                        break;
                }
            } while (opcao != 4);
        } catch (InputMismatchException e) {
            System.err.println("ERRO: Dados fornecidos estão em um formato diferente do esperado.");
        }
    }

    /**
     * Executará métodos da classe no pacote exemplo04
     * 
     * @throws SQLException
     */
    private void exemplo04() throws SQLException {
        int opcao;
        UsandoDAO app = new UsandoDAO();
        try {
            do {
                opcao = this.menu(this.MENU_EX4);

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

                            // Criando uma Pessoa
                            Pessoa p = new Pessoa(nome, peso, altura, email);

                            boolean resultado = app.cadastrarPessoa(p);

                            if (resultado) {
                                System.out.println("\nPessoa cadastrada com sucesso.\n");
                            } else {
                                System.out.println("\nHouve algum problema e não foi possível cadastrar");
                            }
                        } catch (Exception e) {
                            System.err.println("\nErro com os dados fornecidos. Tente novamente.\n");
                        }

                        break;
                    case 2:
                        System.out.println(app.listarPessoas());
                        break;
                }
            } while (opcao != 3);
        } catch (InputMismatchException e) {
            System.err.println("ERRO: Dados fornecidos estão em um formato diferente do esperado.");
        }
    }

    /**
     * Executará métodos da classe no pacote exemplo05mysql
     * 
     * @throws IOException
     */
    private void exemplo05() throws IOException {

        ExemploMySQL exemploMySQL = new ExemploMySQL();

        System.out.println(exemploMySQL.listarDadosDeTodosDepartamentos());

    }

}

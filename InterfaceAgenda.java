import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class InterfaceAgenda {
    public JFrame jfAgenda;
    public JButton btoInserir, btoExcluir, btoAlterar, btoConsultar;
    public JLabel lblNome, lblCelular;
    public JTextField tfNome, tfCelular;
    public JPanel pnBotao, pnDados;

    public void desenhaJanela() {
        btoInserir = new JButton("Inserir");
        btoConsultar = new JButton("Consultar");
        btoAlterar = new JButton("Alterar");
        btoExcluir = new JButton("Excluir");

        lblNome = new JLabel("Nome:");
        lblCelular = new JLabel("Celular:");

        tfNome = new JTextField(20);
        tfCelular = new JTextField(20);

        jfAgenda = new JFrame("Agenda");
        jfAgenda.setLayout(new BorderLayout());

        pnBotao = new JPanel();
        pnBotao.setLayout(new GridLayout(4, 1));

        pnDados = new JPanel();
        pnDados.setLayout(new FlowLayout());

        pnDados.add(lblNome);
        pnDados.add(tfNome);
        pnDados.add(lblCelular);
        pnDados.add(tfCelular);

        pnBotao.add(btoInserir);
        pnBotao.add(btoConsultar);
        pnBotao.add(btoAlterar);
        pnBotao.add(btoExcluir);

        jfAgenda.add(pnDados, BorderLayout.CENTER);
        jfAgenda.add(pnBotao, BorderLayout.SOUTH);

        jfAgenda.setSize(300, 220);
        jfAgenda.setLocation(625, 300);
        jfAgenda.setVisible(true);

        Listener listener = new Listener();

        btoInserir.addActionListener(listener);
        btoConsultar.addActionListener(listener);
        btoAlterar.addActionListener(listener);
        btoExcluir.addActionListener(listener);
    }

    class Listener implements ActionListener {

        String msg;
        String servidor_banco = "jdbc:mysql://localhost:3306/bdteste";
        String usuario_banco = "root";
        String senha_banco = "";
        ConnectionMYSQL bd = new ConnectionMYSQL();

        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btoInserir) {
                String nome = tfNome.getText();
                String celular = tfCelular.getText();

                msg = bd.conectaBanco(
                        this.servidor_banco,
                        this.usuario_banco,
                        this.senha_banco);

                System.out.println("Conexão banco: " + msg);
                System.out.println("Nome a ser inserido: " + nome);
                System.out.println("Celular a ser inserido: " + celular);

                msg = bd.insere("insert into tbusuario (nome,celular)" + " values ('" + nome + "','" + celular + "')");
                System.out.println("Inserção de dados: " + msg);
            }

            if (e.getSource() == btoConsultar) {
                msg = bd.conectaBanco(
                        this.servidor_banco,
                        this.usuario_banco,
                        this.senha_banco);

                System.out.println("Conexão banco: " + msg);
                bd.consulta("select * from tbusuario");
            }

            if (e.getSource() == btoAlterar) {
                String nome = JOptionPane.showInputDialog("Digite o nome que deseja alterar");
                String celular = JOptionPane.showInputDialog("Digite número de ''" + nome + "'', que será alterado");
                String novoNome = JOptionPane.showInputDialog("Nome antigo: ''" + nome + "'' \nDigite o novo nome");
                String novoNumero = JOptionPane.showInputDialog("Número antigo de ''" + nome + "'': ''" + celular + "'' \nDigite o novo número do celular");

                ConnectionMYSQL bd = new ConnectionMYSQL();
                msg = bd.conectaBanco(
                        this.servidor_banco,
                        this.usuario_banco,
                        this.senha_banco);

                msg = bd.altera("UPDATE bdteste.tbusuario SET nome = '" + novoNome + "', celular = '" + novoNumero
                        + "' WHERE tbusuario.nome = '" + nome + "' AND tbusuario.celular = '" + celular
                        + "' LIMIT 1 ;");
                System.out.println("Alteração de dados: " + msg);

            }

            if (e.getSource() == btoExcluir) {

                String excluiNome = JOptionPane.showInputDialog("Digite o nome de quem deseja excluir: ");
                String excluiCelular = JOptionPane.showInputDialog(
                        "Para confirmar a exclusão, digite o número de ''" + excluiNome + "'' que será excluído");

                msg = bd.conectaBanco(
                        this.servidor_banco,
                        this.usuario_banco,
                        this.senha_banco);

                msg = bd.exclui(
                        "delete from tbusuario where nome='" + excluiNome + "' AND celular='" + excluiCelular + "';");
                System.out.println("Conexão banco: " + msg);
            }

        }

    }

}

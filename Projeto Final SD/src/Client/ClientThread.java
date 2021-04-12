package Client;

import Server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class ClientThread extends Thread {
    private Socket socket;
    private DatagramPacket packet;
    private InetAddress group;
    private static ClientManagement cm = new ClientManagement();
    private static int ID;
    private static int port;
    private static String name;
    private Notification notification;

    public ClientThread(Notification notification) throws IOException {
        super("ClientThread");
        this.notification = notification;
    }

    public void run() {

//Pagina que serve para o utilizador escolher se quer fazer Login na aplicação ou se quer Registar-se na mesma;

        JFrame jframe = new JFrame();
        JButton button = new JButton("Login");
        JButton button1 = new JButton("Registo");

        //Setting frame: Visible and Size
        jframe.setSize(500,400);
        jframe.setLayout(null);
        jframe.setTitle("StayAwayCovid");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
        jframe.setLocation(500,300);



        button.setBounds(190,100,120,50);
        button1.setBounds(190,160,120,50);

        jframe.add(button);
        jframe.add(button1);


        //Ação correspondente ao botao Login, criando assim a sua janela com os seus componentes (Botoes, Textos, Caixas de Texto, etc);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jframe.setVisible(false);

                JFrame _frame = new JFrame();
                _frame.setSize(500,400);
                _frame.setLayout(null);
                _frame.setTitle("StayAwayCovid");
                _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                _frame.setVisible(true);
                _frame.setLocation(500,300);


                JLabel label = new JLabel("Digite o seu ID: ");
                JTextField text = new JTextField(10);
                text.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        char c = e.getKeyChar();
                        if(Character.isLetter(c)){
                            text.setEditable(false);
                        }else{
                            text.setEditable(true);
                    }
                    }
                });
                JButton b = new JButton("OK!");

                label.setBounds(200,100,100,40);
                text.setBounds(200,130,100,20);
                b.setBounds(200,180,100,40);

                b.setVisible(true);


                _frame.add(b);
                _frame.add(text);
                _frame.add(label);

                //Após fazer o Login, o mesmo vai redirecionar para a pagina do Menu, que está representada no código aseguir;
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (text.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Preencher todos os campos", "Login Inválido", JOptionPane.WARNING_MESSAGE);
                        } else {

                            try {
                                String[] userData = cm.clientLogin(socket, Integer.parseInt(text.getText()));

                                if(userData == null) {
                                    JOptionPane.showMessageDialog(null, "ID Inválido", "Login Inválido", JOptionPane.WARNING_MESSAGE);
                                } else {
                                    ID = Integer.parseInt(userData[0]);
                                    port = Integer.parseInt(userData[2]);
                                    name = userData[1];

                                    jframe.setVisible(false);
                                    _frame.setVisible(false);

                                    notification.setPort(port);

                                    JFrame mFrame = new JFrame();
                                    mFrame.setSize(500, 400);
                                    mFrame.setLayout(null);
                                    mFrame.setTitle("StayAwayCovid");
                                    mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    mFrame.setVisible(true);
                                    mFrame.setLocation(500, 300);

                                    JButton test = new JButton("Teste Covid-19");
                                    JButton not = new JButton("Notificações");
                                    JButton cont = new JButton("Adicionar Pessoas");
                                    JButton casos = new JButton("Ver Número de Casos");

                                    JLabel nLabel = new JLabel(name);
                                    nLabel.setFont(new Font("Arial", Font.BOLD, 24));
                                    nLabel.setHorizontalAlignment(JLabel.CENTER);

                                    nLabel.setBounds(0, 10,500,50);
                                    test.setBounds(160, 70, 180, 50);
                                    cont.setBounds(160, 127, 180, 50);
                                    not.setBounds(160, 182, 180, 50);
                                    casos.setBounds(160, 240, 180, 50);


                                    mFrame.add(nLabel);
                                    mFrame.add(test);
                                    mFrame.add(cont);
                                    mFrame.add(not);
                                    mFrame.add(casos);

                                    //Corresponde ao primeiro botão do Menu, Fazer Teste ao Covid-19;
                                    test.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            jframe.setVisible(false);
                                            _frame.setVisible(false);
                                            mFrame.setVisible(false);

                                            JFrame tFrame = new JFrame();
                                            tFrame.setSize(500, 400);
                                            tFrame.setLayout(null);
                                            tFrame.setTitle("StayAwayCovid");
                                            tFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                            tFrame.setVisible(true);
                                            tFrame.setLocation(500, 300);

                                            JButton testB = new JButton("Fazer Teste Covid-19");

                                            testB.setBounds(150, 150, 180, 50);

                                            tFrame.add(testB);

                                            //Ação que acontece apos clicar no botao de fazer o teste de covid;

                                            testB.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {

                                                    try {
                                                        JFrame tFrame1 = new JFrame();
                                                        JButton buttonOK = new JButton("Ok!");
                                                        buttonOK.setBounds(150, 180, 180, 50);
                                                        buttonOK.addActionListener(new ActionListener() {
                                                            @Override
                                                            public void actionPerformed(ActionEvent e) {
                                                                tFrame1.setVisible(false);
                                                                mFrame.setVisible(true);

                                                            }
                                                        });

                                                        tFrame1.add(buttonOK);

                                                        if(cm.covidTest(socket, ID)) {

                                                            jframe.setVisible(false);
                                                            _frame.setVisible(false);
                                                            mFrame.setVisible(false);
                                                            tFrame.setVisible(false);
                                                            tFrame1.setSize(500, 400);
                                                            tFrame1.setLayout(null);
                                                            tFrame1.setTitle("StayAwayCovid");
                                                            tFrame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                            tFrame1.setVisible(true);
                                                            tFrame1.setLocation(500, 300);

                                                            JLabel labeL5 = new JLabel("Resultado do teste:");
                                                            JLabel labeL6 = new JLabel("POSITIVO");

                                                            labeL5.setFont(new Font("Arial",Font.PLAIN, 20));
                                                            labeL6.setFont(new Font("Arial",Font.BOLD, 42));
                                                            labeL6.setForeground(Color.GREEN);

                                                            labeL5.setBounds(150, 50, 180, 50);
                                                            labeL6.setBounds(140, 110, 250, 50);

                                                            tFrame1.add(labeL5);
                                                            tFrame1.add(labeL6);

                                                        } else {

                                                            jframe.setVisible(false);
                                                            _frame.setVisible(false);
                                                            mFrame.setVisible(false);
                                                            tFrame.setVisible(false);

                                                            tFrame1.setSize(500, 400);
                                                            tFrame1.setLayout(null);
                                                            tFrame1.setTitle("StayAwayCovid");
                                                            tFrame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                            tFrame1.setVisible(true);
                                                            tFrame1.setLocation(500, 300);

                                                            JLabel labeL5 = new JLabel("Resultado do teste:");
                                                            JLabel labeL6 = new JLabel("NEGATIVO");

                                                            labeL5.setFont(new Font("Arial",Font.PLAIN, 20));
                                                            labeL6.setFont(new Font("Arial",Font.BOLD, 42));
                                                            labeL6.setForeground(Color.RED);

                                                            labeL5.setBounds(150, 50, 180, 50);
                                                            labeL6.setBounds(135, 110, 250, 50);

                                                            tFrame1.add(labeL5);
                                                            tFrame1.add(labeL6);

                                                        }

                                                        tFrame1.setVisible(true);

                                                    } catch (IOException ex) {
                                                        ex.printStackTrace();
                                                    }
                                                }
                                            });
                                        }
                                    });

                                    //Botao que cria a janela das notificações, onde será apresentada a notificação de contacto com outra pessoa, ou não, caso nao tenha estado com ninguem
                                    not.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            jframe.setVisible(false);
                                            _frame.setVisible(false);
                                            mFrame.setVisible(false);

                                            JFrame nFrame = new JFrame();
                                            nFrame.setSize(500, 400);
                                            nFrame.setLayout(null);
                                            nFrame.setTitle("StayAwayCovid");
                                            nFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                            nFrame.setVisible(true);
                                            nFrame.setLocation(500, 300);

                                            try {
                                                if(cm.notificacao(socket,ID)){
                                                    JButton notButton = new JButton("Abrir Notificação");
                                                    notButton.setBounds(160,160,180,50);
                                                    nFrame.add(notButton);

                                                    notButton.addActionListener(new ActionListener() {
                                                        @Override
                                                        public void actionPerformed(ActionEvent e) {
                                                            nFrame.setVisible(false);
                                                            JFrame notFrame = new JFrame();
                                                            notFrame.setSize(500, 400);
                                                            notFrame.setLayout(null);
                                                            notFrame.setTitle("StayAwayCovid");
                                                            notFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                            notFrame.setVisible(true);
                                                            notFrame.setLocation(500, 300);

                                                            JLabel notLabel2 = new JLabel("Você esteve em contacto com uma pessoa com Covid-19");
                                                            JLabel notLabel3 = new JLabel("Seja Prudente!");
                                                            JButton okButton = new JButton("Ok");

                                                            notLabel2.setFont(new Font("Arial", Font.PLAIN, 18));
                                                            notLabel3.setFont(new Font("Arial", Font.BOLD, 20));

                                                            okButton.addActionListener(new ActionListener() {
                                                                @Override
                                                                public void actionPerformed(ActionEvent e) {
                                                                    try {
                                                                        cm.notificado(socket, ID);
                                                                        notFrame.setVisible(false);
                                                                        mFrame.setVisible(true);
                                                                    } catch (IOException ex) {
                                                                        ex.printStackTrace();
                                                                    }
                                                                }
                                                            });

                                                            notLabel2.setBounds(0,50,500,40);
                                                            notLabel3.setBounds(0,90,500,40);
                                                            okButton.setBounds(150,140,210,40);

                                                            notLabel2.setHorizontalAlignment(JLabel.CENTER);
                                                            notLabel3.setHorizontalAlignment(JLabel.CENTER);

                                                            notFrame.add(notLabel2);
                                                            notFrame.add(notLabel3);
                                                            notFrame.add(okButton);
                                                        }
                                                    });
                                                } else {
                                                    JLabel notLabel = new JLabel("Não existem notificações neste momento!");
                                                    notLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                                                    JButton okButton = new JButton("Ok");

                                                    notLabel.setBounds(0,50,500,40);
                                                    okButton.setBounds(150,90,210,40);

                                                    notLabel.setHorizontalAlignment(JLabel.CENTER);

                                                    nFrame.add(notLabel);
                                                    nFrame.add(okButton);

                                                    okButton.addActionListener(new ActionListener() {
                                                        @Override
                                                        public void actionPerformed(ActionEvent e) {
                                                            nFrame.setVisible(false);
                                                            mFrame.setVisible(true);
                                                        }
                                                    });
                                                }
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }

                                        }
                                    });
                                    //Botao que cria a janela de Adicionar as pessoas com quem esteve em contacto, para no caso de estar infetado as mesmas serem avisadas
                                    cont.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            jframe.setVisible(false);
                                            _frame.setVisible(false);
                                            mFrame.setVisible(false);

                                            JFrame cFrame = new JFrame();
                                            cFrame.setSize(500, 400);
                                            cFrame.setLayout(null);
                                            cFrame.setTitle("StayAwayCovid");
                                            cFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                            cFrame.setVisible(true);
                                            cFrame.setLocation(500, 300);

                                            JLabel cLabel = new JLabel("Insira o Id da Pessoa em contacto: ");

                                            //Nao permite a escrita de letras no Id
                                            JTextField cText = new JTextField(10);
                                            cText.addKeyListener(new KeyAdapter() {
                                                @Override
                                                public void keyPressed(KeyEvent e) {
                                                    char c = e.getKeyChar();
                                                    if(Character.isLetter(c)){
                                                        cText.setEditable(false);
                                                    }else{
                                                        cText.setEditable(true);
                                                    }
                                                }
                                            });
                                            JButton cButton = new JButton("Adicionar Contacto");

                                            cLabel.setBounds(150,50,210,40);
                                            cText.setBounds(150,90,150,20);
                                            cButton.setBounds(150,120,150,40);

                                            cFrame.add(cLabel);
                                            cFrame.add(cText);
                                            cFrame.add(cButton);

                                            //Apos adicionar o contacto, volta a pagina do menu, verifica também se os campos acima estão preenchidoos
                                            cButton.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    if (cText.getText().equals("")) {
                                                        JOptionPane.showMessageDialog(null, "Preencher todos os campos", "Id Inválido", JOptionPane.WARNING_MESSAGE);
                                                    } else {

                                                        try {
                                                            if (cm.addContact(socket,ID, Integer.parseInt(cText.getText()))) {

                                                                cFrame.setVisible(false);
                                                                mFrame.setVisible(true);
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "O ID que tentou adicionar não se encontra registado", "Id Inválido", JOptionPane.WARNING_MESSAGE);
                                                            }
                                                        } catch (IOException ex) {
                                                            ex.printStackTrace();
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    });

                                    //Botao que redireciona para a pagina dos casos, onde é possivel consultar os dados de casos do concelho residente ou entao de toda a regiao em questão (Tamega e Sousa)
                                    casos.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            jframe.setVisible(false);
                                            _frame.setVisible(false);
                                            mFrame.setVisible(false);
                                            JFrame casesFrame = new JFrame();

                                            casesFrame.setSize(500, 400);
                                            casesFrame.setLayout(null);
                                            casesFrame.setTitle("StayAwayCovid");
                                            casesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                            casesFrame.setVisible(true);
                                            casesFrame.setLocation(500, 300);

                                            JButton concelhoButton = new JButton("Visualizar dados sobre Concelho");
                                            JButton regiaoButton = new JButton("Visualizar dados sobre a Região");

                                            concelhoButton.setBounds(125,100,260,50);
                                            regiaoButton.setBounds(125,160,260,50);

                                            casesFrame.add(concelhoButton);
                                            casesFrame.add(regiaoButton);

                                            //Apos clicar no botao correspondente ao concelho, ocorrem as seguintes ações
                                            concelhoButton.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    casesFrame.setVisible(false);
                                                    JFrame concFrame = new JFrame();
                                                    concFrame.setSize(500, 400);
                                                    concFrame.setLayout(null);
                                                    concFrame.setTitle("StayAwayCovid");
                                                    concFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                    concFrame.setVisible(true);
                                                    concFrame.setLocation(500, 300);

                                                    JButton okButton = new JButton("Ok!");
                                                    JLabel concLabel = new JLabel("Casos do Concelho: ");
                                                    concLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                                                    JLabel concText;
                                                    JLabel cDate;

                                                    if(notification.getCasos_concelho().equals("")) {
                                                        cDate = new JLabel("");
                                                        concText = new JLabel("O número de casos ainda não se encontra disponível");
                                                        concText.setFont(new Font("Arial", Font.PLAIN, 14));
                                                        concText.setForeground(Color.DARK_GRAY);
                                                    } else {
                                                        String[] notif = notification.getCasos_total().split(";");
                                                        concText = new JLabel(notif[1]);
                                                        cDate = new JLabel(notif[0]);
                                                        cDate.setFont(new Font("Arial", Font.ITALIC, 12));
                                                        concText.setFont(new Font("Arial", Font.BOLD, 24));
                                                        concText.setForeground(Color.DARK_GRAY);
                                                    }

                                                    okButton.setBounds(168,140,150,40);
                                                    concLabel.setBounds(0,50,500,40);
                                                    concText.setBounds(0,90,500,20);
                                                    cDate.setBounds(0,115,500,20);
                                                    concLabel.setHorizontalAlignment(JLabel.CENTER);
                                                    concText.setHorizontalAlignment(JLabel.CENTER);
                                                    cDate.setHorizontalAlignment(JLabel.CENTER);

                                                    concFrame.add(cDate);
                                                    concFrame.add(concLabel);
                                                    concFrame.add(concText);
                                                    concFrame.add(okButton);

                                                    //Apos clicar no Botão "Ok" redireciona á pagina do Menu
                                                    okButton.addActionListener(new ActionListener() {
                                                        @Override
                                                        public void actionPerformed(ActionEvent e) {
                                                            concFrame.setVisible(false);
                                                            mFrame.setVisible(true);
                                                        }
                                                    });
                                                }
                                            });

                                            //Se clicar no botao de região, irá aparecer os dados correspondentes á região;
                                            regiaoButton.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {

                                                    casesFrame.setVisible(false);
                                                    JFrame regFrame = new JFrame();
                                                    regFrame.setSize(500, 400);
                                                    regFrame.setLayout(null);
                                                    regFrame.setTitle("StayAwayCovid");
                                                    regFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                    regFrame.setVisible(true);
                                                    regFrame.setLocation(500, 300);

                                                    JButton okButton = new JButton("Ok!");
                                                    JLabel regLabel = new JLabel("Casos da  Região: ");
                                                    regLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                                                    JLabel regText;
                                                    JLabel date;

                                                    if(notification.getCasos_total().equals("")) {
                                                        date = new JLabel("");
                                                        regText = new JLabel("O número de casos ainda não se encontra disponível");
                                                        regText.setFont(new Font("Arial", Font.PLAIN, 14));
                                                        regText.setForeground(Color.DARK_GRAY);
                                                    } else {
                                                        String[] notif = notification.getCasos_total().split(";");
                                                        regText = new JLabel(notif[1]);
                                                        date = new JLabel(notif[0]);
                                                        date.setFont(new Font("Arial", Font.ITALIC, 12));
                                                        regText.setFont(new Font("Arial", Font.BOLD, 24));
                                                        regText.setForeground(Color.DARK_GRAY);
                                                    }

                                                    okButton.setBounds(168,140,150,40);
                                                    regLabel.setBounds(0,50,500,40);
                                                    regText.setBounds(0,90,500,20);
                                                    date.setBounds(0,115,500,20);
                                                    regLabel.setHorizontalAlignment(JLabel.CENTER);
                                                    regText.setHorizontalAlignment(JLabel.CENTER);
                                                    date.setHorizontalAlignment(JLabel.CENTER);

                                                    regFrame.add(date);
                                                    regFrame.add(regLabel);
                                                    regFrame.add(regText);
                                                    regFrame.add(okButton);

                                                    //Após clicar no botão "Ok", volta ao Menu;
                                                    okButton.addActionListener(new ActionListener() {
                                                        @Override
                                                        public void actionPerformed(ActionEvent e) {
                                                            regFrame.setVisible(false);
                                                            mFrame.setVisible(true);
                                                        }
                                                    });

                                                }
                                            });

                                        }
                                    });
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }



                        }
                    }

                });



            }
        });

        //Clicando no Botão de Registo, é criada a seguinte página para o mesmo;
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                jframe.setVisible(false);



                JFrame frame = new JFrame();
                frame.setSize(500,400);
                frame.setLayout(null);
                frame.setTitle("StayAwayCovid");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setLocation(500,300);

                JLabel label = new JLabel("Digite o seu Primeiro nome: ");
                JLabel label2 = new JLabel("Digite o seu Último nome: ");
                JLabel label3 = new JLabel("Escolha o seu concelho: ");

                String county [] = {"Amarante", "Baião", "Castelo de Paiva", "Celorico de Basto", "Cinfães", "Felgueiras", "Lousada", "Marco de Canaveses", "Paços de Ferreira", "Paredes", "Penafiel", "Resende"};

                JComboBox cb = new JComboBox(county);
                JTextField text = new JTextField(10);

                //Verifica se so adiciona letras ao Primeiro Nome
                text.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        int c = e.getKeyChar();
                        if(Character.isDigit(c)){
                            text.setEditable(false);
                        }else
                            text.setEditable(true);
                    }
                });
                JTextField text2 = new JTextField(10);

                //Verifica se so adiciona letras ao Último Nome
                text2.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        int c = e.getKeyChar();
                        if(Character.isDigit(c)){
                            text2.setEditable(false);
                        }else
                            text2.setEditable(true);

                    }
                });
                JButton b = new JButton("Registar");

                label.setBounds(150,50,160,40);
                label2.setBounds(150,100,160,40);
                text.setBounds(150,80,200,20);
                text2.setBounds(150,130,200,20);
                label3.setBounds(150,150,160,40);
                cb.setBounds(150, 180, 150, 20);
                b.setBounds(150,220,100,40);

                b.setVisible(true);


                frame.add(b);
                frame.add(text);
                frame.add(text2);
                frame.add(label);
                frame.add(label2);
                frame.add(label3);
                frame.add(cb);


                //Após clicar no Botão de Registo, é lhe apresentado uma mensagem e redirecionado para a pagina de Login;
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        //Verifica se todos os campos do Registo estão preenchidos;
                        if (text.getText().equals("") || text2.getText().equals("") ) {
                            JOptionPane.showMessageDialog(null, "Preencher todos os campos", "Registo Inválido", JOptionPane.WARNING_MESSAGE);
                        }else{

                            try {
                                int id = cm.clientRegisto(socket,text.getText(), text2.getText(), cb.getSelectedItem().toString());

                                frame.setVisible(false);

                                JFrame rFrame = new JFrame();
                                rFrame.setSize(500, 400);
                                rFrame.setLayout(null);
                                rFrame.setTitle("StayAwayCovid");
                                rFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                rFrame.setVisible(true);
                                rFrame.setLocation(500, 300);

                                JLabel label1 = new JLabel("Este é o seu ID de utilizador");
                                JLabel label2 = new JLabel("Guarde-o pois precisará dele para fazer login");
                                JLabel label3 = new JLabel(String.valueOf(id));
                                JButton okButton = new JButton("OK!");

                                label1.setFont(new Font("Arial", Font.PLAIN, 18));
                                label2.setFont(new Font("Arial", Font.PLAIN, 18));
                                label3.setFont(new Font("Arial", Font.BOLD, 32));
                                label3.setForeground(Color.ORANGE);

                                label1.setBounds(130, 50, 300, 50);
                                label2.setBounds(60, 90, 375, 50);
                                label3.setBounds(210, 162, 180, 50);
                                okButton.setBounds(160, 220, 180, 50);

                                rFrame.add(label1);
                                rFrame.add(label2);
                                rFrame.add(label3);
                                rFrame.add(okButton);

                                okButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        rFrame.setVisible(false);
                                        jframe.setVisible(true);
                                    }
                                });

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });

            }
        });

    }
}

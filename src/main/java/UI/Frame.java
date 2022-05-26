package UI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author lihui
 * @description TODO
 * @date 2022/5/25 11:07
 */

public class Frame extends JFrame{
    private JPanel contentPane;
    private JTextField txt_command;
    private JTextField textField;
    private JButton btn_encrypted;
    private JButton btn_OpenFile;
    private JButton btn_OpenFile01;
    private JButton btn_decode;
    private JTextArea txt_File;
    private listener listener;
    public Frame() {
        listener=new listener();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 758, 584);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 411, 491);
        this.add(panel);
        panel.setLayout(null);

        txt_File = new JTextArea();
        txt_File.setBounds(10, 10, 391, 481);
        //panel.setViewportView(textField);
        panel.add(txt_File);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(431, 10, 303, 491);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JLabel lb_encrypted = new JLabel("加密");
        lb_encrypted.setBounds(10, 38, 58, 15);
        panel_1.add(lb_encrypted);

        btn_OpenFile = new JButton("打开文件");
        btn_OpenFile.setBounds(10, 91, 97, 23);
        panel_1.add(btn_OpenFile);
        btn_OpenFile.addActionListener(listener);

        btn_encrypted = new JButton("加密文件");
        btn_encrypted.setBounds(144, 91, 97, 23);
        panel_1.add(btn_encrypted);
        btn_encrypted.addActionListener(listener);

        JLabel lblNewLabel = new JLabel("口令");
        lblNewLabel.setBounds(10, 152, 58, 15);
        panel_1.add(lblNewLabel);

        txt_command = new JTextField();
        txt_command.setBounds(10, 205, 283, 21);
        panel_1.add(txt_command);
        txt_command.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("解密");
        lblNewLabel_1.setBounds(10, 264, 58, 15);
        panel_1.add(lblNewLabel_1);

         btn_OpenFile01 = new JButton("打开文件");
        btn_OpenFile01.setBounds(10, 317, 97, 23);
        panel_1.add(btn_OpenFile01);
        btn_OpenFile01.addActionListener(listener);

         btn_decode = new JButton("解密文件");
        btn_decode.setBounds(144, 317, 97, 23);
        panel_1.add(btn_decode);
        btn_decode.addActionListener(listener);

        JLabel lblNewLabel_2 = new JLabel("口令");
        lblNewLabel_2.setBounds(10, 378, 58, 15);
        panel_1.add(lblNewLabel_2);

        textField = new JTextField();
        textField.setBounds(10, 431, 283, 21);
        panel_1.add(textField);
        textField.setColumns(10);
    }
    class listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
           if(e.getSource()==btn_OpenFile){
               try {
                   btn_openFile();
               } catch (IOException ex) {
                   throw new RuntimeException(ex);
               }
           }
        }

        public void btn_openFile() throws IOException {
            JFileChooser fileChooser = new JFileChooser();
            //默认目录
            String defaultDirectory = "d:\\";
            //默认文件名
            String defaultFilename = "prc.txt";
            //设置默认目录
            fileChooser.setCurrentDirectory(new File(defaultDirectory));
            //设置默认文件名
            fileChooser.setSelectedFile(new File(defaultFilename));
            int option = fileChooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                System.out.println(fileChooser.getSelectedFile().getName());
                File file = fileChooser.getSelectedFile();
                //文件显示
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                String temp = "";
                while (true) {
                    try {
                        if (!((temp = br.readLine()) != null)) break;
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    // 拼接换行符
                    sb.append(temp + "\n");
                }
                txt_File.setText(String.valueOf(sb));

            }
        }
    }

    public static void main(String[] args) {
       Frame frame=new Frame();
       frame.setVisible(true);
    }
}

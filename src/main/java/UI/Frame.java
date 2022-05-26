package UI;
import Util.Encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
    private String context;
    private String encryptContext;
    private File defaultDirectory;
    private  File defaultFile;
    public Frame() {
       defaultDirectory=new File("target/classes");
         defaultFile=new File(defaultDirectory,"/test.txt");

        listener=new listener();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 758, 584);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 416, 490);
        contentPane.add(scrollPane);

        txt_File = new JTextArea();
        scrollPane.setViewportView(txt_File);

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
            if (e.getSource() == btn_OpenFile) {
                try {
                    btn_openFile();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if(e.getSource()==btn_encrypted){
                String key = Encrypt.generateAESKey();
                String iv = Encrypt.generateAESIv();
                txt_command.setText(key);
                textField.setText(key);
                try {
                    encryptContext = Encrypt.encryptAES(context.getBytes(),key,iv);
                    txt_File.setText(encryptContext);
                    File file=new File(defaultDirectory,"encrypt(密文).txt");
                    BufferedWriter writer=new BufferedWriter(new FileWriter(file));
                    writer.write(encryptContext);
                    writer.close();
                } catch (NoSuchPaddingException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidKeyException ex) {
                    throw new RuntimeException(ex);
                } catch (BadPaddingException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalBlockSizeException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidAlgorithmParameterException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if(e.getSource()==btn_OpenFile01){
                try {
                    txt_File.setText("");
                    btn_openFile();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if(e.getSource()==btn_decode) {
                String iv=Encrypt.generateAESIv();
                String key=textField.getText();
                if(key.equals("")||key==null)
                    JOptionPane.showMessageDialog(null,"口令为空","错误",JOptionPane.WARNING_MESSAGE);
                else {
                    try {
                        context = Encrypt.decryptAES(encryptContext.getBytes(), key, iv);
                        txt_File.setText(context);
                    }  catch (Exception e1) {
                        JOptionPane.showMessageDialog(null,"解密错误","错误",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
        public void btn_openFile() throws FileNotFoundException {
            JFileChooser fileChooser = new JFileChooser();
            //设置默认目录
            fileChooser.setCurrentDirectory(defaultDirectory);
            fileChooser.setSelectedFile(defaultFile);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("文本文件", "txt");
            fileChooser.setFileFilter(filter);
            System.out.println(defaultDirectory.isDirectory());
            System.out.println(defaultDirectory.getAbsolutePath());
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
                context = String.valueOf(sb);
                txt_File.setText(context);

            }
        }
    }
    public static void main(String[] args) {
       Frame frame=new Frame();
       frame.setVisible(true);

    }
}

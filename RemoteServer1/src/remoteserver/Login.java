package remoteserver;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class Login extends JFrame
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
JButton login,cancel;
JTextField uname;
JPasswordField pass;
JLabel u,p;
 public Login()
 {
 setTitle("Login");
 setLayout(new GridLayout(3,2));
 setDefaultCloseOperation(EXIT_ON_CLOSE);
 setVisible(true);

 u=new JLabel("Username");
 p=new JLabel("Password");

 uname=new JTextField(20);
 pass=new JPasswordField(20);

 login=new JButton("Login");
 cancel=new JButton("Cancel");

 add(u);
 add(uname);

 add(p);
 add(pass);

 add(login);
 add(cancel);

 uname.requestFocus();

 cancel.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent ae)
  {
  System.exit(0);
  }
 });

 login.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent ae)
  {
  String un=uname.getText();
  String pa=new String(pass.getPassword());
  
   if((un.equals("admin"))&&(pa.equals("admin")))
   {
   dispose();
   ServerInitiator.main(null);
   }
   else
   {
       JOptionPane.showMessageDialog(null,"Login Unsuccessful!","Error",1);
   }
  }
 });

 KeyAdapter k=new KeyAdapter(){
  public void keyPressed(KeyEvent ke)
  {
   if(ke.getKeyCode()==KeyEvent.VK_ENTER)
   login.doClick();
  }
 };

 pass.addKeyListener(k);
 uname.addKeyListener(k);

 pack();
 setLocationRelativeTo(null);
 }


 public static void main(String args[])
 {
 new Login();
 }

}
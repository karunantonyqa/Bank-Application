import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.border.EtchedBorder;

import Banking.model.Account;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.Panel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.Canvas;
import javax.swing.UIManager;
import javax.swing.JScrollBar;
import java.awt.SystemColor;
import javax.swing.JTextArea;
import java.awt.Dimension;

public class BankingApplication {

	private JFrame frame;
	
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	
	JPanel CreateAccount, Deposit, Withdraw, Statement;
	private JTextField create_nameField;
	private JTextField create_addressField;
	private JTextField create_accountField;
	private JTextField deposit_accno_field;
	private JTextField deposit_amount_field;
	private JTextField withdraw_accountField;
	private JTextField withdraw_amountField;
	private JTextField deposit_name_field;
	private JTextField deposit_balance_field;
	private JTextField withdraw_name_field;
	private JTextField withdraw_balance_field;
	JLabel account_success_message;
	
	public String createAccountName, createAccountAddress, sql;
	public String retrieveID, retrieveName, retrieveAddress, retrieveAmount;
	public String uniqueAccId;
	String statementText = "";
	String statementWithText = "";
	String statementDepText = "";
	ArrayList<String> statementList = new ArrayList<>();
	String retrieveDeposit;
	int retrieveDepositNum = 0;
	int retrieveWithdrawNum =0;
	int finalBalance = 0;
	private JTextField statement_id_field;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BankingApplication window = new BankingApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BankingApplication() {
		initialize();
	}
	
	
	
	public int calculateBalance(String id) {
		Connection conn = null;
		Statement stmt = null;
		
		String getID = id;
		
		
		// Retrieve account info
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "password");
			stmt = conn.createStatement();
			
			String sql;
			String sqlWithdraw;
			sql = "SELECT * FROM bank.deposit WHERE account_id ='" + getID + "'";
			sqlWithdraw = "SELECT * FROM bank.withdraw WHERE account_id ='" + getID + "'";

			ResultSet rsBalance = stmt.executeQuery(sql);
			
			
			while(rsBalance.next()) {
				
				retrieveDepositNum += rsBalance.getInt("amount");
				System.out.println("Deposit: " + retrieveDepositNum);

			}
			
			ResultSet rsWithdraw = stmt.executeQuery(sqlWithdraw);
			
			while(rsWithdraw.next()) {
				
				retrieveWithdrawNum += rsWithdraw.getInt("amount");
				System.out.println("Withdraw: " + retrieveWithdrawNum);

			}
			
			finalBalance = retrieveDepositNum - retrieveWithdrawNum;

			rsBalance.close();
			rsWithdraw.close();
			stmt.close();
			conn.close();
			retrieveDepositNum = 0;
			retrieveWithdrawNum = 0;
			
		} catch (Exception e) {
			
		}

		
				
		return finalBalance;
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 500);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel HomePage = new JPanel();
		HomePage.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(HomePage, "name_176173073901542");
		HomePage.setLayout(null);
		

		
		JButton CreateAcc_button = new JButton("Create Account");
		CreateAcc_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateAccount.setVisible(true);
				HomePage.setVisible(false);
			}
		});
		CreateAcc_button.setBounds(133, 202, 219, 39);
		CreateAcc_button.setForeground(Color.BLACK);
		CreateAcc_button.setBackground(new Color(51, 153, 51));
		HomePage.add(CreateAcc_button);
		
		JButton ViewStatement_btn = new JButton("View Statement");
		ViewStatement_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Statement.setVisible(true);
				HomePage.setVisible(false);
			}
		});
		ViewStatement_btn.setBounds(170, 372, 150, 40);
		HomePage.add(ViewStatement_btn);
		
		JButton Deposit_btn = new JButton("Deposit Roons");
		Deposit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Deposit.setVisible(true);
				HomePage.setVisible(false);
			}
		});
		Deposit_btn.setBounds(170, 270, 150, 40);
		HomePage.add(Deposit_btn);
		
		JButton Withdraw_btn = new JButton("Withdraw Roons");
		Withdraw_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Withdraw.setVisible(true);
				HomePage.setVisible(false);
			}
		});
		Withdraw_btn.setBounds(170, 321, 150, 40);
		HomePage.add(Withdraw_btn);
		
		Panel HomeTopPanel = new Panel();
		HomeTopPanel.setBackground(new Color(0, 0, 102));
		HomeTopPanel.setBounds(0, 0, 484, 142);
		HomePage.add(HomeTopPanel);
		HomeTopPanel.setLayout(null);
		
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("C:\\Users\\Admin\\Documents\\Eclipse-workspace\\BankingApp\\src\\bank.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    JLabel label = new JLabel(new ImageIcon(image));
	    HomeTopPanel.add(label);
		
		JLabel home_title_label = new JLabel("Bank O' Roon");
		home_title_label.setForeground(Color.CYAN);
		home_title_label.setBounds(153, 46, 196, 42);
		home_title_label.setFont(new Font("Segoe UI Semibold", Font.BOLD | Font.ITALIC, 31));
		HomeTopPanel.add(home_title_label);
		
		CreateAccount = new JPanel();
		CreateAccount.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(CreateAccount, "name_176359610476172");
		CreateAccount.setLayout(null);
		
		JLabel account_name_label = new JLabel("Name:");
		account_name_label.setForeground(Color.WHITE);
		account_name_label.setBounds(100, 233, 76, 39);
		CreateAccount.add(account_name_label);
		

		
		JLabel account_address_label = new JLabel("Address:");
		account_address_label.setForeground(Color.WHITE);
		account_address_label.setBounds(100, 274, 108, 39);
		CreateAccount.add(account_address_label);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Create Account actionPerformed
				
				createAccountName = create_nameField.getText();
				createAccountAddress = create_addressField.getText();
				
				Connection conn = null;
				Statement stmt = null;
				
				int rand =  (int) (Math.random()*1000); 
				
				uniqueAccId = createAccountName.substring(0, 2) + rand + createAccountAddress.substring(0, 2);
				uniqueAccId = uniqueAccId.toUpperCase();
				
				create_accountField.setText(uniqueAccId);
				
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false", "root", "password");
					stmt = conn.createStatement();
					
					sql = "INSERT INTO bank.account VALUES('" + uniqueAccId + "', '" + createAccountName + "', '" + createAccountAddress + "')";
					
					stmt.executeUpdate(sql);
					
					account_success_message.setVisible(true);
					
					stmt.close();
					conn.close();
					
				} catch (Exception ex) {
					
				}
				
				//Check and Generate account number
				
			}
		});
		btnCreateAccount.setBounds(180, 389, 137, 39);
		CreateAccount.add(btnCreateAccount);
		
		create_nameField = new JTextField();
		create_nameField.setBounds(169, 242, 170, 20);
		CreateAccount.add(create_nameField);
		create_nameField.setColumns(10);
		
		create_addressField = new JTextField();
		create_addressField.setBounds(169, 283, 170, 20);
		CreateAccount.add(create_addressField);
		create_addressField.setColumns(10);
		
		JLabel account_create_label = new JLabel("Create An Account");
		account_create_label.setForeground(Color.CYAN);
		account_create_label.setFont(new Font("Tahoma", Font.BOLD, 16));
		account_create_label.setBounds(169, 180, 170, 14);
		CreateAccount.add(account_create_label);
		
		JLabel account_label = new JLabel("Account No.:");
		account_label.setForeground(Color.WHITE);
		account_label.setBounds(100, 314, 108, 39);
		CreateAccount.add(account_label);
		
		create_accountField = new JTextField();
		create_accountField.setEditable(false);
		create_accountField.setColumns(10);
		create_accountField.setBounds(169, 325, 170, 20);
		CreateAccount.add(create_accountField);
		
		Panel accountTopPanel = new Panel();
		accountTopPanel.setBounds(0, 0, 484, 142);
		CreateAccount.add(accountTopPanel);
		accountTopPanel.setLayout(null);
		accountTopPanel.setBackground(new Color(0, 0, 102));
		
		JLabel label_2 = new JLabel("Bank O' Roon");
		label_2.setForeground(Color.CYAN);
		label_2.setFont(new Font("Segoe UI Semibold", Font.BOLD | Font.ITALIC, 31));
		label_2.setBounds(153, 46, 196, 42);
		accountTopPanel.add(label_2);
		
		JButton account_back_btn = new JButton("Back");
		account_back_btn.setBounds(10, 11, 89, 23);
		accountTopPanel.add(account_back_btn);
		account_back_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomePage.setVisible(true);
				CreateAccount.setVisible(false);
			}
		});
		account_back_btn.setForeground(Color.WHITE);
		account_back_btn.setBackground(Color.DARK_GRAY);
		account_back_btn.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		account_success_message = new JLabel("***ACCOUNT CREATED***");
		account_success_message.setFont(new Font("Century Gothic", Font.BOLD, 14));
		account_success_message.setForeground(Color.GREEN);
		account_success_message.setBounds(160, 356, 285, 14);
		account_success_message.setVisible(false);
		CreateAccount.add(account_success_message);
		
		
		//DEPOSIT
		
		
		Deposit = new JPanel();
		Deposit.setBackground(Color.DARK_GRAY);
		Deposit.setForeground(Color.WHITE);
		frame.getContentPane().add(Deposit, "name_176361370229835");
		Deposit.setLayout(null);
		
		JLabel lblDepositRoons = new JLabel("Deposit Roons");
		lblDepositRoons.setForeground(Color.CYAN);
		lblDepositRoons.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDepositRoons.setBounds(182, 178, 170, 14);
		Deposit.add(lblDepositRoons);
		
		JLabel deposit_accno_label = new JLabel("Account No:");
		deposit_accno_label.setForeground(Color.WHITE);
		deposit_accno_label.setFont(new Font("Tahoma", Font.BOLD, 16));
		deposit_accno_label.setBounds(51, 178, 118, 71);
		Deposit.add(deposit_accno_label);
		
		JLabel deposit_amount_label = new JLabel("Amount:");
		deposit_amount_label.setForeground(Color.WHITE);
		deposit_amount_label.setFont(new Font("Tahoma", Font.BOLD, 16));
		deposit_amount_label.setBounds(51, 240, 118, 33);
		Deposit.add(deposit_amount_label);
		
		deposit_accno_field = new JTextField();
		deposit_accno_field.setBounds(179, 205, 160, 20);
		Deposit.add(deposit_accno_field);
		deposit_accno_field.setColumns(10);
		
		deposit_amount_field = new JTextField();
		deposit_amount_field.setBounds(179, 248, 160, 20);
		Deposit.add(deposit_amount_field);
		deposit_amount_field.setColumns(10);
		
		JPanel deposit_accountInfo_panel = new JPanel();
		deposit_accountInfo_panel.setBackground(Color.GRAY);
		deposit_accountInfo_panel.setBounds(0, 330, 484, 131);
		Deposit.add(deposit_accountInfo_panel);
		deposit_accountInfo_panel.setLayout(null);
		
		JLabel deposit_name_label = new JLabel("Name:");
		deposit_name_label.setForeground(Color.WHITE);
		deposit_name_label.setFont(new Font("Tahoma", Font.BOLD, 16));
		deposit_name_label.setBounds(45, 22, 118, 33);
		deposit_accountInfo_panel.add(deposit_name_label);
		
		JLabel deposit_balance_label = new JLabel("Balance:");
		deposit_balance_label.setForeground(Color.WHITE);
		deposit_balance_label.setFont(new Font("Tahoma", Font.BOLD, 16));
		deposit_balance_label.setBounds(45, 65, 118, 33);
		deposit_accountInfo_panel.add(deposit_balance_label);
		
		deposit_name_field = new JTextField();
		deposit_name_field.setColumns(10);
		deposit_name_field.setBounds(174, 30, 160, 20);
		deposit_name_field.setEditable(false);
		deposit_accountInfo_panel.add(deposit_name_field);
		
		deposit_balance_field = new JTextField();
		deposit_balance_field.setColumns(10);
		deposit_balance_field.setBounds(174, 73, 160, 20);
		deposit_balance_field.setEditable(false);
		deposit_accountInfo_panel.add(deposit_balance_field);
		
		JButton deposit_retrieve_acc_btn = new JButton("Retrieve acc.");
		deposit_retrieve_acc_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Connection conn = null;
				Statement stmt = null;
				
				String getID = deposit_accno_field.getText().toUpperCase();
				
				// Retrieve account info
				try {
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "password");
					stmt = conn.createStatement();
					
					String sql;
					sql = "SELECT * FROM bank.account WHERE account_id ='" + getID + "'";
					ResultSet rs = stmt.executeQuery(sql);
					
					while(rs.next()) {
						retrieveID = rs.getString("account_id");
						retrieveName = rs.getString("name");
						retrieveAddress = rs.getString("address");
					}
					
					int balanceField = calculateBalance(getID);
					String a = Integer.toString(balanceField);
					deposit_balance_field.setText(a);
									
					deposit_accno_field.setText(retrieveID);
					deposit_name_field.setText(retrieveName);
					
					stmt.close();
					conn.close();
					
				} catch (Exception e) {
					
				}
				

				
				
			}
		});
		deposit_retrieve_acc_btn.setBounds(362, 203, 89, 23);
		Deposit.add(deposit_retrieve_acc_btn);
		
		JButton deposit_deposit_btn = new JButton("Deposit");
		deposit_deposit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection conn = null;
				Statement stmt = null;
				
				// Insert deposit details into DB
				
				Date date = new Date();
		        Timestamp ts = new Timestamp(date.getTime());
		        String dep_date = ts.toString();
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false", "root", "password");
					stmt = conn.createStatement();
					
					int rand =  (int) (Math.random()*1000); 
					
					retrieveAmount = deposit_amount_field.getText();
					int depAmount = Integer.parseInt(retrieveAmount);
					
					String uniqueId = deposit_accno_field.getText();

					
					sql = "INSERT INTO bank.deposit VALUES(" + rand +  ",'" + uniqueId + "', " + depAmount + ", '" + dep_date + "')";
					
					stmt.executeUpdate(sql);
					
					int balanceField = calculateBalance(uniqueId);
					String a = Integer.toString(balanceField);
					deposit_balance_field.setText(a);
					
					account_success_message.setVisible(true);
					
					stmt.close();
					conn.close();
					
				} catch (Exception ex) {
					
				}
				
			}
		});
		deposit_deposit_btn.setBounds(362, 246, 89, 23);
		Deposit.add(deposit_deposit_btn);
		
		Panel depositTopPanel = new Panel();
		depositTopPanel.setBounds(0, 0, 484, 142);
		Deposit.add(depositTopPanel);
		depositTopPanel.setLayout(null);
		depositTopPanel.setBackground(new Color(0, 0, 102));
		
		JLabel deposit_title_label = new JLabel("Bank O' Roon");
		deposit_title_label.setBounds(155, 45, 196, 42);
		depositTopPanel.add(deposit_title_label);
		deposit_title_label.setForeground(Color.CYAN);
		deposit_title_label.setFont(new Font("Segoe UI Semibold", Font.BOLD | Font.ITALIC, 31));
		
		JButton deposit_back_btn = new JButton("Back");
		deposit_back_btn.setBounds(10, 11, 89, 23);
		depositTopPanel.add(deposit_back_btn);
		deposit_back_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomePage.setVisible(true);
				Deposit.setVisible(false);
			}
		});
		deposit_back_btn.setForeground(Color.WHITE);
		deposit_back_btn.setFont(new Font("Tahoma", Font.BOLD, 14));
		deposit_back_btn.setBackground(Color.DARK_GRAY);
		
		
		//WITHDRAW
		
		
		Withdraw = new JPanel();
		Withdraw.setBackground(Color.DARK_GRAY);
		Withdraw.setForeground(Color.WHITE);
		frame.getContentPane().add(Withdraw, "name_176362657648496");
		Withdraw.setLayout(null);
		
		JLabel lblWithdrawRoons = new JLabel("Withdraw Roons");
		lblWithdrawRoons.setForeground(Color.CYAN);
		lblWithdrawRoons.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblWithdrawRoons.setBounds(181, 177, 170, 14);
		Withdraw.add(lblWithdrawRoons);
		
		JLabel withdraw_account_label = new JLabel("Account No:");
		withdraw_account_label.setForeground(Color.WHITE);
		withdraw_account_label.setFont(new Font("Tahoma", Font.BOLD, 16));
		withdraw_account_label.setBounds(50, 177, 118, 71);
		Withdraw.add(withdraw_account_label);
		
		JLabel withdraw_amount_label = new JLabel("Amount:");
		withdraw_amount_label.setForeground(Color.WHITE);
		withdraw_amount_label.setFont(new Font("Tahoma", Font.BOLD, 16));
		withdraw_amount_label.setBounds(50, 239, 118, 33);
		Withdraw.add(withdraw_amount_label);
		
		withdraw_accountField = new JTextField();
		withdraw_accountField.setColumns(10);
		withdraw_accountField.setBounds(178, 204, 160, 20);
		Withdraw.add(withdraw_accountField);
		
		withdraw_amountField = new JTextField();
		withdraw_amountField.setColumns(10);
		withdraw_amountField.setBounds(178, 247, 160, 20);
		Withdraw.add(withdraw_amountField);
		
		JPanel withdraw_bottom_panel = new JPanel();
		withdraw_bottom_panel.setLayout(null);
		withdraw_bottom_panel.setBackground(Color.GRAY);
		withdraw_bottom_panel.setBounds(0, 330, 484, 131);
		Withdraw.add(withdraw_bottom_panel);
		
		JLabel withdraw_name_label = new JLabel("Name:");
		withdraw_name_label.setForeground(Color.WHITE);
		withdraw_name_label.setFont(new Font("Tahoma", Font.BOLD, 16));
		withdraw_name_label.setBounds(50, 21, 118, 33);
		withdraw_bottom_panel.add(withdraw_name_label);
		
		JLabel withdraw_balance_label = new JLabel("Balance:");
		withdraw_balance_label.setForeground(Color.WHITE);
		withdraw_balance_label.setFont(new Font("Tahoma", Font.BOLD, 16));
		withdraw_balance_label.setBounds(50, 64, 118, 33);
		withdraw_bottom_panel.add(withdraw_balance_label);
		
		withdraw_name_field = new JTextField();
		withdraw_name_field.setColumns(10);
		withdraw_name_field.setBounds(179, 29, 160, 20);
		withdraw_bottom_panel.add(withdraw_name_field);
		
		withdraw_balance_field = new JTextField();
		withdraw_balance_field.setColumns(10);
		withdraw_balance_field.setBounds(179, 72, 160, 20);
		withdraw_bottom_panel.add(withdraw_balance_field);
		
		JLabel withdraw_success_lbl = new JLabel("**Withdraw SUCCESS**");
		withdraw_success_lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		withdraw_success_lbl.setForeground(Color.GREEN);
		withdraw_success_lbl.setBounds(168, 300, 183, 14);
		withdraw_success_lbl.setVisible(false);
		Withdraw.add(withdraw_success_lbl);
		
		JLabel withdraw_fail_lbl = new JLabel("**INSUFFICIENTFUNDS**");
		withdraw_fail_lbl.setForeground(Color.RED);
		withdraw_fail_lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		withdraw_fail_lbl.setBounds(168, 300, 198, 14);
		withdraw_fail_lbl.setVisible(false);
		Withdraw.add(withdraw_fail_lbl);
		
		JButton withdraw_retrieve_btn = new JButton("Retrieve acc.");
		withdraw_retrieve_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection conn = null;
				Statement stmt = null;
				
				String getID = deposit_accno_field.getText().toUpperCase();

				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "password");
					stmt = conn.createStatement();
					
					
					
					String sql;
					sql = "SELECT * FROM bank.account WHERE account_id ='" + getID + "'";
					ResultSet rs = stmt.executeQuery(sql);
					
					while(rs.next()) {
						retrieveID = rs.getString("account_id");
						retrieveName = rs.getString("name");
						retrieveAddress = rs.getString("address");
					}
					
					int balanceField = calculateBalance(getID);
					String a = Integer.toString(balanceField);
					withdraw_balance_field.setText(a);
										
					withdraw_accountField.setText(retrieveID);
					withdraw_name_field.setText(retrieveName);

					stmt.close();
					conn.close();
					
				} catch (Exception e) {
					
				}
			}
		});
		withdraw_retrieve_btn.setBounds(359, 204, 89, 23);
		Withdraw.add(withdraw_retrieve_btn);
		
		JButton withdraw_withdraw_btn = new JButton("Withdraw");
		withdraw_withdraw_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Connection conn = null;
				Statement stmt = null;
				
				// Insert deposit details into DB
				
				Date date = new Date();
		        Timestamp ts = new Timestamp(date.getTime());
		        String dep_date = ts.toString();
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false", "root", "password");
					stmt = conn.createStatement();
					
					//Generate withdraw id
					int rand = (int) (Math.random()*1000); 
					
					//Get withdrawal amount from field
					retrieveAmount = withdraw_amountField.getText();
					int withAmount = Integer.parseInt(retrieveAmount);

					String uniqueId = deposit_accno_field.getText();

					int balance = calculateBalance(uniqueId);
					
					System.out.println(balance);
					
					if(balance > withAmount) {
						sql = "INSERT INTO bank.withdraw VALUES(" + rand +  ",'" + uniqueId + "', " + withAmount + ", '" + dep_date + "')";
						
						stmt.executeUpdate(sql);
						withdraw_success_lbl.setVisible(true);

					} else {
						withdraw_fail_lbl.setVisible(true);
					}
					
					
					
					int balanceField = calculateBalance(uniqueId);
					String a = Integer.toString(balanceField);
					withdraw_balance_field.setText(a);
					
					
					
					stmt.close();
					conn.close();
					
				} catch (Exception ex) {
					
				}
				
			}
		});
		withdraw_withdraw_btn.setBounds(359, 247, 89, 23);
		Withdraw.add(withdraw_withdraw_btn);
		
		Panel withdraw_top_panel = new Panel();
		withdraw_top_panel.setBounds(0, 0, 484, 142);
		Withdraw.add(withdraw_top_panel);
		withdraw_top_panel.setLayout(null);
		withdraw_top_panel.setBackground(new Color(0, 0, 102));
		
		JLabel withdraw_title_label = new JLabel("Bank O' Roon");
		withdraw_title_label.setBounds(145, 45, 196, 42);
		withdraw_top_panel.add(withdraw_title_label);
		withdraw_title_label.setForeground(Color.CYAN);
		withdraw_title_label.setFont(new Font("Segoe UI Semibold", Font.BOLD | Font.ITALIC, 31));
		
		JButton withdraw_back_btn = new JButton("Back");
		withdraw_back_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomePage.setVisible(true);
				Withdraw.setVisible(false);
			}
		});
		withdraw_back_btn.setBounds(10, 11, 89, 23);
		withdraw_top_panel.add(withdraw_back_btn);
		withdraw_back_btn.setForeground(Color.WHITE);
		withdraw_back_btn.setFont(new Font("Tahoma", Font.BOLD, 14));
		withdraw_back_btn.setBackground(Color.DARK_GRAY);
		

		

		
		
		//STATEMENT
		
		
		Statement = new JPanel();
		Statement.setBackground(Color.DARK_GRAY);
		Statement.setForeground(Color.WHITE);
		frame.getContentPane().add(Statement, "name_176399466811433");
		Statement.setLayout(null);
		
		Panel statement_top_panel = new Panel();
		statement_top_panel.setLayout(null);
		statement_top_panel.setBackground(new Color(0, 0, 102));
		statement_top_panel.setBounds(0, 0, 484, 142);
		Statement.add(statement_top_panel);
		
		JLabel statement_title = new JLabel("Bank O' Roon");
		statement_title.setForeground(Color.CYAN);
		statement_title.setFont(new Font("Segoe UI Semibold", Font.BOLD | Font.ITALIC, 31));
		statement_title.setBounds(140, 37, 196, 42);
		statement_top_panel.add(statement_title);
		
		JButton statement_back_btn = new JButton("Back");
		statement_back_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HomePage.setVisible(true);
				Statement.setVisible(false);
			}
		});
		statement_back_btn.setForeground(Color.WHITE);
		statement_back_btn.setFont(new Font("Tahoma", Font.BOLD, 14));
		statement_back_btn.setBackground(Color.DARK_GRAY);
		statement_back_btn.setBounds(10, 11, 89, 23);
		statement_top_panel.add(statement_back_btn);
		
		JLabel lblBalance = new JLabel("Balance:");
		lblBalance.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBalance.setForeground(new Color(255, 255, 255));
		lblBalance.setBounds(140, 117, 77, 14);
		statement_top_panel.add(lblBalance);
		
		JLabel statement_balance_label = new JLabel("");
		statement_balance_label.setForeground(new Color(255, 255, 255));
		statement_balance_label.setFont(new Font("Tahoma", Font.BOLD, 15));
		statement_balance_label.setBounds(227, 119, 109, 14);
		statement_top_panel.add(statement_balance_label);
		
		statement_id_field = new JTextField();
		statement_id_field.setBounds(174, 148, 125, 20);
		Statement.add(statement_id_field);
		statement_id_field.setColumns(10);
		
		JLabel lblEnterId = new JLabel("Enter ID:");
		lblEnterId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEnterId.setForeground(Color.WHITE);
		lblEnterId.setBounds(98, 148, 66, 14);
		Statement.add(lblEnterId);
		
		JPanel statement_tarea_panel = new JPanel();
		statement_tarea_panel.setBackground(new Color(128, 128, 128));
		statement_tarea_panel.setBounds(10, 180, 464, 270);
		Statement.add(statement_tarea_panel);
		statement_tarea_panel.setLayout(null);
		
		JScrollPane withdraw_scroll = new JScrollPane();
		withdraw_scroll.setBounds(443, 31, 11, 228);
		statement_tarea_panel.add(withdraw_scroll);
		
		JLabel lblNewLabel = new JLabel("Money in");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(83, 6, 62, 14);
		statement_tarea_panel.add(lblNewLabel);
		
		JLabel lblWithdrawals = new JLabel("Money out");
		lblWithdrawals.setForeground(Color.WHITE);
		lblWithdrawals.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblWithdrawals.setBounds(305, 6, 107, 14);
		statement_tarea_panel.add(lblWithdrawals);
		
		JScrollPane deposit_scroll = new JScrollPane();
		deposit_scroll.setBounds(224, 31, 11, 228);
		statement_tarea_panel.add(deposit_scroll);
		
		JTextArea statement_dep_text_area = new JTextArea();
		statement_dep_text_area.setBounds(10, 31, 214, 228);
		statement_tarea_panel.add(statement_dep_text_area);
		
		JTextArea statement_with_text_area = new JTextArea();
		statement_with_text_area.setBounds(239, 31, 204, 228);
		statement_tarea_panel.add(statement_with_text_area);
		
		JButton statement_retrieve_btn = new JButton("Statement");
		statement_retrieve_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection conn = null;
				Statement stmt = null;
				
				String getID = statement_id_field.getText().toUpperCase();
				
				// Retrieve account info
				try {
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "password");
					stmt = conn.createStatement();
					
					String sql;
					String sqlWithdraw;
					sql = "SELECT * FROM bank.deposit WHERE account_id ='" + getID + "'";
					sqlWithdraw = "SELECT * FROM bank.withdraw WHERE account_id ='" + getID + "'";
					
					int balance = calculateBalance(getID);
					statement_balance_label.setText("£" + Integer.toString(balance));

					ResultSet rsBalance = stmt.executeQuery(sql);
					while(rsBalance.next()) {
						
						retrieveDepositNum = rsBalance.getInt("amount");
						String depDate = rsBalance.getString("date");
						
						statementDepText += "Amount: " + retrieveDepositNum + "     " + "Date: " + depDate + "\n";
						
						
						statement_dep_text_area.setText(statementDepText);
						statement_dep_text_area.setFont(statement_dep_text_area.getFont().deriveFont(9.5f));

					}
					
					ResultSet rsWithdraw = stmt.executeQuery(sqlWithdraw);
					
					while(rsWithdraw.next()) {
						
						retrieveWithdrawNum = rsWithdraw.getInt("amount");
						String withDate = rsWithdraw.getString("date");
						
						statementWithText += "Amount: " + retrieveWithdrawNum + "     " + "Date: " + withDate + "\n";

						statement_with_text_area.setText(statementWithText);
						statement_with_text_area.setFont(statement_with_text_area.getFont().deriveFont(9.5f));
				
					}
					
					rsBalance.close();
					rsWithdraw.close();
					stmt.close();
					conn.close();
					retrieveDepositNum = 0;
					retrieveWithdrawNum = 0;
					
				} catch (Exception exc) {
					
				}
				
			}
		});
		statement_retrieve_btn.setBounds(309, 147, 89, 23);
		Statement.add(statement_retrieve_btn);
		

	}
}

package com.masion.net.scan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PortScanner extends JFrame {

	private static final long serialVersionUID = 2829475409962012110L;
	private static final int maxThreadNum = 20;
	public static JTextArea resultText;
	public static JTextField startIP;
	public static JTextField endIP;
	public static JTextField minPort;
	public static JTextField maxPort;
	public static JButton submitButton;

	public static Boolean isRunning = false;
	private List<ScanThread> threadList = null;

	private void setAllTextEnabled(boolean enabled){
	    startIP.setEnabled(enabled);
	    endIP.setEnabled(enabled);
	    minPort.setEnabled(enabled);
	    maxPort.setEnabled(enabled);
	}

	public PortScanner() {
		super();
		this.setTitle("Port Scanner");
		this.setBounds(200, 200, 500, 400);
		this.getContentPane().setLayout(null);
		this.add(getJButton(), null);

		JLabel startIPLable = new JLabel();
		startIPLable.setBounds(34, 20, 83, 18);
		startIPLable.setText("起始IP:");
		this.add(startIPLable, null);

		startIP = new JTextField("10.0.20.1");
		startIP.setBounds(86, 20, 120, 20);
		this.add(startIP, null);

		JLabel endIPLable = new JLabel();
		endIPLable.setBounds(250, 20, 83, 18);
		endIPLable.setText("终止IP:");
		this.add(endIPLable, null);

		endIP = new JTextField("10.0.20.253");
		endIP.setBounds(310, 20, 120, 20);
		this.add(endIP, null);

		JLabel startPortLable = new JLabel();
		startPortLable.setBounds(34, 45, 83, 18);
		startPortLable.setText("Min Port:");
		this.add(startPortLable, null);

		minPort = new JTextField("161");
		minPort.setBounds(86, 45, 120, 20);
		this.add(minPort, null);

		JLabel endPortLable = new JLabel();
		endPortLable.setBounds(250, 45, 83, 18);
		endPortLable.setText("Max Port:");
		this.add(endPortLable, null);

		maxPort = new JTextField("161");
		maxPort.setBounds(310, 45, 120, 20);
		this.add(maxPort, null);

		resultText = new JTextArea();
		resultText.setBounds(30, 120, 420, 200);
		resultText.setAutoscrolls(true);
		this.add(resultText, null);

		this.initAction();
	}

	private void initAction() {
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(submitButton)) {
					if (isRunning) {
					    setAllTextEnabled(true);
						submitButton.setText("开始");
						isRunning = false;
						ScanThread.setStop(true);
					} else {
                        setAllTextEnabled(false);
						isRunning = true;
						submitButton.setText("停止");
						PortScanner.resultText.setText("");

						ScanThread.maxThreadNum = maxThreadNum;
						try {
							Integer minp = Integer.parseInt("" + minPort.getText());
							ScanThread.minPort = minp;
						} catch (Exception ee) {
						}

						try {
							Integer maxp = Integer.parseInt("" + maxPort.getText());
							ScanThread.maxPort = maxp;
						} catch (Exception ee) {
						}

						ScanThread.ipStart = startIP.getText();
						ScanThread.ipEnd =  endIP.getText();

						PortScanner.resultText.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 扫描开始\n");

						threadList = new ArrayList<ScanThread>();
						for (int i = 0; i < maxThreadNum; i++) {
							ScanThread th = new ScanThread(i);
							threadList.add(th);
							th.start();
						}

						new Thread(){
    						public void run(){
    						int stopNum = 0;
    						while(stopNum < maxThreadNum){
    						    for(ScanThread t: threadList){
    						        if(!t.isAlive()){
    						            stopNum++ ;
    						        }
    						    }
    						    try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
    						}
                            setAllTextEnabled(true);
                            submitButton.setText("开始");
                            isRunning = false;
                            ScanThread.setStop(true);
                            PortScanner.resultText.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 扫描结束\n");
    						}
						}.start();
					}
				}
			}
		});

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	private JButton getJButton() {
		if (submitButton == null) {
			submitButton = new JButton();
			submitButton.setBounds(180, 80, 100, 27);
			submitButton.setText("开始");
		}
		return submitButton;
	}

	public static void appendResult(String str){
		PortScanner.resultText.append(str);
	}

	public static void main(String[] args) {
		PortScanner w = new PortScanner();
		w.setVisible(true);
	}
}

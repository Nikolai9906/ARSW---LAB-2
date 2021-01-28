package edu.eci.arsw.primefinder;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.applet.*;
import java.awt.event.*;
public class Main  extends Applet {
	static int nHilos=3;
	static ArrayList<PrimeFinderThread> hilos = new ArrayList<PrimeFinderThread>();
	TextField t;
	public static void main(String[] args) {
		TextField t = new TextField("press ENTER");
		t.addKeyListener
				(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
						int key = e.getKeyCode();
						if (key == KeyEvent.VK_ENTER) {
							Toolkit.getDefaultToolkit().beep();
							try {
								Iniciar();
							} catch (InterruptedException interruptedException) {
								interruptedException.printStackTrace();
							}
						}
					}
				});


		for (int i=0;i<nHilos;i++) {
			PrimeFinderThread pft=new PrimeFinderThread((i)*10000000,(i+1)*10000000);
			hilos.add(pft);

		}
		iniciarTiempo();
		for (int i=0;i<nHilos;i++) {
			hilos.get(i).start();
		}


	}

	public static void Detener() throws InterruptedException {

		for (int i=0;i<nHilos;i++) {
			hilos.get(i).suspend();
		}

	}
	public static void Iniciar() throws InterruptedException {

		for (int i=0;i<nHilos;i++) {
			hilos.get(i).resume();

		}

	}
	public static TimerTask tiempoEjecucion() {

		TimerTask _timerTask;
		_timerTask = new TimerTask() {
			int count = 0;

			@Override
			public void run() {
				if (count == 5) {
					try {
						Detener();
						Scanner Linea = new Scanner(System.in);
						Linea.nextLine();
						Iniciar();
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				count+=1;
			}
		};

		return _timerTask;
	}

	public static void iniciarTiempo() {
		int count = 0;
		Timer _timer = new Timer();
		_timer.scheduleAtFixedRate(tiempoEjecucion(), 0, 1000);
	}



	public void keyTyped(KeyEvent e) {
		System.out.println("hola mundo");
	}






	public void keyReleased(KeyEvent e) {
		System.out.println(e.getKeyCode());

	}

}

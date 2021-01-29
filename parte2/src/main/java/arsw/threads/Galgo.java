package arsw.threads;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	boolean estado=false;
	RegistroLlegada regl;

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
	}
	public boolean acabo(){
		return estado;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {			
			Thread.sleep(100);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
			

		}
		if (paso == carril.size()) {
			carril.finish();
			estado=true;
			int ubicacion=regl.getUltimaPosicionAlcanzada();
			regl.setUltimaPosicionAlcanzada(ubicacion+1);

			if (ubicacion==1){

				regl.setGanador(this.getName());

			}
			System.out.println("El galgo "+this.getName()+" llego en la posicion "+ubicacion);


		}
	}


	@Override
	public void run() {
		
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

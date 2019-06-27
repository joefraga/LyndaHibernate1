package ejemplo;
import java.util.*;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;



public class HolaMundoEjemplo {

	private static SessionFactory fabrica;
	private static ServiceRegistry registro;
	
	public static void main(String[] args) {
		Scanner entrada= new Scanner(System.in);
		String m="";
		System.out.println("Ingrese el mensaje: ");
		m=entrada.nextLine();
		
		try {
			Configuration conf=new Configuration().configure();
			//conf.addAnnotatedClass(ejemplo.Message.class); 
			conf.addClass(ejemplo.Message.class); 
			registro = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
			fabrica = conf.buildSessionFactory(registro);			
		}catch(Throwable e) {
			System.err.println("Fallo al crear el objeto de la fabrica de la sesion " + e);
			throw new ExceptionInInitializerError(e);			
		}
			
		Session sesion=fabrica.openSession();
		Transaction tx=null;
		Integer msgId=null;
		
		try {
			
			tx=sesion.beginTransaction();  //iniciamos la trasaccion
			Message msg= new Message(m); //creo el objeto Mensaje
			msgId=(Integer) sesion.save(msg); //lo grabo a la DB
			
			List lista_mensajes=sesion.createQuery("FROM Message").list(); //hago consulta a la DB de la tabla
			for(Iterator iterator=lista_mensajes.iterator(); iterator.hasNext();) { //leno la lista de mensajes de la tabla
				Message mensaje=(Message) iterator.next();
				System.out.println("Mensaje: " + mensaje.getMessage());
			}
			
			tx.commit();  //commit a la transaccion
			
		}catch(HibernateException e) {
			if(tx != null) tx.rollback();			
			e.printStackTrace();			
		}finally {
			sesion.close();
		}
		StandardServiceRegistryBuilder.destroy(registro);
	}

}

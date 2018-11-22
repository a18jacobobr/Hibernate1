package baseDatos;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {
	private static final SessionFactory sessionFactory;
	static{
		try{
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex){
			System.err.println("Fallo en creacion inicial de SessionFactory"+ex);
			throw new ExceptionInInitializerError(ex);
		}
	}//static
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}//getSessionFactory
}
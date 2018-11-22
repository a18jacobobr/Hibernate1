package operaciones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import baseDatos.Libro;
import baseDatos.Prestamo;
import baseDatos.SessionFactoryUtil;
import baseDatos.Usuario;

public class OperacionsUsuario {

	private static Session sesion;

	public static void abrirConexion() throws Exception {
		SessionFactory sesionFac = SessionFactoryUtil.getSessionFactory();
		sesion = sesionFac.openSession();
	}

	public static void cerrarConexion() {
		sesion.close();
	}

	public static Libro consultaLibro(int idLibro) throws Exception {
		Query consultaLibro = sesion.createQuery("from Libro where idLibro = ?"); // Libro con mayus, va por la clase
		consultaLibro.setInteger(0, idLibro);
		Libro libroConsultado = (Libro) consultaLibro.uniqueResult();

		return libroConsultado;
	}

	public static Usuario consultaUsuario(int idUsuario) throws Exception {
		Query consultaUsuario = sesion.createQuery("from Usuario where idUsuario = ?");
		consultaUsuario.setInteger(0, idUsuario);
		Usuario usuarioConsultado = (Usuario) consultaUsuario.uniqueResult();

		return usuarioConsultado;
	}

	public static boolean isDevolto(int idLibro) throws Exception {
		Libro consultaLibro = consultaLibro(idLibro);
		Set<Prestamo> prestamos = consultaLibro.getPrestamos();
		for (Prestamo prestamo : prestamos) {
			if (!prestamo.getDevolto()) {
				return false;
			}
		}
		return true;
	}

	public static List<Usuario> consultarUsuarios() {
		Query consultaUsuarios = sesion.createQuery("from Usuario");
		List<Usuario> listaUsuarios = consultaUsuarios.list();
		return listaUsuarios;
	}
	
	public static List<Libro> consultarLibros() {
		Query consultaLibro = sesion.createQuery("from Libro");
		List<Libro> listaLibro= consultaLibro.list();
		return listaLibro;
	}

	public static List<Prestamo> consultaPrestamos(int idUsuario) throws Exception {
		Query consultaPrestamo = sesion.createQuery("from Prestamo where idUsuario = ?");
		consultaPrestamo.setInteger(0, idUsuario);
		// Prestamo prestamoConsultado = (Prestamo)consultaPrestamo.uniqueResult();

		List<Prestamo> arrayDevuelto = (List<Prestamo>) consultaPrestamo.list();

		return arrayDevuelto;
	}

	public static void engadirUsuario(Usuario usuarioEnviado) throws Exception {
		Transaction transaccion = sesion.beginTransaction();

		Usuario usuarioAnadir = usuarioEnviado;
		sesion.save(usuarioAnadir); // ojo aqui

		transaccion.commit();
	}

	public static int insertarPrestamo(int libroId, int usuarioId) throws Exception {
		Libro libro = consultaLibro(libroId);
		Usuario usuario = consultaUsuario(usuarioId);
		if (libro == null) {
			return 3;
		}
		if (usuario == null) {
			return 4;  
		}
		if (!isDevolto(libroId)) {
			return 2;
		}
		Transaction transaccion = sesion.beginTransaction();
		sesion.save(new Prestamo(libro, usuario, new Date(), false));
		transaccion.commit();
		return 1;
	}

	public static int modificarUsuario(Usuario user, int id) throws Exception {
		Transaction transaccion = sesion.beginTransaction();
		Query modificarUsuario = sesion.createQuery("update Usuario set dni=?, nome=?, correoe=? where idUsuario=?");

		modificarUsuario.setString(0, user.getDni());
		modificarUsuario.setString(1, user.getNome());
		modificarUsuario.setString(2, user.getCorreoe());
		modificarUsuario.setInteger(3, id);

		int resultado = modificarUsuario.executeUpdate();
		transaccion.commit();

		return resultado;
	}

	public static int borrarUsuario(int id) throws Exception {
		Transaction transaccion = sesion.beginTransaction();
		Query borraUsuario = sesion.createQuery("delete from Usuario where idUsuario=?");
		borraUsuario.setInteger(0, id);

		int resultado = borraUsuario.executeUpdate();
		transaccion.commit();

		return resultado;
	}

	public static List<Libro> librosDevoltos() throws Exception{
//		Query query = sesion.createQuery("from Prestamo where devolto = ?");
//		query.setBoolean(0, devolto);
//		List<Prestamo> list = (List<Prestamo>) query.list();
//		List<Libro> listLibros = new ArrayList<>();
//		for (Prestamo prestamo : list) {
//			listLibros.add(prestamo.getLibro());
//		}
		List<Libro> librosDevoltos = new ArrayList<>();
		
		List<Libro> list = consultarLibros();
		for (Libro libro : list) {
			if (isDevolto(libro.getIdLibro()))
				librosDevoltos.add(libro);
		}
		return librosDevoltos;
	}
	
	public static int devolverLibro(int idLibro) throws Exception {
		int i = 0;
		Libro libro = consultaLibro(idLibro);
		if (libro != null && !isDevolto(idLibro)) {
			Query query = sesion.createQuery("update Prestamo set devolto = 1 where idLibro = ?");
			query.setInteger(0, idLibro);
			Transaction beginTransaction = sesion.beginTransaction();
			i = query.executeUpdate();
			beginTransaction.commit();
		}
		return i;
	}
	
}

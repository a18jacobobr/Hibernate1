import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import baseDatos.Libro;
import baseDatos.Prestamo;
import baseDatos.Usuario;
import operaciones.OperacionsUsuario;

public class Programa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int idUsuarioBuscar;
		int tamanoListPrestamo;
		
		
		Usuario user = new Usuario("123wwe", "ded22", "asdasd2");
		Usuario userModificado = new Usuario("owowow", "opa", "viahace");
		
		Scanner scan = new Scanner (System.in);
		
		
		
		try {
		
			OperacionsUsuario.abrirConexion();
			
			/*System.out.println("Introduce o id do usuario");
			idUsuarioBuscar = scan.nextInt();
			
//			Usuario usuarioBuscado = OperacionsUsuario.consultaUsuario(idUsuarioBuscar);
			//List<Prestamo> prestamoBuscado = OperacionsUsuario.consultaPrestamo(idUsuarioBuscar);
			Set<Prestamo> prestamoBuscado = usuarioBuscado.getPrestamos();
	
			System.out.println("Se encontraron "+prestamoBuscado.size());
			for (Prestamo pou : prestamoBuscado) {
				System.out.println("Prestamo numero: "+pou.getIdPrestamo()+" Libro: "+pou.getLibro().getTitulo()+" Usuario: "+usuarioBuscado.getNome()+" fecha: "+pou.getData()+" Devolto: "+pou.getDevolto());
			}*/
			
			
			
			//OperacionsUsuario.engadirUsuario(user);
			
			//System.out.println("Se ha modificado "+OperacionsUsuario.modificarUsuario(userModificado, 3)+" resultado");
			
//			userModificado.setIdUsuario(3);
//			System.out.println("Se ha modificado "+OperacionsUsuario.borrarUsuario(userModificado.getIdUsuario())+" resultado");
			
//			List<Usuario> list = OperacionsUsuario.listadoUsuario();
//			
//			for (Usuario usu : list) {
//				System.out.println(usu.toString());
//			}
//			
			
//			List<Libro> librosNonDevoltos = OperacionsUsuario.librosDevoltos();
////			for (Libro libro : librosNonDevoltos) {
////				System.out.println(libro);
//			}
			
			System.out.println(OperacionsUsuario.devolverLibro(3));
//			System.out.println(OperacionsUsuario.insertarPrestamo(3,1));
			OperacionsUsuario.cerrarConexion();
			scan.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

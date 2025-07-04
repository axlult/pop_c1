package org.demoClasePooc125.view;

import org.demoClasePooc125.controller.EstudianteController;
import org.demoClasePooc125.model.Estudiante;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EstudianteView {

    private static EstudianteController controller = new EstudianteController();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n--- Menú Estudiantes ---");
            System.out.println("1. Insertar estudiante");
            System.out.println("2. Ver estudiantes");
            System.out.println("3. Actualizar estudiante");
            System.out.println("4. Eliminar estudiante");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpia buffer

            switch (opcion) {
                case 1:
                    insertarEstudiante();
                    break;
                case 2:
                    obtenerEstudiantes();
                    break;
                case 3:
                    actualizarEstudiante();
                    break;
                case 4:
                    eliminarEstudiante();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void insertarEstudiante() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Identificación: ");
        String identificacion = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        LocalDate fechaNacimiento = LocalDate.parse(sc.nextLine());

        System.out.print("Estado (activo/inactivo): ");
        String estado = sc.nextLine();

        Estudiante estudiante = new Estudiante(nombre, identificacion, email, fechaNacimiento, estado);
        controller.insertarEstudiante(estudiante);
        System.out.println("Estudiante insertado correctamente.");
    }

    private static void obtenerEstudiantes() {
        List<Estudiante> lista = controller.obtenerEstudiantes();

        for (Estudiante e : lista) {
            System.out.println("ID: " + e.getId() +
                    "\tNombre: " + e.getNombre() +
                    "\tIdentificación: " + e.getIdentificacion() +
                    "\tEmail: " + e.getEmail() +
                    "\tNacimiento: " + e.getfecha_nacimiento() +
                    "\tEstado: " + e.getEstado());
        }
    }

    private static void actualizarEstudiante() {
        System.out.print("ID del estudiante a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine(); // limpia buffer

        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nueva identificación: ");
        String identificacion = sc.nextLine();

        System.out.print("Nuevo email: ");
        String email = sc.nextLine();

        System.out.print("Nueva fecha de nacimiento (YYYY-MM-DD): ");
        LocalDate fechaNacimiento = LocalDate.parse(sc.nextLine());

        System.out.print("Nuevo estado (activo/inactivo): ");
        String estado = sc.nextLine();

        Estudiante estudiante = new Estudiante(id, nombre, identificacion, email, fechaNacimiento, estado);
        controller.actualizarEstudiante(estudiante);
        System.out.println("Estudiante actualizado correctamente.");
    }

    private static void eliminarEstudiante() {
        System.out.print("ID del estudiante a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine(); // limpia buffer

        controller.eliminarEstudiante
                (id);
        System.out.println("Estudiante eliminado correctamente.");
    }
}

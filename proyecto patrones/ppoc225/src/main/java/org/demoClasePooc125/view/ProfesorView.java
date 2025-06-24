package org.demoClasePooc125.view;

import org.demoClasePooc125.controller.ProfesorController;
import org.demoClasePooc125.model.Profesor;

import java.util.List;
import java.util.Scanner;

public class ProfesorView {

    private static ProfesorController controller = new ProfesorController();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    insertarProfesor();
                    break;
                case 2:
                    obtenerProfesores();
                    break;
                case 3:
                    actualizarProfesor();
                    break;
                case 4:
                    eliminarProfesor();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú de Profesores ---");
        System.out.println("1. Insertar profesor");
        System.out.println("2. Ver lista de profesores");
        System.out.println("3. Actualizar profesor");
        System.out.println("4. Eliminar profesor");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public static void obtenerProfesores() {
        List<Profesor> lista = controller.obtenerProfesores();

        for (Profesor profesor : lista) {
            System.out.println("ID: " + profesor.getId() +
                    "\tNombre: " + profesor.getNombre() +
                    "\tIdentificación: " + profesor.getIdentificacion() +
                    "\tEmail: " + profesor.getEmail() +
                    "\tDepartamento: " + profesor.getDepartamento() +
                    "\tEstado: " + profesor.getEstado());
        }
    }

    private static void insertarProfesor() {
        System.out.println("Ingrese el nombre del profesor:");
        String nombre = sc.nextLine();

        System.out.println("Ingrese la identificación del profesor:");
        String identificacion = sc.nextLine();

        System.out.println("Ingrese el email del profesor:");
        String email = sc.nextLine();

        System.out.println("Ingrese el departamento del profesor:");
        String departamento = sc.nextLine();

        System.out.println("Ingrese el estado del profesor:");
        String estado = sc.nextLine();

        Profesor profesor = new Profesor(nombre, identificacion, email, departamento, estado);
        controller.insertarProfesor(profesor);
        System.out.println("Profesor insertado correctamente.");
    }

    private static void actualizarProfesor() {
        System.out.print("Ingrese el ID del profesor a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nueva identificación: ");
        String identificacion = sc.nextLine();

        System.out.print("Nuevo email: ");
        String email = sc.nextLine();

        System.out.print("Nuevo departamento: ");
        String departamento = sc.nextLine();

        System.out.print("Nuevo estado: ");
        String estado = sc.nextLine();

        Profesor profesor = new Profesor(id, nombre, identificacion, email, departamento, estado);
        controller.actualizarProfesor(profesor);
        System.out.println("Profesor actualizado correctamente.");
    }

    private static void eliminarProfesor() {
        System.out.print("Ingrese el ID del profesor a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine();

        controller.eliminarProfesor(id);
        System.out.println("Profesor eliminado correctamente.");
    }
}

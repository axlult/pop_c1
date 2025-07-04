package org.demoClasePooc125.view;

import org.demoClasePooc125.controller.CursoController;
import org.demoClasePooc125.model.Curso;

import java.util.List;
import java.util.Scanner;

public class CursoView {

    private static CursoController controller = new CursoController();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n--- Menú de Cursos ---");
            System.out.println("1. Insertar curso");
            System.out.println("2. Ver cursos");
            System.out.println("3. Actualizar curso");
            System.out.println("4. Eliminar curso");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    insertarCurso();
                    break;
                case 2:
                    obtenerCursos();
                    break;
                case 3:
                    actualizarCurso();
                    break;
                case 4:
                    eliminarCurso();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private static void insertarCurso() {
        System.out.print("Nombre del curso: ");
        String nombre = sc.nextLine();

        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        System.out.print("Estado (activo/inactivo): ");
        String estado = sc.nextLine();

        Curso curso = new Curso(nombre, descripcion, estado);
        controller.insertarCurso(curso);
        System.out.println("Curso insertado correctamente.");
    }

    private static void obtenerCursos() {
        List<Curso> lista = controller.obtenCurso();

        for (Curso c : lista) {
            System.out.println("ID: " + c.getId() +
                    "\tNombre: " + c.getNombre() +
                    "\tDescripción: " + c.getDescripcion() +
                    "\tEstado: " + c.getEstado());
        }
    }

    private static void actualizarCurso() {
        System.out.print("ID del curso a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nueva descripción: ");
        String descripcion = sc.nextLine();

        System.out.print("Nuevo estado (activo/inactivo): ");
        String estado = sc.nextLine();

        Curso curso = new Curso(id, nombre, descripcion, estado);
        controller.actualizarCurso(curso);
        System.out.println("Curso actualizado correctamente.");
    }

    private static void eliminarCurso() {
        System.out.print("ID del curso a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        controller.eliminarCurso(id);
        System.out.println("Curso eliminado correctamente.");
    }
}

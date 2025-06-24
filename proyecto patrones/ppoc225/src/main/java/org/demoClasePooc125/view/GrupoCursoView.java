package org.demoClasePooc125.view;

import org.demoClasePooc125.controller.GrupoCursoController;
import org.demoClasePooc125.model.GrupoCurso;

import java.util.List;
import java.util.Scanner;

public class GrupoCursoView {

    private static GrupoCursoController controller = new GrupoCursoController();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    insertarGrupoCurso();
                    break;
                case 2:
                    obtenerGrupoCurso();
                    break;
                case 3:
                    actualizarGrupoCurso();
                    break;
                case 4:
                    eliminarGrupoCurso();
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
        System.out.println("\n--- Menú de GrupoCurso ---");
        System.out.println("1. Insertar relación grupo-curso");
        System.out.println("2. Ver relaciones grupo-curso");
        System.out.println("3. Actualizar relación");
        System.out.println("4. Eliminar relación");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void insertarGrupoCurso() {
        System.out.print("Ingrese el ID del grupo: ");
        int grupoId = sc.nextInt();
        System.out.print("Ingrese el ID del curso: ");
        int cursoId = sc.nextInt();

        GrupoCurso nuevaRelacion = new GrupoCurso(grupoId, cursoId);
        controller.insertarGrupoCurso(nuevaRelacion);
        System.out.println("Relación insertada correctamente.");
    }

    private static void obtenerGrupoCurso() {
        List<GrupoCurso> lista = controller.obtenerGrupoCurso();

        for (GrupoCurso item : lista) {
            System.out.println("ID: " + item.getId() +
                    "\tGrupo ID: " + item.getGrupoId() +
                    "\tCurso ID: " + item.getCursoId());
        }
    }

    private static void actualizarGrupoCurso() {
        System.out.print("Ingrese el ID de la relación a actualizar: ");
        int id = sc.nextInt();

        System.out.print("Nuevo ID del grupo: ");
        int nuevoGrupoId = sc.nextInt();

        System.out.print("Nuevo ID del curso: ");
        int nuevoCursoId = sc.nextInt();

        GrupoCurso relacion = new GrupoCurso(id, nuevoGrupoId, nuevoCursoId);
        controller.actualizarGrupoCurso(relacion);
        System.out.println("Relación actualizada correctamente.");
    }

    private static void eliminarGrupoCurso() {
        System.out.print("Ingrese el ID de la relación a eliminar: ");
        int id = sc.nextInt();

        controller.eliminarGrupoCurso(id);
        System.out.println("Relación eliminada correctamente.");
    }
}

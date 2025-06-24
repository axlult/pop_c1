package org.demoClasePooc125.view;

import org.demoClasePooc125.controller.GrupoController;
import org.demoClasePooc125.model.Grupo;

import java.util.List;
import java.util.Scanner;

public class GrupoView {

    private static GrupoController controller = new GrupoController();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    insertarGrupo();
                    break;
                case 2:
                    obtenGrupo();
                    break;
                case 3:
                    actualizarGrupo();
                    break;
                case 4:
                    eliminarGrupo();
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
        System.out.println("\n--- Menú de Grupos ---");
        System.out.println("1. Insertar grupo");
        System.out.println("2. Ver grupos");
        System.out.println("3. Actualizar grupo");
        System.out.println("4. Eliminar grupo");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void obtenGrupo() {
        List<Grupo> lista = controller.obtenGrupo();

        for (Grupo itemGrupo : lista) {
            System.out.println("ID: " + itemGrupo.getId() +
                    "\tNombre: " + itemGrupo.getNombre() +
                    "\tDescripción: " + itemGrupo.getDescripcion() +
                    "\tEstado: " + itemGrupo.getEstado());
        }
    }

    private static void insertarGrupo() {
        System.out.print("Ingrese el nombre del grupo: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese la descripción del grupo: ");
        String descripcion = sc.nextLine();

        System.out.print("Ingrese el estado del grupo: ");
        String estado = sc.nextLine();

        controller.insertarGrupo(new Grupo(nombre, descripcion, estado));
        System.out.println("Grupo insertado correctamente.");
    }

    private static void actualizarGrupo() {
        System.out.print("Ingrese el ID del grupo a modificar: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nueva descripción: ");
        String descripcion = sc.nextLine();

        System.out.print("Nuevo estado: ");
        String estado = sc.nextLine();

        controller.actualizarGrupo(new Grupo(id, nombre, descripcion, estado));
        System.out.println("Grupo actualizado correctamente.");
    }

    private static void eliminarGrupo() {
        System.out.print("Ingrese el ID del grupo a eliminar: ");
        int id = sc.nextInt();
        controller.eliminarGrupo(id);
        System.out.println("Grupo eliminado correctamente.");
    }
}

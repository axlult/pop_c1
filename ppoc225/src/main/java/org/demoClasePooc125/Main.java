package org.demoClasePooc125;
import org.demoClasePooc125.view.CursoView;
import org.demoClasePooc125.view.GrupoView;
import org.demoClasePooc125.view.GrupoCursoView;
import org.demoClasePooc125.view.ProfesorView;
import org.demoClasePooc125.view.EstudianteView;

import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN ===");
            System.out.println("1. Gestión de Grupos");
            System.out.println("2. Gestión de Estudiantes");
            System.out.println("3. Gestión de Profesores");
            System.out.println("4. Gestión de Cursos");
            System.out.println("5. Relación Grupo-Curso");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    GrupoView.main(null);
                    break;
                case 2:
                    EstudianteView.main(null);
                    break;
                case 3:
                    ProfesorView.main(null);
                    break;
                case 4:
                    CursoView.main(null);
                    break;
                case 5:
                    GrupoCursoView.main(null);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
}
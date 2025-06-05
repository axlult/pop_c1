package org.demoClasePooc125.view;

import org.demoClasePooc125.controller.GrupoController;
import org.demoClasePooc125.model.Grupo;

import java.util.List;
import java.util.Scanner;

public class GrupoView {

    private static GrupoController controller = new GrupoController();
    private static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        insertarGrupo();
        obtenGrupo();
    }



    private static void obtenGrupo(){
        List<Grupo> lista = controller.obtenGrupo();

        for(Grupo itemGrupo : lista){
            System.out.println("id: " + itemGrupo.getId() + "\tNombre: " + itemGrupo.getNombre() + "\tDescripcion: " + itemGrupo.getDescripcion() + "\tEstado: " + itemGrupo.getEstado());
        }
    }


    private static void insertarGrupo() {
//        Grupo itemGrupo
        System.out.println("Ingrese el nombre del grupo: ");
        String nombre = sc.next();
        System.out.println("Ingrese el descripcion del grupo: ");
        String descripcion = sc.next();
        System.out.println("Ingrese el estado del grupo: ");
        String estado = sc.next();

        controller.insertarGrupo(new Grupo(nombre, descripcion, estado));
    }

}

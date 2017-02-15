/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.curso.nota;

import java.util.List;

/**
 *
 * @author Usuario1
 */
public interface INotaDAO {
   public int Persistir(sae.dominio.curso.nota.Nota nota);
   public int Modificar(long id,sae.dominio.curso.nota.Nota nota);
   public sae.dominio.curso.nota.Nota Buscar(long id);
   public int Eliminar(long id);
   public List<sae.dominio.curso.nota.Nota> Busqueda(long idcurso,String busqueda);    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.dominio.corte;

import java.util.List;

/**
 *
 * @author Usuario1
 */
public interface ICorteEscolarDAO {
   public int Persistir(sae.dominio.corte.CorteEscolar corte);
   public int Modificar(long id,sae.dominio.corte.CorteEscolar corte);
   public sae.dominio.corte.CorteEscolar Buscar(long id);
   public int Eliminar(long id);
   public List<sae.dominio.corte.CorteEscolar> Busqueda(final String busqueda);
   public sae.dominio.corte.CorteEscolar ObtenerCorteVigente();
   public int Desmarcar(long id);
}

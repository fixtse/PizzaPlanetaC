package com.ulima.sw.pizzaplanetac.listado;

/**
 * Created by Admin on 6/05/2016.
 */
public interface ListadoPizzasPresenter {
    public void obtenerListaP(int idpizzas);
    public void actualizarEstado(int idPedido, String usuario, String distrito);
}

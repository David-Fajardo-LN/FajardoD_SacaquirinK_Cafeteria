/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDecorador;

/**
 *
 * @author User
 */
public abstract class IngredienteAdicional implements ComponenteBase{
    
    protected final ComponenteBase componente;

    public IngredienteAdicional(ComponenteBase componente) {
        this.componente = componente;
    }

    @Override
    public String getNombre() {
        return componente.getNombre();
    }

    @Override
    public double getPrecioTotal() {
        return componente.getPrecioTotal()+ getPrecioIndividual();
    }
    
    public abstract double getPrecioIndividual();
}

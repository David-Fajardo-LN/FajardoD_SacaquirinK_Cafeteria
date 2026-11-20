/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDecorador;

/**
 *
 * @author User
 */
public class PanDeAjo extends IngredienteAdicional{

    public PanDeAjo(ComponenteBase componente) {
        super(componente);
    }

    @Override
    public double getPrecioIndividual() {
        return 2;
    }

    @Override
    public String getNombre() {
        return "Pan de ajo";
    }
    
    
    
}

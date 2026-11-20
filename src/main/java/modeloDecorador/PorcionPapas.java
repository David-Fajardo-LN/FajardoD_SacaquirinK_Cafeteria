/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDecorador;

/**
 *
 * @author User
 */
public class PorcionPapas extends IngredienteAdicional {
    
    public PorcionPapas(ComponenteBase componente) {
        super(componente);
    }

    @Override
    public double getPrecioIndividual() {
        return 3;
    }

    @Override
    public String getNombre() {
        return "Porcion de papas";
    }
    
    
    
}

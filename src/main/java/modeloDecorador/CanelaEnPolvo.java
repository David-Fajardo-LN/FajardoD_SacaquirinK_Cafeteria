/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDecorador;

/**
 *
 * @author User
 */
public class CanelaEnPolvo extends IngredienteAdicional{

    public CanelaEnPolvo(ComponenteBase componente) {
        super(componente);
    }
    
    @Override
    public double getPrecioIndividual(){
        return 0.25;
    }

    @Override
    public String getNombre() {
        return "Canela en polvo";
    }
    
    
    
}

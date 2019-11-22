package iut.dames.damier;

/**
 * Permet de définir un déplacement sur un damier.
 */
public class DamierDeplacement{

    Damier damier;
    Deplacement deplacement;

    /**
     * Crée une nouvelle instance de déplacement sur un damier passé en paramètre
     * @param damier un damier
     */    
    public DamierDeplacement(Damier damier){
	this.damier = damier;
    }

    /**
     * Crée un nouvelle instance de déplacement sur un damier avec pour paramètre un damier et un déplacement
     * @param damier Le damier sur lequel se fait le déplacement
     * @param deplacement Le déplacement effectué
     */    
    public DamierDeplacement(Damier damier, Deplacement deplacement){
	this.damier = damier;
	this.deplacement = deplacement;
    }


    /**
     * Get the Damier value.
     * @return the Damier value.
     */
    public Damier getDamier() {
	return damier;
    }

    /**
     * Set the Damier value.
     * @param damier The new Damier value.
     */
    public void setDamier(Damier damier) {
	this.damier = damier;
    }

    /**
     * Get the Deplacement value.
     * @return the Deplacement value.
     */
    public Deplacement getDeplacement() {
	return deplacement;
    }

    /**
     * Set the Deplacement value.
     * @param deplacement The new Deplacement value.
     */
    public void setDeplacement(Deplacement deplacement) {
	this.deplacement = deplacement;
    }

    

}
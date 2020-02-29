package fr.gsb.visprat;

import static org.junit.Assert.*;
import org.junit.Test;
import fr.gsb.visprat.metier.Medecin;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MedecinTest {
    @Test
    public void constructeurTest() {
        Medecin unMedecin;
        unMedecin = new Medecin(10, "Durand", "Paul", "Rennes");
        assertNotNull(unMedecin);
        assertEquals(10, unMedecin.getId());
        assertEquals("Durand", unMedecin.getNom());
        assertEquals("Paul", unMedecin.getPrenom());
        assertEquals("Rennes", unMedecin.getVille());
    }
}
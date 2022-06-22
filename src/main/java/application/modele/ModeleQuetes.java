package application.modele;

import application.modele.quetes.BaseQuete;
import application.modele.quetes.QueteType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class ModeleQuetes {

    private ArrayList<BaseQuete> quetesDisponibles;
    private IntegerProperty queteActuelProperty;

    public ModeleQuetes() {
        quetesDisponibles = new ArrayList<BaseQuete>();
        this.queteActuelProperty = new SimpleIntegerProperty(-1);
        chargerQuetes();
    }

    public void chargerQuete(int indexQuete) {
        this.queteActuelProperty.setValue(indexQuete);
    }


    public BaseQuete getQueteActuel() {
        if(this.queteActuelProperty.getValue() >= 0) {
            return this.quetesDisponibles.get(this.queteActuelProperty.getValue());
        }
        return null;
    }

    public IntegerProperty getQueteActuelProperty() {
        return queteActuelProperty;
    }

    public void chargerQuetes() {
        BaseQuete bq = new BaseQuete("Renforce toi");
        bq.ajouterObjectif(QueteType.TYPE_QUETE.RAMASSER, "Terre", 3);

        this.quetesDisponibles.add(bq);
    }
}

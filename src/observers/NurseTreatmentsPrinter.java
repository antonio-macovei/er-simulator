package observers;

import entities.Patient;
import utils.Simulation;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public final class NurseTreatmentsPrinter implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Simulation simulation = Simulation.getInstance();
        int nurses = simulation.getNurses();
        List<Patient> patients = simulation.getPatients();
        System.out.println("~~~~ Nurses treat patients ~~~~");
        int nurseId = 0;
        for (Patient p : patients) {
            if (p.isHospitalised()) {
                System.out.print("Nurse " + nurseId % nurses + " treated " + p.getName());
                String s = "";
                if (p.getTreatment() != 1) {
                    s = "s";
                }
                System.out.println(" and patient has " + p.getTreatment() + " more round" + s);
                nurseId++;
            }
        }
        System.out.print("\n");
    }

}

package observers;

import entities.Patient;
import utils.PatientsComparator;
import utils.Simulation;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public final class PatientPrinter implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Simulation simulation = Simulation.getInstance();
        int round = simulation.getCurrentRound();
        List<Patient> patients = simulation.getPatients();
        Collections.sort(patients, new PatientsComparator());
        System.out.println("~~~~ Patients in round " + round + " ~~~~");
        for (Patient p : patients) {
            System.out.println(p.getName() + " is " + p.getCurrentState().getValue());
        }
        System.out.print("\n");
    }

}

package observers;

import entities.Doctor;
import entities.Patient;
import types.DoctorVerdict;
import utils.PatientsComparator;
import utils.Simulation;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public final class DoctorVerdictsPrinter implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Simulation simulation = Simulation.getInstance();
        List<Doctor> doctors = simulation.getDoctors();
        System.out.println("~~~~ Doctors check their hospitalized patients and give verdicts ~~~~");
        for (Doctor d : doctors) {
            List<Patient> hospitalisedPatients = d.getHospitalisedPatients();
            PatientsComparator pc = new PatientsComparator();
            Collections.sort(hospitalisedPatients, pc);
            for (Patient p : hospitalisedPatients) {
                if (p.getVerdict() == DoctorVerdict.SENT_HOME) {
                    System.out.println(d.getSpecialty().getValue() + " sent "
                            + p.getName() + " home");
                }
                if (p.getVerdict() == DoctorVerdict.STILL_HOSPITALIZED) {
                    System.out.println(d.getSpecialty().getValue() + " says that "
                            + p.getName() + " should remain in hospital");
                }
            }
        }
        System.out.print("\n");
    }
}

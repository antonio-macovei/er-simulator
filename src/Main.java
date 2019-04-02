import com.fasterxml.jackson.databind.ObjectMapper;
import utils.Simulation;
import observers.QueueObserver;
import observers.NurseObserver;
import observers.DoctorObserver;
import observers.PatientPrinter;
import observers.NurseTreatmentsPrinter;
import observers.DoctorVerdictsPrinter;

import java.io.File;
import java.io.IOException;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Invalid arguments");
            System.exit(-1);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        File inputFile = new File(args[0]);

        Simulation tmp = objectMapper.readValue(inputFile, Simulation.class);
        Simulation.setInstance(tmp); // workaround for jackson mapping to singleton

        Simulation simulation = Simulation.getInstance();

        QueueObserver queueObserver = new QueueObserver();
        NurseObserver nurseObserver = new NurseObserver();
        DoctorObserver doctorObserver = new DoctorObserver();
        PatientPrinter patientPrinter = new PatientPrinter();
        NurseTreatmentsPrinter nurseTreatmentsPrinter = new NurseTreatmentsPrinter();
        DoctorVerdictsPrinter doctorVerdictsPrinter = new DoctorVerdictsPrinter();

        simulation.addObserver(doctorObserver);
        simulation.addObserver(nurseObserver);
        simulation.addObserver(queueObserver);

        queueObserver.addObserver(patientPrinter);
        nurseObserver.addObserver(nurseTreatmentsPrinter);
        doctorObserver.addObserver(doctorVerdictsPrinter);

        simulation.initializeAvailableDoctors();

        while (simulation.getCurrentRound() <= simulation.getSimulationLength()) {
            simulation.handleIncomingPatients();
            simulation.update();
            simulation.nextRound();
        }
    }
}

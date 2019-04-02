package utils;

import entities.Doctor;
import entities.Patient;
import types.IllnessType;
import types.InvestigationResult;
import types.State;
import types.Urgency;
import types.CureFactors;
import types.DoctorVerdict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public final class Simulation extends Observable {

    private static Simulation instance = null;

    private int simulationLength;

    private int currentRound = 1;

    private int nurses;

    private int investigators;

    private List<Doctor> doctors = new ArrayList<>();

    private List<Patient> incomingPatients = new ArrayList<>();

    private List<Patient> patients = new ArrayList<>();

    private List<Patient> triageQueue = new ArrayList<>();

    private List<Patient> examinationQueue = new ArrayList<>();

    private List<Patient> investigationQueue = new ArrayList<>();

    private List<Doctor> availableDoctors = new ArrayList<>();

    public static final int ERTECH_C1 = 75;
    public static final int ERTECH_C2 = 40;

    /**
     * Move patients from the incoming queue to the triage queue.
     */
    public void handleIncomingPatients() {
        List<Patient> arrivedPatients = new ArrayList<>();
        for (Patient p : incomingPatients) {
            if (currentRound == p.getTime() + 1) {
                arrivedPatients.add(p);
                p.setCurrentState(State.TRIAGEQUEUE);
            }
        }
        triageQueue.addAll(arrivedPatients);
        patients.addAll(arrivedPatients);
        incomingPatients.removeAll(arrivedPatients);
    }

    /**
     * Nurses estimate patients urgency and send them in the examinations queue.
     */
    public void handleTriageQueue() {
        TriageQueueComparator tqc = new TriageQueueComparator();
        Collections.sort(triageQueue, tqc);

        List<Patient> toBeRemoved = new ArrayList<>();
        int checkedPatients = 0;
        for (Patient p : triageQueue) {
            checkedPatients++;
            if (checkedPatients > nurses) {
                break; // if there are not enough nurses, break the loop
            }

            // estimate the urgency and set it
            UrgencyEstimator urgencyEstimator = UrgencyEstimator.getInstance();
            IllnessType illnessType = p.getIllness().getIllnessType();
            int severity = p.getIllness().getSeverity();
            Urgency urgency = urgencyEstimator.estimateUrgency(illnessType, severity);
            p.setUrgency(urgency);

            p.setCurrentState(State.EXAMINATIONSQUEUE);
            examinationQueue.add(p);
            toBeRemoved.add(p);
        }
        triageQueue.removeAll(toBeRemoved);
    }

    /**
     * Doctors check patients arrived for the first time in this queue and send them
     * either in the investigations queue or home with treatment. Also, patients arrived
     * from the investigations queue are either operated, hospitalised, transferred
     * to another hospital or sent home with treatment.
     */
    public void handleExaminationQueue() {
        ExaminationQueueComparator eqc = new ExaminationQueueComparator();
        Collections.sort(examinationQueue, eqc);

        List<Patient> toBeRemoved = new ArrayList<>();
        for (Patient p : examinationQueue) {
            // get the first doctor in list for current patient's affection
            Doctor d = p.findDoctor(availableDoctors);
            if (d == null) {
                continue; // no doctor found for his affection
            }

            if (p.getInvestigationResult() == InvestigationResult.NOT_DIAGNOSED) {
                // he is in this queue for the first time

                if (p.getIllness().getSeverity() <= d.getMaxForTreatment()) {
                    // send him home with treatment
                    p.setCurrentState(d.getPatientState(InvestigationResult.TREATMENT));
                } else {
                    // send him to investigations
                    investigationQueue.add(p);
                    p.setCurrentState(State.INVESTIGATIONSQUEUE);
                }
            } else {
                // he is in this queue back from investigations
                if (p.getInvestigationResult() == InvestigationResult.OPERATE) {
                    if (!d.getIsSurgeon()) {
                        d = p.findSurgeon(availableDoctors);
                    }
                    if (d == null) {
                        // transfer him to another hospital
                        p.setCurrentState(State.OTHERHOSPITAL);
                    } else {
                        // operate and hospitalize him
                        d.operatePatient(p);
                        d.hospitalizePatient(p);
                        p.setCurrentState(d.getPatientState(InvestigationResult.OPERATE));
                    }
                }
                if (p.getInvestigationResult() == InvestigationResult.HOSPITALIZE) {
                    // hospitalize him
                    p.setCurrentState(d.getPatientState(InvestigationResult.HOSPITALIZE));
                    d.hospitalizePatient(p);
                }
                if (p.getInvestigationResult() == InvestigationResult.TREATMENT) {
                    // send him home with treatment
                    p.setCurrentState(d.getPatientState(InvestigationResult.TREATMENT));
                }
            }

            toBeRemoved.add(p);

            // remove the doctor from the list and add it to the back
            if (d != null) {
                availableDoctors.remove(d);
                availableDoctors.add(d);
            }
        }
        examinationQueue.removeAll(toBeRemoved);
    }

    /**
     * ER Technicians suggest whether a patient needs an operation, to be hospitalised
     * or to be sent home with a treatment based on their illness severity. Then,
     * patients return in the examinations queue for the doctor to make a decision.
     */
    public void handleInvestigationQueue() {
        ExaminationQueueComparator eqc = new ExaminationQueueComparator();
        Collections.sort(investigationQueue, eqc);

        List<Patient> toBeRemoved = new ArrayList<>();
        int checkedPatients = 0;
        for (Patient p : investigationQueue) {
            checkedPatients++;
            if (checkedPatients > investigators) {
                break; // if there are not enough investigators, break the loop
            }
            int s = p.getIllness().getSeverity();
            if (s <= ERTECH_C2) {
                // the patient will be sent home with treatment
                p.setInvestigationResult(InvestigationResult.TREATMENT);
            } else if (s <= ERTECH_C1) {
                // the patient needs to be hospitalised
                p.setInvestigationResult(InvestigationResult.HOSPITALIZE);
            } else {
                // the patient needs operation
                p.setInvestigationResult(InvestigationResult.OPERATE);
            }
            p.setCurrentState(State.EXAMINATIONSQUEUE);
            examinationQueue.add(p);
            toBeRemoved.add(p);
        }
        investigationQueue.removeAll(toBeRemoved);
    }

    /**
     * Nurses check hospitalised patients and give them their treatment.
     */
    public void handlePatientsTreatment() {
        for (Patient p : patients) {
            if (p.isHospitalised()) {
                p.setTreatment(p.getTreatment() - 1);
                p.getIllness().setSeverity(p.getIllness().getSeverity()
                        - (int) CureFactors.ALL_T.getFactor());
            }
        }
    }

    /**
     * Doctors check their hospitalised patients and discharge them from the hospital
     * if their treatment is done or their severity is zero.
     */
    public void handleDoctorsDischarge() {
        for (Doctor d : doctors) {
            List<Patient> toBeRemoved = new ArrayList<>();
            for (Patient p : d.getHospitalisedPatients()) {
                if (!p.isHospitalised()) {
                    // if the patient was discharged in the past round, remove
                    // him from the doctor's list
                    toBeRemoved.add(p);
                } else {
                    if (p.getIllness().getSeverity() <= 0 || p.getTreatment() <= 0) {
                        // treatment is done or severity reached zero
                        p.setHospitalised(false);
                        p.setCurrentState(State.HOME_DONE_TREATMENT);
                        p.setVerdict(DoctorVerdict.SENT_HOME);
                    } else {
                        // still needs to be hospitalised
                        p.setVerdict(DoctorVerdict.STILL_HOSPITALIZED);
                    }
                }
            }
            d.getHospitalisedPatients().removeAll(toBeRemoved);
        }
    }

    public void initializeAvailableDoctors() {
        availableDoctors.addAll(doctors);
    }

    private Simulation() {

    }

    public static Simulation getInstance() {
        if (instance == null) {
            instance = new Simulation();
        }
        return instance;
    }

    public static void setInstance(Simulation s) {
        // workaround for jackson mapping to singleton
        instance = s;
    }

    public void update() {
        this.setChanged();
        this.notifyObservers();
    }

    public int getSimulationLength() {
        return simulationLength;
    }

    public void setSimulationLength(int simulationLength) {
        this.simulationLength = simulationLength;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void nextRound() {
        this.currentRound++;
    }

    public int getNurses() {
        return nurses;
    }

    public void setNurses(int nurses) {
        this.nurses = nurses;
    }

    public int getInvestigators() {
        return investigators;
    }

    public void setInvestigators(int investigators) {
        this.investigators = investigators;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Patient> getIncomingPatients() {
        return incomingPatients;
    }

    public void setIncomingPatients(List<Patient> incomingPatients) {
        this.incomingPatients = incomingPatients;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}

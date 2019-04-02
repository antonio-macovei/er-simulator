package utils;

import entities.Patient;

import java.util.Comparator;

/**
 * Sort patients by severity in descending order.
 */
public final class TriageQueueComparator implements Comparator<Patient> {

    @Override
    public int compare(Patient firstPatient, Patient secondPatient) {
        return (secondPatient.getIllness().getSeverity() - firstPatient.getIllness().getSeverity());
    }

}

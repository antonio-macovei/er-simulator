package utils;

import entities.Patient;

import java.util.Comparator;

/**
 * Sort patients by urgency and severity in descending order and then alphabetically in
 * ascending order.
 */
public final class ExaminationQueueComparator implements Comparator<Patient> {

    @Override
    public int compare(Patient firstPatient, Patient secondPatient) {
        int compResult = firstPatient.getUrgency().compareTo(secondPatient.getUrgency());
        if (compResult != 0) {
            return compResult;
        }
        compResult = secondPatient.getIllness().getSeverity()
                - firstPatient.getIllness().getSeverity();
        if (compResult != 0) {
            return compResult;
        }
        compResult = secondPatient.getName().compareTo(firstPatient.getName());
        return compResult;
    }

}

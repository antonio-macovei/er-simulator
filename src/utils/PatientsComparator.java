package utils;

import entities.Patient;

import java.util.Comparator;

/**
 * Sort patients alphabetically.
 */
public final class PatientsComparator implements Comparator<Patient> {

    @Override
    public int compare(Patient firstPatient, Patient secondPatient) {
        return (firstPatient.getName().compareTo(secondPatient.getName()));
    }

}

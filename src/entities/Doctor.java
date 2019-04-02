package entities;

import com.fasterxml.jackson.annotation.JsonSetter;
import types.InvestigationResult;
import types.DoctorAffections;
import types.DoctorType;
import types.IllnessType;
import types.CureFactors;
import types.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Doctor {

    private DoctorType specialty;

    private boolean isSurgeon;

    private int maxForTreatment;

    private List<Patient> hospitalisedPatients = new ArrayList<>();

    private static final double ROUND_HELPER = 0.5;
    private static final int TREATMENT_HELPER = 3;

    /**
     * Check if the doctor can cure specific illness.
     */
    public boolean canCureAffection(IllnessType illness) {
        IllnessType[] affections;
        switch (this.specialty) {
            case CARDIOLOGIST:
                affections = DoctorAffections.CARDIOLOGIST.getAffections();
                break;
            case ER_PHYSICIAN:
                affections = DoctorAffections.ER_PHYSICIAN.getAffections();
                break;
            case GASTROENTEROLOGIST:
                affections = DoctorAffections.GASTROENTEROLOGIST.getAffections();
                break;
            case GENERAL_SURGEON:
                affections = DoctorAffections.GENERAL_SURGEON.getAffections();
                break;
            case INTERNIST:
                affections = DoctorAffections.INTERNIST.getAffections();
                break;
            case NEUROLOGIST:
                affections = DoctorAffections.NEUROLOGIST.getAffections();
                break;
            default:
                return false;
        }
        return Arrays.asList(affections).contains(illness);
    }

    /**
     * Doctor hospitalizes the patient and decides his treatment according to the
     * cure factor.
     */
    public void hospitalizePatient(Patient p) {
        int severity = p.getIllness().getSeverity();
        double cureFactor;

        switch (this.specialty) {
            case CARDIOLOGIST:
                cureFactor = CureFactors.CARDIOLOGIST_C1.getFactor();
                break;
            case ER_PHYSICIAN:
                cureFactor = CureFactors.ER_PHYSICIAN_C1.getFactor();
                break;
            case GASTROENTEROLOGIST:
                cureFactor = CureFactors.GASTROENTEROLOGIST_C1.getFactor();
                break;
            case GENERAL_SURGEON:
                cureFactor = CureFactors.GENERAL_SURGEON_C1.getFactor();
                break;
            case INTERNIST:
                cureFactor = CureFactors.INTERNIST_C1.getFactor();
                break;
            case NEUROLOGIST:
                cureFactor = CureFactors.NEUROLOGIST_C1.getFactor();
                break;
            default:
                cureFactor = 0.0;
                break;
        }
        p.setTreatment(Math.max((int) Math.round(severity * cureFactor), TREATMENT_HELPER));
        p.setHospitalised(true);
        this.hospitalisedPatients.add(p);
    }

    /**
     * Doctor operates the patient and sets the new severity according to the
     * cure factor.
     */
    public void operatePatient(Patient p) {
        int severity = p.getIllness().getSeverity();
        double cureFactor;

        switch (this.specialty) {
            case CARDIOLOGIST:
                cureFactor = CureFactors.CARDIOLOGIST_C2.getFactor();
                break;
            case ER_PHYSICIAN:
                cureFactor = CureFactors.ER_PHYSICIAN_C2.getFactor();
                break;
            case GASTROENTEROLOGIST:
                cureFactor = CureFactors.GASTROENTEROLOGIST_C2.getFactor();
                break;
            case GENERAL_SURGEON:
                cureFactor = CureFactors.GENERAL_SURGEON_C2.getFactor();
                break;
            case INTERNIST:
                cureFactor = CureFactors.INTERNIST_C2.getFactor();
                break;
            case NEUROLOGIST:
                cureFactor = CureFactors.NEUROLOGIST_C2.getFactor();
                break;
            default:
                cureFactor = 0;
                break;
        }

        // workaround for the .5 approximation
        double tmp = severity - severity * cureFactor;
        if (tmp - ROUND_HELPER == Math.floor(tmp)) {
            tmp -= ROUND_HELPER;
        } else {
            tmp = Math.round(tmp);
        }
        p.getIllness().setSeverity((int) tmp);
    }

    /**
     * Gets patient state according to the doctor's specialty.
     */
    public State getPatientState(InvestigationResult investigationResult) {
        switch (investigationResult) {
            case OPERATE:
                switch (this.specialty) {
                    case CARDIOLOGIST:
                        return State.OPERATED_CARDIO;
                    case ER_PHYSICIAN:
                        return State.OPERATED_ERPHYSICIAN;
                    case GENERAL_SURGEON:
                        return State.OPERATED_SURGEON;
                    case NEUROLOGIST:
                        return State.OPERATED_NEURO;
                    default:
                        return State.NOT_AVAILABLE;
                }
            case HOSPITALIZE:
                switch (this.specialty) {
                    case CARDIOLOGIST:
                        return State.HOSPITALIZED_CARDIO;
                    case ER_PHYSICIAN:
                        return State.HOSPITALIZED_ERPHYSICIAN;
                    case GASTROENTEROLOGIST:
                        return State.HOSPITALIZED_GASTRO;
                    case GENERAL_SURGEON:
                        return State.HOSPITALIZED_SURGEON;
                    case INTERNIST:
                        return State.HOSPITALIZED_INTERNIST;
                    case NEUROLOGIST:
                        return State.HOSPITALIZED_NEURO;
                    default:
                        return State.NOT_AVAILABLE;
                }
            case TREATMENT:
                switch (this.specialty) {
                    case CARDIOLOGIST:
                        return State.HOME_CARDIO;
                    case ER_PHYSICIAN:
                        return State.HOME_ERPHYSICIAN;
                    case GASTROENTEROLOGIST:
                        return State.HOME_GASTRO;
                    case GENERAL_SURGEON:
                        return State.HOME_SURGEON;
                    case INTERNIST:
                        return State.HOME_INTERNIST;
                    case NEUROLOGIST:
                        return State.HOME_NEURO;
                    default:
                        return State.NOT_AVAILABLE;
                }
            default:
                return State.NOT_AVAILABLE;
        }
    }

    public DoctorType getSpecialty() {
        return specialty;
    }

    @JsonSetter("type")
    public void setSpecialty(DoctorType specialty) {
        this.specialty = specialty;
    }

    public boolean getIsSurgeon() {
        return isSurgeon;
    }

    public void setIsSurgeon(boolean surgeon) {
        isSurgeon = surgeon;
    }

    public int getMaxForTreatment() {
        return maxForTreatment;
    }

    public void setMaxForTreatment(int maxForTreatment) {
        this.maxForTreatment = maxForTreatment;
    }

    public List<Patient> getHospitalisedPatients() {
        return hospitalisedPatients;
    }

    public void setHospitalisedPatients(List<Patient> hospitalisedPatients) {
        this.hospitalisedPatients = hospitalisedPatients;
    }
}

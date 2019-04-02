package entities;

import com.fasterxml.jackson.annotation.JsonSetter;
import types.DoctorVerdict;
import types.InvestigationResult;
import types.State;
import types.Urgency;

import java.util.List;

public final class Patient {

    private int id;

    private String name;

    private int age;

    private int time;

    private Illness illness;

    private int treatment = 0;

    private boolean isHospitalised = false;

    private DoctorVerdict verdict = DoctorVerdict.NOT_AVAILABLE;

    private Urgency urgency = Urgency.NOT_DETERMINED;

    private InvestigationResult investigationResult = InvestigationResult.NOT_DIAGNOSED;

    private State currentState = State.NOT_AVAILABLE;

    /**
     * Find the first doctor who can cure the affection.
     */
    public Doctor findDoctor(List<Doctor> availableDoctors) {
        for (Doctor d : availableDoctors) {
            if (d.canCureAffection(this.getIllness().getIllnessType())) {
                return d;
            }
        }
        return null;
    }

    /**
     * Find the first surgeon who can cure the affection.
     */
    public Doctor findSurgeon(List<Doctor> availableDoctors) {
        for (Doctor d : availableDoctors) {
            if (d.canCureAffection(this.getIllness().getIllnessType()) && d.getIsSurgeon()) {
                return d;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Illness getIllness() {
        return illness;
    }

    @JsonSetter("state")
    public void setIllness(Illness illness) {
        this.illness = illness;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public InvestigationResult getInvestigationResult() {
        return investigationResult;
    }

    public void setInvestigationResult(InvestigationResult investigationResult) {
        this.investigationResult = investigationResult;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public int getTreatment() {
        return treatment;
    }

    public void setTreatment(int treatment) {
        this.treatment = treatment;
    }

    public boolean isHospitalised() {
        return isHospitalised;
    }

    public void setHospitalised(boolean hospitalised) {
        isHospitalised = hospitalised;
    }

    public DoctorVerdict getVerdict() {
        return verdict;
    }

    public void setVerdict(DoctorVerdict verdict) {
        this.verdict = verdict;
    }
}

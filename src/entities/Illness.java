package entities;

import com.fasterxml.jackson.annotation.JsonSetter;
import types.IllnessType;

public final class Illness {

    private IllnessType illnessType;

    private int severity;

    public IllnessType getIllnessType() {
        return illnessType;
    }

    @JsonSetter("illnessName")
    public void setIllnessType(IllnessType illnessType) {
        this.illnessType = illnessType;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

}

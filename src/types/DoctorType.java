package types;
/**
 * All the doctor's specializations.
 *
 */
public enum DoctorType {

    CARDIOLOGIST("Cardiologist"),
    ER_PHYSICIAN("ERPhysician"),
    GASTROENTEROLOGIST("Gastroenterologist"),
    GENERAL_SURGEON("General Surgeon"),
    INTERNIST("Internist"),
    NEUROLOGIST("Neurologist");

    private String value;

    DoctorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package types;
/**
 * All the doctor's cure factors.
 *
 */
public enum CureFactors {

    CARDIOLOGIST_C1(0.4),
    CARDIOLOGIST_C2(0.1),
    ER_PHYSICIAN_C1(0.1),
    ER_PHYSICIAN_C2(0.3),
    GASTROENTEROLOGIST_C1(0.5),
    GASTROENTEROLOGIST_C2(0.0),
    GENERAL_SURGEON_C1(0.2),
    GENERAL_SURGEON_C2(0.2),
    INTERNIST_C1(0.01),
    INTERNIST_C2(0.0),
    NEUROLOGIST_C1(0.5),
    NEUROLOGIST_C2(0.1),
    ALL_T(22.0);

    private double factor;

    CureFactors(double factor) {
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }
}

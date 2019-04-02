package types;

/**
 * All the doctor's cure factors.
 *
 */
public enum DoctorAffections {

    CARDIOLOGIST(IllnessType.HEART_ATTACK,
            IllnessType.HEART_DISEASE),
    ER_PHYSICIAN(IllnessType.ALLERGIC_REACTION,
            IllnessType.BROKEN_BONES,
            IllnessType.BURNS,
            IllnessType.CAR_ACCIDENT,
            IllnessType.CUTS,
            IllnessType.HIGH_FEVER,
            IllnessType.SPORT_INJURIES),
    GASTROENTEROLOGIST(IllnessType.ABDOMINAL_PAIN,
            IllnessType.ALLERGIC_REACTION,
            IllnessType.FOOD_POISONING),
    GENERAL_SURGEON(IllnessType.ABDOMINAL_PAIN,
            IllnessType.BURNS,
            IllnessType.CAR_ACCIDENT,
            IllnessType.CUTS,
            IllnessType.SPORT_INJURIES),
    INTERNIST(IllnessType.ABDOMINAL_PAIN,
            IllnessType.ALLERGIC_REACTION,
            IllnessType.FOOD_POISONING,
            IllnessType.HEART_DISEASE,
            IllnessType.HIGH_FEVER,
            IllnessType.PNEUMONIA),
    NEUROLOGIST(IllnessType.STROKE);

    private IllnessType[] affections;

    DoctorAffections(IllnessType... affections) {
        this.affections = affections;
    }

    public IllnessType[] getAffections() {
        return affections;
    }
}

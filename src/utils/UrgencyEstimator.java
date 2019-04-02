package utils;

import types.IllnessType;
import types.Urgency;

import java.util.HashMap;
import java.util.Map;

/**
 * Estimates the urgency based on the patient's illness and how severe the illness is manifested.
 */
public final class UrgencyEstimator {

    private static UrgencyEstimator instance;
    private Map<Urgency, HashMap<IllnessType, Integer>> algorithm;

    private static final int ZERO = 0;
    private static final int TEN = 10;
    private static final int TWENTY = 20;
    private static final int THIRTY = 30;
    private static final int FORTY = 40;
    private static final int FIFTY = 50;
    private static final int SIXTY = 60;
    private static final int SEVENTY = 70;
    private static final int EIGHTY = 80;

    private UrgencyEstimator() {
        algorithm = new HashMap<Urgency, HashMap<IllnessType, Integer>>() {
            {
                put(Urgency.IMMEDIATE,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, SIXTY);
                                put(IllnessType.ALLERGIC_REACTION, FIFTY);
                                put(IllnessType.BROKEN_BONES, EIGHTY);
                                put(IllnessType.BURNS, FORTY);
                                put(IllnessType.CAR_ACCIDENT, THIRTY);
                                put(IllnessType.CUTS, FIFTY);
                                put(IllnessType.FOOD_POISONING, FIFTY);
                                put(IllnessType.HEART_ATTACK, ZERO);
                                put(IllnessType.HEART_DISEASE, FORTY);
                                put(IllnessType.HIGH_FEVER, SEVENTY);
                                put(IllnessType.PNEUMONIA, EIGHTY);
                                put(IllnessType.SPORT_INJURIES, SEVENTY);
                                put(IllnessType.STROKE, ZERO);

                            }
                        });

                put(Urgency.URGENT,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, FORTY);
                                put(IllnessType.ALLERGIC_REACTION, THIRTY);
                                put(IllnessType.BROKEN_BONES, FIFTY);
                                put(IllnessType.BURNS, TWENTY);
                                put(IllnessType.CAR_ACCIDENT, TWENTY);
                                put(IllnessType.CUTS, THIRTY);
                                put(IllnessType.HEART_ATTACK, ZERO);
                                put(IllnessType.FOOD_POISONING, TWENTY);
                                put(IllnessType.HEART_DISEASE, TWENTY);
                                put(IllnessType.HIGH_FEVER, FORTY);
                                put(IllnessType.PNEUMONIA, FIFTY);
                                put(IllnessType.SPORT_INJURIES, FIFTY);
                                put(IllnessType.STROKE, ZERO);
                            }
                        });

                put(Urgency.LESS_URGENT,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, TEN);
                                put(IllnessType.ALLERGIC_REACTION, TEN);
                                put(IllnessType.BROKEN_BONES, TWENTY);
                                put(IllnessType.BURNS, TEN);
                                put(IllnessType.CAR_ACCIDENT, TEN);
                                put(IllnessType.CUTS, TEN);
                                put(IllnessType.FOOD_POISONING, ZERO);
                                put(IllnessType.HEART_ATTACK, ZERO);
                                put(IllnessType.HEART_DISEASE, TEN);
                                put(IllnessType.HIGH_FEVER, ZERO);
                                put(IllnessType.PNEUMONIA, TEN);
                                put(IllnessType.SPORT_INJURIES, TWENTY);
                                put(IllnessType.STROKE, ZERO);
                            }
                        });

            }
        };
    }

    public static UrgencyEstimator getInstance() {
        if (instance == null) {
            instance = new UrgencyEstimator();
        }
        return instance;
    }

    //called by doctors and nurses
    public Urgency estimateUrgency(IllnessType illnessType, int severity) {

        if (severity >= algorithm.get(Urgency.IMMEDIATE).get(illnessType)) {
            return Urgency.IMMEDIATE;
        }
        if (severity >= algorithm.get(Urgency.URGENT).get(illnessType)) {
            return Urgency.URGENT;
        }
        if (severity >= algorithm.get(Urgency.LESS_URGENT).get(illnessType)) {
            return Urgency.LESS_URGENT;
        }
        return Urgency.NON_URGENT;
    }
}

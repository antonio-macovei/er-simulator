package types;

/**
 * All the patient's states based on the queue they are in or its hospitalization status.
 * The string values are needed for printing the homework's output messages.
 *
 * [Part of the homework's skeleton]
 */
public enum DoctorVerdict {
    SENT_HOME,
    STILL_HOSPITALIZED,
    NOT_AVAILABLE;
}

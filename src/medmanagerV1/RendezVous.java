package medmanagerV1;

import java.time.LocalDateTime;

public class RendezVous {

    private Patient patient;
    private Medecin medecin;
    private LocalDateTime dateHeure;

    public RendezVous(Patient patient, Medecin medecin, LocalDateTime dateHeure) {
        this.patient = patient;
        this.medecin = medecin;
        this.dateHeure = dateHeure;
    }

    public Patient getPatient() {
        return patient;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    @Override
    public String toString() {
        return "RDV | " + dateHeure +
               " | Patient : " + patient.getIdentiteComplete() +
               " | Médecin : " + medecin.getIdentiteComplete();
    }
}
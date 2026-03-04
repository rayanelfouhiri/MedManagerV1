package medmanagerV1;

import java.util.ArrayList;
import java.util.List;

public class ServiceHospitalier {
	private String code;
    private String nom;
    private int capaciteLits;
    private List<Medecin> medecins = new ArrayList<>();
    private List<Patient> patients;

    public ServiceHospitalier(String code, String nom,
                              int capaciteLits) {
        this.code = code;
        this.nom = nom;
        this.capaciteLits = capaciteLits;
        this.medecins = new ArrayList<>();
        this.patients = new ArrayList<>();
    }

    // ── Ajouter un médecin ──
    public boolean ajouterMedecin(Medecin medecin) {

        if (medecin == null) {
            System.out.println("⚠ Médecin invalide.");
            return false;
        }

        if (medecins.contains(medecin)) {
            System.out.println("⚠ Médecin déjà affecté à ce service.");
            return false;
        }

        medecins.add(medecin);
        return true;
    }

    // ── Admettre un patient (avec vérification) ──
    public boolean admettre(Patient patient) {
        if (patients.size() >= capaciteLits) {
            System.out.println("⚠ Service " + nom
                + " complet (" + capaciteLits + "/"
                + capaciteLits + " lits)");
            return false;
        }
        patients.add(patient);
        return true;
    }

    // ── Trouver un patient par ID ──
    public Patient trouverPatient(String id) {
        for (Patient p : patients) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;  // On améliorera ça plus tard avec Optional
    }

    // ── Informations ──
    public int getLitsDisponibles() {
        return capaciteLits - patients.size();
    }

    public String getNom() { return nom; }
    public String getCode() { return code; }
    public List<Medecin> getMedecins() { return medecins; }
    public List<Patient> getPatients() { return patients; }

    // ── Tableau de bord ──
    public void afficherTableauDeBord() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.printf("║  🏥 %-20s  [%s]       ║%n", nom, code);
        System.out.printf("║  🛏 Lits : %d/%d disponibles       ║%n",
            getLitsDisponibles(), capaciteLits);
        System.out.println("╠══════════════════════════════════════╣");

        System.out.println("║  👨‍⚕ Médecins :");
        for (Medecin m : medecins) {
            System.out.println("║    → " + m);
        }
        if (medecins.isEmpty())
            System.out.println("║    (aucun)");

        System.out.println("║  👤 Patients :");
        for (Patient p : patients) {
            System.out.printf("║    → %-20s %d ans%n",
                p.getIdentiteComplete(), p.getAge());
        }
        if (patients.isEmpty())
            System.out.println("║    (aucun)");

        System.out.println("╚══════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return nom + " [" + getLitsDisponibles()
            + "/" + capaciteLits + " lits disponibles]";
    }
}

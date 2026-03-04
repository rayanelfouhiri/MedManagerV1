package medmanagerV1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class prog {
	
	static List<Patient> patients = new ArrayList<>();
    static List<Medecin> medecins = new ArrayList<>();
    static List<ServiceHospitalier> services = new ArrayList<>();
    static List<RendezVous> rendezVousList = new ArrayList<>();
    static int prochainIdPatient = 1;
    static int prochainIdMedecin = 1;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

        // Créer quelques services par défaut
        services.add(new ServiceHospitalier("CARD", "Cardiologie", 30));
        services.add(new ServiceHospitalier("URG",  "Urgences", 50));
        services.add(new ServiceHospitalier("PED",  "Pédiatrie", 20));

        int choix;
        do {
            afficherMenu();
            choix = lireEntier(sc);
            switch (choix) {
                case 1 -> ajouterPatient(sc);
                case 2 -> ajouterMedecin(sc);
                case 3 -> afficherTousLesPatients();
                case 4 -> afficherTousLesMedecins();
                case 5 -> affecterPatientAuService(sc);
                case 6 -> tableauDeBordServices();
                case 7 -> supprimerPatient(sc);
                case 8 -> modifierPatient(sc);
                case 9 -> affecterMedecinAuService(sc);
                case 10 -> planifierRendezVous(sc);
                case 0 -> System.out.println("\n👋 Fermeture de MedManager.");
                default -> System.out.println("⚠ Choix invalide.");
            }
        } while (choix != 0);

        sc.close();
    }

    static void afficherMenu() {
        System.out.println("\n══════ MedManager v1.0 ══════");
        System.out.println("  1. ➕ Ajouter un patient");
        System.out.println("  2. ➕ Ajouter un médecin");
        System.out.println("  3. 📋 Afficher les patients");
        System.out.println("  4. 📋 Afficher les médecins");
        System.out.println("  5. 🏥 Affecter patient → service");
        System.out.println("  6. 📊 Tableau de bord des services");
        System.out.println("  7. 📊 Supprimer Patient");
        System.out.println("  8. ✏️ Modifier un patient");
        System.out.println("  9. 👨‍⚕️ Affecter médecin → service");
        System.out.println(" 10. 📅 Planifier un rendez-vous");
        System.out.println("  0. 🚪 Quitter");
        System.out.print("Votre choix : ");
    }

    static int lireEntier(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("⚠ Nombre attendu : ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }

    static void ajouterPatient(Scanner sc) {
        System.out.println("\n--- Nouveau Patient ---");
        String id = String.format("P%03d", prochainIdPatient++);

        System.out.print("Nom : ");
        String nom = sc.nextLine();
        System.out.print("Prénom : ");
        String prenom = sc.nextLine();
        System.out.print("Date de naissance (AAAA-MM-JJ) : ");
        LocalDate dn = LocalDate.parse(sc.nextLine());
        System.out.println("Telephone : ");
     

        Patient p = new Patient(id, nom, prenom, dn);
        
        String sang;
        System.out.print("Groupe sanguin (A+, A-, B+, B-, AB+, AB-, O+, O-) : ");
        do {
        	sang = sc.nextLine();
        	if(!sang.equals("A+") && !sang.equals("A-") && !sang.equals("B+") && !sang.equals("B-") && !sang.equals("AB+") && !sang.equals("AB-") && !sang.equals("O+") && !sang.equals("O-")) {
        		System.out.println("Invalide ! Reessayer : ");
        	}
        }while(!sang.equals("A+") && !sang.equals("A-") && !sang.equals("B+") && !sang.equals("B-") && !sang.equals("AB+") && !sang.equals("AB-") && !sang.equals("O+") && !sang.equals("O-"));
        p.setGroupeSanguin(sang);

        patients.add(p);
        System.out.println("✅ " + p.getIdentiteComplete()
            + " enregistré (" + p.getAge() + " ans)");
    }

    static void ajouterMedecin(Scanner sc) {
        System.out.println("\n--- Nouveau Médecin ---");
        String id = String.format("M%03d", prochainIdMedecin++);

        System.out.print("Nom : ");
        String nom = sc.nextLine();
        System.out.print("Prénom : ");
        String prenom = sc.nextLine();
        System.out.print("Date de naissance (AAAA-MM-JJ) : ");
        LocalDate dn = LocalDate.parse(sc.nextLine());
        System.out.print("Spécialité : ");
        String spe = sc.nextLine();
        System.out.print("Matricule : ");
        String mat = sc.nextLine();

        Medecin m = new Medecin(id, nom, prenom, dn, spe, mat);
        medecins.add(m);
        System.out.println("✅ " + m + " enregistré");
    }

    static void afficherTousLesPatients() {
        if (patients.isEmpty()) {
            System.out.println("\nAucun patient enregistré.");
            return;
        }
        System.out.println("\n--- Patients ---");
        System.out.printf("%-6s %-15s %-15s %-5s %-5s%n",
            "ID", "Nom", "Prénom", "Âge", "Sang");
        System.out.println("─".repeat(50));
        for (Patient p : patients) {
            System.out.printf("%-6s %-15s %-15s %-5d %-5s%n",
                p.getId(), p.getNom(), p.getPrenom(),
                p.getAge(),
                p.getGroupeSanguin() != null ? p.getGroupeSanguin() : "—");
        }
    }

    static void afficherTousLesMedecins() {
        if (medecins.isEmpty()) {
            System.out.println("\nAucun médecin enregistré.");
            return;
        }
        System.out.println("\n--- Médecins ---");
        for (Medecin m : medecins) {
            System.out.println("  → " + m);
        }
    }

    static void affecterPatientAuService(Scanner sc) {
        if (patients.isEmpty()) {
            System.out.println("\nAucun patient à affecter.");
            return;
        }

        // Choisir le patient
        System.out.print("\nID du patient : ");
        String idPat = sc.nextLine();
        Patient patient = null;
        for (Patient p : patients) {
            if (p.getId().equals(idPat)) { patient = p; break; }
        }
        if (patient == null) {
            System.out.println("⚠ Patient introuvable.");
            return;
        }

        // Choisir le service
        System.out.println("Services disponibles :");
        for (int i = 0; i < services.size(); i++) {
            System.out.println("  " + (i+1) + ". " + services.get(i));
        }
        System.out.print("Votre choix : ");
        int idx = lireEntier(sc) - 1;

        if (idx < 0 || idx >= services.size()) {
            System.out.println("⚠ Service invalide.");
            return;
        }

        ServiceHospitalier service = services.get(idx);
        if (service.admettre(patient)) {
            System.out.println("✅ " + patient.getIdentiteComplete()
                + " → " + service.getNom());
        }
    }

    static void tableauDeBordServices() {
        for (ServiceHospitalier s : services) {
            s.afficherTableauDeBord();
        }
    }

    static void supprimerPatient(Scanner sc) {

            if (patients.isEmpty()) {
                System.out.println("\nAucun patient à supprimer.");
                return;
            }

            System.out.print("\nID du patient à supprimer : ");
            String idRecherche = sc.nextLine();

            Patient patientTrouve = null;

            // Recherche avec equals()
            for (Patient p : patients) {
                if (p.getId().equals(idRecherche)) {
                    patientTrouve = p;
                    break;
                }
            }

            if (patientTrouve == null) {
                System.out.println("⚠ Patient introuvable.");
                return;
            }

            // Afficher infos
            System.out.println("\nPatient trouvé :");
            System.out.println("ID : " + patientTrouve.getId());
            System.out.println("Nom : " + patientTrouve.getIdentiteComplete());
            System.out.println("Âge : " + patientTrouve.getAge());
            System.out.println("Groupe sanguin : " + patientTrouve.getGroupeSanguin());

            // Confirmation
            System.out.print("\nConfirmer suppression ? (oui/non) : ");
            String confirmation = sc.nextLine();

            if (confirmation.equalsIgnoreCase("oui")) {

                // 🔥 Retirer du service aussi (cohérence)
                for (ServiceHospitalier s : services) {
                    s.getPatients().remove(patientTrouve);
                }

                patients.remove(patientTrouve);

                System.out.println("✅ Patient supprimé avec succès.");
            } else {
                System.out.println("❌ Suppression annulée.");
            }
        }
        
     static void modifierPatient(Scanner sc) {

                if (patients.isEmpty()) {
                    System.out.println("\nAucun patient enregistré.");
                    return;
                }

                System.out.print("\nID du patient à modifier : ");
                String idRecherche = sc.nextLine();

                Patient patientTrouve = null;

                for (Patient p : patients) {
                    if (p.getId().equals(idRecherche)) {
                        patientTrouve = p;
                        break;
                    }
                }

                if (patientTrouve == null) {
                    System.out.println("⚠ Patient introuvable.");
                    return;
                }

                System.out.println("\n--- Modification de " + patientTrouve.getIdentiteComplete() + " ---");

                // NOM
                System.out.print("Nom actuel (" + patientTrouve.getNom() + ") : ");
                String saisie = sc.nextLine();
                if (!saisie.isEmpty()) {
                    patientTrouve.setNom(saisie);
                }

                // PRENOM
                System.out.print("Prénom actuel (" + patientTrouve.getPrenom() + ") : ");
                saisie = sc.nextLine();
                if (!saisie.isEmpty()) {
                    patientTrouve.setPrenom(saisie);
                }

                // DATE DE NAISSANCE
                System.out.print("Date actuelle (" + patientTrouve.getDateNaissance() + ") [AAAA-MM-JJ] : ");
                saisie = sc.nextLine();
                if (!saisie.isEmpty()) {
                    patientTrouve.setDateNaissance(LocalDate.parse(saisie));
                }

                // GROUPE SANGUIN
                System.out.print("Groupe sanguin actuel (" 
                        + (patientTrouve.getGroupeSanguin() != null ? patientTrouve.getGroupeSanguin() : "—") 
                        + ") : ");
                saisie = sc.nextLine();
                if (!saisie.isEmpty()) {
                    patientTrouve.setGroupeSanguin(saisie);
                }

                System.out.println("✅ Patient mis à jour avec succès.");
            
            
	}
     
     static void affecterMedecinAuService(Scanner sc) {

    	    if (medecins.isEmpty()) {
    	        System.out.println("\nAucun médecin enregistré.");
    	        return;
    	    }

    	    System.out.print("\nID du médecin : ");
    	    String idMed = sc.nextLine();

    	    Medecin medecinTrouve = null;

    	    for (Medecin m : medecins) {
    	        if (m.getId().equals(idMed)) {
    	            medecinTrouve = m;
    	            break;
    	        }
    	    }

    	    if (medecinTrouve == null) {
    	        System.out.println("⚠ Médecin introuvable.");
    	        return;
    	    }

    	    System.out.println("\nServices disponibles :");
    	    for (int i = 0; i < services.size(); i++) {
    	        System.out.println("  " + (i + 1) + ". " + services.get(i));
    	    }

    	    System.out.print("Votre choix : ");
    	    int idx = lireEntier(sc) - 1;

    	    if (idx < 0 || idx >= services.size()) {
    	        System.out.println("⚠ Service invalide.");
    	        return;
    	    }

    	    ServiceHospitalier service = services.get(idx);

    	    if (service.ajouterMedecin(medecinTrouve)) {
    	        System.out.println("✅ Médecin affecté à " + service.getNom());
    	    }
    	}
     
     static void planifierRendezVous(Scanner sc) {

    	    if (patients.isEmpty() || medecins.isEmpty()) {
    	        System.out.println("⚠ Il faut au moins un patient et un médecin.");
    	        return;
    	    }

    	    System.out.print("ID du patient : ");
    	    String idPatient = sc.nextLine();

    	    Patient patientTrouve = null;
    	    for (Patient p : patients) {
    	        if (p.getId().equals(idPatient)) {
    	            patientTrouve = p;
    	            break;
    	        }
    	    }

    	    if (patientTrouve == null) {
    	        System.out.println("⚠ Patient introuvable.");
    	        return;
    	    }

    	    System.out.print("ID du médecin : ");
    	    String idMed = sc.nextLine();

    	    Medecin medecinTrouve = null;
    	    for (Medecin m : medecins) {
    	        if (m.getId().equals(idMed)) {
    	            medecinTrouve = m;
    	            break;
    	        }
    	    }

    	    if (medecinTrouve == null) {
    	        System.out.println("⚠ Médecin introuvable.");
    	        return;
    	    }

    	    System.out.print("Date et heure (AAAA-MM-JJTHH:MM) : ");
    	    String saisie = sc.nextLine();

    	    LocalDateTime dateHeure = LocalDateTime.parse(saisie);
    	    if (dateHeure.isBefore(LocalDateTime.now())) {
    	        System.out.println("⚠ La date est déjà passée !");
    	    } else {
    	        System.out.println("✅ Date/heure valide : " + dateHeure);
    	    }

    	    RendezVous rdv = new RendezVous(patientTrouve, medecinTrouve, dateHeure);
    	    rendezVousList.add(rdv);

    	    System.out.println("✅ Rendez-vous planifié !");
    	} 
     
}

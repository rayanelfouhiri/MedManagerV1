package medmanagerV1;

import java.time.LocalDate;
import java.time.Period;

public class Patient {
	private String id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String telephone;
    private String groupeSanguin;

    // Constructeur complet
    public Patient(String id, String nom, String prenom,
                   LocalDate dateNaissance, String telephone,
                   String groupeSanguin) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.groupeSanguin = groupeSanguin;
    }

    // Constructeur minimal — appelle le constructeur complet
    public Patient(String id, String nom, String prenom,
                   LocalDate dateNaissance) {
        this(id, nom, prenom, dateNaissance, null, null);
    }
    
    public int getAge() {
        return Period.between(dateNaissance, LocalDate.now())
                     .getYears();
    }
    
    public String getIdentiteComplete() {
        return prenom + " " + nom + " (ID: " + id + ")";
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		 if (telephone == null) {
		        System.out.println("Numéro invalide !");
		        return;
		    }

		    if (telephone.matches("[+\\d\\s]{10,}")) {
		        this.telephone = telephone;
		    } else {
		        System.out.println("Numéro invalide ! Minimum 10 caractères (chiffres, espaces ou +)");
		    }
	}

	public String getGroupeSanguin() {
		return groupeSanguin;
	}

	public void setGroupeSanguin(String groupeSanguin) {
		this.groupeSanguin = groupeSanguin;
	}

	
	
    
    
    
}

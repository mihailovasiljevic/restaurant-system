package restaurant.server.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "korisnik")
@NamedQuery(name = "findKorisnikSaKorisnickimImenomILozinkom", query = "SELECT k FROM Korisnik k WHERE k.korisnickoImeKorisnika like :korisnickoIme AND k.lozinkaKorisnika LIKE :lozinka")
public class Korisnik implements Serializable {

	private static final long serialVersionUID = 3770759786667844735L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "korisnik_id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "korisnik_ime", unique = false, nullable = false)
	private String imeKorisnika;

	@Column(name = "korisnik_prezime", unique = false, nullable = false)
	private String prezimeKorisnika;

	@Column(name = "korisnik_korisnicko_ime", unique = true, nullable = false)
	private String korisnickoImeKorisnika;

	@Column(name = "korisnik_lozinka", unique = false, nullable = false)
	private String lozinkaKorisnika;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImeKorisnika() {
		return imeKorisnika;
	}

	public void setImeKorisnika(String imeKorisnika) {
		this.imeKorisnika = imeKorisnika;
	}

	public String getPrezimeKorisnika() {
		return prezimeKorisnika;
	}

	public void setPrezimeKorisnika(String prezimeKorisnika) {
		this.prezimeKorisnika = prezimeKorisnika;
	}

	public String getKorisnickoImeKorisnika() {
		return korisnickoImeKorisnika;
	}

	public void setKorisnickoImeKorisnika(String korisnickoImeKorisnika) {
		this.korisnickoImeKorisnika = korisnickoImeKorisnika;
	}

	public String getLozinkaKorisnika() {
		return lozinkaKorisnika;
	}

	public void setLozinkaKorisnika(String lozinkaKorisnika) {
		this.lozinkaKorisnika = lozinkaKorisnika;
	}

	public Korisnik() {
		super();
	}

	public Korisnik(String imeKorisnika, String prezimeKorisnika, String korisnickoImeKorisnika, String lozinkaKorisnika) {
		super();
		this.imeKorisnika = imeKorisnika;
		this.korisnickoImeKorisnika = korisnickoImeKorisnika;
		this.lozinkaKorisnika = lozinkaKorisnika;
		this.prezimeKorisnika = prezimeKorisnika;
	}

	public String toString() {
		return "(Korisnik)[id=" + id + ",korisnicko ime=" + korisnickoImeKorisnika + "]";
	}

}

package hr.java.web.petkovic.moneyapp.trosak;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Novcanik {

	private static Logger logger = LoggerFactory.getLogger(Novcanik.class);
	
	@NotEmpty(message = "Novèanik mora imati naziv!")
	@Size(min=3, max=25, message = "Naziv mora biti izmeðu 3 i 25 znakova!")
	private String naziv;
	private List<Trosak> listaTroskova;
	
	@NotNull(message = "Novèanik mora imati vrstu!")
	private VrstaNovcanikaEnum vrstaNovcanika;

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<Trosak> getListaTroskova() {
		return listaTroskova;
	}

	public void setListaTroskova(List<Trosak> listaTroskova) {
		this.listaTroskova = listaTroskova;
	}

	public VrstaNovcanikaEnum getVrstaNovcanika() {
		return vrstaNovcanika;
	}

	public void setVrstaNovcanika(VrstaNovcanikaEnum vrstaNovcanika) {
		this.vrstaNovcanika = vrstaNovcanika;
	}

	public void addTrosak(Trosak trosak) {
		this.listaTroskova.add(trosak);
		logger.info("Dodan novi trošak" + trosak.toString() + " u novèanik " + this.naziv);
	}

	public Double getTroskoviSum() {
		Double sum = 0.0;

		for (Trosak trosak : listaTroskova) {
			sum += trosak.getIznos();
		}
		logger.info("Trenutni troškovi novèanika: " + sum);
		return sum;

	}
	public Novcanik() {
		this.listaTroskova = new ArrayList<>();
		this.naziv = null;
		this.vrstaNovcanika = null;
	}

	public Novcanik(String naziv, VrstaNovcanikaEnum vrsta)
	{
		this.naziv = naziv;
		this.listaTroskova = new ArrayList<>();
		this.vrstaNovcanika = vrsta;
	}
	public enum VrstaNovcanikaEnum {
		GOTOVINA, KARTICA;
	}
}
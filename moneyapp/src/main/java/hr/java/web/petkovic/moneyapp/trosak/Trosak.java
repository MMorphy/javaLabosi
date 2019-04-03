package hr.java.web.petkovic.moneyapp.trosak;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Trosak
{
	@NotEmpty(message = "Trošak mora imati naziv!")
	@Size(min=3, max=25, message = "Naziv mora biti izmeðu 3 i 25 znakova!")
	private String naziv;

	@NotNull(message = "Trošak mora imati iznos!")
	@Min(value=0, message = "Trošak mora biti pozitivan!")
	private Double iznos;

	@NotNull(message = "Trošak mora imati vrstu!")
	private VrstaTroskaEnum vrstaTroska;

	public String getNaziv() 
	{
		return naziv;
	}

	public void setNaziv(String naziv) 
	{
		this.naziv = naziv;
	}

	public Double getIznos() 
	{
		return iznos;
	}

	public void setIznos(Double iznos) 
	{
		this.iznos = iznos;
	}
	
	public VrstaTroskaEnum getVrstaTroska() 
	{
		return vrstaTroska;
	}

	public void setVrstaTroska(VrstaTroskaEnum vrstaTroska) 
	{
		this.vrstaTroska = vrstaTroska;
	}

	public static enum VrstaTroskaEnum 
	{
		Špeceraj,
		Režije,
		Najam,
		Ostalo
	}

}

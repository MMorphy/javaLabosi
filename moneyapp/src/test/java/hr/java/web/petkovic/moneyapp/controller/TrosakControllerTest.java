package hr.java.web.petkovic.moneyapp.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import hr.java.web.petkovic.moneyapp.trosak.Novcanik;
import hr.java.web.petkovic.moneyapp.trosak.Trosak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TrosakControllerTest {

	private MultiValueMap<String, String> trosakMap = new LinkedMultiValueMap<>();
	private MultiValueMap<String, String> novcanikMap = new LinkedMultiValueMap<>();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testNoviTrosakGet() throws Exception {
		this.mockMvc
				.perform(get("/troskovi/novitrosak").with(user("admin").password("admin").roles("USER", "ADMIN"))
						.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("trosak"))
				.andExpect(model().attributeExists("vrste"))
				.andExpect(model().attribute("vrste", Trosak.VrstaTroska.values()))
				.andExpect(model().attributeExists("novcanici"))
				.andExpect(view().name("trosak"));
	}

	private MultiValueMap<String, String> generateTrosak(MultiValueMap<String, String> map) {
		map.add("naziv", "testTrosak");
		map.add("vrstaTroska",Trosak.VrstaTroska.Hrana.toString());
		map.add("novcanikId", "1");
		map.add("iznos", "1000");

		return map;
	}

	@Test 
	public void testNoviTrosakPost() throws Exception
	{
		this.mockMvc
		.perform(
				post("/troskovi/novitrosak")
				.with(user("admin").password("admin").roles("USER", "ADMIN"))
				.with(csrf())
				.params(generateTrosak(trosakMap)))
		.andExpect(status().isOk())
		.andExpect(view().name("uneseniTrosak"));
	}

	@Test
	public void testIsprazniNovcanikAdmin() throws Exception
	{
		this.mockMvc
		.perform(
				get("/troskovi/isprazniNovcanik")
				.with(user("admin").password("admin").roles("USER", "ADMIN"))
				.with(csrf()))
		.andExpect(status().is3xxRedirection());
	}

	@Test
	public void testNoviNovcanikGet() throws Exception
	{
		this.mockMvc
		.perform(
				get("/troskovi/novinovcanik")
				.with(user("admin").password("admin").roles("USER", "ADMIN"))
				.with(csrf()))
		.andExpect(model().attributeExists("transportnov"))
		.andExpect(model().attributeExists("vrstenov"))
		.andExpect(status().isOk())
		.andExpect(view().name("newwallet"));
	}

	private MultiValueMap<String, String> generateNovcanik(MultiValueMap<String, String> map) {
		map.add("ime", "testNovcanik");
		map.add("tipNovcanika",Novcanik.TipNovcanika.Ostalo.toString());
		return map;
	}

	@Test
	public void testNoviNovcanikPost() throws Exception
	{
		this.mockMvc
		.perform(
				post("/troskovi/novinovcanik")
				.with(user("admin").password("admin").roles("USER", "ADMIN"))
				.with(csrf())
				.params(generateNovcanik(novcanikMap)))
		.andExpect(status().is3xxRedirection());
	}

	@Test
	public void testTraziTrosakGet() throws Exception
	{
		this.mockMvc
		.perform(
				get("/troskovi/trazitrosak")
				.with(user("admin").password("admin").roles("USER", "ADMIN"))
				.with(csrf()))
		.andExpect(view().name("traziTrosak"))
		.andExpect(status().isOk());
	}

	@Test
	public void testTraziTrosakPost() throws Exception
	{
		MultiValueMap<String, String> localMap = new LinkedMultiValueMap<String, String>();
		localMap.add("imeTroska", "testTrosak");
		this.mockMvc
		.perform(
				post("/troskovi/trazitrosak")
				.with(user("admin").password("admin").roles("USER", "ADMIN"))
				.with(csrf())
				.params(localMap))
		.andExpect(model().attributeExists("listaTrazenihTroskova"))
		.andExpect(status().isOk())
		.andExpect(view().name("traziTrosak"));
	}

	@Test
	public void testTraziNovcanikGet() throws Exception
	{
		this.mockMvc
		.perform(
				get("/troskovi/nadjiNovcanik")
				.with(user("admin").password("admin").roles("USER", "ADMIN"))
				.with(csrf()))
		.andExpect(status().isOk())
		.andExpect(view().name("nadjiNovcanik"));
	}

}

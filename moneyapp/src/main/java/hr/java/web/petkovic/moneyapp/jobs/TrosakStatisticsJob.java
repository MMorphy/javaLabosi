package hr.java.web.petkovic.moneyapp.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import hr.java.web.petkovic.moneyapp.repository.TrosakRepository;
import hr.java.web.petkovic.moneyapp.trosak.Trosak;
import hr.java.web.petkovic.moneyapp.trosak.TrosakController;
import hr.java.web.petkovic.moneyapp.trosak.Trosak.VrstaTroska;

public class TrosakStatisticsJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(TrosakStatisticsJob.class);

	Map<String, List<Trosak>> data = new HashMap<>();

	@Autowired
	private TrosakRepository trosakRepo;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		StringBuilder sb = new StringBuilder();
		StringBuilder line = new StringBuilder();
		sb.append("\n");
		//         1234567890123456789012345678901234567890123456789
		sb.append("            SUM            MIN            MAX");
		sb.append("\n");
		for (VrstaTroska vrsta : Trosak.VrstaTroska.values())
		{
			line.append(vrsta.toString())
			.append(addSpaces(line.toString(), "SUM"))
			.append(trosakRepo.getSumByVrsta(vrsta.toString()))
			.append(addSpaces(line.toString(), "MIN"))
			.append(trosakRepo.getMinByVrsta(vrsta.toString()))
			.append(addSpaces(line.toString(), "MAX"))
			.append(trosakRepo.getMaxByVrsta(vrsta.toString()))
			.append("\n");
			sb.append(line);
			line = new StringBuilder();
		}
		logger.info(sb.toString());
	}

	private String addSpaces(String currentLine, String aggregation)
	{
		String reference = "            SUM            MIN            MAX";
		StringBuilder spaces = new StringBuilder("");
		for (int i = 0; i < (reference.indexOf(aggregation) - currentLine.length()); i++)
		{
			spaces.append(" ");
		}
		return spaces.toString();
	}

}

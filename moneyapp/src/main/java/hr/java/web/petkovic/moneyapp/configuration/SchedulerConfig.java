package hr.java.web.petkovic.moneyapp.configuration;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hr.java.web.petkovic.moneyapp.jobs.TrosakStatisticsJob;

@Configuration
public class SchedulerConfig {

	@Bean
	public JobDetail trosakStatJobDetail() {
		return JobBuilder.newJob(TrosakStatisticsJob.class).withIdentity("trosakStatJob").storeDurably().build();
	}

	@Bean
	public Trigger trosakStatJobTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10)
				.repeatForever();
		return TriggerBuilder.newTrigger().forJob(trosakStatJobDetail()).withIdentity("trosakStatTrigger")
				.withSchedule(scheduleBuilder).build();
	}
}

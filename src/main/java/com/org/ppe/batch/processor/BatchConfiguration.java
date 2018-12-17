package com.org.ppe.batch.processor;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.Job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;


import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.FileSystemResource;


import com.org.ppe.domain.CUSTSTATUS;
import com.org.ppe.service.dto.CUSTSTATUSDTO;



@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	 public JobBuilderFactory jobBuilderFactory;
	 
	 @Autowired
	 public StepBuilderFactory stepBuilderFactory;
	 

	 @Bean
	 public FlatFileItemReader<CUSTSTATUSDTO> reader(){
	  FlatFileItemReader<CUSTSTATUSDTO> reader = new FlatFileItemReader<CUSTSTATUSDTO>();
	  reader.setResource(new FileSystemResource("upload.csv"));
	  reader.setLinesToSkip(1);
	  reader.setLineMapper(new DefaultLineMapper<CUSTSTATUSDTO>() {{
	    setLineTokenizer(new DelimitedLineTokenizer() {{setIncludedFields(new int[] {1,2,3,4});
	    setNames(new String[] { "userId","custId","ppeStatus","changeDt" });
	   }});
	      setFieldSetMapper(new CustStatusFieldSetMapper());
	  }});
	  
	  return reader;
	 }
		


	 @Bean
	 public CustStatusItemProcessor processor(){
	  return new CustStatusItemProcessor();
	 }
	 
	 @Bean
	 public JpaItemWriter<CUSTSTATUS> writer(){
		
		 JpaItemWriter<CUSTSTATUS> writer = new JpaItemWriter<CUSTSTATUS>();
		 writer.setEntityManagerFactory(entityManager.getEntityManagerFactory());
		 
		 return writer;
	     
	 }

	 @Bean
	 public Job importCustStatuses(JobCompletionNotificationListener listener, Step step1) {
	  return jobBuilderFactory.get("importCustStatuses")
	    .incrementer(new RunIdIncrementer())
	    .listener(listener)
	    .flow(step1)
	    .end()
	    .build();
	 }
	    @Bean
	    public Step step1(JpaItemWriter<CUSTSTATUS> writer) {
	        return stepBuilderFactory.get("step1")
	            .<CUSTSTATUSDTO, CUSTSTATUS> chunk(20)
	            .reader(reader())
	            .processor(processor())
	            .writer(writer)
	            .build();
	    }

	 
	
	 
	
}

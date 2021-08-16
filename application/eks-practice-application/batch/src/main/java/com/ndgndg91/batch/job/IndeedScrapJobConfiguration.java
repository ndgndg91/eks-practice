package com.ndgndg91.batch.job;

import com.ndgndg91.batch.dto.IndeedItem;
import com.ndgndg91.batch.dto.IndeedItems;
import com.ndgndg91.batch.processor.IndeedScrapProcessor;
import com.ndgndg91.batch.reader.IndeedScrapReader;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@RequiredArgsConstructor
public class IndeedScrapJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job indeedScrapJob() {
        return jobBuilderFactory.get("indeedScrapJob")
                .start(indeedScrapStep())
                .build();
    }

    @Bean
    public Step indeedScrapStep() {
        return stepBuilderFactory.get("indeedScrapStep")
                .<String, IndeedItems>chunk(1)
                .reader(new IndeedScrapReader())
                .processor(new IndeedScrapProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemWriter<IndeedItems> itemWriter() {
        FileSystemResource fileSystemResource = new FileSystemResource("test.csv");
        return new FlatFileItemWriterBuilder<IndeedItems>()
                .name("indeedItemWriter")
                .headerCallback(w -> w.write("id, title, companyName, companyLocation, salary, summary"))
                .lineAggregator(items -> {
                    final var lines = new StringBuilder();
                    for (IndeedItem item : items.getItems()) {
                        lines.append(item.getId());
                        lines.append(",");
                        lines.append(item.getTitle());
                        lines.append(",");
                        lines.append(item.getCompanyName());
                        lines.append(",");
                        lines.append(item.getCompanyLocation());
                        lines.append(",");
                        lines.append(item.getSalary());
                        lines.append(",");
                        lines.append(item.getSummary().replace("\"", ""));
                        lines.append("\n");
                    }

                    String result = lines.toString();
                    return result.substring(0, result.length() - 2);
                })
                .resource(fileSystemResource)
                .build();
    }
}

package com.ndgndg91.batch.processor;

import com.ndgndg91.batch.dto.IndeedItem;
import com.ndgndg91.batch.dto.IndeedItems;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;

@Slf4j
public class IndeedScrapProcessor implements ItemProcessor<String, IndeedItems> {
    @Override
    public IndeedItems process(String html) throws Exception {
        final var items = new ArrayList<IndeedItem>();
        final var document = Jsoup.parse(html);
        final Elements searchCards = document.body().select("a[data-mobtk]");
        for (final Element el : searchCards) {
            final var id = el.attr("data-jk");
            final var title = el.select("span[title]").text();
            final var companyName = el.select("span.companyName").text();
            final var companyLocation = el.select("div.companyLocation").text();
            final var salary = el.select("span.salary-snippet").text();
            final var summary = el.select("div.job-snippet").text();
            log.info("data-jk : {}", id);
            log.info("title : {}", title);
            log.info("company name : {}", companyName);
            log.info("company location : {}", companyLocation);
            log.info("salary : {}", salary);
            log.info("summary : {}", summary);
            final var item = IndeedItem.builder()
                    .id(id)
                    .title(title)
                    .companyName(companyName)
                    .companyLocation(companyLocation)
                    .salary(salary)
                    .summary(summary)
                    .build();
            items.add(item);
        }
        return new IndeedItems(items);
    }
}

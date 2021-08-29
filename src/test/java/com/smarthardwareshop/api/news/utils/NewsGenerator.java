package com.smarthardwareshop.api.news.utils;

import com.github.javafaker.Faker;
import com.smarthardwareshop.api.news.entities.News;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewsGenerator {
    private static final Faker faker = new Faker();

    public static News generateNews() {
        News news = new News();
        news.setTitle(faker.lorem().sentence());
        news.setDescription(faker.lorem().sentence());
        news.setImage(faker.internet().url());

        // TODO: Fix future date generation below:
        news.setExpiryDate(LocalDateTime.of(faker.number().numberBetween(2050, 2100), 2, 2, 10, 23));

        // TODO: check "bound must be positive" error after invoking .future() multiple times below:
        /* news.setExpiryDate(
            faker.date().future(10, 10, TimeUnit.DAYS)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
        ); */

        return news;
    }

    public static List<News> generateManyNews(int quantity) {
        List<News> newsList = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            News news = NewsGenerator.generateNews();
            newsList.add(news);
        }

        return newsList;
    }
}

package de.entwickler.training.openapispring.controller;

import org.openapitools.api.NewsApiDelegate;
import org.openapitools.model.NewsArticle;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Component
public class NewsApiDelegateImpl implements NewsApiDelegate {

    @Override
    public ResponseEntity<List<NewsArticle>> newsGet(String category,
                                                     LocalDate publicationDate,
                                                     Integer limit,
                                                     Integer offset) {
        return ResponseEntity.ok(
            List.of(
                new NewsArticle()
                        .id(1L)
                        .title("Test 1")
                        .content("Content")
                        .publicationDate(OffsetDateTime.now())
                        .author("Author")
                        .category(NewsArticle.CategoryEnum.POLITICS)
                        .tags(List.of("Tag1", "Tag2"))
                        .sourceUrl("http://example.com"),
                new NewsArticle()
                        .id(2L)
                        .title("Test 2")
                        .content("Content")
                        .publicationDate(OffsetDateTime.now())
                        .author("Author")
                        .category(NewsArticle.CategoryEnum.POLITICS)
                        .tags(List.of("Tag1", "Tag2"))
                        .sourceUrl("http://example.com")
            )
        );
    }

    @Override
    public ResponseEntity<NewsArticle> newsIdGet(Long id) {
        return ResponseEntity.ok(new NewsArticle()
                .id(1L)
                .title("Test - " + id)
                .content("Content")
                .publicationDate(OffsetDateTime.now())
                .author("Author")
                .category(NewsArticle.CategoryEnum.POLITICS)
                .tags(List.of("Tag1", "Tag2"))
                .sourceUrl("http://example.com"));
    }
}

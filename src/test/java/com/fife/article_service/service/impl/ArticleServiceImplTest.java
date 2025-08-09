package com.fife.article_service.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.fife.article_service.dao.ArticleDao;
import com.fife.article_service.exception.NotFoundException;
import com.fife.article_service.model.Article;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    private static final String ID = "id";

    @InjectMocks private ArticleServiceImpl articleService;

    @Mock private ArticleDao articleDao;

    @Test
    void givenArticleWhenCreateArticleThenReturnSavedArticle() {
        Article article = mock(Article.class);
        Article savedArticle = mock(Article.class);

        when(this.articleDao.save(article)).thenReturn(savedArticle);

        Article result = this.articleService.createArticle(article);

        assertThat(result, is(savedArticle));
        verify(this.articleDao, times(1)).save(article);
    }

    @Test
    void givenArticlesWhenGetAllArticlesThenReturnListOfArticles() {
        Article article = mock(Article.class);

        when(this.articleDao.findAll()).thenReturn(Collections.singletonList(article));

        List<Article> result = this.articleService.getAllArticles();

        assertThat(result, is(Collections.singletonList(article)));
        verify(this.articleDao, times(1)).findAll();
    }

    @Test
    void givenExistentIdWhenGetArticleByIdThenReturnArticle() {
        Article article = mock(Article.class);

        when(this.articleDao.findById(ID)).thenReturn(Optional.of(article));

        Article result = this.articleService.getArticleById(ID);

        assertThat(result, is(article));
        verify(this.articleDao, times(1)).findById(ID);
    }

    @Test
    void givenNonExistentIdWhenGetArticleByIdThenThrowException() {
        when(this.articleDao.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.articleService.getArticleById(ID));
        verify(this.articleDao, times(1)).findById(ID);
    }

    @Test
    void givenExistentIdWhenUpdateArticleThenReturnUpdatedArticle() {
        Article existingArticle = mock(Article.class);
        Article updateData = mock(Article.class);
        Article updatedArticle = mock(Article.class);

        when(this.articleDao.findById(ID)).thenReturn(Optional.of(existingArticle));
        when(this.articleDao.save(existingArticle)).thenReturn(updatedArticle);

        Article result = this.articleService.updateArticle(ID, updateData);

        assertThat(result, is(updatedArticle));
        verify(existingArticle, times(1)).setTitle(any());
        verify(existingArticle, times(1)).setContent(any());
        verify(existingArticle, times(1)).setUpdatedAt(any());
        verify(this.articleDao, times(1)).save(existingArticle);
    }

    @Test
    void givenNonExistentIdWhenUpdateArticleThenThrowException() {
        Article updateData = mock(Article.class);

        when(this.articleDao.findById(ID)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class, () -> this.articleService.updateArticle(ID, updateData));
        verify(this.articleDao, times(1)).findById(ID);
        verify(this.articleDao, never()).save(any());
    }

    @Test
    void givenExistentIdWhenDeleteArticleThenDeleteIt() {
        Article article = mock(Article.class);

        when(this.articleDao.findById(ID)).thenReturn(Optional.of(article));

        this.articleService.deleteArticle(ID);

        verify(this.articleDao, times(1)).findById(ID);
        verify(this.articleDao, times(1)).delete(article);
    }

    @Test
    void givenNonExistentIdWhenDeleteArticleThenThrowException() {

        when(this.articleDao.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.articleService.deleteArticle(ID));
        verify(this.articleDao, times(1)).findById(ID);
        verify(this.articleDao, never()).delete(any());
    }
}

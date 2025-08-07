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
        Long id = 1L;
        Article article = mock(Article.class);

        when(this.articleDao.findById(id)).thenReturn(Optional.of(article));

        Article result = this.articleService.getArticleById(id);

        assertThat(result, is(article));
        verify(this.articleDao, times(1)).findById(id);
    }

    @Test
    void givenNonExistentIdWhenGetArticleByIdThenThrowException() {
        Long id = 1L;

        when(this.articleDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.articleService.getArticleById(id));
        verify(this.articleDao, times(1)).findById(id);
    }

    @Test
    void givenExistentIdWhenUpdateArticleThenReturnUpdatedArticle() {
        Long id = 1L;
        Article existingArticle = mock(Article.class);
        Article updateData = mock(Article.class);
        Article updatedArticle = mock(Article.class);

        when(this.articleDao.findById(id)).thenReturn(Optional.of(existingArticle));
        when(this.articleDao.save(existingArticle)).thenReturn(updatedArticle);

        Article result = this.articleService.updateArticle(id, updateData);

        assertThat(result, is(updatedArticle));
        verify(existingArticle, times(1)).setTitle(any());
        verify(existingArticle, times(1)).setContent(any());
        verify(existingArticle, times(1)).setUpdatedAt(any());
        verify(this.articleDao, times(1)).save(existingArticle);
    }

    @Test
    void givenNonExistentIdWhenUpdateArticleThenThrowException() {
        Long id = 1L;
        Article updateData = mock(Article.class);

        when(this.articleDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class, () -> this.articleService.updateArticle(id, updateData));
        verify(this.articleDao, times(1)).findById(id);
        verify(this.articleDao, never()).save(any());
    }

    @Test
    void givenExistentIdWhenDeleteArticleThenDeleteIt() {
        Long id = 1L;
        Article article = mock(Article.class);

        when(this.articleDao.findById(id)).thenReturn(Optional.of(article));

        this.articleService.deleteArticle(id);

        verify(this.articleDao, times(1)).findById(id);
        verify(this.articleDao, times(1)).delete(article);
    }

    @Test
    void givenNonExistentIdWhenDeleteArticleThenThrowException() {
        Long id = 1L;

        when(this.articleDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.articleService.deleteArticle(id));
        verify(this.articleDao, times(1)).findById(id);
        verify(this.articleDao, never()).delete(any());
    }
}

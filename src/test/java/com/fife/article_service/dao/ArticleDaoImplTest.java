package com.fife.article_service.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import com.fife.article_service.dao.impl.ArticleDaoImpl;
import com.fife.article_service.entity.ArticleEntity;
import com.fife.article_service.model.Article;
import com.fife.article_service.repository.ArticleRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class ArticleDaoImplTest {

    private static final String ID = "id";

    @InjectMocks private ArticleDaoImpl jpaArticleDao;

    @Mock private ArticleRepository articleRepository;

    @Mock private ModelMapper mapper;

    @Test
    void givenArticleWhenSaveArticleThenSaveArticle() {
        Article article = mock(Article.class);
        ArticleEntity articleEntity = mock(ArticleEntity.class);

        when(this.mapper.map(article, ArticleEntity.class)).thenReturn(articleEntity);
        this.jpaArticleDao.save(article);

        verify(this.articleRepository, times(1)).save(articleEntity);
    }

    @Test
    void givenArticlesWhenFindAllThenReturnArticles() {
        ArticleEntity articleEntity = mock(ArticleEntity.class);
        Article article = mock(Article.class);

        when(this.articleRepository.findAll()).thenReturn(Collections.singletonList(articleEntity));
        when(this.mapper.map(articleEntity, Article.class)).thenReturn(article);

        List<Article> result = this.jpaArticleDao.findAll();

        assertThat(result, is(Collections.singletonList(article)));
    }

    @Test
    void givenExistentIdWhenFindByIdThenReturnArticle() {
        ArticleEntity articleEntity = mock(ArticleEntity.class);
        Article article = mock(Article.class);

        when(this.articleRepository.findById(ID)).thenReturn(Optional.of(articleEntity));
        when(this.mapper.map(articleEntity, Article.class)).thenReturn(article);

        Article result = this.jpaArticleDao.findById(ID).orElse(null);

        assertThat(result, is(article));
    }

    @Test
    void givenNonExistentIdWhenFindByIdThenReturnNull() {
        when(this.articleRepository.findById(ID)).thenReturn(Optional.empty());

        Article result = this.jpaArticleDao.findById(ID).orElse(null);

        assertNull(result);
    }

    @Test
    void givenArticleWhenDeleteArticleThenDeleteArticleEntity() {
        Article article = mock(Article.class);
        ArticleEntity articleEntity = mock(ArticleEntity.class);

        when(this.mapper.map(article, ArticleEntity.class)).thenReturn(articleEntity);

        this.jpaArticleDao.delete(article);

        verify(this.articleRepository, times(1)).delete(articleEntity);
    }
}

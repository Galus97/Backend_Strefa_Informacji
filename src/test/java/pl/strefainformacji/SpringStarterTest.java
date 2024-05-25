package pl.strefainformacji;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.strefainformacji.controller.ArticleController;
import pl.strefainformacji.controller.SpecificArticleController;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class SpringStarterTest {

    @Autowired
    private ArticleController articleController;
    @Autowired
    private SpecificArticleController specificArticleController;

    @Test
    public void contextLoads() throws Exception{
        assertThat(articleController).isNotNull();
        assertThat(specificArticleController).isNotNull();
    }

}
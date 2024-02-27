package pl.strefainformacji;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.strefainformacji.controller.ArticleController;
import pl.strefainformacji.controller.ImageViewController;
import pl.strefainformacji.controller.SpecificArticleController;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class SpringStarterTest {

    @Autowired
    private ArticleController articleController;
    @Autowired
    private SpecificArticleController specificArticleController;
    @Autowired
    private ImageViewController imageViewController;

    @Test
    public void contextLoads() throws Exception{
        assertThat(articleController).isNotNull();
        assertThat(specificArticleController).isNotNull();
        assertThat(imageViewController).isNotNull();
    }

}
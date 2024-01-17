package pl.strefainformacji.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddedArticleController {


    @GetMapping("/add/viewAddedArticle")
    public String addedArticlePage(){
        return "viewAddedArticle";
    }
}

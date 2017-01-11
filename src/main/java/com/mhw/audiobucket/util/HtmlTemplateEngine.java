package com.mhw.audiobucket.util;

import com.mhw.audiobucket.app.Main;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Created by mxw4182 on 12/23/16.
 */
public class HtmlTemplateEngine extends TemplateEngine {

    @Override
    public String render(ModelAndView modelAndView) {
        try {
            return new String(Files.readAllBytes(Paths.get(Main.STATIC_RESOURCES + "/" + modelAndView.getViewName())));
        } catch (IOException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        HtmlTemplateEngine e = new HtmlTemplateEngine();
        System.out.println(e.render(new ModelAndView(null, "/html/index.html")));
    }
}

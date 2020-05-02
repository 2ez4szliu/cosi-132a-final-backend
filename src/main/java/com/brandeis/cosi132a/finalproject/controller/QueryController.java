package com.brandeis.cosi132a.finalproject.controller;

import com.brandeis.cosi132a.finalproject.model.CovidMeta;
import com.brandeis.cosi132a.finalproject.service.CovidMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private CovidMetadataService covidMetadataService;

   @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello world";
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public CovidMeta test() {
        CovidMeta covidMeta = new CovidMeta();
        covidMeta.setId("217");
        covidMeta.setAuthors(Arrays.asList(new String[]{"Bohao Li", "Han Xia", "Shizhao Liu"}));
        covidMeta.setTitle("COVID-19 IR SYSTEM");
        return covidMeta;
    }

    @RequestMapping(path = "/title", method = RequestMethod.GET)
    public Page<CovidMeta> searchByTitle(@RequestParam(value = "title") String title,
                                         @RequestParam(value = "page") int page) {
        return covidMetadataService.findByTitle(title, page);
    }

    @RequestMapping(path = "/author", method = RequestMethod.GET)
    public Page<CovidMeta> searchByAuthor(@RequestParam(value = "author") String author,
                                          @RequestParam(value = "page") int page) {
        return covidMetadataService.findByAuthors(author, page);
    }

    @RequestMapping(path = "/publishTime", method = RequestMethod.GET)
    public Page<CovidMeta> searchByPublishTime(@RequestParam(value = "publishTime") String publishTime,
                                               @RequestParam(value = "page") int page) {
        return covidMetadataService.findByPublishTime(publishTime, page);
    }

    @RequestMapping(path = "/publishTimeGTE", method = RequestMethod.GET)
    public Page<CovidMeta> searchByPublishTimeGTE(@RequestParam(value = "publishTime") String publishTime,
                                                  @RequestParam(value = "page") int page) {
        return covidMetadataService.findByPublishTimeGTE(publishTime, page);
    }

    @RequestMapping(path = "/text", method = RequestMethod.GET)
    public Page<CovidMeta> searchByText(@RequestParam(value = "text") String text,
                                                  @RequestParam(value = "page") int page) {
        return covidMetadataService.findByText(text, page);
    }

    @RequestMapping(path = "/vector", method = RequestMethod.GET)
    public List<CovidMeta> searchByDenseVector(@RequestParam(value = "base64vector") String base64vector) {
        return covidMetadataService.findByDenseVector(base64vector);
    }


}

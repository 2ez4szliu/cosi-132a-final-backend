package com.brandeis.cosi132a.finalproject.controller;

import com.brandeis.cosi132a.finalproject.model.CovidMeta;
import com.brandeis.cosi132a.finalproject.model.Sentence;
import com.brandeis.cosi132a.finalproject.service.CovidMetadataService;
import com.brandeis.cosi132a.finalproject.service.Word2VecService;
import com.brandeis.cosi132a.finalproject.utils.VectorEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private CovidMetadataService covidMetadataService;

    @Autowired
    private Word2VecService word2VecService;

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello world";
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public List<Sentence> test() {
        String query = "test";
        double[] vector = word2VecService.sentenceToVector(query);
        String urlEncode = VectorEncoder.urlBase64Encode(VectorEncoder.convertArrayToBase64(vector));
        return covidMetadataService.findSentence(urlEncode);
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
    public List<Sentence> searchByVector(@RequestParam(value = "query") String query) {
        double[] vector = word2VecService.sentenceToVector(query);
        String urlEncode = VectorEncoder.urlBase64Encode(VectorEncoder.convertArrayToBase64(vector));
        return covidMetadataService.findSentence(urlEncode);
    }


}

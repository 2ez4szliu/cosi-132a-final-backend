package com.brandeis.cosi132a.finalproject.controller;

import com.brandeis.cosi132a.finalproject.model.CovidMeta;
import com.brandeis.cosi132a.finalproject.model.ResultWrapper;
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

    /**
     *  Execute full-text search query and return a page of search result
     * @param text text of the input
     * @param title title of the input
     * @param authors authors of the input
     * @param dateSince search for documents after 'dateSince'
     * @param dateTo search for documents before 'dateTo'
     * @param page page number
     * @return
     */
    @RequestMapping(path = "/query", method = RequestMethod.GET)
    public Page<CovidMeta> query(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "authors", required = false) List<String> authors,
            @RequestParam(value = "dateSince", required = false) String dateSince,
            @RequestParam(value = "dateTo", required = false) String dateTo,
            @RequestParam(value = "page") int page) {
        return covidMetadataService.query(text, title, authors, dateSince, dateTo, page);
    }

    /**
     *  Execute text embedding vector search, return a list of
     *  results wrapper(the text and the document containing the text)
     * @param query the search query
     * @return list of resultWrapper(the text and the document containing the text)
     */
    @RequestMapping(path = "/vector", method = RequestMethod.GET)
    public List<ResultWrapper> searchByVector(@RequestParam(value = "query") String query) {
        double[] vector = word2VecService.sentenceToVector(query);
        String imgUrl = word2VecService.plotTrendImage(query);
        String urlEncode = VectorEncoder.urlBase64Encode(VectorEncoder.convertArrayToBase64(vector));
        List<ResultWrapper> result = covidMetadataService.findSentence(urlEncode);
        for (ResultWrapper wrapper : result) {
            wrapper.setImgUrl(imgUrl);
        }
        return result;
    }

}

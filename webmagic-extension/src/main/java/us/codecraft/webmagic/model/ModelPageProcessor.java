package us.codecraft.webmagic.model;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The extension to PageProcessor for page model extractor.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.2.0
 */
class ModelPageProcessor implements PageProcessor {

    /**
     * The list of page model extractor.
     */
    private List<PageModelExtractor> pageModelExtractorList = new ArrayList<PageModelExtractor>();

    /**
     * The site of this page processor.
     */
    private Site site;

    /**
     * Whether to extract links from the page.
     */
    private boolean extractLinks = true;

    /**
     * Create a ModelPageProcessor with the given site and page model classes.
     *
     * @param site   the site
     * @param clazzs the page model classes
     * @return the ModelPageProcessor instance
     */
    public static ModelPageProcessor create(Site site, Class... clazzs) {
        ModelPageProcessor modelPageProcessor = new ModelPageProcessor(site);
        for (Class clazz : clazzs) {
            modelPageProcessor.addPageModel(clazz);
        }
        return modelPageProcessor;
    }

    /**
     * Add a page model class to the processor.
     *
     * @param clazz the page model class
     * @return the ModelPageProcessor instance
     */
    public ModelPageProcessor addPageModel(Class clazz) {
        PageModelExtractor pageModelExtractor = PageModelExtractor.create(clazz);
        pageModelExtractorList.add(pageModelExtractor);
        return this;
    }

    /**
     * set the site of this page processor.
     * @param site
     */
    private ModelPageProcessor(Site site) {
        this.site = site;
    }

    /**
     * Process the page.
     * @param page page
     */
    @Override
    public void process(Page page) {
        for (PageModelExtractor pageModelExtractor : pageModelExtractorList) {
            if (extractLinks) {
                extractLinks(page, pageModelExtractor.getHelpUrlRegionSelector(), pageModelExtractor.getHelpUrlPatterns());
                extractLinks(page, pageModelExtractor.getTargetUrlRegionSelector(), pageModelExtractor.getTargetUrlPatterns());
            }
            Object process = pageModelExtractor.process(page);
            if (process == null || (process instanceof List && ((List) process).size() == 0)) {
                continue;
            }
            postProcessPageModel(pageModelExtractor.getClazz(), process);
            page.putField(pageModelExtractor.getClazz().getCanonicalName(), process);
        }
        if (page.getResultItems().getAll().size() == 0) {
            page.getResultItems().setSkip(true);
        }
    }

    /**
     * Extract links from the page.
     * @param page page
     * @param urlRegionSelector the selector for the region to extract links from
     * @param urlPatterns the patterns to match the links
     */
    private void extractLinks(Page page, Selector urlRegionSelector, List<Pattern> urlPatterns) {
        List<String> links;
        if (urlRegionSelector == null) {
            links = page.getHtml().links().all();
        } else {
            links = page.getHtml().selectList(urlRegionSelector).links().all();
        }
        for (String link : links) {
            for (Pattern targetUrlPattern : urlPatterns) {
                Matcher matcher = targetUrlPattern.matcher(link);
                if (matcher.find()) {
                    page.addTargetRequest(new Request(matcher.group(0)));
                }
            }
        }
    }

    /**
     * Post process the page model.
     * @param clazz the class of the page model
     * @param object the page model object
     */
    protected void postProcessPageModel(Class clazz, Object object) {
    }

    /**
     * Get the site of this page processor.
     * @return site
     */
    @Override
    public Site getSite() {
        return site;
    }

    /**
     * get if the links should be extracted.
     * @return true if links should be extracted, false otherwise
     */
    public boolean isExtractLinks() {
        return extractLinks;
    }

    /**
     * set if the links should be extracted.
     * @param extractLinks true if links should be extracted, false otherwise
     */
    public void setExtractLinks(boolean extractLinks) {
        this.extractLinks = extractLinks;
    }
}

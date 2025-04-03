package us.codecraft.webmagic.processor;

import org.junit.Test;

import junit.framework.Assert;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.MockGithubDownloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author code4crafter@gmail.com
 */
public class GithubRepoProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        //page.putField("star",page.getHtml().xpath("//ul[@class='pagehead-actions']/li[2]//a[@class='social-count js-social-count']/text()").toString());
        //page.putField("fork",page.getHtml().xpath("//ul[@class='pagehead-actions']/li[3]//a[@class='social-count']/text()").toString());
        String star = page.getHtml().css("a[href$='stargazers']", "text").get();
        String fork = page.getHtml().css("a[href$='network']", "text").get();
        star = star.trim().replaceAll("[^0-9]", "");
        fork = fork.trim().replaceAll("[^0-9]", "");
        page.putField("star", star);
        page.putField("fork", fork);
    }

    @Override
    public Site getSite() {
        return Site.me();
    }

    @Test
    public void test() {
        OOSpider.create(new GithubRepoProcessor()).addPipeline(new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {
                Assert.assertEquals("86",((String)resultItems.get("star")).trim()); // 86 est le nombre défini dans le html de MockGithubDownloader
                Assert.assertEquals("70",((String)resultItems.get("fork")).trim()); // 70 est le nombre défini dans le html de MockGithubDownloader
            }
        }).setDownloader(new MockGithubDownloader()).test("https://github.com/code4craft/webmagic");
    }

}

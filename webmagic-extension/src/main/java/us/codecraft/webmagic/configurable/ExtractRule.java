package us.codecraft.webmagic.configurable;

import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.selector.Selector;
import static us.codecraft.webmagic.selector.Selectors.$;
import static us.codecraft.webmagic.selector.Selectors.regex;
import static us.codecraft.webmagic.selector.Selectors.xpath;

/**
 * @author code4crafter@gmail.com
 */
public class ExtractRule {

    private String fieldName;

    private ExpressionType expressionType;

    private String expressionValue;

    private String[] expressionParams;

    private boolean multi = false;

    private volatile Selector selector;

    private boolean notNull = false;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setExpressionType(ExpressionType expressionType) {
        this.expressionType = expressionType;
    }

    public void setExpressionValue(String expressionValue) {
        this.expressionValue = expressionValue;
    }

    public boolean isMulti() {
        return multi;
    }

    public Selector getSelector() {
        if (selector == null) {
            synchronized (this) {
                if (selector == null) {
                    selector = compileSelector();
                }
            }
        }
        return selector;
    }

    private Selector compileSelector() {
        switch (expressionType) {
            case Css:
                if (expressionParams.length >= 1) {
                    return $(expressionValue, expressionParams[0]);
                } else {
                    return $(expressionValue);
                }
            case XPath:
                return xpath(expressionValue);
            case Regex:
                if (expressionParams.length >= 1) {
                    return regex(expressionValue, Integer.parseInt(expressionParams[0]));
                } else {
                    return regex(expressionValue);
                }
            case JsonPath:
                return new JsonPathSelector(expressionValue);
            default:
                return xpath(expressionValue);
        }
    }

    public boolean isNotNull() {
        return notNull;
    }

}

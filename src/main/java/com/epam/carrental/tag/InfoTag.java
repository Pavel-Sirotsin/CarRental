package com.epam.carrental.tag;

import com.epam.carrental.tag.util.TagBodyParser;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.Map;

public class InfoTag extends BodyTagSupport {
    private static final Logger logger = LogManager.getFormatterLogger(InfoTag.class);

    private String language;

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int doStartTag() {
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doAfterBody() throws JspException {
        logger.traceEntry("- try to get params for tag body.");
        BodyContent content = this.getBodyContent();
        JspWriter out = content.getEnclosingWriter();
        Map<String, String> params = TagBodyParser.parse(content.getString());
        logger.debug("Map params: %d", params.size());

        try {
            for (Map.Entry<String, String> set : params.entrySet()) {
                String key = set.getKey();
                String parameter = set.getValue();
                String result = TagActionMapper.getTagAction(key).execute(language, parameter);
                out.write(result);
            }
        } catch (IOException | ServiceException ex) {
            logger.throwing(Level.ERROR, ex);

            throw new JspException(ex);
        }
        logger.traceExit(" with configuration: %s", "SKIP_BODY");
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}

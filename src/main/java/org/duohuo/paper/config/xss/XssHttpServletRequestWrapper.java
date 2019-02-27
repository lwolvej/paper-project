package org.duohuo.paper.config.xss;

import org.duohuo.paper.exceptions.XssException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lwolvej
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final Whitelist WHITELIST = new Whitelist();

    private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);

    private HttpServletRequest request;

    XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getParameter(String name) {
        if ("content".equals(name) || name.endsWith("WithHtml")) {
            return super.getParameter(name);
        }
        name = clean(name);
        String value = super.getParameter(name);
        if (!StringUtils.isEmpty(value)) {
            value = clean(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        if (arr != null) {
            int len = arr.length;
            for (int i = 0; i < len; i++) {
                arr[i] = clean(arr[i]);
            }
        }
        return arr;
    }


    @Override
    public String getHeader(String name) {
        name = clean(name);
        String value = super.getHeader(name);
        if (!StringUtils.isEmpty(value)) {
            value = clean(value);
        }
        return value;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        String result = inputHandlers(super.getInputStream());
        return new WrappedServletInputStream(new ByteArrayInputStream(result.getBytes()));
    }

    @Override
    public Map getParameterMap() {
        Map<String, String[]> map = super.getParameterMap();
        Map<String, String> resultMap = new HashMap<>();
        Iterator entries = map.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                //这里for改为了for-each
                for (String value1 : values) {
                    value = value1 + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            resultMap.put(name, clean(value).trim());
        }
        return resultMap;
    }

    private String inputHandlers(ServletInputStream servletInputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(servletInputStream, Charset.forName("UTF-8")));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            //转换异常
            throw new XssException(e.getMessage());
        } finally {
            if (servletInputStream != null) {
                try {
                    servletInputStream.close();
                } catch (IOException ignored) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return clean(stringBuilder.toString());
    }

    /**
     * Use Jsoup clean content to define xss fight!
     *
     * @param content origin content
     * @return content cleaned
     */
    private String clean(String content) {
        return Jsoup.clean(content, "", WHITELIST, OUTPUT_SETTINGS);
    }

    private class WrappedServletInputStream extends ServletInputStream {
        private InputStream inputStream;

        WrappedServletInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
    }
}

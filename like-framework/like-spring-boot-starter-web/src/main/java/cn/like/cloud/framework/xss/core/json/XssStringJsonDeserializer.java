package cn.like.cloud.framework.xss.core.json;

import cn.like.cloud.framework.xss.core.clean.XssCleaner;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * XSS 过滤 jackson 反序列化器。
 * 在反序列化的过程中，会对字符串进行 XSS 过滤。
 */
@Slf4j
@AllArgsConstructor
public class XssStringJsonDeserializer extends StringDeserializer {

    private final XssCleaner xssCleaner;

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.hasToken(JsonToken.VALUE_STRING)) {
            return xssCleaner.clean(p.getText());
        }
        JsonToken token = p.currentToken();
        if (token == JsonToken.START_ARRAY) {
            return _deserializeFromArray(p, ctxt);
        }
        if (token == JsonToken.VALUE_EMBEDDED_OBJECT) {
            Object ob = p.getEmbeddedObject();
            if (ob == null) {
                return null;
            }
            if (ob instanceof byte[]) {
                return ctxt.getBase64Variant().encode((byte[]) ob, false);
            }
            return ob.toString();
        }
        if (token == JsonToken.START_OBJECT) {
            return ctxt.extractScalarFromObject(p, this, _valueClass);
        }

        if (token.isScalarValue()) {
            String text = p.getValueAsString();
            return xssCleaner.clean(text);
        }
        return (String) ctxt.handleUnexpectedToken(_valueClass, p);
    }
}


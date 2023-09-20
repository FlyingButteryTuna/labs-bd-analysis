package org.labs.lab1.services;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import java.io.File;
import java.io.IOException;

@Data
public class XmlSerDes<T> {
    private final XmlMapper xmlMapper = new XmlMapper();
    private final Class<T> type;
    public XmlSerDes(Class<T> type){
        this.type = type;
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    public T deserialize(File file) throws IOException {
        return xmlMapper.readValue(file, type);
    }

    public void serialize(String filePath, T obj) throws IOException {
        xmlMapper.writeValue(new File(filePath), obj);
    }
}

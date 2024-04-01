package ru.job4j.io.serialization;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

@XmlRootElement(name = "myExample")
@XmlAccessorType(XmlAccessType.FIELD)
public class MyExample {
    @XmlAttribute
    private boolean flag;
    @XmlAttribute
    private int number;
    @XmlAttribute
    private String str;
    @XmlElement
    private Person person;
    @XmlElementWrapper(name = "contacts")
    @XmlElement(name = "contact")
    private Contact[] contacts;

    public MyExample() {

    }

    public MyExample(boolean flag, int number, String str, Person person, Contact... contacts) {
        this.flag = flag;
        this.number = number;
        this.str = str;
        this.person = person;
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "MyExample{"
                + "flag=" + flag
                + ", number=" + number
                + ", str=" + str
                + ", Person=" + person
                + ", contacts=" + Arrays.toString(contacts)
                + '}';
    }

    public static void main(String[] args) throws Exception {
        MyExample example = new MyExample(true, 37, "string value",
                new Person(true, 31, new Contact("77-333"), "first", "second"),
                new Contact("12-123"), new Contact("34-567"));
        JAXBContext context = JAXBContext.newInstance(MyExample.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        String xml;
        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(example, writer);
            xml = writer.getBuffer().toString();
            System.out.println(xml);
        }
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (StringReader reader = new StringReader(xml)) {
            MyExample result = (MyExample) unmarshaller.unmarshal(reader);
            System.out.println(result);
        }
    }
}

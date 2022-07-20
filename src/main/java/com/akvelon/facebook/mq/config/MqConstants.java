package com.akvelon.facebook.mq.config;

public class MqConstants {
    public final static String DLQ_EXCHANGE_NAME = "deadLetterExchange";
    public final static String DLQ_ROUTING_KEY = "deadLetter";
    public static final String FILES_EXCHANGE_NAME = "files_topic";
    public static final String FILES_BINDING_KEY = "files.#";

    public final static String PNG_ROUTING_KEY = "files.images.png";
    public final static String JPG_ROUTING_KEY = "files.images.jpg";
    public final static String PDF_ROUTING_KEY = "files.documents.pdf";
    public final static String HTML_ROUTING_KEY = "files.documents.html";

    public final static String DLQ_NAME = "deadLetterQueue";

}

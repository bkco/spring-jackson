package co.bk.task.jackson.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.common.base.Strings;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

public class ObjectMapperUtil {

    // TODO msecs https://stackoverflow.com/questions/42210257/java-util-date-to-string-using-datetimeformatter
  public static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_LOCAL_DATE_TIME).appendLiteral("Z").toFormatter();

  public static final ZoneId ZONEID_EUROPE_BERLIN = ZoneId.of("Europe/Berlin");

  public static ObjectMapper getDefaultObjectMapper() {

     //DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STRING);

    // Override the default ObjectMapper auto-configured by SpringBoot. Background info at:  https://github.com/FasterXML/jackson-modules-java8
    // Add JSR-310 module. It is a datatype module to make Jackson recognize Java 8 Date & Time API data types
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    //javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
    //javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
      //javaTimeModule.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer(FORMATTER));
      javaTimeModule.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
    /*
     * Airlift customised gave crap result (didn't output json). Use customised objectmapper version below.
     */
    //ObjectMapper objectMapper = new ObjectMapperProvider().get();
    //objectMapper.registerModule(javaTimeModule);
    //objectMapper.enable(new MapperFeature[]{MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES});

    ObjectMapper objectMapper = new ObjectMapper(new JsonFactory())
      .registerModules(
        javaTimeModule,
        new Jdk8Module(),
        new ParameterNamesModule()
      );

    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(FAIL_ON_EMPTY_BEANS);

    objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    //objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS); // TODO review with Money
    objectMapper.enable(new MapperFeature[]{MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES});

    return objectMapper;
  }

  public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      gen.writeString(FORMATTER.format(value));
    }
  }

  public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
      return LocalDateTime.parse(jsonParser.getValueAsString(), FORMATTER);
    }
  }

//  public static class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {
//
//      private final DateTimeFormatter dateTimeFormatter;
//
//      public OffsetDateTimeSerializer(final DateTimeFormatter dateTimeFormatter) {
//          this.dateTimeFormatter = dateTimeFormatter;
//      }
//
//      @Override
//      public void serialize(final OffsetDateTime value, final JsonGenerator gen, final SerializerProvider serializers)
//          throws IOException {
//          gen.writeString(dateTimeFormatter.format(value));
//      }
//  }

    public static class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {


      //java.time.format.DateTimeParseException: Text '2015-05-28T14:07:17Z' could not be parsed: Unable to obtain OffsetDateTime from TemporalAccessor: {},
      //   ISO resolved to 2015-05-28T14:07:17 of type java.time.format.Parse
      // Caused by: java.time.DateTimeException: Unable to obtain OffsetDateTime from TemporalAccessor: {},ISO resolved to 2015-05-28T14:07:17 of type java.time.format.Parsed

      // NOTE: null JSON input for a field will not trigger this serializer. An empty "" string will trigger it.
        @Override
        public OffsetDateTime deserialize(final JsonParser p, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

            //adds +01:00 String dateTimeValue = (p.getValueAsString() == "") ? OffsetDateTime.ofInstant(Instant.EPOCH, ZONEID_EUROPE_BERLIN).toString() : p.getValueAsString();
          Instant instant = Instant.EPOCH;
          //if (!Strings.isNullOrEmpty(p.getValueAsString())) {
          if (isValid(p.getValueAsString())) {
            //String dateTimeValue = (p.getValueAsString() == "") ? OffsetDateTime.ofInstant(Instant.EPOCH, ZONEID_EUROPE_BERLIN).toString() : p.getValueAsString();

            instant = Instant.parse(p.getValueAsString()); // Instant instant = Instant.parse("2017-08-02T06:05:30.000Z");
          }
//          String dateTimeValue = (p.getValueAsString() == "") ? OffsetDateTime.ofInstant(Instant.EPOCH, ZONEID_EUROPE_BERLIN).toString() : p.getValueAsString();

            //Instant instant = Instant.parse(dateTimeValue); // Instant instant = Instant.parse("2017-08-02T06:05:30.000Z");

            //return OffsetDateTime.parse(dateTimeValue, FORMATTER);
            //return OffsetDateTime.parse(p.getValueAsString(), FORMATTER);
            return DateTimeMapperUtil.mapToOffsetDateTime(instant);
        }
    }

// https://stackoverflow.com/questions/2149680/regex-date-format-validation-on-java
  public static boolean isValid(String text) {
//    if (text == null || !text.matches("\\d{4}-[01]\\d-[0-3]\\d"))
//    if (text == null)
//      return false;

    try {
      //format.get().parse(text);
      OffsetDateTime.parse(text, FORMATTER);
      return true;
    } catch (DateTimeParseException ex) {
      return false;
    }

  }


}

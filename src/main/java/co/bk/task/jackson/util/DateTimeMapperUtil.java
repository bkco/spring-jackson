package co.bk.task.jackson.util;

//import javax.annotation.Nullable;
import java.time.*;
import java.util.Optional;

public class DateTimeMapperUtil {

    private DateTimeMapperUtil() {
    }

    //@Nullable
    public static Instant mapToInstant(OffsetDateTime timestamp) {
        return Optional
            .ofNullable(timestamp)
            .map(OffsetDateTime::toInstant)
            .orElse(null);
    }

    //@Nullable
    public static OffsetDateTime mapToOffsetDateTime(Instant input) {
        return Optional
            .ofNullable(input)
            .map(timestamp -> timestamp.atOffset(ZoneOffset.UTC))
            .orElse(null);
    }

    //@Nullable
    //public static OffsetDateTime mapToOffsetDateTime(@Nullable LocalDate localDate) {
    public static OffsetDateTime mapToOffsetDateTime(LocalDate localDate) {
        return Optional
            .ofNullable(localDate)
            .map(ld -> ld.atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime())
            .orElse(null);
    }

    //@Nullable
    //public static LocalDate mapToLocalDate(@Nullable OffsetDateTime value) {
    public static LocalDate mapToLocalDate(OffsetDateTime value) {
        return Optional
            .ofNullable(value)
            .map(OffsetDateTime::toLocalDate)
            .orElse(null);
    }
}

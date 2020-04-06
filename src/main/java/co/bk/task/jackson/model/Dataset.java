package co.bk.task.jackson.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * Command model for DataItem.
 */
@Builder(toBuilder = true)
@Value
@JsonDeserialize(builder = Dataset.DatasetBuilder.class)
@SuppressWarnings({"PMD.VariableNamingConventions"})
public class Dataset implements Serializable {

    private static final long serialVersionUID = 8631743355874860880L;

    private String group;

    private String artifact;

    private String version;

    private Boolean accessGranted;

//    group:
//    type: string
//    example: 'dwh'
//    artifact:
//    type: string
//    example: 'f_salesorder_position'
//    version:
//    type: string
//    format: date-time
//    example: '2019-03-19T00:00:00Z'
//    access_granted:
//    type: boolean

    @JsonPOJOBuilder(withPrefix = "")
    public static final class DatasetBuilder {
    }
}

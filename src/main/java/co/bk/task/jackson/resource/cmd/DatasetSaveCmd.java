package co.bk.task.jackson.resource.cmd;

import com.google.common.base.Strings;
import org.springframework.util.CollectionUtils;

import co.bk.task.jackson.model.Dataset;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Command pattern wrapping JSON data submitted to REST endpoint.
 *
 * Example CURL:
 *   curl -X POST --header "Content-type: application/json" --header "Accept: application/json"  --data '{"datasets": [{"group": "","artifact": "", "version": ""}], "duration": 4, "duration_unit": "hours", "utc_date": "2015-05-28T14:07:17Z"}' http://localhost:8080/dataset
 */
public class DatasetSaveCmd {

    private List<Dataset> datasets;

    private Integer duration;

    private String duration_unit;

    private OffsetDateTime utc_date;

    //    /**
//     * Check integrity of data submitted.
//     */
//    public void isValid() {
//
//        if (this.getDuration() == null || !isValidDurationUnit(getDuration_unit())
//          || CollectionUtils.isEmpty(this.getDatasets()) ) {
//            throw new AccessApiServiceException(AccessApiServiceException.ErrorCode.ACCESS_REQUEST_INCOMPLETE);
//        }
//    }
//
//    private boolean isValidDurationUnit(String unit) {
//        // unspecified duration unit is allowed
//        boolean isValid = true;
//        if (!Strings.isNullOrEmpty(unit)) {
//            isValid = DurationUnit.contains(unit);
//        }
//        return isValid;
//    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<Dataset> datasets) {
        this.datasets = datasets;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDuration_unit() {
        return duration_unit;
    }

    public void setDuration_unit(String duration_unit) {
        this.duration_unit = duration_unit;
    }

    public OffsetDateTime getUtc_date() {
        return utc_date;
    }

    public void setUtc_date(OffsetDateTime utc_date) {
        this.utc_date = utc_date;
    }

}
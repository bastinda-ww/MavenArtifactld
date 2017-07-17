package ru.sbt.qa.model;

        import java.util.List;
        import com.fasterxml.jackson.annotation.JsonInclude;
        import com.fasterxml.jackson.annotation.JsonProperty;
        import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "result",
        "expression",
        "errorDescription",
        "success"
})
public class CalcResponse {

    @JsonProperty("result")
    private Float result;
    @JsonProperty("expression")
    private List<Object> expression = null;
    @JsonProperty("errorDescription")
    private String errorDescription;
    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("result")
    public double getResult() {
        return result;
    }

    @JsonProperty("expression")
    public List<Object> getExpression() {
        return expression;
    }

    @JsonProperty("errorDescription")
    public String getErrorDescription() {
        return errorDescription;
    }

    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

}
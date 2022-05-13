
package com.rzeznicki.twitterapp.Entities;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "newest_id",
    "oldest_id",
    "result_count",
    "next_token"
})
@Generated("jsonschema2pojo")
public class Meta {

    @JsonProperty("newest_id")
    private String newestId;
    @JsonProperty("oldest_id")
    private String oldestId;
    @JsonProperty("result_count")
    private Integer resultCount;
    @JsonProperty("next_token")
    private String nextToken;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("newest_id")
    public String getNewestId() {
        return newestId;
    }

    @JsonProperty("newest_id")
    public void setNewestId(String newestId) {
        this.newestId = newestId;
    }

    @JsonProperty("oldest_id")
    public String getOldestId() {
        return oldestId;
    }

    @JsonProperty("oldest_id")
    public void setOldestId(String oldestId) {
        this.oldestId = oldestId;
    }

    @JsonProperty("result_count")
    public Integer getResultCount() {
        return resultCount;
    }

    @JsonProperty("result_count")
    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    @JsonProperty("next_token")
    public String getNextToken() {
        return nextToken;
    }

    @JsonProperty("next_token")
    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Meta.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("newestId");
        sb.append('=');
        sb.append(((this.newestId == null)?"<null>":this.newestId));
        sb.append(',');
        sb.append("oldestId");
        sb.append('=');
        sb.append(((this.oldestId == null)?"<null>":this.oldestId));
        sb.append(',');
        sb.append("resultCount");
        sb.append('=');
        sb.append(((this.resultCount == null)?"<null>":this.resultCount));
        sb.append(',');
        sb.append("nextToken");
        sb.append('=');
        sb.append(((this.nextToken == null)?"<null>":this.nextToken));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}

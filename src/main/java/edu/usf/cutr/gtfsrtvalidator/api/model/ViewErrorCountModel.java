/*
 * Copyright (C) 2011 Nipuna Gunathilake.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.usf.cutr.gtfsrtvalidator.api.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@Entity
@NamedNativeQuery(name = "ErrorCountByrtfeedID",
    query = "SELECT ? AS rtFeedID, Error.errorID AS id, Error.title, " +
              "Error.severity, errorCount.totalCount, " +
              "errorCount.IterationTimestamp AS lastOccurrence " +
            "FROM Error " +
            "INNER JOIN " +
              "(SELECT errorID, count(*) AS totalCount, " +
                "MAX(IterationTimestamp)  AS IterationTimestamp " +
              "FROM MessageLog " +
              "INNER JOIN  " +
                "(SELECT IterationID, IterationTimestamp " +
                "FROM GtfsRtFeedIteration " +
                "WHERE rtFeedID = ?) GtfsRtFeedIDIteration " +
              "ON MessageLog.iterationID = GtfsRtFeedIDIteration.IterationID " +
            "GROUP BY errorID) errorCount " +
            "ON Error.errorID = errorCount.errorID ",
    resultClass = ViewErrorCountModel.class)
public class ViewErrorCountModel implements Serializable {

    @Column(name="rtFeedID")
    private int gtfsRtId;
    @Column(name="lastOccurrence")
    private long lastOccurrence;
    @Column(name="totalCount")
    private int count; // total number of error or warning count
    @Id
    @Column(name = "id")
    private String id; // error or warning ID
    @Column(name = "severity")
    private String severity;
    @Column (name = "title")
    private String title;
    @Transient
    private String formattedTimestamp;
    @Transient
    private String timeZone;

    public int getGtfsRtId() {
        return gtfsRtId;
    }

    public void setGtfsRtId(int gtfsRtId) {
        this.gtfsRtId = gtfsRtId;
    }

    public long getLastOccurrence() {
        return lastOccurrence;
    }

    public void setLastOccurrence(long lastOccurrence) {
        this.lastOccurrence = lastOccurrence;
    }

    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFormattedTimestamp(String formattedTimestamp) {
        this.formattedTimestamp = formattedTimestamp;
    }
    public String getFormattedTimestamp() {
        return formattedTimestamp;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}

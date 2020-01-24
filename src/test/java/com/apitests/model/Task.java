package com.apitests.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Task {

    private String content;
    @SerializedName("project_id")
    private Integer projectId;
    @SerializedName("section_id")
    private Integer sectionId;
    private Integer parent;
    private Integer order;
    @SerializedName("labelIds")
    private List<Integer> labelIds;
    private Integer priority;
    @SerializedName("due_string")
    private String dueString;
    @SerializedName("due_date")
    private String dueDate;
    @SerializedName("due_datetime")
    private String dueDatetime;
    @SerializedName("due_lang")
    private String dueLang;

    public Task() {
    }

    public Task(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<Integer> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<Integer> labelIds) {
        this.labelIds = labelIds;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDueString() {
        return dueString;
    }

    public void setDueString(String dueString) {
        this.dueString = dueString;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDatetime() {
        return dueDatetime;
    }

    public void setDueDatetime(String dueDatetime) {
        this.dueDatetime = dueDatetime;
    }

    public String getDueLang() {
        return dueLang;
    }

    public void setDueLang(String dueLang) {
        this.dueLang = dueLang;
    }
}

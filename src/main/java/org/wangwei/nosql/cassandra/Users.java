package org.wangwei.nosql.cassandra;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Users {
    @PrimaryKey
    private int userid;
    private Date create_time;
    private Set<String> emails;
    private String first_name;
    private String last_name;
    private Map<Date, String> todo;
    private List<Integer> top_scores;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Map<Date, String> getTodo() {
        return todo;
    }

    public void setTodo(Map<Date, String> todo) {
        this.todo = todo;
    }

    public List<Integer> getTop_scores() {
        return top_scores;
    }

    public void setTop_scores(List<Integer> top_scores) {
        this.top_scores = top_scores;
    }

}

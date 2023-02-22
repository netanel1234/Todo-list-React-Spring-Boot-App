package com.netanel.todolistserver.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userid;
    private String task;
    private boolean completed;

    public Item() {
    }

    public Item(Long userid, String task) {
        this.userid = userid;
        this.task = task;
        this.completed = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return completed == item.completed && Objects.equals(id, item.id) && Objects.equals(userid, item.userid) && Objects.equals(task, item.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, task, completed);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", userid=" + userid +
                ", task='" + task + '\'' +
                ", completed=" + completed +
                '}';
    }
}

package com.egor.tasks.entity;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 300, nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Column(name = "priority", nullable = false)
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "assigned_id")
    private User assigned;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // for correctly execution of code, not error-prone
    private List<Comments> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                status == task.status && priority == task.priority &&
                Objects.equals(author, task.author) &&
                Objects.equals(assigned, task.assigned) &&
                Objects.equals(comments, task.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, priority, author, assigned, comments);
    }
}

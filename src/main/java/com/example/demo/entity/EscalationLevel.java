
package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(
        name = "escalation_levels",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"escalation_policy_id", "level"}
                )
        }
)
public class EscalationLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer timeoutMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escalation_policy_id", nullable = false)
    private EscalationPolicy escalationPolicy;

    public EscalationLevel() {
    }

    public EscalationLevel(Integer level, Integer timeoutMinutes, User user) {
        this.level = level;
        this.timeoutMinutes = timeoutMinutes;
        this.user = user;
    }


    public UUID getId() {
        return id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getTimeoutMinutes() {
        return timeoutMinutes;
    }

    public void setTimeoutMinutes(Integer timeoutMinutes) {
        this.timeoutMinutes = timeoutMinutes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EscalationPolicy getEscalationPolicy() {
        return escalationPolicy;
    }

    public void setEscalationPolicy(EscalationPolicy escalationPolicy) {
        this.escalationPolicy = escalationPolicy;
    }
}

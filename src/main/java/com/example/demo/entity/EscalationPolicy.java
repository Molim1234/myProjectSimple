
package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "escalation_policies")
public class EscalationPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false, unique = true)
    private Team team;

    @OneToMany(
            mappedBy = "escalationPolicy",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EscalationLevel> levels = new ArrayList<>();

    public EscalationPolicy() {
    }

    public EscalationPolicy(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public void addLevel(EscalationLevel level) {
        levels.add(level);
        level.setEscalationPolicy(this);
    }

    public void removeLevel(EscalationLevel level) {
        levels.remove(level);
        level.setEscalationPolicy(null);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<EscalationLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<EscalationLevel> levels) {
        this.levels = levels;
    }
}


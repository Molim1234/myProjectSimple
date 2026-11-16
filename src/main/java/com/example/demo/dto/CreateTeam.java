
package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateTeam {

    @NotBlank(message = "Team name is required")
    @Size(min = 2, max = 100, message = "Team name must contain between 2 and 100 characters")
    private String name;

    public CreateTeam() {
    }

    public CreateTeam(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

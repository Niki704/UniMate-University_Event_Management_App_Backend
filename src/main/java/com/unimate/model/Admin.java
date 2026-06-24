package com.unimate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Admin extends User {
    // No role-specific attributes beyond base User at this time.
    // See SCHEMA_DECISIONS.md Section 5 — Admin table.
}

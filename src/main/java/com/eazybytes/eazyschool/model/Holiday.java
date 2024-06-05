package com.eazybytes.eazyschool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Table(name = "holidays")
public class Holiday extends BaseEntity {

    @Id
    @Column(name = "holiday_day")
    private String day;

    @Column(name = "holiday_reason")
    private String reason;

    @Enumerated(value = EnumType.STRING)  // Converts the enum value to a string before storing it in the database.
    @Column(name = "holiday_type")
    private Type type;

    public enum Type{
        FESTIVAL, FEDERAL
    }

}

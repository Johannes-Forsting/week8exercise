package com.example.week8exercise.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NameResponse{
    public String name;
    public String gender;
    public double genderPropability;
    public int age;
    public int ageCount;
    public String country;
    public double countryPropability;

    public void setGenderPropability(double p){
        genderPropability = p * 100;
    }

    public void setCountryPropability(double p){
        countryPropability = p * 100;
    }
}

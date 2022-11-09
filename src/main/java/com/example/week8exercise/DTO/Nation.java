package com.example.week8exercise.DTO;

import com.example.week8exercise.DTO.Country;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Nation {
    List<Country> country;
    String name;
}

package com.example.demo.Models;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hrs {

    @ApiModelProperty(notes = "This helps to know whether the time indicated is the OPENING time or CLOSING time ")
    private String type;

    @ApiModelProperty(notes = "This is the Value of time:" +
            "An Integer with value within the range of 0 to 86399 which translate to 12:00:00PM to 11:59.59PM")
    private Integer value;


    @Override
    public String toString() {
        return "Time{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}


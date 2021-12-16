package com.example.demo.Payload;


import com.example.demo.Models.Hrs;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvidedHrs {

    @ApiModelProperty(notes = "Opening and closing hour(s) on Mondays")
    private ArrayList<Hrs> Monday;

    @ApiModelProperty(notes = "Opening and closing hour(s) on Tuesdays")
    private ArrayList<Hrs> Tuesday;

    @ApiModelProperty(notes = "Opening and closing hour(s) on Wednesdays")
    private ArrayList<Hrs> Wednesday;

    @ApiModelProperty(notes = "Opening and closing hour(s) on Thursdays")
    private ArrayList<Hrs> Thursday;

    @ApiModelProperty(notes = "Opening and closing hour(s) on Fridays")
    private ArrayList<Hrs> Friday;

    @ApiModelProperty(notes = "Opening and closing hour(s) on Saturdays")
    private ArrayList<Hrs> Saturday;

    @ApiModelProperty(notes = "Opening and closing hour(s) on Sundays")
    private ArrayList<Hrs> Sunday;

}
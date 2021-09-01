
package com.example.njunji.models;

import lombok.Data;

import javax.annotation.Generated;
@Data
@Generated("jsonschema2pojo")
public class MainRequest {

    public String countryCode;
    public String credentials;
    public String function;
    public Payload payload;
}

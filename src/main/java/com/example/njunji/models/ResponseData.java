
package com.example.njunji.models;

import lombok.Data;

import java.util.List;
import javax.annotation.Generated;
@Data
@Generated("jsonschema2pojo")
public class ResponseData {

    public AuthStatus authStatus;
    public List<Result> results = null;
}

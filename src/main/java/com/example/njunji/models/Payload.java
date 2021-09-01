
package com.example.njunji.models;

import lombok.Data;

import java.util.List;
import javax.annotation.Generated;

@Data
@Generated("jsonschema2pojo")
public class Payload {

    public Credentials credentials;
    public List<Packet> packet = null;

}

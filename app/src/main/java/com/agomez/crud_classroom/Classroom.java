package com.agomez.crud_classroom;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Classroom {

    int id, cedula;
    String username, password, email;
    role role;

    public Classroom(JSONObject jsonObject) throws JSONException {
        this.id = Integer.parseInt(jsonObject.getString("id"));
        this.cedula = Integer.parseInt(jsonObject.getString("cedula"));
        this.username = jsonObject.getString("username");
        this.password = jsonObject.getString("password");
        this.email = jsonObject.getString("email");
        this.role = role.valueOf(jsonObject.getString("role"));
    }
}

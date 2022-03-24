package br.edu.utfpr.pb.pw26s.server.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String username;

    private String displayName;

    private String password;

}

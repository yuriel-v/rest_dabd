package dabd.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "test")
public class SampleUser implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id  // isso indica que esse atributo é uma PK, ou parte dela
    private String cpf;

    @Column
    private String name;

    public SampleUser(String cpf, String name) {
        this.cpf = cpf;
        this.name = name;
    }

    public SampleUser() {}
    

    @Override
    public String toString() {
        return String.format(
            "CPF: %s | Nome: %s ",
            this.cpf, name
            // o "this" (se refere ao objeto atual) é opcional, mas gosto de explicitar
        );
    }
}
